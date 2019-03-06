package org.yesworkflow.save;

import org.yesworkflow.model.*;
import org.yesworkflow.save.data.*;
import org.yesworkflow.save.response.SaveResponse;
import org.yesworkflow.save.response.UpdateResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.function.BiFunction;

public class HttpSaver implements Saver
{
    private final String hashAlgorithm = "SHA-256";

    IYwSerializer ywSerializer;
    Hash hasher;
    IClient client = null;
    Integer workflowId = null;
    String baseUrl = "http://localhost:8000/";
    String username = null;
    String title = null;
    String description = null;
    String graph = "";
    Model model = null;
    String modelChecksum = "";
    String modelFacts = "";
    List<String> tags = null;
    List<ScriptDto> scripts = null;
    List<DataDto> data = null;
    List<ChannelDto> channels = null;
    List<PortDto> ports = null;
    List<ProgramBlockDto> programBlocks = null;

    public HttpSaver(IYwSerializer ywSerializer) throws YwSaveException
    {
        this.ywSerializer = ywSerializer;
        try
        {
            hasher = new Hash(hashAlgorithm);
        }
        catch (NoSuchAlgorithmException e)
        { // this case should never occur
            throw new YwSaveException("Invalid internal hashing algorithm " + hashAlgorithm);
        }
        tags = new ArrayList<>();
        scripts = new ArrayList<>();
        data = new ArrayList<>();
        channels = new ArrayList<>();
        ports = new ArrayList<>();
        programBlocks = new ArrayList<>();
        resources = new ArrayList<>();
        uriVariables = new ArrayList<>();
        uriVariableValues = new ArrayList<>();
    }

    public Saver build(Run run, String graph, List<String> sourceCodeList, List<String> sourcePaths)
    {
        this.run = run;
        this.graph = graph;
        this.scripts = hashAndMapSourcePaths(sourcePaths, sourceCodeList);
        return this;
    }

    public Saver save() throws Exception
    {
        client = new YwClient(baseUrl, ywSerializer);

        // TODO:: make model string representation and take checksum.
        modelChecksum = scripts.get(0).checksum;
        flattenModel(run.model);
        resources = mapCustomObjectList(run.resources, ResourceDto::new);
        uriVariables = mapCustomObjectList(run.uriVariables, UriVariableDto::new);
        uriVariableValues = mapCustomObjectList(run.uriVariableValues, UriVariableValueDto::new);

        RunDto.Builder builder = new RunDto.Builder(username, "modelV", modelChecksum, graph, scripts)
                                            .setChannels(channels)
                                            .setData(data)
                                            .setPorts(ports)
                                            .setProgramBlocks(programBlocks)
                                            .setResources(resources)
                                            .setUriVariables(uriVariables)
                                            .setUriVariableValues(uriVariableValues);

        if(title != null)
            builder.setTitle(title);
        if(description != null)
            builder.setDescription(description);
        if(!tags.isEmpty())
            builder.setTags(tags);

        RunDto run = builder.build();

        String message = "Succesfully uploaded this run to %s.\nWorkflow ID: %d\nVersion: %d\nRun Number: %d";
        if(workflowId == null) {
            SaveResponse response = client.SaveRun(run);
            message = String.format(message,
                                    baseUrl,
                                    response.ResponseObject.workflowId,
                                    response.ResponseObject.versionNumber,
                                    response.ResponseObject.runNumber);
        }
        else {
            UpdateResponse response = client.UpdateWorkflow(workflowId, run);
            String newVersionMessage = "\nChanges to the workflow script created a new version of your workflow on the server.";
            message = String.format(message,
                                    baseUrl,
                                    response.ResponseObject.workflowId,
                                    response.ResponseObject.versionNumber,
                                    response.ResponseObject.runNumber);

            if(response.ResponseObject.newVersion)
                message = message + newVersionMessage;
        }

        System.out.println(message);

        return this;
    }
    private List<ScriptDto> hashAndMapSourcePaths(List<String> sourcePaths, List<String> sourceCodeList)
    {
        List<ScriptDto> scriptDtoList = new ArrayList<>();

        for (int i = 0; i < sourceCodeList.size(); i++)
        {
            try
            {
                String checksum = hasher.getHash(sourcePaths.get(i));
                ScriptDto scriptDto = new ScriptDto(sourcePaths.get(i), sourceCodeList.get(i), checksum);
                scriptDtoList.add(scriptDto);
            } catch(IOException e)
            { // This case should never occur, the values are validated earlier int the program
            }
        }
        return scriptDtoList;
    }
    private void flattenModel(Model model)
    {
        data.addAll(mapData(model.data));
        recurseProgramFlatten(model.workflow, null);
    }

    private void recurseProgramFlatten(Program program, Long parentId)
    {
        ProgramBlockDto.Builder programBuilder = new ProgramBlockDto.Builder(program);

        if(parentId != null)
            programBuilder.setInProgramBlock(parentId);

        programBlocks.add(programBuilder.build());
        data.addAll(mapData(program.data, program.id));
        ports.addAll(mapPorts(this::inPortBuilder, program.inPorts, program.id));
        ports.addAll(mapPorts(this::outPortBuilder, program.outPorts, program.id));
        channels.addAll(mapChannels(program.channels));

        // program.programs always is at least an array of size 0,
        // so if no children exist, this acts as the base case
       for(Program childProgram : program.programs)
           recurseProgramFlatten(childProgram, program.id);
    }

    private List<ChannelDto> mapChannels(Channel[] channelArray)
    {
        List<ChannelDto> dtos = new ArrayList<>();
        for(Channel c : channelArray)
        {
            ChannelDto channelDto = new ChannelDto(c);
            channelDto.setInflow(c.sourcePort.flowAnnotation.keyword.toLowerCase().startsWith("@in"));
            channelDto.setOutlfow(c.sourcePort.flowAnnotation.keyword.toLowerCase().startsWith("@out"));
            dtos.add(channelDto);
        }
        return dtos;
    }

    private List<PortDto> mapPorts(BiFunction<Port, Long, PortDto.Builder> portBuilderFunc, Port[] portArray, Long programId)
    {
        List<PortDto> dtoList = new ArrayList<>();
        for(Port p : portArray)
        {
            PortDto.Builder builder = portBuilderFunc.apply(p, programId);

            String alias = p.flowAnnotation.alias();
            if(alias != null)
                builder.setAlias(alias);

            if(p.uriTemplate != null)
                builder.setUriTemplate(p.uriTemplate.toString());

            dtoList.add(builder.build());
        }
        return dtoList;
    }

    private PortDto.Builder inPortBuilder(Port port, Long programId)
    {
        return new PortDto.Builder(port, programId, true, false);
    }

    private PortDto.Builder outPortBuilder(Port port, Long programId)
    {
        return new PortDto.Builder(port, programId, false, true);
    }

    private List<DataDto> mapData(Data[] dataArray)
    {
        return mapData(dataArray, null);
    }

    private List<DataDto> mapData(Data[] dataArray, Long programId)
    {
        List<DataDto> dtoList = new ArrayList<>();
        for(Data d : dataArray)
        {
            DataDto.Builder builder = new DataDto.Builder(d);

            if(programId != null)
                builder.setInProgramBlock(programId);

            dtoList.add(builder.build());
        }
        return dtoList;
    }

    private <CustomObj, Obj> List<CustomObj> mapCustomObjectList(List<Obj> objectList, Function<Obj, CustomObj> customMapper)
    {
        List<CustomObj> customObjList = new ArrayList<>();
        for(Obj object : objectList)
        {
            customObjList.add(customMapper.apply(object));
        }
        return customObjList;
    }

    public Saver configure(Map<String, Object> config) throws Exception {
        if (config != null) {
            for (Map.Entry<String, Object> entry : config.entrySet()) {
                configure(entry.getKey(), entry.getValue());
            }
        }
        return this;
    }

    public Saver configure(String key, Object value) throws Exception {
        switch(key.toLowerCase())
        {
            case "serveraddress":
                baseUrl = formatUrl((String) value);
                break;
            case "username":
                username = (String) value;
                break;
            case "workflow":
                workflowId = Integer.parseInt((String) value);
                break;
            case "title":
                title = (String) value;
                break;
            case "description":
                description = (String) value;
                break;
            case "tags" :
                String valTags = (String) value;
                tags = new ArrayList<>(Arrays.asList(valTags.split("\\s*,\\s*")));
                break;
            default:
                break;
        }
        return this;
    }

    private String formatUrl(String url)
    {
        if(!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;

        if(!url.endsWith("/"))
            url = url + "/";

        return url;
    }
}

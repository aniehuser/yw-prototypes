package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RunDto {

    @SerializedName("username")
    public String username;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("model")
    public String model;
    @SerializedName("modelChecksum")
    public String modelChecksum;
    @SerializedName("graph")
    public String graph;
    @SerializedName("tags")
    public List<String> tags;
    @SerializedName("scripts")
    public List<ScriptDto> scripts;
    @SerializedName("files")
    public List<FileDto> files;
    @SerializedName("programBlock")
    public List<ProgramBlockDto> programBlocks;
    @SerializedName("data")
    public List<DataDto> data;
    @SerializedName("port")
    public List<PortDto> ports;
    @SerializedName("channel")
    public List<ChannelDto> channels;
    @SerializedName("uriVariable")
    public List<UriVariableDto> uriVariables;
    @SerializedName("resource")
    public List<ResourceDto> resources;
    @SerializedName("uriVariableValue")
    public List<UriVariableValueDto> uriVariableValues;

    public RunDto(String username,
                  String title,
                  String description,
                  String model,
                  String modelChecksum,
                  String graph,
                  List<String> tags,
                  List<ScriptDto> scripts,
                  List<FileDto> files,
                  List<ProgramBlockDto> programBlocks,
                  List<DataDto> data,
                  List<PortDto> ports,
                  List<ChannelDto> channels,
                  List<UriVariableDto> uriVariables,
                  List<ResourceDto> resources,
                  List<UriVariableValueDto> uriVariableValues)
    {
        this.username = username;
        this.title = title;
        this.description = description;
        this.model = model;
        this.modelChecksum = modelChecksum;
        this.graph = graph;
        this.tags = tags;
        this.scripts = scripts;
        this.files = files;
        this.programBlocks = programBlocks;
        this.data = data;
        this.ports = ports;
        this.channels = channels;
        this.uriVariables = uriVariables;
        this.resources = resources;
        this.uriVariableValues = uriVariableValues;
    }

    public static class Builder
    {
        public String username;
        public String title;
        public String description;
        public String model;
        public String modelChecksum;
        public String graph;
        public List<String> tags;
        public List<ScriptDto> scripts;
        public List<FileDto> files;
        public List<ProgramBlockDto> programBlocks;
        public List<DataDto> data;
        public List<PortDto> ports;
        public List<ChannelDto> channels;
        public List<UriVariableDto> uriVariables;
        public List<ResourceDto> resources;
        public List<UriVariableValueDto> uriVariableValues;

        public Builder(String username, String model, String modelChecksum, String graph, List<ScriptDto> scripts)
        {
            this.username = username;
            this.model = model;
            this.modelChecksum = modelChecksum;
            this.graph = graph;
            this.scripts = scripts;
        }

        public Builder setTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder setDescription(String description)
        {
            this.description = description;
            return this;
        }

        public Builder setTags(List<String> tags)
        {
            this.tags = tags;
            return this;
        }

        public Builder setFiles(List<FileDto> files)
        {
            this.files = files;
            return this;
        }

        public Builder setProgramBlocks(List<ProgramBlockDto> programBlocks)
        {
            this.programBlocks = programBlocks;
            return this;
        }

        public Builder setData(List<DataDto> data)
        {
            this.data = data;
            return this;
        }

        public Builder setPorts(List<PortDto> ports)
        {
            this.ports = ports;
            return this;
        }

        public Builder setChannels(List<ChannelDto> channels)
        {
            this.channels = channels;
            return this;
        }

        public Builder setUriVariables(List<UriVariableDto> uriVariables)
        {
            this.uriVariables = uriVariables;
            return this;
        }

        public Builder setResources(List<ResourceDto> resources)
        {
            this.resources = resources;
            return this;
        }
        public Builder setUriVariableValues(List<UriVariableValueDto> uriVariableValues)
        {
            this.uriVariableValues = uriVariableValues;
            return this;
        }

        public RunDto build()
        {
            return new RunDto(this);
        }
    }

    public RunDto(Builder builder)
    {
        this.username=builder.username;
        this.title=builder.title;
        this.description=builder.description;
        this.model=builder.model;
        this.modelChecksum =builder.modelChecksum;
        this.graph=builder.graph;
        this.tags=builder.tags;
        this.scripts=builder.scripts;
        this.files=builder.files;
        this.programBlocks=builder.programBlocks;
        this.data=builder.data;
        this.ports=builder.ports;
        this.channels=builder.channels;
        this.resources=builder.resources;
        this.uriVariables=builder.uriVariables;
        this.uriVariableValues=builder.uriVariableValues;
    }
}

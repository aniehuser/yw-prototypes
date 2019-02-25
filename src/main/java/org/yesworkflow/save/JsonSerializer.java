package org.yesworkflow.save;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonSerializer implements IYwSerializer
{

    private Gson gson;
    public JsonSerializer()
    {
        gson = new Gson();
    }

    public InputStream SerializeToInputStream(Object object)
    {
        return org.apache.commons.io.IOUtils.toInputStream(Serialize(object), StandardCharsets.UTF_8);
    }

    public String Serialize(Object object)
    {
        return gson.toJson(object);
    }

    public <T> T Deserialize(String json, Class<T> Dto)
    {
        T object = null;
        try
        {
            object = gson.fromJson(json, Dto);
        }
        catch (JsonParseException e)
        {
        }
        return object;
    }
}

package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;

public class URIVariableDto
{
    @SerializedName("id")
    public int id;
    @SerializedName("port")
    public int port;
    @SerializedName("name")
    public String name;

    public URIVariableDto(int id, int port, String name)
    {
        this.id = id;
        this.port = port;
        this.name = name;
    }
}

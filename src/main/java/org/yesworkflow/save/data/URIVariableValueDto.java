package org.yesworkflow.save.data;

import com.google.gson.annotations.SerializedName;

public class URIVariableValueDto
{
    @SerializedName("uriVariable")
    public int uriVariable;
    @SerializedName("resource")
    public int resource;
    @SerializedName("value")
    public String value;

    public URIVariableValueDto(int uriVariable, int resource, String value)
    {
        this.uriVariable = uriVariable;
        this.resource = resource;
        this.value = value;
    }
}

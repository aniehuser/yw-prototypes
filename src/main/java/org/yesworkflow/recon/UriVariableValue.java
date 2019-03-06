package org.yesworkflow.recon;

public class UriVariableValue
{
    public Integer uriVariable;
    public Long resource;
    public String value;

    public UriVariableValue(Integer uriVariable, Long resource, String value)
    {
        this.uriVariable = uriVariable;
        this.resource = resource;
        this.value = value;
    }
}

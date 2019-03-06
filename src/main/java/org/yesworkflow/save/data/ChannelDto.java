package org.yesworkflow.save.data;
import com.google.gson.annotations.SerializedName;
import org.yesworkflow.model.Channel;

public class ChannelDto
{
    @SerializedName("channelId")
    public Integer id;
    @SerializedName("outPort")
    public Integer outPort;
    @SerializedName("inPort")
    public Integer inPort;
    @SerializedName("data")
    public Long data;
    @SerializedName("isInflow")
    public Boolean isInflow;
    @SerializedName("isOutflow")
    public Boolean isOutflow;

    public ChannelDto(Integer id, Integer outPort, Integer inPort, Long data, Boolean isInflow, Boolean isOutflow) {
        this.id = id;
        this.outPort = outPort;
        this.inPort = inPort;
        this.data = data;
        this.isInflow = isInflow;
        this.isOutlfow = isOutlfow;
    }

    public ChannelDto(Channel channel)
    {
        this.id = channel.id;
        this.outPort = channel.sourcePort.id;
        this.inPort = channel.sinkPort.id;
        this.data = channel.data.id;
    }

    public ChannelDto setInflow(Boolean inflow)
    {
        isInflow = inflow;
        return this;
    }

    public ChannelDto setOutlfow(Boolean outflow)
    {
        isOutlfow = outflow;
        return this;
    }
}

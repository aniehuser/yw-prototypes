package org.yesworkflow.save.response;

import org.apache.http.HttpResponse;
import org.yesworkflow.exceptions.YwSaveException;
import org.yesworkflow.save.serialization.IYwSerializer;
import org.yesworkflow.save.data.PingDto;

public class PingResponse extends YwResponse<PingDto>
{
    @Override
    public YwResponse<PingDto> Build(HttpResponse response, IYwSerializer serializer)
            throws YwSaveException
    {
        this.build(response, serializer);
        this.ResponseObject = DeserializeResponseContent();
        return this;
    }

    @Override
    protected PingDto DeserializeResponseContent()
    {
        return this.serializer.Deserialize(this.ResponseBody, PingDto.class);
    }
}

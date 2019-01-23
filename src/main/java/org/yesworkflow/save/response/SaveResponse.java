package org.yesworkflow.save.response;

import org.apache.http.HttpResponse;
import org.yesworkflow.save.IYwSerializer;
import org.yesworkflow.save.data.RunDto;

public class SaveResponse extends YwResponse<RunDto>
{

    @Override
    public YwResponse<RunDto> Build(HttpResponse response, IYwSerializer serializer)
    {
        build(response, serializer);
        this.ResponseObject = DeserializeResponseContent();
        return this;
    }

    @Override
    protected RunDto DeserializeResponseContent()
    {
        return serializer.Deserialize(this.ResponseBody, RunDto.class);
    }
}

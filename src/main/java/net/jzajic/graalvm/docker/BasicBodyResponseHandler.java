package net.jzajic.graalvm.docker;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

public class BasicBodyResponseHandler implements ResponseHandler<String> {

	@Override
	public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
		final StatusLine statusLine = response.getStatusLine();
    final HttpEntity entity = response.getEntity();
    if (statusLine.getStatusCode() >= 300) {
        String errorResp = EntityUtils.toString(entity);
        throw new HttpResponseException(statusLine.getStatusCode(),
                statusLine.getReasonPhrase() + ": " + errorResp);
    }
    return entity == null ? null : handleEntity(entity);		
	}

	private String handleEntity(HttpEntity entity) throws ParseException, IOException {
		return EntityUtils.toString(entity);
	}

}

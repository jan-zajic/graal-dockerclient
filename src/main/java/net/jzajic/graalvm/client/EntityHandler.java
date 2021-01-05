package net.jzajic.graalvm.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;

@FunctionalInterface
public interface EntityHandler<T> {

	/**
   * Handle the response entity and transform it into the actual response
   * object.
   */
  public abstract T handleEntity(HttpEntity entity) throws IOException;
	
  default ResponseHandler<T> asResponseHandler() {
  	return new ResponseHandler<T>() {

			public T handleEntity(HttpEntity entity) throws IOException {
				return EntityHandler.this.handleEntity(entity);
			}

			@Override
			public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				final StatusLine statusLine = response.getStatusLine();
        final HttpEntity entity = response.getEntity();
        if (statusLine.getStatusCode() >= 300) {
            String errorResp = EntityUtils.toString(entity);
            throw new HttpResponseException(statusLine.getStatusCode(),
                    statusLine.getReasonPhrase() + ": " + errorResp);
        }
        return entity == null ? null : handleEntity(entity);
			}
		};
  }
  
  static <T> ResponseHandler<T> responseHandler(EntityHandler<T> handler) {
  	return handler.asResponseHandler();
  }
  
  static final boolean DEBUG = false;
  
  static <T> ResponseHandler<T> objectResponseHandler(JavaType type) {
  	return new EntityHandler<T>() {

			@Override
			public T handleEntity(HttpEntity entity) throws IOException {
				ObjectMapper objectMapper = ObjectMapperProvider.objectMapper();
				try (InputStream content = entity.getContent()) {					
					if(content instanceof EmptyInputStream) {
						return null;
					} else {
						if(DEBUG) {
							String text = new String(ByteStreams.toByteArray(content), StandardCharsets.UTF_8);
							System.out.println(text);
							return objectMapper.readValue(text, type);
						} else
							return objectMapper.readValue(content, type);
					}
				}
			}
		}.asResponseHandler();
  }
  
}

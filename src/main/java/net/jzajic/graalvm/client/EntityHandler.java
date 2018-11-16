package net.jzajic.graalvm.client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ResponseHandler;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.io.EmptyInputStream;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

@FunctionalInterface
public interface EntityHandler<T> {

	/**
   * Handle the response entity and transform it into the actual response
   * object.
   */
  public abstract T handleEntity(HttpEntity entity) throws IOException;
	
  default ResponseHandler<T> asResponseHandler() {
  	return new AbstractResponseHandler<T>() {

			@Override
			public T handleEntity(HttpEntity entity) throws IOException {
				return EntityHandler.this.handleEntity(entity);
			}
		};
  }
  
  static <T> ResponseHandler<T> responseHandler(EntityHandler<T> handler) {
  	return handler.asResponseHandler();
  }
  
  static <T> ResponseHandler<T> objectResponseHandler(JavaType type) {
  	return new EntityHandler<T>() {

			@Override
			public T handleEntity(HttpEntity entity) throws IOException {
				ObjectMapper objectMapper = ObjectMapperProvider.objectMapper();
				try (InputStream content = entity.getContent()) {
					if(content instanceof EmptyInputStream) {
						return null;
					} else {
						return objectMapper.readValue(content, type);
					}
				}
			}
		}.asResponseHandler();
  }
  
}

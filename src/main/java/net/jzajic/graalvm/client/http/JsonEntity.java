package net.jzajic.graalvm.client.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.entity.AbstractHttpEntity;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.jzajic.graalvm.client.ObjectMapperProvider;

public class JsonEntity<T> extends AbstractHttpEntity {

	private byte[] content;

	public JsonEntity(final String object) {
		content = object.getBytes();
	}
	
	public JsonEntity(final T object) {		
		try {
			this.content = ObjectMapperProvider
				.objectMapper().writeValueAsBytes(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	public static <T> JsonEntity<T> create(String object) {
		return new JsonEntity<T>(object);
	}
	
	public static <T> JsonEntity<T> create(T object) {
		return new JsonEntity<T>(object);
	}
	
	@Override
	public long getContentLength() {
		return content.length;
	}

	@Override
	public boolean isRepeatable() {
		return true;
	}

	@Override
	public InputStream getContent() throws IOException, UnsupportedOperationException {
		return new ByteArrayInputStream(content);
	}

	@Override
	public void writeTo(OutputStream outstream) throws IOException {
		outstream.write(content);
	}

	@Override
	public boolean isStreaming() {
		return false;
	}
	
}

package net.jzajic.graalvm.client;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.utils.URIBuilder;

public class URIResource extends URIBuilder {

	public URIResource() {
		super();
	}

	public URIResource(String string) throws URISyntaxException {
		super(string);
	}

	public URIResource(URI uri) {
		super(uri);
	}
	
	public URIResource addPath(String path) {
		StringBuilder pathBuilder = new StringBuilder();
		if(this.getPath() != null) {
			pathBuilder.append(this.getPath()).append("/");
		}
		pathBuilder.append(path);
		this.setPath(pathBuilder.toString());
		return this;
	}
	
	public URIResource addParameter(final String param, final String value) {
		super.addParameter(param, value);
		return this;
	}
	
}

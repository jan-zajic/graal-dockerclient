package net.jzajic.graalvm.client.exceptions;

public class DockerInterruptedException extends DockerException {

	public DockerInterruptedException(String message) {
		super(message);
	}
	
	public DockerInterruptedException(String message, Throwable cause) {
		super(message, cause);
	}
	
}

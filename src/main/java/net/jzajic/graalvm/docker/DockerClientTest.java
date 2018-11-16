package net.jzajic.graalvm.docker;

import java.util.List;

import net.jzajic.graalvm.client.DefaultDockerClient;
import net.jzajic.graalvm.client.DockerClient;
import net.jzajic.graalvm.client.messages.Container;

public class DockerClientTest {
	
	public static void main(String[] args) {
		try (DockerClient client = new DefaultDockerClient("unix:///var/run/docker.sock")) {			
			List<Container> containers = client.listContainers();
			for (Container container : containers) {
				System.out.println(container.image);
			}
		}
	}
	
}

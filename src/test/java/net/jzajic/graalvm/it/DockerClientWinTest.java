package net.jzajic.graalvm.it;

import java.util.List;

import net.jzajic.graalvm.client.DefaultDockerClient;
import net.jzajic.graalvm.client.DockerClient;
import net.jzajic.graalvm.client.messages.Container;
import net.jzajic.graalvm.client.messages.ContainerInfo;

public class DockerClientWinTest {
	
	public static void main(String[] args) {
		try (DockerClient client = new DefaultDockerClient("npipe:////./pipe/docker_engine")) {			
			List<Container> containers = client.listContainers();
			for (Container container : containers) {
				System.out.println(container.image);
				ContainerInfo inspectContainer = client.inspectContainer(container.id);
				String runtime = inspectContainer.hostConfig.runtime;
				System.out.println("runtime: "+runtime);
			}
		}
	}
	
}

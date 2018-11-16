package net.jzajic.graalvm.docker;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import net.jzajic.graalvm.socket.UnixSocketAddress;
import net.jzajic.graalvm.socket.channel.UnixSocketSelectorProvider;

public class DockerClientTest {
	
	public static void main(String[] args) {
		try {
			SocketChannel openSocketChannel = UnixSocketSelectorProvider.provider().openSocketChannel();
			boolean conn = openSocketChannel.connect(new UnixSocketAddress("/var/run/docker.sock"));
			System.out.println("Channel connected: "+conn);
			openSocketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

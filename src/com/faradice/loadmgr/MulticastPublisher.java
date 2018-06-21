package com.faradice.loadmgr;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//https://stackoverflow.com/questions/18747134/getting-cant-assign-requested-address-java-net-socketexception-using-ehcache
//https://docs.oracle.com/javase/tutorial/networking/datagrams/broadcasting.html
public class MulticastPublisher {
	private DatagramSocket socket;
	private InetAddress group;
	private byte[] buf;

	public void multicast(String multicastMessage) throws IOException {
		socket = new DatagramSocket();
		group = InetAddress.getByName("230.0.0.0");
		buf = multicastMessage.getBytes();

		DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
		socket.send(packet);
		socket.close();
	}
	
	public static void main(String[] args) throws Exception {
		System.setProperty("java.net.preferIPv4Stack", "true");
		MulticastPublisher mp = new MulticastPublisher();
		mp.multicast("hi");
	}
}

package com.faradice.loadmgr;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver extends Thread {
	protected byte[] buf = new byte[256];
	InetAddress group = null;
	MulticastSocket socket = null;

	@Override
	public void run() {
		try (MulticastSocket socket = new MulticastSocket(4446)) {
			byte[] buffer = new byte[8196];
			group = InetAddress.getByName("230.0.0.0");
			socket.joinGroup(group);
			while (true) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length);
				socket.receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
				System.out.println("Received: "+received);
				if ("end".equals(received)) {
					break;
				}
			}
			socket.leaveGroup(group);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		MulticastReceiver mcr = new MulticastReceiver();
		mcr.start();
	}
}
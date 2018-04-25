package edu.wpi;

import edu.wpi.SimplePacketComs.phy.UDPSimplePacketComs;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;

public class SimplePacketComsTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting SimplePacketComs Test");

		HashSet<InetAddress> addresses = UDPSimplePacketComs.getAllAddresses();
		if (addresses.size() < 1) {
			System.out.println("No devices found");
			return;
		}
		ArrayList<SwarmRobot> robots = new ArrayList<>();
		for (InetAddress add : addresses) {
			System.out.println("Got " + add);
			SwarmRobot e = new SwarmRobot(add);
			e.connectDeviceImp();
			robots.add(e);
		}

		Thread.sleep(1000);
		SwarmRobot robot = robots.get(0);
		double[] data = new double[15];
		for (int i = 0; i < 15; i++)
			data[i] = 0;
		robot.readFloats(1871, data);
		robot.readFloats(1936, data);
		robot.writeFloats(1936, data);

		byte[] bytes = new byte[60];
		for (int i = 0; i < 60; i++)
			bytes[i] = 0;
		robot.readBytes(2012, bytes);
		robot.writeBytes(2012, bytes);
		Thread.sleep(1000);

		robot.disconnectDeviceImp();
	}

}

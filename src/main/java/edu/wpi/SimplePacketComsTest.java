package edu.wpi;

import edu.wpi.SimplePacketComs.phy.UDPSimplePacketComs;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;

public class SimplePacketComsTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting SimplePacketComs Test");

		HashSet<InetAddress> addresses = UDPSimplePacketComs.getAllAddresses("Warehouse-21");
		if (addresses.size() < 1) {
			System.out.println("No devices found");
			return;
		}
		ArrayList<WarehouseRobot> robots = new ArrayList<>();
		for (InetAddress add : addresses) {
			System.out.println("Got " + add.getHostAddress());
			WarehouseRobot e = new WarehouseRobot(add);
			e.connect();
			robots.add(e);
		}

		Thread.sleep(1000);
		for(WarehouseRobot robot:robots) {
			robot.clearFaults();
			robot.directDrive(10, 0, 0, 0, 0, 0, 100);
			robot.pickOrder(0, 0, 0, 1, 100, 200);
			String status = " Robot status "+robot.getStatus()+", drive status "+robot.getDriveStatus()+", location: "+robot.getLocationData();
			System.out.println(status);
			System.out.println(robot.getName()+" name");

		}
		Thread.sleep(1000);

		for(WarehouseRobot s:robots)
			s.disconnect();
	}

}

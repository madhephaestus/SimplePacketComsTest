package edu.wpi;

import edu.wpi.SimplePacketComs.phy.UDPSimplePacketComs;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SimplePacketComsTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting SimplePacketComs Test");

		List<WarehouseRobot> robots = WarehouseRobot.get("Warehouse-21");
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

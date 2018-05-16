package edu.wpi;

import java.util.List;

import edu.wpi.SimplePacketComs.device.warehouse.WarehouseRobot;


public class SimplePacketComsTest {

	public static void main(String[] args) throws Exception {
		System.out.println("Starting SimplePacketComs Test");

		List<WarehouseRobot> robots = WarehouseRobot.get();
		Thread.sleep(1000);
		for(WarehouseRobot robot:robots) {
			robot.clearFaults();
			robot.directDrive(10, 0, 0, 0, 0, 0, 100);
			robot.pickOrder(0, 0, 0, 1, 100, 200);
			String status = " Robot status "+robot.getStatus()+", drive status "+robot.getDriveStatus()+", location: "+robot.getLocationData();
			System.out.println(status);
			System.out.println("name = "+robot.getName());

		}
		Thread.sleep(1000);

		for(WarehouseRobot s:robots)
			s.disconnect();
	}

}

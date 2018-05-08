package edu.wpi;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import edu.wpi.SimplePacketComs.PacketType;
import edu.wpi.SimplePacketComs.bytepacket.BytePacketType;
import edu.wpi.SimplePacketComs.floatpacket.FloatPacketType;
import edu.wpi.SimplePacketComs.phy.UDPSimplePacketComs;

public class WarehouseRobot {
	private UDPSimplePacketComs udpdevice;	
	private PacketType getName = new BytePacketType(1776, 64);
	private PacketType getStatus = new BytePacketType(2012, 64);
	private PacketType clearFaults = new BytePacketType(1871, 64);
	private PacketType pickOrder = new FloatPacketType(1936, 64);
	private PacketType getLocation = new FloatPacketType(1994, 64);
	private PacketType directDrive = new FloatPacketType(1786, 64);
	private byte[] name = new byte[60];
	private byte[] status = new byte[1];
	private double[] pickOrderData = new double[6];
	private double[] locationData = new double[8];
	private double[] driveData = new double[8];
	private double[] driveStatus = new double[1];
	
	public WarehouseRobot(InetAddress add) throws Exception {

		udpdevice = new UDPSimplePacketComs(add);
		getName.downstream[0]=(byte)'*';// read name
		for (PacketType pt : Arrays.asList(clearFaults, pickOrder, getStatus, directDrive, getLocation,getName)) {
			udpdevice.addPollingPacket(pt);
		}
		udpdevice.addEvent(getName.idOfCommand, () -> {
			udpdevice.readBytes(getName.idOfCommand, name);// read name
		});
		udpdevice.addEvent(getStatus.idOfCommand, () -> {
			udpdevice.readBytes(getStatus.idOfCommand, status);
		});
		udpdevice.addEvent(directDrive.idOfCommand, () -> {
			udpdevice.readFloats(directDrive.idOfCommand, driveStatus);
		});
		udpdevice.addEvent(getLocation.idOfCommand, () -> {
			udpdevice.readFloats(getLocation.idOfCommand, locationData);
		});
		pickOrder.oneShotMode();
		pickOrder.sendOk();// remove the first call here
		clearFaults.oneShotMode();
		clearFaults.sendOk();// remove the first call here
		getName.oneShotMode();
		
		
	}
	

	/**
	 * This method tells the connection object to disconnect its pipes and close out
	 * the connection. Once this is called, it is safe to remove your device.
	 */

	public void disconnect() {
		udpdevice.disconnect();
	}

	/**
	 * Connect device imp.
	 *
	 * @return true, if successful
	 */
	public boolean connect() {
		return udpdevice.connect();
	}

	@Override
	public String toString() {
		return getName();
	}


	public List<Double> getLocationData() {
		return DoubleStream.of(locationData).boxed().collect(Collectors.toList());

	}


	public double getDriveStatus() {
		return driveStatus[0];
	}
	
	public void pickOrder(double pickupArea, double pickupX,double pickupZ,double dropoffArea, double dropoffX,double dropoffZ) {
		pickOrderData[0]=pickupArea;
		pickOrderData[1]=pickupX;
		pickOrderData[2]=pickupZ;
		pickOrderData[3]=dropoffArea;
		pickOrderData[4]=dropoffX;
		pickOrderData[5]=dropoffZ;
		udpdevice.writeFloats(pickOrder.idOfCommand, pickOrderData);
		pickOrder.oneShotMode();

	}

	public void directDrive(double deltaX, double deltaY,double deltaZ,double deltaAzimuth, double deltaElevation,double deltaTilt,double milisecondsTransition) throws Exception {
		if(getDriveStatus()<0.9999) {
			//throw new Exception("The robot is not done with pervious command");
		}
		driveData[0]=deltaX;
		driveData[1]=deltaY;
		driveData[2]=deltaZ;
		driveData[3]=deltaAzimuth;
		driveData[4]=deltaElevation;
		driveData[5]=deltaTilt;
		driveData[6]=milisecondsTransition;
		driveData[7]=(double)(Math.round(Math.random()*100000));

		udpdevice.writeFloats(directDrive.idOfCommand, driveData);
		
	}
	
	public WarehouseRobotStatus getStatus() {
		return WarehouseRobotStatus.fromValue(status[0]);
	}


	public String getName() {
		return new String(name);
	}
	
	public void clearFaults() {
		clearFaults.oneShotMode();

	}


}

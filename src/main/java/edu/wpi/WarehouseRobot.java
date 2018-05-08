package edu.wpi;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.SimplePacketComs.PacketType;
import edu.wpi.SimplePacketComs.bytepacket.BytePacketType;
import edu.wpi.SimplePacketComs.floatpacket.FloatPacketType;
import edu.wpi.SimplePacketComs.phy.UDPSimplePacketComs;

public class WarehouseRobot {
	UDPSimplePacketComs udpdevice;
	PacketType getStatus = new BytePacketType(2012, 64);
	PacketType clearFaults = new BytePacketType(1871, 64);
	PacketType pickOrder = new FloatPacketType(1936, 64);
	PacketType getLocation = new FloatPacketType(1994, 64);
	PacketType directDrive = new FloatPacketType(1786, 64);


	List<PacketType> packets = Arrays.asList(clearFaults,
				pickOrder,
				getStatus,
				directDrive,
				getLocation);
	public WarehouseRobot(InetAddress add) throws Exception{

		udpdevice = new UDPSimplePacketComs(add);
		pickOrder.oneShotMode();
		clearFaults.oneShotMode();
		for(PacketType pt:packets) {
			udpdevice.addPollingPacket(pt);
		}
		

	}

	void writeFloats(int id,double [] values){
		udpdevice.writeFloats(id, values);
	}
	void writeBytes(int id,byte [] values){
		udpdevice.writeBytes(id, values);
	}
	void readFloats(int id,double [] values){
		udpdevice.readFloats(id, values);
	}
	void readBytes(int id,byte [] values){
		udpdevice.readBytes(id, values);
	}
	
	/**
	 * This method tells the connection object to disconnect its pipes and close out the connection. Once this is called, it is safe to remove your device.
	 */
	
	public void disconnectDeviceImp(){
		udpdevice.disconnect();
	}
	
	/**
	 * Connect device imp.
	 *
	 * @return true, if successful
	 */
	public  boolean connectDeviceImp(){
		return udpdevice.connect();
	}
	@Override
	public String toString() {
		return udpdevice.getName();
	}

}

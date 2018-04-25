package edu.wpi;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.SimplePacketComs.PacketType;
import edu.wpi.SimplePacketComs.bytepacket.BytePacketType;
import edu.wpi.SimplePacketComs.floatpacket.FloatPacketType;
import edu.wpi.SimplePacketComs.phy.UDPSimplePacketComs;

public class SwarmRobot {
	UDPSimplePacketComs udpdevice;
	List<PacketType> packets = Arrays.asList(new FloatPacketType(1871, 64),
				new FloatPacketType(1936, 64),
				new BytePacketType(2012, 64));
	SwarmRobot(InetAddress add) throws Exception{

		udpdevice = new UDPSimplePacketComs(add);
		for(PacketType pt:packets)
			udpdevice.addPollingPacket(pt);

	}

	void writeFloats(int id,double [] values){
		for(PacketType pt:packets){
			if(pt.idOfCommand ==id){
				for(int i=0;i<pt.downstream.length;i++){
					pt.downstream[i]=(float)values[i];
				}
				return;
			}
		}
	}
	void writeBytes(int id,byte [] values){
		for(PacketType pt:packets){
			if(pt.idOfCommand ==id){
				for(int i=0;i<pt.downstream.length;i++){
					pt.downstream[i]=(byte)values[i];
				}
				return;
			}
		}
	}
	void readFloats(int id,double [] values){
		for(PacketType pt:packets){
			if(pt.idOfCommand ==id){
				for(int i=0;i<pt.downstream.length;i++){
					values[i]=(double)pt.upstream[i];
				}
				return;
			}
		}
	}
	void readBytes(int id,byte [] values){
		for(PacketType pt:packets){
			if(pt.idOfCommand ==id){
				for(int i=0;i<pt.downstream.length;i++){
					values[i]=(byte)pt.upstream[i];
				}
				return;
			}
		}
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

}

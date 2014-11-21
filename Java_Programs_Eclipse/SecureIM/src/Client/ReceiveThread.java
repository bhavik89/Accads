package Client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import Client.ClientMessageTypeHandler;
import Client.UDPMessages;

public class ReceiveThread extends Thread{
	
	private final static int PACKETSIZE = 2048 ;
	private DatagramSocket socket;
	private Client client;
	
    public ReceiveThread(Client client) {
    	//Initialize socket Descriptor from main thread
    	this.client = client;
    	this.socket = client.getSocket();
    }
    public void run() {
    	try{
    		DatagramPacket receiveData = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
    		for(;;){
    			//Waiting for data from server....(blocking)
    			this.socket.receive(receiveData);
    			String receivedData = new String(receiveData.getData());
    			
    			//System.out.println(new String (receivedData));
    			
    			UDPMessages handler = new UDPMessages(this.client, receivedData, receiveData.getAddress(), receiveData.getPort());
    			
				handler.messageAction();   			
    			
    		}
    	}
    	catch(Exception e){
			System.out.println(e);
		}
    }

}

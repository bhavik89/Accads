package client;

import generic.CryptoOperations;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import client.ClientUDPMessageHandler;

public class UDPReceiveThread extends Thread{
	
	private final static int PACKETSIZE = 4096;
	private DatagramSocket socket;
	private Client client;
	
    public UDPReceiveThread(Client client) {    	
    	this.client = client;
    	socket = client.getSocket();
    }
    
    public void run() {
    	try{
    		DatagramPacket receiveData = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
    		
    		for(;;){
    			//Waiting for data from server....(blocking)
    			socket.receive(receiveData);
    			//System.out.println("Client Received Bytes: " + receiveData.getLength());
    			
    			String receivedData = new String(receiveData.getData(), 0 , receiveData.getLength(), CryptoOperations.ISO_CHARSET);    	    			
    			//System.out.println("receivedDataLen" + receivedData.length());
    			//System.out.println("receivedData" + receivedData);
    			
    			ClientUDPMessageHandler handler = new ClientUDPMessageHandler(client, receivedData, receiveData.getAddress(), receiveData.getPort());
    			
				handler.messageAction();    			
    		}
    	}
    	catch(Exception e){
			System.out.println(e);
		}finally{
			if(!socket.isClosed()){
				this.socket.close();
			}
    }
    }
}

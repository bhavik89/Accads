package client;

import java.net.ServerSocket;
import java.net.Socket;

import java.util.HashMap;
import java.util.UUID;

import java.io.IOException;

/* Handles the TCP connection requests from other clients
 * */
public class ClientTCPHandler implements Runnable {
	
	private final int thisClientport;
	private final HashMap<UUID, ClientConnection> waitingClients = new HashMap<UUID,ClientConnection>();
	
	public ClientTCPHandler(int port) {
		this.thisClientport = port;		
	}	
	
	/*Start the thread to listen to TCP Connections*/
	@Override
	public void run() {
		ServerSocket serverSocket;
		Socket thisClientSocket;		
		try {			
		    serverSocket = new ServerSocket(this.thisClientport);
			while ((thisClientSocket = serverSocket.accept()) != null) {
				new TCPReceiveThread(thisClientSocket, this);
			}
		} catch (IOException e) {
		    System.out.println("ERROR: Cound not create TCP socket");
		    System.out.println("Check your other connections and this client port");
		    System.exit(-1);
		}
	}	

	public void addWaitingClients(UUID userId, ClientConnection peerConnection){
		this.waitingClients.put(userId, peerConnection);
	}
	
	public ClientConnection removeWaitingClients(UUID userId){
		return this.waitingClients.remove(userId);
	}
}
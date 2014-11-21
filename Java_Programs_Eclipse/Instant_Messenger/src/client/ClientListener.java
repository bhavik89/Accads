package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import src.common.CryptoLibrary;
import src.common.HeaderHandler;
import src.common.MessageType;
import src.common.MessageType.UnsupportedMessageTypeException;

/* Listens for the incoming TCP Connections */
public class ClientListener implements Runnable {
	
	private final int port;
	private final HashMap<UUID, PeerConnection> awaitingConnection = 
			new HashMap<UUID,PeerConnection>();
	
	public ClientListener(int port) {
		this.port = port;
	}
	
	public void addAwaitingConnection(UUID userId, PeerConnection peerConnection){
		this.awaitingConnection.put(userId, peerConnection);
	}
	
	public PeerConnection removeAwaitingConnection(UUID userId){
		return this.awaitingConnection.remove(userId);
	}
	
	@Override
	public void run() {
		ServerSocket serverSocket;
		Socket clientSocket;
		
		try {
			
		    serverSocket = new ServerSocket(this.port);
			while ((clientSocket = serverSocket.accept()) != null) {

				new SocketReceiver(clientSocket);
			}
		} catch (IOException e) {
		    System.out.println("Could not listen on port");
		    System.exit(-1);
		}
	}
	
	/*
	 * Class that handles a received socket connection.
	 */
	private class SocketReceiver implements Runnable {

		private final Socket clientSocket;
		
		public SocketReceiver(Socket clientSocket) {
			this.clientSocket = clientSocket;
			(new Thread(this)).start();
		}
		
		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(
						new InputStreamReader(
								this.clientSocket.getInputStream()));
			
				String string = in.readLine();
				String message = new String(new BigInteger(string, 16).toByteArray(), CryptoLibrary.CHARSET);
				
				// Verify packet header length
				if (message.length() < 2) {
					System.out.println("Invalid message");
					
				} else {
					MessageType type = null;
					try {
						type = MessageType.getMessageType(message);
						
					} catch (UnsupportedMessageTypeException e) {
						System.out.println("Invalid message type received via TCP");
					}
					
					if (type.equals(MessageType.CLIENT_CLIENT_MESSAGE)) {
						ArrayList<String> decodedParams = HeaderHandler.unpack(message.substring(2));
						
						UUID peerId = UUID.fromString(decodedParams.get(0));
						
						PeerConnection connection = removeAwaitingConnection(peerId);
						
						if (connection != null) {
							connection.setSocket(clientSocket);
							connection.receiveMessage(message);
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
}

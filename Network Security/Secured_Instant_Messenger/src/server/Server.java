package server;


import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.security.PrivateKey;

import java.util.*;

import server.ServerUDPMessageHandler;
import generic.*;

public class Server {
	
	private final static int SERVER_BUFFER = 8192;
	private static int serverPort;
	private static String serverPubKeyFile;
	private static String serverPvtKeyFile;
	public  HashMap<String, ClientsConnected> registeredUsers = new HashMap<String, ClientsConnected>();
	public ClientsOnline onlineUsers = new ClientsOnline();
	private ServerClientConfigReader serverConfig;
	
	
	private Server(String server){
		serverConfig = new ServerClientConfigReader(server);
		serverPubKeyFile = serverConfig.getServerPublicKeyFile();
		serverPvtKeyFile = serverConfig.getServerPrivateKeyFile();
	}
	
	
	public static void main(String[] args) {
		
		Server server  = new Server("Server"); 
		
		byte[] serverBuffer = new byte[SERVER_BUFFER];
		DatagramSocket serverSocket = null;
		
		if(args.length == 1){
			serverPort = Integer.parseInt(args[0]);
		}
		else 
			serverPort = server.serverConfig.getServerPort();
		
		try {
			serverSocket = new DatagramSocket(serverPort);			
			System.out.println("Server Initialized...");
			DatagramPacket serverPacket = new DatagramPacket(serverBuffer, serverBuffer.length);
			
			while(true){
				
				serverSocket.receive(serverPacket);												
				String received = new String(serverPacket.getData(), 0, serverPacket.getLength(), CryptoOperations.ISO_CHARSET);	
				
				//System.out.println("\nReceived: " + received);
				//System.out.println("Received From: " + serverPacket.getAddress() + ":" + serverPacket.getPort());
							
				ServerUDPMessageHandler serverMessages = new ServerUDPMessageHandler(server,
																					received, 
																					serverPacket.getAddress(), 
																					serverPacket.getPort(), 
																					serverSocket);
				(new Thread(serverMessages)).start();
				
			}
			
		} catch (Exception e) {
			System.out.println("ERROR: SocketException:" + e.toString());
			e.printStackTrace();
		} finally {
			if(!serverSocket.isClosed())
				serverSocket.close();
		}
	}
	
	public ClientsConnected getRegisteredUser(String userName) {
		if(this.registeredUsers == null){
			return null;
		}
		
		return this.registeredUsers.get(userName);
	}

	public boolean isRegistered(String userName) {
		if(this.registeredUsers == null){
			return false;
		}
		return this.registeredUsers.containsKey(userName);
	}

	public ClientsConnected getRegisteredUser(UUID userId) {
		if(this.registeredUsers == null){
			return null;
		}
		
		for (ClientsConnected user : this.registeredUsers.values()) {
			if (user.getClientUUId() != null) {
				if (user.getClientUUId().equals(userId)) {
					return user;
				}
			}
		}
		return null;
	}
	
	
	//Getters and Setters
	
	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		Server.serverPort = serverPort;
	}

	public String getServerPubKeyFile() {
		return serverPubKeyFile;
	}

	public void setServerPubKeyFile(String serverPubKeyFile) {
		Server.serverPubKeyFile = serverPubKeyFile;
	}

	public String getServerPvtKeyFile() {
		return serverPvtKeyFile;
	}

	public void setServerPvtKeyFile(String serverPvtKeyFile) {
		Server.serverPvtKeyFile = serverPvtKeyFile;
	}

	public PrivateKey getServerPrivateKey() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public ClientsOnline getOnlineUsers() {
		return onlineUsers;
	}

	public void setOnlineUsers(ClientsOnline onlineUsers) {
		this.onlineUsers = onlineUsers;
	}

	public ServerClientConfigReader getServerConfig() {
		return serverConfig;
	}

	public void setServerConfig(ServerClientConfigReader serverConfig) {
		this.serverConfig = serverConfig;
	}

	public ClientsConnected getOnlineUser(UUID userId){
		return this.onlineUsers.getClientById(userId);
	}
	
	public void loginUser(UUID userId, ClientsConnected user){
		this.onlineUsers.addClientToList(userId, user);
	}
	
	public void logoutUser(UUID userId){
		this.onlineUsers.removeClientFromList(userId);
	}
	
}
	
	
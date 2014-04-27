package client;

import java.util.HashMap;
import java.util.UUID;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import client.UDPReceiveThread;
import generic.*;

public class Client {
	
	/*Socket connection varibles*/
	private static InetAddress serverIP;	
	private static int clientPort;
	private static int serverPort;
	private static DatagramSocket clientSocket;	
	private boolean isLoggedIn = false;
	public boolean clientRunning = true;
	
	/*User credentials*/
	private static String username;
	private static String password;	
		
	/*Variables to store the keys*/
	private String clientServerPubKeyFile;
	private PublicKey serverPublicKey;
	private KeyPair dhKeyPair;
	private SecretKey clientServerSecretKey;	
	private static ServerClientConfigReader clientConfig;
	
	/*Client Unique User ID*/
	private UUID clientUUID;
	
	/*Helpers to store info about other connected clients and their connections*/	
	public HashMap<String, String> clientMessageToSend = new HashMap<String, String>();	
	public ConnectedClients connectedClients = new ConnectedClients();
	public ClientTCPHandler clientTCPHandler = null;
	
	/*Constructor*/
	private Client(String client){
		clientConfig = new ServerClientConfigReader(client);
		clientServerPubKeyFile = clientConfig.getClientServerPublicKeyFile();		
	}
	
	/*Main method to get the socket config from command line and user credentials*/
	public static void main(String[] args) throws IOException {		
		
		Client client = new Client("Client");
				
		if(args.length == 3){
			clientPort = Integer.parseInt(args[0]);
			serverIP = InetAddress.getByName(args[1]);
			serverPort = Integer.parseInt(args[2]);
			
		}
		else if (args.length == 2) {
			clientPort = Integer.parseInt(args[0]);
			serverIP = InetAddress.getByName(args[1]);
			serverPort = getClientConfig().getClientServerPort();
			
		}
		else if (args.length == 1) {
			clientPort = Integer.parseInt(args[0]);
			serverIP = getClientConfig().getClientServerIP();
			serverPort = getClientConfig().getClientServerPort();
		
		}
		else{
			clientPort = getClientConfig().getClientPort();
			serverIP = getClientConfig().getClientServerIP();
			serverPort = getClientConfig().getClientServerPort();
			
		}
		
		client.getLoginDetails();
	}
	
	/*Prompts for user credentials on console*/
	public void getLoginDetails() throws IOException{	
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Username: ");		
		username = in.readLine().trim();		
		
		System.out.print("Password: ");		
		password = in.readLine().trim();
							
		this.getConnected();		
	}

	/*Sends the first hello message to server to start Authentication*/
	private void getConnected() throws IOException {		
		
		String message = MessageEncoder.CLIENT_HELLO.getEncodedMessage("HELLO");
		byte[] messageBytes = null;
		messageBytes = message.getBytes(CryptoOperations.ISO_CHARSET);		
		this.sendUDPMessage(messageBytes);	
			
		/* Start listening for TP connections */
		this.clientTCPHandler = new ClientTCPHandler(clientPort);
		(new Thread(this.clientTCPHandler)).start();
		
		new UDPReceiveThread(this).start();
		
	}
	
	/*Establishes UDP socket for given port and sends the socket packet */
	public void sendUDPMessage(byte[] messageBytes) throws IOException{
		
		try{
		clientSocket = new DatagramSocket(getClientPort());
		setClientSocket(clientSocket);
		}catch (IOException e){
			System.out.println("Given port alredy used... Trying another port");
			int randomPort = (1025 + (int)(Math.random() * ((9999 - 1025) + 1)));
			setClientPort(randomPort);
			System.out.println("Client Port: " + randomPort);
			clientSocket = new DatagramSocket(randomPort);			
			setClientSocket(clientSocket);
		}

		
		DatagramPacket sendPacket = new DatagramPacket(messageBytes, messageBytes.length, serverIP, serverPort);
		
		this.getClientSocket().send(sendPacket);			
	}	
	
	/*Sends UDP message to server*/
	public void sendUDPMessage(String message, MessageEncoder messageType) throws IOException{

		InetAddress IPAddress = serverIP;	
		String SendResponse = messageType.getEncodedMessage(message);
		byte[] messageBytes;
		messageBytes = SendResponse.getBytes(CryptoOperations.ISO_CHARSET);			
		DatagramPacket sendPacket = new DatagramPacket(messageBytes, messageBytes.length , IPAddress, serverPort);		
		this.getClientSocket().send(sendPacket);			
	}
   	
	/* Send a message to given ip and port */
	public void sendMessage(String message, MessageEncoder messageCode, InetAddress destinationIP, int destinationPort) 
			throws UnknownHostException {		
		
		message = messageCode.getEncodedMessage(message);
		byte[] messageBytes;

		try {
			messageBytes = message.getBytes(CryptoOperations.ISO_CHARSET);
		} catch (UnsupportedEncodingException e) {
			System.out.println("ERROR: Unable to encode send message");
			e.printStackTrace();
			return;
		}
		
		DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, destinationIP, destinationPort);
		
		try {
			this.getClientSocket().send(packet);
		} catch (IOException e) {
			System.out.println("ERROR:Unable to send message");
			e.printStackTrace();
			return;
		}
	}	
	
	/*Logs out client and destroys all records*/
	public void clientLogout(){
		this.clientRunning = false;
		this.setIsLoggedIn(false);
		this.destoryKeys();
		this.connectedClients.clear();
	}
	
	/*Destroys all the keys in record*/
	public void destoryKeys() {
		this.setDhKeyPair(null);
		this.setClientServerSecretKey(null);
		this.setClientUUID(null);
	}
	
	//Getter and Setter
	public InetAddress getServerIP() {
		return serverIP;
	}

	public void setServerIP(InetAddress serverIP) {
		Client.serverIP = serverIP;
	}

	public int getServerPort() {
		return serverPort;
	}

	public static void setServerPort(int serverPort) {
		Client.serverPort = serverPort;
	}

	public static ServerClientConfigReader getClientConfig() {
		return clientConfig;
	}

	public static void setClientConfig(ServerClientConfigReader clientConfig) {
		Client.clientConfig = clientConfig;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public void setClientServerPubKeyFile(String clientServerPubKeyFile) {
		this.clientServerPubKeyFile = clientServerPubKeyFile;
	}
	
	public static int getClientPort() {
		return clientPort;
	}

	public static void setClientPort(int clientPort) {
		Client.clientPort = clientPort;
	}

	public String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Client.username = username;
	}

	public String getPassword() {
		return password;
	}

	public static void setPassword(String password) {
		Client.password = password;
	}

	public DatagramSocket getClientSocket() {
		return clientSocket;
	}

	public static void setClientSocket(DatagramSocket clientSocket) {
		Client.clientSocket = clientSocket;
	}
  
	public PublicKey getServerPublicKey() {
		return serverPublicKey;
	}

	public void setServerPublicKey(PublicKey serverPublicKey) {
		this.serverPublicKey = serverPublicKey;
	}

	public KeyPair getDhKeyPair() {
		return dhKeyPair;
	}

	public void setDhKeyPair(KeyPair dhKeyPair) {
		this.dhKeyPair = dhKeyPair;
	}

	public SecretKey getClientServerSecretKey() {
		return clientServerSecretKey;
	}

	public void setClientServerSecretKey(SecretKey secretKey) {
		this.clientServerSecretKey = secretKey;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setIsLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public UUID getClientUUID() {
		return clientUUID;
	}

	public void setClientUUID(UUID userId) {
		this.clientUUID = userId;
	}

	private Client() throws SocketException{
		clientSocket = new DatagramSocket();
	}

	public DatagramSocket getSocket() {
		return clientSocket;
	}
	
	public String getClientServerPubKeyFile(){
		return this.clientServerPubKeyFile;
	}	
	
}
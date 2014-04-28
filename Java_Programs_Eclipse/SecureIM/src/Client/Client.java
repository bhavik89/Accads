package Client;

import java.io.*;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.List;
import java.util.UUID;

import javax.crypto.SecretKey;

import Client.ClientMessageTypeHandler;
import Client.ReceiveThread;
import Generic.CryptoOperations;
import Generic.MessageTypeHandler;

public class Client {
	
	
	private static InetAddress serverIP = null;
	private static int clientPort = 9990;
	private static int serverPort = 9980;
	private static String username;
	private static String password;
	private static DatagramSocket clientSocket;		
	private PublicKey serverPublicKey;
	private KeyPair dhKeyPair;
	private SecretKey secretKey;
	private boolean isLoogedIn = false;
	private UUID userId;
	private String serverPubKeyFile = "Public_key.der";
	
	
	public static void main(String[] args) throws IOException {
		
		Client client = new Client();
		client.getLoginDetails();		
		
	}
	
	private void getLoginDetails() throws IOException{
		Console console = System.console();
		
		
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Username: ");
		
		this.username = in.readLine().trim();
		
		System.out.print("Password: ");
		
		this.password = in.readLine().trim();
		System.out.println();
		
		System.out.println("Username:" + username);
		System.out.println("Password:" + password);
		
		byte[] usernameByte = username.getBytes();
		byte[] passwordByte = password.getBytes();
		
		//System.out.println("Username:" + new String(usernameByte));
		//System.out.println("Password:" + passwordByte.toString());
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream packet = new DataOutputStream(baos);
		packet.writeInt(username.length());
		packet.writeInt(password.length());
		packet.writeBytes(username);
		packet.writeBytes(password);
		
		//int packetSize = packet.size();
		
		System.out.println(baos.size());
		
		//DatagramPacket sendPacket = new DatagramPacket(baos.toByteArray(), baos.size() , IPAddress, 9980);
	    //clientSocket.send(sendPacket);
		this.getConnected();
		
	}

	
private void getConnected() throws IOException {		
		
		
		String message = ClientMessageTypeHandler.CLIENT_SERVER_HELLO.createMessage("HELLO");
		byte[] messageBytes = null;
		messageBytes = message.getBytes();
		
		System.out.println(new String (messageBytes));
		
		this.sendUDPHelloMessage(messageBytes);
		
		new ReceiveThread(this).start();
		
	}
	
	public void sendUDPHelloMessage(byte[] messageBytes) throws IOException{
		clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		DatagramPacket sendPacket = new DatagramPacket(messageBytes, messageBytes.length , IPAddress, 9980);
		clientSocket.send(sendPacket);			
	}
	
	public void sendUDPMessage(String string, MessageTypeHandler messageType) throws IOException{
		//System.out.println();
		//System.out.println("Message Sent:" + message);
		clientSocket = new DatagramSocket();
		InetAddress IPAddress = InetAddress.getByName("localhost");
		
		String SendResponse = messageType.createMessage(string);
		byte[] messageBytes;
		
		messageBytes = SendResponse.getBytes();
		//System.out.println();
		System.out.println("MessagBytese Sent:" + messageBytes.length);
			
		DatagramPacket sendPacket = new DatagramPacket(messageBytes, messageBytes.length , IPAddress, 9980);
		
		clientSocket.send(sendPacket);			
	}

	public PublicKey getServerPublicKeyFromFile(String ServerPublicKeyFile) {
		try {
			return (PublicKey) readKeyFromFile(serverPubKeyFile, "public");
		} catch (Exception e) {
			System.out.println("Unable to read server's public key");
			throw new RuntimeException(e);
		}
	}	
	
	private static Object readKeyFromFile(String keyFile, String fileType) throws IOException {
		InputStream in = new FileInputStream(keyFile);
			  ObjectInputStream oin =
			    new ObjectInputStream(new BufferedInputStream(in));
			  try {
			    BigInteger m = (BigInteger) oin.readObject();
			    BigInteger e = (BigInteger) oin.readObject();
			    KeyFactory fact = KeyFactory.getInstance("RSA");
			    
			    if (fileType == "public"){
			    	RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);			    
			    	return fact.generatePublic(keySpec);
			    }
			    else if (fileType == "private"){
				    RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);			    
				    return fact.generatePrivate(keySpec);
				    }
			    else
			    	return null;
			  } catch (Exception e) {
			    throw new RuntimeException("Spurious serialisation error", e);
			  } finally {
			    oin.close();
			  }
	}	
	
	
	public static int getClientPort() {
		return clientPort;
	}


	public static void setClientPort(int clientPort) {
		Client.clientPort = clientPort;
	}


	public static String getUsername() {
		return username;
	}


	public static void setUsername(String username) {
		Client.username = username;
	}


	public static String getPassword() {
		return password;
	}


	public static void setPassword(String password) {
		Client.password = password;
	}


	public static DatagramSocket getClientSocket() {
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


	public SecretKey getSecretKey() {
		return secretKey;
	}


	public void setSecretKey(SecretKey secretKey) {
		this.secretKey = secretKey;
	}


	public boolean isLoogedIn() {
		return isLoogedIn;
	}


	public void setLoogedIn(boolean isLoogedIn) {
		this.isLoogedIn = isLoogedIn;
	}


	public UUID getUserId() {
		return userId;
	}


	public void setUserId(UUID userId) {
		this.userId = userId;
	}


	private Client() throws SocketException{
		clientSocket = new DatagramSocket();
	}
	

	public DatagramSocket getSocket() {
		return this.clientSocket;
	}
	
	public String getServerPubKeyFile(){
		return this.serverPubKeyFile;
	}

	
	
}
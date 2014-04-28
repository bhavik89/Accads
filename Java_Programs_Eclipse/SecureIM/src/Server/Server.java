package Server;



import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;

import Server.ServerMessageTypeHandler;

public class Server {
	
	private int serverPort;
	private String serverPubKeyFile = "Public_key.der";//"destination_PublicKey.key";
	private String serverPvtKeyFile = "Private_key.der";//"destination_PrivateKey.key";
	
	private Server(int port){
		this.serverPort = port;
	}
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		Server server  = new Server(9980); 
		
		byte[] serverBuffer = new byte[4096];
		DatagramSocket serverSocket = null;
		
		if(args.length == 1){
			server.serverPort = Integer.parseInt(args[0]);
		}
		else 
			server.serverPort = 9980;
		
		try {
			serverSocket = new DatagramSocket(server.serverPort);	
			
			
			System.out.println("Server Initialized on port:" + server.serverPort);
			DatagramPacket serverPacket = new DatagramPacket(serverBuffer, serverBuffer.length);
			
			while(true){
				
				serverSocket.receive(serverPacket);
				
				//byte[] clientData = serverPacket.getData();
				
				String received = new String(serverPacket.getData(), 0, serverPacket.getLength());
				
				
				
				System.out.println("Received From IP:" + serverPacket.getAddress()); 
				System.out.println("Received From Port:" + serverPacket.getPort()); 
				
				System.out.println("Received bytes:" + new String(serverPacket.getData()));
				System.out.println();
				
				ServerMessageTypeHandler messageHandle = new ServerMessageTypeHandler(server,
																					received, 
																					serverPacket.getAddress(), 
																					serverPacket.getPort(), 
																					serverSocket);
				(new Thread(messageHandle)).start();
				
			}
			
		} catch (SocketException e) {
			System.out.println("SocketException:" + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException:" + e.toString());
			e.printStackTrace();
		}		
	}
	
	
	

	public PublicKey getServerPublicKeyFromFile(String ServerPublicKeyFile) {
		try {
			return (PublicKey) readKeyFromFile(ServerPublicKeyFile, "public");
		} catch (Exception e) {
			System.out.println("Unable to read server's public key");
			throw new RuntimeException(e);
		}
	}
	
	public PrivateKey getServerPrivateKeyFromFile(String ServerPrivateKeyFile) {
		try {
			return (PrivateKey) readKeyFromFile(ServerPrivateKeyFile, "private");
		} catch (Exception e) {
			System.out.println("Unable to read server's private key");
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
	
	//Getters and Setters
	
	public int getServerPort() {
		return serverPort;
	}

	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}

	public String getServerPubKeyFile() {
		return serverPubKeyFile;
	}

	public void setServerPubKeyFile(String serverPubKeyFile) {
		this.serverPubKeyFile = serverPubKeyFile;
	}

	public String getServerPvtKeyFile() {
		return serverPvtKeyFile;
	}

	public void setServerPvtKeyFile(String serverPvtKeyFile) {
		this.serverPvtKeyFile = serverPvtKeyFile;
	}
	
	
}
	
	
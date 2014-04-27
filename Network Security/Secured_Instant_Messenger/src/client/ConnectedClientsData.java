package client;

import java.net.InetAddress;
import java.util.UUID;

import javax.crypto.SecretKey;

/*Class to store the data of all the clients connected to given client
 * */

public class ConnectedClientsData {
	private String clientUsername;
	private UUID clientUUid;
	private InetAddress connectedClientIp;
	private int connectedClientPort;	
	private SecretKey tempKey;
	private SecretKey clientSessionSecret;
	private String messagesToSend;
	
	private ClientConnection clientConn;

	
	public ConnectedClientsData(String clientName, InetAddress clIPAddr, int clPorNum,UUID clUUID, SecretKey clTempKey){
		this.clientUsername = clientName;
		this.connectedClientIp = clIPAddr;
		this.connectedClientPort = clPorNum;
		this.clientUUid = clUUID;
		this.tempKey = clTempKey;
	}
	
	public void destroyClientData(){
		this.clientSessionSecret = null;
		this.clientUUid = null;
	}
	
	/* getters and setters*/
	public String getClientUsername(){
		return this.clientUsername;
	}
	
	public SecretKey getTempKey(){
		return this.tempKey;
	}
	
	public InetAddress getConnetedClientIP(){
		return this.connectedClientIp;
	}
	
	public int getConnectedClientPort(){
		return this.connectedClientPort;
	}
	
	public UUID getClientUUid(){
		return this.clientUUid;
	}
	
	public SecretKey getClientSessionSecret(){
		return this.clientSessionSecret;
	}
	
	public ClientConnection getClientConnection(){
		return this.clientConn;
	}
	
	public String getMessagesToSend(){
		return this.messagesToSend;
	}
	
	public void setClientSessionKey(SecretKey key){
		this.clientSessionSecret = key;
	}
	
	public void setPeerConnection(ClientConnection peerConnection){
		this.clientConn = peerConnection;
	}
	
	
}

package src.client;

import java.net.InetAddress;
import java.util.UUID;

import javax.crypto.SecretKey;

/* Peer is another client basically, peerinfo keeps
 * the information about peer, key info , connection info */
public class PeerInfo {
	private String username;
	private SecretKey tempSessionKey;
	private InetAddress peerIp;
	private int peerPort;
	private UUID peerUserId;
	private SecretKey secretKey;
	private String pendingMessage;
	
	private PeerConnection peerConnection;

	/* Constructor */
	public PeerInfo(String username, InetAddress peerIp, int peerPort,UUID userId, SecretKey tempKey){
		this.username = username;
		this.peerIp = peerIp;
		this.peerPort = peerPort;
		this.peerUserId = userId;
		this.tempSessionKey = tempKey;
	}
	
	public void setSecretKey(SecretKey key){
		this.secretKey = key;
	}
	
	public void setPeerConnection(PeerConnection peerConnection){
		this.peerConnection = peerConnection;
	}
	
	/* getters */
	public String getPeerUsername(){
		return this.username;
	}
	
	public SecretKey getTempSessionKey(){
		return this.tempSessionKey;
	}
	
	public InetAddress getPeerIp(){
		return this.peerIp;
	}
	
	public int getPeerPort(){
		return this.peerPort;
	}
	
	public UUID getPeerUserId(){
		return this.peerUserId;
	}
	
	public SecretKey getSecretKey(){
		return this.secretKey;
	}
	
	public PeerConnection getPeerConnection(){
		return this.peerConnection;
	}
	
	public String getPendingMessage(){
		return this.pendingMessage;
	}
	
	public void destroy(){
		this.secretKey = null;
		this.peerUserId = null;
	}
}

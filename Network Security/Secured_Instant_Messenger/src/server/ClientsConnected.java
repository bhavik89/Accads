package server;

import java.net.InetAddress;
import java.util.UUID;

import javax.crypto.SecretKey;

/* Class to store information about the connected clients */
public class ClientsConnected {
	private String username;
	private String pwdHash;
	private UUID clientUUId;	
	private SecretKey clientSessionKey;
	
	private int clientPort;
	private InetAddress clientIp;	
	
	private long clientLastPingedtime;
	
	/* Constructor */
	public ClientsConnected(String clientName, String passHash) {
		this.username = clientName;
		this.pwdHash = passHash;
	}
	
	/* Getters and setters */
	public String getUsername(){
		return this.username;
	}
	
	public void setUsername(String username){
		this.username = username;
	}
	
	public String getPwdHash(){
		return this.pwdHash;
	}
	
	public UUID getClientUUId(){
		return this.clientUUId;
	}
	
	public void setCleintUUId(UUID clientUUId){
		this.clientUUId = clientUUId;
	}
	
	public int getClientPort(){
		return this.clientPort;
	}
	
	public void setClientPort(int port){
		this.clientPort = port;
	}
	
	public InetAddress getClientIp(){
		return this.clientIp;
	}
	
	public void setClientIp(InetAddress clientIp){
		this.clientIp = clientIp;
	}
	
	public SecretKey getClientSessionKey(){
		return this.clientSessionKey;
	}
	
	public void setClientSessionKey(SecretKey clientSessionKey){
		this.clientSessionKey = clientSessionKey;
	}
	
	public long getClientLastPinged(){
		return this.clientLastPingedtime;
	}
	
	public void setClientLastPinged(long clientLastPingedtime){
		this.clientLastPingedtime = clientLastPingedtime;
	}
	
	public void removeCleintSessionKey(){
		this.clientSessionKey = null;
	}
}

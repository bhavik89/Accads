package src.server;

import java.security.PrivateKey;

/* Server information , port and key info */
public class ServerInfo {
	
	private int serverPort;
	private PrivateKey privateKey;
	
	public ServerInfo(PrivateKey privateKey, int port) {
		// TODO Auto-generated constructor stub
		this.privateKey = privateKey;
		this.serverPort = port;
	}
	
	/* setters */
	public void setPrivateKey(PrivateKey key){
		this.privateKey = key;
	}
	
	public void setServerPort(int port){
		this.serverPort = port;
	}
	
	/* getters */
	public PrivateKey getServerPrivateKey(){
		return this.privateKey;
	}
	
	public int getServerPort(){
		return this.serverPort;
	}
}

package src.client;

import java.net.DatagramSocket;
import java.net.InetAddress;

/* ConnectionInfo keeps the socket and ip information
 * for the connection between client and server
 */
public class ConnectionInfo {
	
	private DatagramSocket clientSocket;
	private InetAddress clientIp;
	private int clientPort;
	private InetAddress serverIp;
	private int serverPort;
	
	public ConnectionInfo(int clientPort, InetAddress serverIp, int serverPort){
		this.clientPort = clientPort;
		this.serverIp = serverIp;
		this.serverPort = serverPort;
	}
	
	public void setClientSocket(DatagramSocket outSocket){
		this.clientSocket = outSocket;
	}
	
	public void setClientIp(InetAddress clientIp){
		this.clientIp = clientIp;
	}
	
	public void setClientPort(int clientPort){
		this.clientPort = clientPort;
	}
	
	public void setServerIp(InetAddress serverIp){
		this.serverIp = serverIp;
	}
	
	public void setServerPort(int serverPort){
		this.serverPort = serverPort;
	}
	
	public DatagramSocket getClientSocket(){
		return this.clientSocket;
	}
	
	public InetAddress getClientIp(){
		return this.clientIp;
	}
	
	public int getClientPort(){
		return this.clientPort;
	}
	
	public InetAddress getServerIp(){
		return this.serverIp;
	}
	
	public int getServerPort(){
		return this.serverPort;
	}
}

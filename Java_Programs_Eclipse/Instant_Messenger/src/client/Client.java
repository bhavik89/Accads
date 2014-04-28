package src.client;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.HashMap;

import src.common.CryptoLibrary;
import src.common.ConfigReader.ConfigReaderException;

/* Handles the client, contains clientInfo, peers connected to client,
 * and receiving UDP packets and UDPMessageHandler handles those packets 
 */
public class Client {

	public ClientInfo clientInfo = null;
	public ClientListener clientListener = null;
	public Peers peers = new Peers();
	public HashMap<String, String> pendingMessages = new HashMap<String, String>();
	public boolean running = true;
	
	private static InetAddress serverIP = null;
	private static int clientPort = 0;
	private static int serverPort = 0;

	/* starting point of the client */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		Client client = new Client();
		String configFile = "src/resources/client.cfg";
		
		if(args.length == 3){
			clientPort = Integer.parseInt(args[0]);
			serverIP = InetAddress.getByName(args[1]);
			serverPort = Integer.parseInt(args[2]);
		}
		
		if (args.length == 2) {
			clientPort = Integer.parseInt(args[0]);
			serverIP = InetAddress.getByName(args[1]);
		}
		if (args.length == 1) {
			clientPort = Integer.parseInt(args[0]);
		}
		client.loadClientInfofromConfigFile(configFile);
	}
	
	public void loadClientInfofromConfigFile(String configFile){
		ClientConfigReader configReader;
		try {
			configReader = new ClientConfigReader(configFile);
		} catch (ConfigReaderException e) {
			System.out.println("Unable to read config file");
			e.printStackTrace();
			return;
		}

		this.clientInfo = new ClientInfo(configReader);
		if(serverPort != 0){
			this.clientInfo.getConnectionInfo().setServerPort(serverPort);
		}
		if (serverIP != null){
			this.clientInfo.getConnectionInfo().setServerIp(serverIP);
		}
		if (clientPort != 0){
			this.clientInfo.getConnectionInfo().setClientPort(clientPort);
		}
		try {
			this.clientInfo.loginPrompt(true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/* Listen for TCP Connections */
		this.clientListener = new ClientListener(this.clientInfo.getConnectionInfo().getClientPort());
		(new Thread(this.clientListener)).start();
		
		/* Listens for UDP Packets */
		recieveMessage();
	}

	public void recieveMessage(){
		byte[] buf = new byte[1024];
		
		/* Start listening */
		try{
			
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			
			while(running){
				
				this.clientInfo.getConnectionInfo().getClientSocket().receive(packet);
				String received = new String(packet.getData(),0, packet.getLength(),CryptoLibrary.CHARSET);
				
				UDPMessageHandler handler = new UDPMessageHandler(this, received, packet.getAddress(), packet.getPort());
				(new Thread(handler)).start();
			}
		}catch(Exception e){
			System.out.println("Exception :" + e);
			e.printStackTrace();
			return;
		} finally{
			if(!clientInfo.getConnectionInfo().getClientSocket().isClosed()){
				this.clientInfo.getConnectionInfo().getClientSocket().close();
			}
		}
	}
	
	public void logout(){
		this.running = false;
		this.clientInfo.setIsLoggedIn(false);
		this.clientInfo.destoryKeys();
		this.peers.clear();
	}
}

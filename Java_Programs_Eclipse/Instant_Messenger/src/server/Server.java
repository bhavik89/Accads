package src.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.UUID;

import src.common.CryptoLibrary;
import src.common.ConfigReader.ConfigReaderException;

/* Server contains information about all the registered users,
 * online users, and server information.
 */
public class Server {

	public ServerInfo serverInfo = null;
	public final HashMap<String, UserInfo> registeredUsers = new HashMap<String, UserInfo>();
	public OnlineUsers onlineUsers = new OnlineUsers();
	
	public static int serverPort = 0;

	/*
	 * Entry point or main method of the server
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String configFile = "src/resources/server.cfg";
		
		if(args.length == 1){
			serverPort = Integer.parseInt(args[0]);
		}
		final Server server = new Server(configFile);

		server.run();
	}

	/* Constructor */
	private Server(String configFile) {
		ServerConfigReader configReader;

		try {
			configReader = new ServerConfigReader(configFile);
		} catch (ConfigReaderException e) {
			System.out.println("Unable to read config file");
			e.printStackTrace();
			return;
		}

		this.initializeServerInfo(configReader);
	}

	public void initializeServerInfo(ServerConfigReader serverConfig) {
		try {
			serverInfo = new ServerInfo(
					CryptoLibrary
							.readPrivateKey(serverConfig.privateKeyLocation),
					serverConfig.port);
			if(serverPort != 0){
				this.serverInfo.setServerPort(serverPort);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		for (final UserInfo user : serverConfig.users) {
			registeredUsers.put(user.getUsername(), user);
		}
	}

	/*
	 * Listen on the configured port for incoming UDP packets.
	 */
	private void run() {
		byte[] buf = new byte[2048];
		boolean running = true;
		DatagramSocket socket = null;
		
		// Start Listening
		try {
			socket = new DatagramSocket(
					this.serverInfo.getServerPort());
			DatagramPacket packet = new DatagramPacket(buf, buf.length);

			System.out.println("Server running...");

			while (running) {
				socket.receive(packet);
				String received = new String(packet.getData(), 0,
						packet.getLength(), CryptoLibrary.CHARSET);

				MessageHandler handler = new MessageHandler(this, received,
						packet.getAddress(), packet.getPort(), socket);
				(new Thread(handler)).start();
			}

		} catch (Exception e) {
			System.out.print("Exception:" + e.toString());
		} finally {
			if(!socket.isClosed())
			socket.close();
		}
	}

	public UserInfo getRegisteredUser(String userName) {
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

	public UserInfo getRegisteredUser(UUID userId) {
		if(this.registeredUsers == null){
			return null;
		}
		
		for (UserInfo user : this.registeredUsers.values()) {
			if (user.getUserId() != null) {
				if (user.getUserId().equals(userId)) {
					return user;
				}
			}
		}
		return null;
	}
	
	public void loginUser(UUID userId, UserInfo user){
		this.onlineUsers.addUser(userId, user);
	}
	
	public void logoutUser(UUID userId){
		this.onlineUsers.removeUser(userId);
	}
	
	public UserInfo getOnlineUser(UUID userId){
		return this.onlineUsers.getUser(userId);
	}
	
	public void destroySessionKey(UUID userId){
		UserInfo user = this.onlineUsers.getUser(userId);
		if(user != null){
			user.destroySessionKey();
			return;
		}
	}
}

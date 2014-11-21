package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.KeyPair;
import java.security.PublicKey;
import java.util.UUID;

import javax.crypto.SecretKey;

import src.common.CryptoLibrary;
import src.common.MessageType;

/* Information about the client, contains key info, 
 * connection info, userId, username and password
 */
public class ClientInfo {

	private String username;
	private String password;
	private UUID userId;
	private ConnectionInfo connectionInfo;
	private PublicKey serverPublicKey;
	private KeyPair dhKeyPair;
	private SecretKey secretKey;
	private boolean isLoogedIn = false;

	public ClientInfo(ClientConfigReader config) {
		this.connectionInfo = new ConnectionInfo(config.getPort(),
				config.getServerAddress(), config.getServerPort());
		this.setServerPublicKey(getServerPublicKeyFromFile(config));
	}

	public PublicKey getServerPublicKeyFromFile(ClientConfigReader config) {
		try {
			return CryptoLibrary.readPublicKey(config.getPublicKeyLocation());
		} catch (Exception e) {
			System.out.println("Unable to read server's public key");
			throw new RuntimeException(e);
		}
	}

	/* setters */
	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public void setServerPublicKey(PublicKey key) {
		this.serverPublicKey = key;
	}

	public void setdhKeyPair(KeyPair dhKeyPair) {
		this.dhKeyPair = dhKeyPair;
	}

	public void setSecretKey(SecretKey key) {
		this.secretKey = key;
	}

	public void setIsLoggedIn(boolean value) {
		this.isLoogedIn = value;
	}

	/* getters */
	public String getUserName() {
		return this.username;
	}

	public String getPassword() {
		return this.password;
	}

	public UUID getUserId() {
		return this.userId;
	}

	public ConnectionInfo getConnectionInfo() {
		return this.connectionInfo;
	}

	public PublicKey getServerPublicKey() {
		return this.serverPublicKey;
	}

	public KeyPair getDHKeyPair() {
		return this.dhKeyPair;
	}

	public SecretKey getSecretKey() {
		return this.secretKey;
	}

	public boolean isLoggedIn() {
		return this.isLoogedIn;
	}

	/* methods */
	public void loginPrompt(boolean useConsole) throws Exception {
		if (useConsole) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					System.in));
			System.out.print("Username: ");
			try {
				this.setUsername(in.readLine().trim());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.print("Password: ");
			try {
				this.setPassword(in.readLine().trim());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				this.connectionInfo.setClientSocket(new DatagramSocket(this
						.getConnectionInfo().getClientPort()));
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
		}
		
		this.sendHelloToServer();
	}

	public void sendHelloToServer() throws Exception {
		String message = MessageType.CLIENT_SERVER_HELLO.createMessage("HELLO");
		byte[] messageBytes = null;
		try {
			messageBytes = message.getBytes(CryptoLibrary.CHARSET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		DatagramPacket packet = new DatagramPacket(messageBytes,
				messageBytes.length, this.getConnectionInfo().getServerIp(),
				this.getConnectionInfo().getServerPort());

		try {
			this.connectionInfo.getClientSocket().send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}

	/* Send a message to given ip and port */
	public void sendMessage(String message, MessageType messageType,
			InetAddress destIp, int destPort) {
		message = messageType.createMessage(message);
		byte[] messageBytes;

		try {
			messageBytes = message.getBytes(CryptoLibrary.CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}

		DatagramPacket packet = new DatagramPacket(messageBytes,
				messageBytes.length, destIp, destPort);

		try {
			this.connectionInfo.getClientSocket().send(packet);
		} catch (IOException e) {
			System.out.println("Error sending packet");
			e.printStackTrace();
			return;
		}
	}

	public void destoryKeys() {
		this.setdhKeyPair(null);
		this.setSecretKey(null);
		this.setUserId(null);
	}
}
package src.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.UUID;

import javax.crypto.SecretKey;

import src.common.CookieManager;
import src.common.CryptoLibrary;
import src.common.HeaderHandler;
import src.common.MessageType;
import src.common.NonceManager;
import src.common.TicketManager;
import src.common.TimestampManager;
import src.common.CryptoLibrary.DecryptionException;
import src.common.CryptoLibrary.EncryptionException;
import src.common.CryptoLibrary.KeyCreationException;
import src.common.MessageType.UnsupportedMessageTypeException;

/* Handles all the UDP messages received by the server
 * All communication between client and server is UDP
 */
public class MessageHandler implements Runnable {

	private Server server;
	private String message;
	private InetAddress clientIp;
	private int clientPort;
	private DatagramSocket outSocket;

	/* Constructor */
	public MessageHandler(Server server, String message, InetAddress ipAddress,
			int port, DatagramSocket outSocket) {
		this.server = server;
		this.message = message;
		this.clientIp = ipAddress;
		this.clientPort = port;
		this.outSocket = outSocket;
	}

	public void run() {
		// Verify packet header length
		if (message.length() < 2) {
			System.out.println("Invalid message");

		} else {
			MessageType type = null;
			try {
				type = MessageType.getMessageType(message);
			} catch (UnsupportedMessageTypeException e) {
				System.out.println("Invalid message received");
				return;
			}

			message = message.substring(2);

			switch (type) {
			case CLIENT_SERVER_HELLO:
				this.helloResponse(message);
				break;
			case CLIENT_SERVER_AUTH:
				this.authenticateClient(message);
				break;
			case CLIENT_SERVER_VERIFY:
				this.authenticationComplete(message);
				break;
			case CLIENT_SERVER_LIST:
				this.listLoggedInUsers(message);
				break;
			case CLIENT_SERVER_TALK_REQUEST:
				this.ticketToUser(message);
				break;
			case CLIENT_SERVER_LOGOUT:
				this.logoutUser(message);
				break;
			case CLIENT_SERVER_PING:
				this.clientPing(message);
				break;
			default:
				break;

			}
		}
	}

	private void sendMessage(String message, MessageType messageType) {
		message = messageType.createMessage(message);

		byte[] messageBytes;
		try {
			messageBytes = message.getBytes(CryptoLibrary.CHARSET);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}

		DatagramPacket packet = new DatagramPacket(messageBytes,
				messageBytes.length, this.clientIp, this.clientPort);
		try {
			outSocket.send(packet);
		} catch (IOException e) {
			System.out.println("Error sending packet");
			e.printStackTrace();
			return;
		}
	}

	private void helloResponse(String message) {
		try {
			this.sendMessage(
					String.valueOf(CookieManager.generateCookie(this.clientIp)),
					MessageType.SERVER_CLIENT_COOKIE);
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			return;
		}
	}

	private void authenticateClient(String message) {
		ArrayList<String> response = HeaderHandler.unpack(message);

		if (!CookieManager.verifyCookie(this.clientIp, response.get(0))) {
			System.out.print("DEBUG: Wrong Coookie");
			return;
		}

		String authRequest = null;
		try {
			SecretKey key;

			key = CryptoLibrary.aesCreateKey(CryptoLibrary.rsaDecrypt(
					server.serverInfo.getServerPrivateKey(), response.get(1))
					.getBytes(CryptoLibrary.CHARSET));

			authRequest = CryptoLibrary.aesDecrypt(key, response.get(2));

		} catch (DecryptionException e) {
			System.out.println("Error decrypting authentication request:");
			e.printStackTrace();
			return;
		} catch (KeyCreationException e) {
			System.out.println("Error creating key from authentication");
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}

		final ArrayList<String> decryptedList = HeaderHandler
				.unpack(authRequest);
		final String clientNonce = decryptedList.get(3);

		final String username = decryptedList.get(0);

		if (!this.server.isRegistered(username)) {
			System.out.println(username + " is not resgistered");
			return;
		}

		if (this.server.onlineUsers.isOnline(username)) {
			System.out.println(username + " is already online");
			return;
		}

		final UserInfo user = this.server.getRegisteredUser(username);

		String validationHash = CryptoLibrary
				.generateValidationHash(decryptedList.get(1));
		if (!validationHash.equals(user.getPasswordHash())) {
			System.out
					.print("Password doesn't match for " + user.getUsername());
			return;
		}

		if (this.server.onlineUsers.isOnline(clientPort, clientIp)) {
			System.out.println("Client is online: Same port and IP address");
			return;
		}

		PublicKey publicKey = null;
		SecretKey secretKey = null;

		try {
			final KeyPair keyPair = CryptoLibrary.dhGenerateKeyPair();
			final PrivateKey privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			secretKey = CryptoLibrary.dhGenerateSecretKey(privateKey,
					decryptedList.get(2).getBytes(CryptoLibrary.CHARSET));
		} catch (KeyCreationException e) {
			System.out.println("Error generating diffie hellman key");
			e.printStackTrace();
			return;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		}

		UUID priorId = user.getUserId();
		if (priorId != null) {
			this.server.logoutUser(priorId);
		}

		final UUID userId = UUID.randomUUID();
		user.setUserId(userId);
		String encryptedResponse = null;

		try {

			final String[] encryptedResponseParams = new String[3];
			final long serverNonce = NonceManager.generateNonce();
			encryptedResponseParams[0] = userId.toString();
			encryptedResponseParams[1] = clientNonce;
			encryptedResponseParams[2] = String.valueOf(serverNonce);
			encryptedResponse = CryptoLibrary.aesEncrypt(secretKey,
					HeaderHandler.pack(encryptedResponseParams));

		} catch (EncryptionException e) {
			System.out.println("Error performing encryption");
			e.printStackTrace();
			return;
		}

		user.setUserIp(this.clientIp);
		user.setUserPort(this.clientPort);
		user.setUserSessionKey(secretKey);

		final String responseParams[] = new String[3];
		try {
			String dhpublicKey = new String(publicKey.getEncoded(),
					CryptoLibrary.CHARSET);
			responseParams[0] = dhpublicKey;

			responseParams[1] = new String(CryptoLibrary.sign(
					server.serverInfo.getServerPrivateKey(), dhpublicKey),
					CryptoLibrary.CHARSET);
			responseParams[2] = encryptedResponse;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		final String messageResponse = HeaderHandler.pack(responseParams);

		sendMessage(messageResponse, MessageType.SERVER_CLIENT_AUTH);
	}

	private void authenticationComplete(String message) {
		final ArrayList<String> responseReceived = HeaderHandler
				.unpack(message);
		UUID userId = UUID.fromString(responseReceived.get(0));
		UserInfo user = this.server.getRegisteredUser(userId);
		if (user == null) {
			System.out.println("User doesn't exist with UserId " + userId);
		}
		try {
			long nonce = Long.valueOf(CryptoLibrary.aesDecrypt(
					user.getSessionKey(), responseReceived.get(1)));
			if (NonceManager.verifyNonce(nonce)) {
				System.out.println(user.getUsername() + " logged in");
			} else {
				System.out.println("Authentication Incomplete: Wrong Nonce");
				return;
			}

		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DecryptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		user.setLastPinged(System.currentTimeMillis());
		this.server.loginUser(userId, user);
	}

	private void listLoggedInUsers(String message) {
		final ArrayList<String> listRequest = HeaderHandler.unpack(message);
		UserInfo user = this.server.onlineUsers.getUser(UUID.fromString(listRequest
				.get(0)));

		if (user == null) {
			System.out.println("User doesn't exist in online users");
			return;
		}

		String decryptedMessage;
		try {
			decryptedMessage = CryptoLibrary.aesDecrypt(user.getSessionKey(),
					listRequest.get(1));
		} catch (DecryptionException e) {
			System.out.println("Error decrypting user listing string");
			e.printStackTrace();

			return;
		}

		final ArrayList<String> decryptedParams = HeaderHandler
				.unpack(decryptedMessage);
		final Long timestamp = Long.valueOf(decryptedParams.get(1));

		if (TimestampManager.isExpired(timestamp)) {
			System.out.println("Expired timestamp");
			return;
		}

		final String[] returnParams = new String[2];
		returnParams[0] = this.server.onlineUsers.getUsers();
		returnParams[1] = String.valueOf(timestamp + 1);

		String encryptedReturn;

		try {
			encryptedReturn = CryptoLibrary.aesEncrypt(user.getSessionKey(),
					HeaderHandler.pack(returnParams));
			sendMessage(encryptedReturn, MessageType.SERVER_CLIENT_LIST);
		} catch (EncryptionException e) {
			System.out.println("Error encrypting user list");
			e.printStackTrace();
			return;
		}
	}

	private void ticketToUser(String message) {
		final ArrayList<String> talkRequest = HeaderHandler.unpack(message);
		UserInfo from = this.server.onlineUsers.getUser(UUID.fromString(talkRequest
				.get(0)));

		if (from == null) {
			System.out.println("User doesn't exist in online users");
			return;
		}

		String decryptedMessage;
		try {
			decryptedMessage = CryptoLibrary.aesDecrypt(from.getSessionKey(),
					talkRequest.get(1));
		} catch (DecryptionException e) {
			System.out.println("Error decrypting user talk request");
			e.printStackTrace();
			return;
		}

		final ArrayList<String> decryptedParams = HeaderHandler
				.unpack(decryptedMessage);
		final Long timestamp = Long.valueOf(decryptedParams.get(2));

		if (TimestampManager.isExpired(timestamp)) {
			System.out.println("Expired talk request timestamp");
			return;
		}

		UserInfo to = null;

		try {
			String toUserName = decryptedParams.get(1);
			if (!this.server.isRegistered(toUserName)) {
				System.out.println(toUserName + " is not registered");
				return;
			}

			to = this.server.onlineUsers.getUser(decryptedParams.get(1));
			if (to == null) {
				System.out.println(decryptedParams.get(1) + " is not online");
				return;
			}

			SecretKey tempSessionKey = null;
			try {
				tempSessionKey = CryptoLibrary.aesGenerateKey();
			} catch (KeyCreationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}

			String[] ticket = TicketManager.getTicket(from.getUsername(),
					to.getUsername(), from.getUserId(), tempSessionKey);
			String[] talkResponse = new String[7];
			talkResponse[0] = to.getUsername();
			talkResponse[1] = to.getUserIp().getHostAddress();
			talkResponse[2] = String.valueOf(to.getUserPort());
			talkResponse[3] = new String(tempSessionKey.getEncoded(),
					CryptoLibrary.CHARSET);
			talkResponse[4] = CryptoLibrary.aesEncrypt(to.getSessionKey(),
					HeaderHandler.pack(ticket));
			talkResponse[5] = String.valueOf(timestamp + 1);
			talkResponse[6] = to.getUserId().toString();
			String messageToSend = CryptoLibrary.aesEncrypt(
					from.getSessionKey(), HeaderHandler.pack(talkResponse));
			sendMessage(messageToSend, MessageType.SERVER_CLIENT_TICKET);

		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			e.printStackTrace();
			return;
		}
	}

	private void logoutUser(String message) {
		final ArrayList<String> ResponseReceived = HeaderHandler
				.unpack(message);
		UUID userId = UUID.fromString(ResponseReceived.get(0));
		UserInfo user = this.server.onlineUsers.getUser(userId);
		if (user == null) {
			System.out.println("User is not online");
			return;
		}

		String decryptedMessage = null;
		try {
			decryptedMessage = CryptoLibrary.aesDecrypt(user.getSessionKey(),
					ResponseReceived.get(1));
		} catch (DecryptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		final ArrayList<String> logoutParams = HeaderHandler
				.unpack(decryptedMessage);
		final Long timestamp = Long.valueOf(logoutParams.get(1));

		if (TimestampManager.isExpired(timestamp)) {
			System.out.println("Expired logout timestamp");
			return;
		}

		String logoutResponse = String.valueOf(timestamp + 1);
		String encryptedResponse = null;
		try {
			encryptedResponse = CryptoLibrary.aesEncrypt(user.getSessionKey(),
					logoutResponse);
		} catch (EncryptionException e) {
			// TODO: handle exception
			e.printStackTrace();
			return;
		}

		sendMessage(encryptedResponse, MessageType.SERVER_CLIENT_LOGOUT);
		System.out.println(user.getUsername() + " logged out");
		this.server.logoutUser(userId);
	}

	/* Handle a client ping response */
	private void clientPing(String message) {
		final ArrayList<String> params = HeaderHandler.unpack(message);
		final UserInfo user = server.getOnlineUser(UUID.fromString(params.get(0)));

		if (user == null) {
			sendMessage("", MessageType.SERVER_CLIENT_REAUTHENTICATE);
			return;
		}

		String decryptedMessage;
		try {
			decryptedMessage = CryptoLibrary.aesDecrypt(user.getSessionKey(),
					params.get(1));
		} catch (DecryptionException e) {
			System.out.println("Error decrypting ping");
			e.printStackTrace();
			return;
		}

		final ArrayList<String> pingParams = HeaderHandler
				.unpack(decryptedMessage);
		final Long timestamp = Long.valueOf(pingParams.get(1));
		final Long currentTime = System.currentTimeMillis();

		if (Math.abs(timestamp - currentTime) >= 2 * 60 * 1000) {
			System.out.println("Expired timestamp in ping");
			return;
		}

		user.setLastPinged(currentTime);
		
		sendMessage("", MessageType.SERVER_CLIENT_PING_RESPONSE);
	}
}

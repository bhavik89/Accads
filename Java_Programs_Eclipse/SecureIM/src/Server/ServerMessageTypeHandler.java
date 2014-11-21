package Server;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import Generic.CryptoOperations;
import Generic.CryptoOperations.DecryptionException;
import Generic.CryptoOperations.EncryptionException;
import Generic.CryptoOperations.KeyCreationException;
import Generic.MessagePackUnpack;
import Generic.MessageTypeHandler;
import Generic.MessageTypeHandler.UnsupportedMessageTypeException;
import Generic.CookieManager;

public class ServerMessageTypeHandler implements Runnable{
	
	private Server server;
	private String message;
	private InetAddress clientIp;
	private int clientPort;
	private DatagramSocket outSocket;
	
	/* Constructor */
	public ServerMessageTypeHandler(Server server, String message, InetAddress ipAddress, int port, DatagramSocket outSocket) {
		this.server = server;
		this.message = message;
		this.clientIp = ipAddress;
		this.clientPort = port;
		this.outSocket = outSocket;
	}

	@Override
	public void run() {
		if (message.length() < 2) {
			System.out.println("Invalid message");

		} else {
			MessageTypeHandler type = null;
			try {
				type = MessageTypeHandler.getMessageType(message);
			} catch (UnsupportedMessageTypeException e) {
				System.out.println("Invalid message received");
				return;
			}

			message = message.substring(2);
			
			System.out.println(type);
			System.out.println(message);
			
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
			}
	}
	
	}

	private void authenticationComplete(String message2) {
		// TODO Auto-generated method stub
		
	}

	private void authenticateClient(String message) {
		
		//ArrayList<String> response = MessagePackUnpack.unpack(message);
        /*
		if (!CookieManager.verifyCookie(this.clientIp,  response.get(0))) {
			System.out.print("DEBUG: Wrong Coookie");
			return;
		}else
			System.out.println("Cookie Verified!");
		
		System.out.println("Enc Message:" + response.get(1));		
	   */			
		String authRequest = rsaDecryptAES(message, "serverPrivateKey.key");
		System.out.println("DECRYPTED: " + authRequest);
	
		
		/*
		String AESKey = null;
		//AESKey = rsaDecryptAES(response.get(1), this.server.getServerPvtKeyFile());
		try {
			AESKey = rsaDecryptAES(response.get(1), "serverPrivateKey.key");
			System.out.println("AES Key Recovered Successfully!");
			System.out.println("\nRecoveres Size: "+ AESKey.getBytes(CryptoOperations.CHARSET).length);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SecretKey key = null;
		try {
			key = CryptoOperations.aesCreateKey(AESKey.getBytes(CryptoOperations.CHARSET));
		} catch (KeyCreationException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			System.out.println("AES Key--" +  new String(key.getEncoded(), CryptoOperations.CHARSET));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//AESKey = CryptoOperations.aesCreateKey(CryptoOperations.rsaDecrypt(CryptoOperations.readPrivateKey(this.server.getServerPvtKeyFile()), response.get(1)).getBytes(CryptoOperations.CHARSET));			
		
		
		
		try {
			byte[] iv = response.get(3).getBytes(CryptoOperations.CHARSET);
			//SecretKey key;
			//key = CryptoOperations.aesCreateKey(CryptoOperations.rsaDecrypt(CryptoOperations.readPrivateKey(this.server.getServerPvtKeyFile()), response.get(1)).getBytes(CryptoOperations.CHARSET));			
			//key  = CryptoOperations.rsaDecrypt(CryptoOperations.readPrivateKey(this.server.getServerPvtKeyFile()), response.get(1));		
			Cipher AESCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			AESCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
			
			authRequest = new String (AESCipher.doFinal(response.get(2).getBytes(CryptoOperations.AES_CIPHER)));//CryptoOperations.aesDecrypt(key, response.get(2));

		} catch (Exception e) {
			System.out.println("Error!");
			e.printStackTrace();
		}

		
		final ArrayList<String> decryptedList = MessagePackUnpack.unpack(authRequest);
		final String clientTimeStamp = decryptedList.get(3);
		final String username = decryptedList.get(0);
		System.out.println();
		System.out.println("Received Username:" + username);
		System.out.println("Received Password:" + decryptedList.get(1));
       */  
		/*
		if (!this.server.isRegistered(username)) {
			System.out.println(username + " is not resgistered");
			return;
		}

		if (this.server.onlineUsers.isOnline(username)) {
			System.out.println(username + " is already online");
			return;
		}

		final UserInfo user = this.server.getRegisteredUser(username);
        */
		
		
		//String validationHash = CryptoOperations.generateValidationHash(decryptedList.get(1));
		
		/*
		if (!validationHash.equals(user.getPasswordHash())) {
			System.out
					.print("Password doesn't match for " + user.getUsername());
			return;
		}

		if (this.server.onlineUsers.isOnline(clientPort, clientIp)) {
			System.out.println("Client is online: Same port and IP address");
			return;
		}
		*/

		/* 
		PublicKey publicKey = null;
		SecretKey secretKey = null;

		try {
			final KeyPair keyPair = CryptoOperations.dhGenerateKeyPair();
			final PrivateKey privateKey = keyPair.getPrivate();
			publicKey = keyPair.getPublic();
			secretKey = CryptoOperations.dhGenerateSecretKey(privateKey,
					decryptedList.get(2).getBytes(CryptoOperations.CHARSET));
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
			encryptedResponse = CryptoOperations.aesEncrypt(secretKey,
					MessagePackUnpack.pack(encryptedResponseParams));

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
					CryptoOperations.CHARSET);
			responseParams[0] = dhpublicKey;

			responseParams[1] = new String(CryptoOperations.sign(
					server.serverInfo.getServerPrivateKey(), dhpublicKey),
					CryptoOperations.CHARSET);
			responseParams[2] = encryptedResponse;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		final String messageResponse = MessagePackUnpack.pack(responseParams);

		sendMessage(messageResponse, MessageTypeHandler.SERVER_CLIENT_AUTH);
		*/
		
	}

	private String rsaDecryptAES(String data, String ServerPrivateKeyFile) {
		
		System.out.println(ServerPrivateKeyFile);
		
		byte[] decryptedData = null;
		
		try{
			
			PrivateKey serverPrivate = this.server.getServerPrivateKeyFromFile(ServerPrivateKeyFile);
			Cipher RSACipher = Cipher.getInstance("RSA/ECB/NOPadding");
			
			RSACipher.init(Cipher.DECRYPT_MODE, serverPrivate); 
			return  new String(RSACipher.doFinal(data.getBytes(CryptoOperations.CHARSET)), CryptoOperations.CHARSET);
			//decryptedData = RSACipher.doFinal(data);  
            //System.out.println("Decrypted Data: " + new String(decryptedData)); 
			
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}

	private void helloResponse(String message) {
		try {
			this.sendMessage(
					String.valueOf(CookieManager.generateCookie(this.clientIp)),
					MessageTypeHandler.SERVER_CLIENT_COOKIE);
		} catch (Exception e) {
			System.out.println("Exception:" + e.toString());
			return;
		}
	}

	private void sendMessage(String message, MessageTypeHandler messageType) {
		
		message = messageType.createMessage(message);

		byte[] messageBytes;
		messageBytes = message.getBytes();

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

}

package Client;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import Generic.*;
import Generic.CryptoOperations.EncryptionException;
import Generic.CryptoOperations.KeyCreationException;
import Generic.MessageTypeHandler.UnsupportedMessageTypeException;


public class UDPMessages {
	
	private String message;
	private Client client;
	private int destinationPort;
	private InetAddress destinationIp;

	//private static PingAction pinger;
	//private static Thread pingerThread;
	
	public UDPMessages(Client client, 
						String message,
						InetAddress destinationIp, 
						int destinationPort) {
		this.client = client;
		this.message = message;
		this.destinationIp = destinationIp;
		this.destinationPort = destinationPort;
	}
	
	public void messageAction() throws KeyCreationException {
		
		if (message.length() < 2) {
			System.out.println("Invalid message");

		} else {
			MessageTypeHandler type = null;
			try {
				type = MessageTypeHandler.getMessageType(message);

			} catch (UnsupportedMessageTypeException e) {
				System.out.println("Invalid message type received via UDP");
				return;
			}

			String typeId = message.substring(0, 2);
			message = message.substring(2);
		
			switch (type) {
			case SERVER_CLIENT_COOKIE:
				this.startServerAuth(message);
				break;
			case SERVER_CLIENT_AUTH:
				this.authenticationCompleteWithServer(message);
				break;
			default:
				break;
			}
	}

	}

	private void sendMessage(String string, MessageTypeHandler messageType) throws IOException {
		this.client.sendUDPMessage(string, messageType);
	}

	/*
	private void sendMessage(String message, MessageTypeHandler messageType, InetAddress destIp, int destPort) throws IOException {
		this.client.sendUDPMessage(message, messageType);
	}*/
	
	private void startServerAuth(String message) {
		
		String[] serverCookieResponse = new String[2];
		
		long serverCookieResponseTimestamp = TimeStamps.getTimestamp();
		
		try{
		this.client.setDhKeyPair(CryptoOperations.dhGenerateKeyPair());
		
				//Generate AES key object for AES Encryption
				KeyGenerator genAESKey = KeyGenerator.getInstance("AES");
				
				//Generate AES key size of key size specified above (here it is 128 bits)
				genAESKey.init(128);
				
				
				//Generate AES key
				SecretKey AESKey = genAESKey.generateKey(); // CryptoOperations.aesGenerateKey();
				System.out.println("\nKeySize: " + AESKey.getEncoded().length);
				System.out.println("Generated AES Key...");
				
				//Encrypt AES Secret key with destination's PublicKey
				String RSAencryptedAESKey = rsaEncrypt(new String(AESKey.getEncoded(), CryptoOperations.CHARSET), "serverPublicKey.key");
				System.out.println("\nAES Key --" + new String(AESKey.getEncoded(), CryptoOperations.CHARSET));
				
				
				
        				
		
		/*byte[] usernameBytes = this.client.getUsername().getBytes(CryptoOperations.CHARSET);
		byte[] passwordHashBytes = (CryptoOperations.generateValidationHash(this.client.getPassword())).getBytes(CryptoOperations.CHARSET);
		byte[] clientDHPartBytes = (new String(this.client.getDhKeyPair().getPublic().getEncoded(), CryptoOperations.CHARSET)).getBytes(CryptoOperations.CHARSET);
		byte[] serverCookieResponseTimestampBytes = String.valueOf(serverCookieResponseTimestamp).getBytes(CryptoOperations.CHARSET);
		*/
		//DataOutputStream serverCookieResponse = new DataOutputStream(new ByteArrayOutputStream());
		
		//serverCookieResponse.writeInt(RSAencryptedAESKey.length);
		//serverCookieResponse.write(RSAencryptedAESKey);
		
		//serverCookieResponse.writeInt(usernameBytes.length);
		//serverCookieResponse.writeInt(passwordHashBytes.length);
		//serverCookieResponse.writeInt(clientDHPartBytes.length);
		//serverCookieResponse.writeInt(serverCookieResponseTimestampBytes.length);
		//serverCookieResponse.write(usernameBytes);
		//serverCookieResponse.write(passwordHashBytes);
		//serverCookieResponse.write(clientDHPartBytes);
		//serverCookieResponse.write(serverCookieResponseTimestampBytes);
		
		//byte[] encryptedserverCookieResponse = rsaEncrypt(serverCookieResponse, this.client.getServerPubKeyFile());
		
		Cipher AESCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AESCipher.init(Cipher.ENCRYPT_MODE, AESKey);		
		byte[] iv = AESCipher.getIV();
		
		serverCookieResponse[0] = this.client.getUsername();
		serverCookieResponse[1] = CryptoOperations.generateValidationHash(this.client.getPassword());
		//serverCookieResponse[2] = new String(this.client.getDhKeyPair().getPublic().getEncoded(), CryptoOperations.CHARSET);
		//serverCookieResponse[3] = String.valueOf(serverCookieResponseTimestamp);
		
		//String encryptedMessage = new String(AESCipher.doFinal((MessagePackUnpack.pack(serverCookieResponse)).getBytes(CryptoOperations.CHARSET)));
		
		//SecretKey key = CryptoOperations.aesGenerateKey();
		
		//String encryptedMessage = CryptoOperations.aesEncrypt(AESKey, MessagePackUnpack.pack(serverCookieResponse));
		
		//String encryptedKey = CryptoOperations.rsaEncrypt(CryptoOperations.readPublicKey(this.client.getServerPubKeyFile()), 
														//	new String(key.getEncoded(), CryptoOperations.CHARSET));
		
		
		String[] response = new String[2];
		response[0] = message;
		response[1] = rsaEncrypt(MessagePackUnpack.pack(serverCookieResponse), "serverPublicKey.key");//RSAencryptedAESKey;
		//response[2] = encryptedMessage;
		//response[3] = new String(iv);
		//response[1] = new String(encryptedserverCookieResponse);
		//response[1] = encryptedKey;
		//response[2] = encryptedMessage;
		//System.out.println("Cookie Response:" + MessagePackUnpack.pack(response));
		sendMessage(rsaEncrypt("HelloWassup", "serverPublicKey.key"), MessageTypeHandler.CLIENT_SERVER_AUTH);		
		
		}
		catch (Exception e){
			System.out.println("Unable to send serverCookieReplyPacket to server");
			e.printStackTrace();
			return;
		}
	}

	//Encrypt the Secret AES key with receiver's public key
		private static String rsaEncrypt(String data, String receiverPublicKey) 
									throws NoSuchAlgorithmException, 
										   NoSuchPaddingException, 
									       InvalidKeyException, 
									       IllegalBlockSizeException, 
									       IOException, BadPaddingException {
			
			  //byte[] dataToEncrypt = aESKey.toString().getBytes();
			  String encryptedData = null;
			  //get receiver's public key
			  PublicKey pubKey = (PublicKey) readKeyFromFile(receiverPublicKey, "public");
			  
			  //Generate RSA cipher for AES Secret key
			  Cipher cipher = Cipher.getInstance("RSA/ECB/NOPadding");
				cipher.init(Cipher.ENCRYPT_MODE, pubKey);

				encryptedData =  new String(cipher.doFinal(data.getBytes(CryptoOperations.CHARSET)), CryptoOperations.CHARSET);
			  //System.out.println(new String(encryptedData));
			  return encryptedData;
			  //byte[] cipherData = cipher.wrap((Key) serverCookieResponse);
			  //return cipherData;		
		}

		//Gets the required key from the given keyFile, return key Object
		private static Object readKeyFromFile(String keyFile, String fileType) 
												throws IOException {
			
			//Input stream for the given key file 
			InputStream fileStream = new FileInputStream(keyFile);
			
			ObjectInputStream input =   new ObjectInputStream(new BufferedInputStream(fileStream));
			
			try {
				    BigInteger mod = (BigInteger) input.readObject();
				    BigInteger exp = (BigInteger) input.readObject();
				    KeyFactory fact = KeyFactory.getInstance("RSA");
				    
				    //Get public key
				    if (fileType == "public"){
				    	RSAPublicKeySpec keySpec = new RSAPublicKeySpec(mod, exp);			    
				    	return fact.generatePublic(keySpec);
				    }
				    //Get private key
				    else if (fileType == "private"){
					    RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(mod, exp);			    
					    return fact.generatePrivate(keySpec);
					    }
				    else
				    	return null;
				  } catch (Exception e) {
				    throw new RuntimeException("Runtime Error", e);
				  } finally {
					  input.close();
				  }
			}

	private void authenticationCompleteWithServer(String message2) {
		// TODO Auto-generated method stub
		
	}
}

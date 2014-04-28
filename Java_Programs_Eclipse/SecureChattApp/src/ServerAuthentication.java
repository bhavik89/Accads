

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;



public class ServerAuthentication implements Serializable {


	public static ArrayList<Object> ServerAuthenticate(Socket connectionSocket, ArrayList<String> clientInformationData) throws Exception{

		ArrayList<Object> status = new ArrayList<>();
		String stat;
		System.out.println("Start Authentication now");

		//generate an object N1(nonce) to send to client authentication
		byte[] message = ServerAuthentication.nonceGeneration();
		//System.out.println(message);
		RegisterLoginProcessing.ServerSendStream(connectionSocket, message);
		//System.out.println("send 1");


		//Receive the authentication message from Client
		ArrayList<Object> serverAuthMessage = (ArrayList<Object>) RegisterLoginProcessing.ReceiveDataStream(connectionSocket);
		//System.out.println("recieve 1");
		if(serverAuthMessage.get(0).equals(clientInformationData.get(0)) && Arrays.equals((byte[]) serverAuthMessage.get(1),message)){

			System.out.println("Saved from Replay, Carry onn.......");

			//decrypt this array with Hash of Password
			byte[] key = clientInformationData.get(1).getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16); // use only first 128 bit
			// Generate the secret key specs.
			SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
			ArrayList<Object> decrypted = (ArrayList<Object>) ServerCryptoAlgos.decryptData((SealedObject) serverAuthMessage.get(2), keySpec, "AES");

			PublicKey clientPub = (PublicKey) decrypted.get(0);
			//System.out.println("Public Key Obtained" + clientPub);
			byte[] N2 = (byte[]) decrypted.get(1);
			//System.out.println(N2);

			//Now generate a secret key to connect to client
			SecretKey clientServer = ServerCryptoAlgos.obtainSecretKey();
			Server.clientServKey.put(clientInformationData.get(0), clientServer);
			//Save the secret Key of corresponding users

			//Save public key Secret Key and UserName
			Server.clientPubKeys.put(clientInformationData.get(0), clientPub);

			ArrayList<Object> authenMess = new ArrayList<>();
			authenMess.add(decrypted.get(1));//n2
			authenMess.add(ServerAuthentication.nonceGeneration());//n3
			//System.out.println(authenMess);

			SealedObject Authhhh = ServerCryptoAlgos.dataEncryption(authenMess, clientServer,"AES");

			SealedObject authMess2 = ServerCryptoAlgos.dataEncryption(clientServer, clientPub, "RSA/ECB/PKCS1Padding");
			ArrayList<Object> authenMess1 = new ArrayList<>();
			authenMess1.add(authMess2);//Symmetric Key Encrypted With public key is sent 
			authenMess1.add(Authhhh);// new Nonce is sent

			RegisterLoginProcessing.ServerSendStream(connectionSocket, authenMess1);
			//System.out.println("send 2");
			SealedObject serverAuth2Message =  (SealedObject) RegisterLoginProcessing.ReceiveDataStream(connectionSocket);
			ArrayList<Object> decrypted1 = (ArrayList<Object>) ServerCryptoAlgos.decryptData(serverAuth2Message, clientServer, "AES");
			//System.out.println("recieve 2");
			//Check if nonce 3 is returned by the server		
			if(Arrays.equals((byte[])decrypted1.get(0), (byte[]) authenMess.get(1)))
			{
				stat = "Authentication Successful";
			}
			else{
				stat = "Authentication Failed";
				System.out.println("Authentication Failed");			

			}
			//int  = (int) serverAuth2Message.get(1);
			status.add(stat);
			status.add(decrypted1.get(1));

			RegisterLoginProcessing.ServerSendStream(connectionSocket , stat);
			//System.out.println("send 3");
		}
		return status;
	}

	public static byte[] nonceGeneration() throws Exception{

		byte [] nonce = new byte[16];
		Random rand2 = SecureRandom.getInstance("SHA1PRNG");
		rand2.nextBytes(nonce);
		return nonce;
	}
}

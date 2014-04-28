

import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map.Entry;
import java.util.Random;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class ClientAuthenticationProcess {


	public static Object ClientAuthenticate(Socket clientSocket, ArrayList<String> clientInformation) throws Exception, Exception{

		//1
		System.out.println("Start Authentication Process");
		Object getData = Client.ReceiveDataStream(clientSocket);
		//System.out.println("recieved1");//N1 printed



		//Send the Authentication Message from Client
		ArrayList<Object> authMess = new ArrayList<>();
		authMess.add(clientInformation.get(0));//uname [0]
		authMess.add(getData);//n1[1]

		//An array containing Public key and new Nonce
		ArrayList<Object> encryptMess = new ArrayList<>();
		encryptMess.add(Client.readClientPublic(clientSocket.getLocalPort() + "PublicKey"));//Public Key A sent
		byte[] N2Client = ClientAuthenticationProcess.nonceGeneration();
		encryptMess.add(N2Client);//N2 added


		//		PrivateKey clientPriv = Client.readClientPrivate(clientSocket.getLocalPort() + "PrivateKey");
		//		SealedObject seal = ClientCryptoAlgos.dataEncryption(getData, clientPriv, "RSA/ECB/PKCS1Padding");
		//		encryptMess.add(seal);//Add nonce encrypted with Private Key


		//encrypt this array with Hash of Password
		byte[] key = clientInformation.get(1).getBytes("UTF-8");
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		key = sha.digest(key);
		key = Arrays.copyOf(key, 16); // use only first 128 bit

		// Generate the secret key specs.
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		SealedObject sealObj = ClientCryptoAlgos.dataEncryption(encryptMess, keySpec, "AES");

		//Add this to N1 and username
		authMess.add(sealObj);
		Client.SendDataStream(clientSocket, authMess);
		//System.out.println("send 1");

		authMess = (ArrayList<Object>) Client.ReceiveDataStream(clientSocket);
		//System.out.println("recieve 2");
		
		//get the secret key from server
		PrivateKey clientPrivat = Client.readClientPrivate(clientSocket.getLocalPort() + "PrivateKey"); 
		SecretKey clientServerKey = (SecretKey) ClientCryptoAlgos.decryptData((SealedObject) authMess.get(0),clientPrivat,"RSA/ECB/PKCS1Padding");

		//The client stores keys of connection with server
		ClientInputProcessing.clientServerKey.put(clientInformation.get(0),clientServerKey);

		ArrayList<Object> obj2 =  (ArrayList<Object>) ClientCryptoAlgos.decryptData((SealedObject) authMess.get(1), clientServerKey, "AES");
		if(Arrays.equals((byte[]) obj2.get(0), N2Client))
		{			
			System.out.println("Key Obtained lets connect");			
		}
		else
		{
			System.out.println("Authentication error");
			clientSocket.close();
		}

		//Send the last authenticating nonce
		ArrayList<Object> obj3 = new ArrayList<>();
		obj3.add(obj2.get(1));//n3
		//The port where Listening will be done
		obj3.add(Client.port);
		SealedObject sendencrypted = ClientCryptoAlgos.dataEncryption(obj3, clientServerKey, "AES");
		Client.SendDataStream(clientSocket, sendencrypted);			
		//System.out.println("send 2");

		Object status = Client.ReceiveDataStream(clientSocket);
		//System.out.println("recieve 3");
		System.out.println(status);		

		return status;		
	}	

	public static byte[] nonceGeneration() throws Exception{

		byte [] nonce = new byte[16];
		Random rand2 = SecureRandom.getInstance("SHA1PRNG");
		rand2.nextBytes(nonce);
		//System.out.println(nonce);

		return nonce;


	}
}

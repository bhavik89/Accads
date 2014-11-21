
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.*;
import java.math.BigInteger;
import java.io.*;

import javax.crypto.*;

public class Server implements Serializable {

	static Socket connectionSocket;
	public static HashMap<String, Socket> connectionInfo = new HashMap<>();
	public static HashMap<String, Integer> userLists = new HashMap<>();
	public static String ServerPublicFile = "server_public";
	public static String ServerPrivateFile = "server_private";

	public static HashMap<String, PublicKey> clientPubKeys = new HashMap<>();
	public static HashMap<String, SecretKey> clientServKey = new HashMap<>();

	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = new ServerSocket(3030);
		System.out.println("Server Socket Created");
		Server.rsa_ServerKey();
		System.out.println("Server RSA keys generated");

		ServerLoginRegistrationProcessing.PreRegisteredUsers();


		while(true){
			//Create a socket to Accept the connection
			connectionSocket = serverSocket.accept();
			ServerThread st= new ServerThread(connectionSocket);
			st.start();
		}
	}	



	public static Object GetOnlineUsers(String usernme, Integer port){
		return Server.userLists.put(usernme, port);
	}	


	public static void rsa_ServerKey() throws Exception {
		// TODO Auto-generated method stub
		File serverPublicKey = new File(ServerPublicFile);
		File serverPrivateKey = new File(ServerPrivateFile);

		if (!serverPublicKey.exists()) 
		{
			serverPublicKey.createNewFile();
		}
		if (!serverPrivateKey.exists())
		{
			serverPrivateKey.createNewFile();
		}

		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
		keyGen.initialize(2048);

		KeyPair pair = keyGen.genKeyPair();
		KeyFactory factory = KeyFactory.getInstance("RSA");

		RSAPublicKeySpec publicKey = factory.getKeySpec(pair.getPublic(),RSAPublicKeySpec.class);
		saveKeyInFile(ServerPublicFile, publicKey.getModulus(), publicKey.getPublicExponent());

		RSAPrivateKeySpec privateKey = factory.getKeySpec(pair.getPrivate(),RSAPrivateKeySpec.class);
		saveKeyInFile(ServerPrivateFile, privateKey.getModulus(), privateKey.getPrivateExponent());
	}

	private static void saveKeyInFile (String file, BigInteger mod, BigInteger exp) throws Exception {

		ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(file)));

		try {

			outStream.writeObject(mod);
			outStream.writeObject(exp);

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			outStream.close();
		}
	}


	public static PublicKey readServerPublic (String fileName) throws Exception {

		InputStream in = new FileInputStream(fileName);
		ObjectInputStream inputStream =	new ObjectInputStream(new BufferedInputStream(in));

		try {
			BigInteger mod = (BigInteger) inputStream.readObject();
			BigInteger exp = (BigInteger) inputStream.readObject();
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(mod, exp);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PublicKey publicKey = factory.generatePublic(keySpec);

			return publicKey;

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			inputStream.close();
		}
	}


	public static PrivateKey readServerPrivate(String fileName) throws Exception {

		InputStream is = new FileInputStream(fileName);
		ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(is));

		try {

			BigInteger mod = (BigInteger) inputStream.readObject();
			BigInteger exp = (BigInteger) inputStream.readObject();
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(mod, exp);
			KeyFactory factory = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = factory.generatePrivate(keySpec);

			return privateKey;

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			inputStream.close();
		}
	}


}
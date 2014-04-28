
import java.io.*;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Client implements Serializable {

	static Integer port;
	static Socket clientSocket;
	static String filePubName;
	static String filePrivName;

	public static void main(String[] args) throws Exception {

		String addrPort = readFile("server.con");
		createsock(addrPort);
		//clientSocket = new Socket("localhost", 3030);
		System.out.println("Welcome to Secure CHAT Protocol");

		filePubName = clientSocket.getLocalPort() + "PublicKey";
		filePrivName = clientSocket.getLocalPort() + "PrivateKey";

		Client.rsa_ClientKey(filePubName, filePrivName);
		//port = clientSocket.getLocalPort() + 10;
		//Create a ServerSocket to listen to other connections 
		//ServerSocket clientListenSocket = new ServerSocket();

		ClientThread ct = new ClientThread(clientSocket);
		ct.start();		
		ClientInputProcessing.ClientProcess(clientSocket);

	}



	//Receive from client
	public static Object ReceiveDataStream(Socket receiveSocket) throws IOException, ClassNotFoundException
	{
		ObjectInputStream getMessage = new ObjectInputStream(receiveSocket.getInputStream());
		Object receiveData = getMessage.readObject();
		return receiveData;		
	}

	//Sending from Client
	public static void SendDataStream(Socket sendingSocket, Object dataSent ) throws IOException
	{
		ObjectOutputStream sendStream = new ObjectOutputStream(sendingSocket.getOutputStream());
		sendStream.writeObject(dataSent);
	}

	//Take input from Client
	public static String InputFromClient() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String message = br.readLine();	
		return message;		
	}

	//Hash the entered Password
	public static String HashThePassword(String passwd) throws NoSuchAlgorithmException{

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");		
		messageDigest.update(passwd.getBytes());
		byte encryptedpasswd [] = messageDigest.digest();
		//System.out.println(encryptedpasswd);

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< encryptedpasswd.length; i++)
		{
			sb.append(Integer.toString((encryptedpasswd[i] & 0xff) + 0x100, 16).substring(1));
		}
		String generatedPassword = sb.toString();
		return generatedPassword;
	}		




	public static void rsa_ClientKey(String filePubName, String filePrivName) throws Exception {

		File serverPublicKey = new File(filePubName);
		File serverPrivateKey = new File(filePrivName);

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
		saveKeyInFile(filePubName, publicKey.getModulus(), publicKey.getPublicExponent());

		RSAPrivateKeySpec privateKey = factory.getKeySpec(pair.getPrivate(),RSAPrivateKeySpec.class);
		saveKeyInFile(filePrivName, privateKey.getModulus(), privateKey.getPrivateExponent());
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


	public static PublicKey readClientPublic (String fileName) throws Exception {

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


	public static PrivateKey readClientPrivate(String fileName) throws Exception {

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


	public static void FileDelete(String filePubName, String filePrivName){

		File serverPublicKey = new File(filePubName);
		File serverPrivateKey = new File(filePrivName);

		serverPrivateKey.delete();
		serverPublicKey.delete();
	}

	//Read From Configuration file
	public static String readFile(String pathname) throws IOException {

		File file = new File(pathname);
		StringBuilder fileContents = new StringBuilder((int)file.length());
		Scanner scanner = new Scanner(file);
		String lineSeparator = System.getProperty("line.separator");

		try {
			while(scanner.hasNextLine()) {        
				fileContents.append(scanner.nextLine() + lineSeparator);
			}
			return fileContents.toString();
		} finally {
			scanner.close();
		}
	}


	public static void createsock(String addrPort) throws IOException{

		String delims = "[|]+";
		String[] tokens = addrPort.split(delims);

		String address = tokens[1];
		int port = Integer.parseInt(tokens[3]);
		clientSocket = new Socket(address, port);
		//return clientSocket;
	}
}


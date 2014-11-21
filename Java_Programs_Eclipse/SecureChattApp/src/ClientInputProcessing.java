

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;


public class ClientInputProcessing implements Serializable {

	static HashMap<String, Integer> onlineUsers;
	static String filePubName;
	static String filePrivName;

	public static HashMap<String, SecretKey> clientServerKey = new HashMap<>(); 

	public static void ClientProcess(Socket clientSocket) throws Exception
	{		 

		System.out.println("What do you wanna do?");
		System.out.println("Enter 1 to Register as new user");
		System.out.println("Enter 2 to Login ");
		System.out.println("Enter option: ");
		String opt = Client.InputFromClient();
		int option = Integer.parseInt(opt);


		switch(option){
		case 1:{
			System.out.println("");
			System.out.println("Registration under process");
			ClientInputProcessing.ClientRegistration(clientSocket);
			break;			
		}
		case 2:{
			System.out.println("");
			System.out.println("Login process");
			ClientInputProcessing.ClientLogin(clientSocket);			
			break;
		}		
		}
		ClientInputProcessing.ChooseAction(clientSocket);

	}


	private static void Logout(Socket clientSocket) throws Exception {

		System.out.println("Username: "); 
		String userId = Client.InputFromClient();

		//Take the password and create its hash by calling the Hashing Data function
		System.out.println("Password: ");
		String password = Client.InputFromClient();	
		//Get the hashvalue of the password
		String hashedPass = Client.HashThePassword(password);

		//send the username and hash of password and register to an arraylist
		ArrayList<String> clientInformation = new ArrayList<String>();
		clientInformation.add(userId);
		clientInformation.add(hashedPass);
		clientInformation.add("logout");

		PublicKey pubKey = Server.readServerPublic("server_public");
		SealedObject chatInfoEncrypt = ClientCryptoAlgos.dataEncryption(clientInformation, pubKey, "RSA/ECB/PKCS1Padding");
		Client.SendDataStream(clientSocket, chatInfoEncrypt);

		Object starmsg = Client.ReceiveDataStream(clientSocket);
		if(starmsg.toString().equalsIgnoreCase("Re-Enter Credentials")){
			ClientInputProcessing.ChooseAction(clientSocket);
		}
	}


	//Store userId and Password in an Array
	public static void ClientRegistration(Socket clientSocket) throws Exception{

		ArrayList<String> clientInformation = new ArrayList<String>();
		while(true){
			System.out.println("Username: "); 
			String userId = Client.InputFromClient();

			//Take the password and create its hash by calling the Hashing Data function
			System.out.println("Password: ");
			String password = Client.InputFromClient();	
			//Get the hashvalue of the password
			String hashedPass = Client.HashThePassword(password);
			//System.out.println(hashedPass);

			//send the username and hash of password and register to an arraylist
			clientInformation = new ArrayList<String>();
			clientInformation.add(userId);
			clientInformation.add(hashedPass);
			clientInformation.add("register");

			//get the public key of client
			PublicKey pk = Server.readServerPublic("server_public");

			SealedObject sealed = ClientCryptoAlgos.dataEncryption(clientInformation, pk, "RSA/ECB/PKCS1Padding");

			Client.SendDataStream(clientSocket, sealed);

			Object status = Client.ReceiveDataStream(clientSocket);
			System.out.println(status);
			if(status.toString().equalsIgnoreCase("Registration Successful Buddyyyy")){
				break;
			}		
		}
		ClientInputProcessing.ClientLogin(clientSocket);
	}


	public static HashMap<String,Integer> ClientLogin(Socket clientSocket) throws Exception{

		ArrayList<String> clientInformation = new ArrayList<String>();

		while(true){		

			System.out.println("Provide the Login credentials");
			System.out.println("Username: "); 
			String userId = Client.InputFromClient();

			//Take the password and create its hash by calling the Hashing Data function
			System.out.println("Password: ");
			String password = Client.InputFromClient();	

			//Get the hashvalue of the password
			String hashedPass = Client.HashThePassword(password);
			//System.out.println(hashedPass);

			//send the username and hash of password and register to an arraylist
			clientInformation.add(userId);
			clientInformation.add(hashedPass);
			clientInformation.add("login");

			//get the public key of Server
			PublicKey pk = Server.readServerPublic("server_public");

			SealedObject sealed = ClientCryptoAlgos.dataEncryption(clientInformation, pk, "RSA/ECB/PKCS1Padding");

			Client.SendDataStream(clientSocket, sealed);

			Object stat = Client.ReceiveDataStream(clientSocket);
			System.out.println(stat);
			if(stat.toString().equalsIgnoreCase("Password verified")){
				break;
			}
		}

		Object status = ClientAuthenticationProcess.ClientAuthenticate(clientSocket,clientInformation);
		System.out.println(status);

		//start the method to get list of online users
		//HashMap<String, Integer> onlineUsers = ClientInputProcessing.GetOnlineUsers(clientSocket, clientInformation);
		return onlineUsers;	
	}


	public static HashMap<String, Integer> GetOnlineUsers(Socket clientSocket) throws Exception{
		while(true){
			//Remove the login element and instead replace it with list
			//clientInformation.remove(2);
			//clientInformation.add("list");
			ArrayList<Object> sendToserver = new ArrayList<Object>();
			sendToserver.add("");
			sendToserver.add("");
			sendToserver.add("list");
			//get the public key of Server
			PublicKey pk = Server.readServerPublic("server_public");		
			SealedObject sealed = ClientCryptoAlgos.dataEncryption(sendToserver, pk, "RSA/ECB/PKCS1Padding");
			Client.SendDataStream(clientSocket, sealed);
			break;
		}
		HashMap<String, Integer> onlineUsers = (HashMap<String, Integer>) Client.ReceiveDataStream(clientSocket);
		return onlineUsers;
	}


	public static ArrayList<String> BreakInput(Object user){

		String delims = "[ ]+";
		String[] tokens = ((String) user).split(delims);

		ArrayList<String> chatInfo = new ArrayList<>();
		chatInfo.add(tokens[1]);
		chatInfo.add(tokens[0]);
		System.out.println(chatInfo);

		String mssg = "";
		for (int i = 2; i < tokens.length; i++){  
			mssg = mssg + tokens[i] + " ";
		}		
		chatInfo.add(mssg);
		return chatInfo;		
	}



	public static void MessageInput(Socket clientSocket) throws Exception{

		System.out.println("Write message in form of 'Send (username) (message)'");
		Object user = Client.InputFromClient();

		//Take input from user for checking whom to talk to and what to send
		ArrayList<String> chatInfo = new ArrayList<>();
		chatInfo = ClientInputProcessing.BreakInput(user);
		System.out.println(chatInfo);
		if(chatInfo.get(1).equalsIgnoreCase("send"))
		{
			chatInfo.add("send");
			ClientToClientInfo.ClientInfoExchange(clientSocket ,chatInfo);
		}
		else{
			ClientInputProcessing.MessageInput(clientSocket);
		}		
	}

	public static void ChooseAction(Socket clientSocket) throws Exception{
		while (true){
			System.out.println("");
			System.out.println("Enter 1 for List of Online Users  2 to Send message  3 to Logout");
			System.out.println("Enter Options 1 2 or 3");
			String optn = Client.InputFromClient();
			int option = Integer.parseInt(optn);

			switch(option){
			case 1 :
			{
				HashMap<String,Integer> onlineuser = ClientInputProcessing.GetOnlineUsers(clientSocket);
				System.out.println("");
				System.out.println(onlineuser);			
				ClientInputProcessing.ChooseAction(clientSocket);
			}	
			case 2 :
			{
				ClientInputProcessing.MessageInput(clientSocket);
				ClientInputProcessing.ChooseAction(clientSocket);
			}
			case 3 :
			{
				ClientInputProcessing.Logout(clientSocket);
				filePubName = clientSocket.getLocalPort() + "PublicKey";
				filePrivName = clientSocket.getLocalPort() + "PrivateKey";
				Client.FileDelete(filePubName, filePrivName);

				System.out.println("Exitting Client");
				System.exit(0);
				clientSocket.close();				
				break;
			}
			}
		}
	}
}

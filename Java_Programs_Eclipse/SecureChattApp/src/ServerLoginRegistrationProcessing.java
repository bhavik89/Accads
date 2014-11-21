

import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.Map.Entry;


public class ServerLoginRegistrationProcessing implements Serializable {

	public static void PreRegisteredUsers() throws Exception
	{

		File fi = new File("RegisteredClients");
		ObjectOutputStream preRegister = new ObjectOutputStream(new	FileOutputStream(fi));

		HashMap<String, String> client = new HashMap<>();
		client.put("netsec", RegisterLoginProcessing.HashTheString ("netsec"));
		client.put("admin", RegisterLoginProcessing.HashTheString("admin"));
		client.put("smit", RegisterLoginProcessing.HashTheString("smit"));

		preRegister.writeObject(client);
		preRegister.flush();
		preRegister.close();
	}


	public static void ServerRegistrationCheck(Socket connectionSocket, ArrayList<String> clientInformationData) throws Exception{

		String statMessage;
		File fi = new File("RegisteredClients");
		ObjectInputStream getRegisteredUsers = new ObjectInputStream(new FileInputStream(fi));
		HashMap<String, String> users = (HashMap<String, String>)getRegisteredUsers.readObject();
		getRegisteredUsers.close();

		//		for(Entry<String, String> m :users.entrySet()){
		//			System.out.println(m.getKey()+" : "+m.getValue());		
		//		}

		if(users.containsKey(clientInformationData.get(0))){
			statMessage = "Username already exist, Change it";
			System.out.println(statMessage);			
			Client.SendDataStream(connectionSocket, statMessage);
		}
		else{
			users.put(clientInformationData.get(0), clientInformationData.get(1));			

			ObjectOutputStream setRegisteredUsers = new ObjectOutputStream(new FileOutputStream(new File("RegisteredClients")));
			setRegisteredUsers.writeObject(users);
			setRegisteredUsers.close();					

			statMessage = "Registration Successful";
			Client.SendDataStream(connectionSocket, statMessage);

		}	
	}


	//Method to check if login credentials correct or not
	public static String ServerLoginCheck(Socket connectionSocket, ArrayList<String> clientInformationData) throws Exception{

		String statMessage;
		File fi = new File("RegisteredClients");
		ObjectInputStream registeredUsers = new ObjectInputStream(new FileInputStream(fi));
		HashMap<String, String> users = (HashMap<String, String>)registeredUsers.readObject();
		registeredUsers.close();

		String message;

		//if registered users contains the username go in
		if(users.containsKey(clientInformationData.get(0)))
		{
			System.out.println("username correct, Lets check the password");
			Object keys = clientInformationData.get(0);
			System.out.println(keys);
			Object passValue = users.get(keys);
			//System.out.println(passValue);
			//System.out.println(clientInformationData.get(1).trim());

			//if password correct send verified
			if(passValue.equals(clientInformationData.get(1))){
				message = "Password verified";
				System.out.println(message);	
			}
			else {
				// or send password wrong
				message = "Wrong Password, connect again";
				System.out.println(message);
			}
		}
		else{
			message = "username not found";
		}

		System.out.println(message);
		//Send the login status message
		Client.SendDataStream(connectionSocket, message);
		return message;		
	}	
}

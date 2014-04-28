

import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class SendClientInformation {

	public static void DistributeClientInfo(Socket connectionSocket, HashMap<String, Integer> userList, String clientName) throws Exception{


		if(Server.userLists.containsKey(clientName)){
			System.out.println(clientName);
			Integer port = Server.userLists.get(clientName);
			System.out.println(port);

			ArrayList<Object> clientInformation = new ArrayList<>();
			clientInformation.add(clientName);
			clientInformation.add(port);

			//Create a Nonce to authenticate between clients and send it to the connecting client
			String nonce = "common Nonce";
			clientInformation.add(nonce);
			System.out.println(clientInformation);

			RegisterLoginProcessing.ServerSendStream(connectionSocket, clientInformation);				
		}
		else{
			Object fail = "User not online, try again";
			RegisterLoginProcessing.ServerSendStream(connectionSocket, fail);
		}				
	}			
}


import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.PublicKey;
import java.util.ArrayList;

import javax.crypto.SealedObject;
import javax.sound.midi.Receiver;

public class ClientToClientInfo {


	public static void ClientInfoExchange(Socket clientSocket, ArrayList<String> chatInfo) throws Exception{

		String message = chatInfo.get(2);
		chatInfo.remove(2);		

		PublicKey pubKey = Server.readServerPublic("server_public");
		SealedObject chatInfoEncrypt = ClientCryptoAlgos.dataEncryption(chatInfo, pubKey, "RSA/ECB/PKCS1Padding");

		Client.SendDataStream(clientSocket, chatInfoEncrypt);		
		System.out.println("Data sent");
		//System.out.println(chatInfo.get(0));

		ArrayList<Object> client = (ArrayList<Object>) Client.ReceiveDataStream(clientSocket);
		//System.out.println(client);

		if(client.get(0).equals(chatInfo.get(0)))
		{				
			Object portClient = client.get(1);
			//System.out.println(portClient);

			Object authenticator = client.get(2);
			System.out.println("Ticket Received");

			ClientToClientInfo.ConnectClient((int) portClient,authenticator, message);
		}
		else{			
			System.out.println("There is some issue");
		}				
	}	


	public static void ConnectClient(int portClient, Object authenticator, Object message) throws Exception
	{
		//System.out.println(portClient);
		Socket clientToClient = new Socket("localhost", portClient);
		System.out.println("New connection established");
		ArrayList<Object> authenticators = new ArrayList<>();
		authenticators.add(authenticator);

		Client.SendDataStream(clientToClient, authenticators);

		ArrayList<Object> authenticator2 = (ArrayList<Object>) Client.ReceiveDataStream(clientToClient);
		if(authenticator2.get(0).equals(authenticator)){

			System.out.println("authenticator received, move along");

			ArrayList<Object> authenticator3 = new ArrayList<>();
			authenticator3.add(authenticator2.get(2));

			//send the nonce received back to complete authentication process
			Client.SendDataStream(clientToClient, authenticator3);

			//receive the acknowledgment
			authenticator = RegisterLoginProcessing.ReceiveDataStream(clientToClient);
			System.out.println(authenticator);

			if(authenticator.toString().equalsIgnoreCase("Authentication Successful")){
				//send the message	
				String mssg = clientToClient.getInetAddress()+ "/" +clientToClient.getLocalPort() + " : " + message;
				RegisterLoginProcessing.ServerSendStream(clientToClient, mssg);		//Send the message after authentication	

			}
		}
	} 
}

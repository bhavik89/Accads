

import java.io.IOException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;

import javax.crypto.SealedObject;


public class ServerThread extends Thread implements Serializable{
	Socket connectionSocket;

	public ServerThread(Socket socket){
		this.connectionSocket=socket;
	}


	@Override
	public void run() {

		while(true){
			SealedObject clientInfoData;
			try {
				clientInfoData = (SealedObject) RegisterLoginProcessing.ReceiveDataStream(connectionSocket);

				PrivateKey pk = Server.readServerPrivate("server_private");
				ArrayList<String> clientInformationData = (ArrayList<String>) ServerCryptoAlgos.decryptData(clientInfoData, pk, "RSA/ECB/PKCS1Padding");

				System.out.println(clientInformationData);

				String check = clientInformationData.get(2);
				System.out.println(check);
				String username = clientInformationData.get(0);

				if(check.equalsIgnoreCase("register"))
				{					
					ServerLoginRegistrationProcessing.ServerRegistrationCheck(connectionSocket,clientInformationData);		
				}

				if(check.equalsIgnoreCase("login"))
				{					
					String stat = ServerLoginRegistrationProcessing.ServerLoginCheck(connectionSocket,clientInformationData);
					//If Password verified begin the authentication Process
					if(stat.equalsIgnoreCase("Password verified"))
					{						
						ArrayList<Object> status = ServerAuthentication.ServerAuthenticate(connectionSocket, clientInformationData);
						System.out.println(username + "---->" + status.get(0));
						Integer port = (Integer) status.get(1);

						//Add the connection Information of the authenticated clients
						Server.connectionInfo.put(clientInformationData.get(0), connectionSocket);

						//if authenticated add it to list of online users
						//HashMap<String, Integer> onlineUser = (HashMap<String, Integer>) Server.GetOnlineUsers(clientInformationData.get(0), port);
						Server.userLists.put(username, port);
					}
					else break;
				}				

				if(check.equalsIgnoreCase("logout")){

					String stat = ServerLoginRegistrationProcessing.ServerLoginCheck(connectionSocket,clientInformationData);
					//If Password verified begin the authentication Process
					if(stat.equalsIgnoreCase("Password verified"))
					{
					Server.userLists.remove(username);
					System.out.println(Server.userLists);
					connectionSocket.close();
					break;
					}else{
						String status = "Re-Enter Credentials";
						RegisterLoginProcessing.ServerSendStream(connectionSocket, status);
					}
				}

				if(check.equalsIgnoreCase("list")){

					System.out.println("getting the user list");
					System.out.println( Server.userLists);
					RegisterLoginProcessing.ServerSendStream(connectionSocket, Server.userLists);


				}

				if(clientInformationData.get(1).equalsIgnoreCase("send")){
					String usernme = clientInformationData.get(0);
					System.out.println(usernme);
					//Send information to client trying to communicate
					SendClientInformation.DistributeClientInfo(connectionSocket, Server.userLists, username);

				}

			} catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		try {
			connectionSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}

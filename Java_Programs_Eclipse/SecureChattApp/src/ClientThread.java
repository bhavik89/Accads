

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ClientThread extends Thread {

	Integer port;
	Socket clientClientconn;
	Socket clientSocket;
	ServerSocket clientsServerSocket;
	static ArrayList<String> clientInformationData;

	public ClientThread(Socket clientSocket){
		//this.port = cPort;
		this.clientSocket = clientSocket;
	}


	@Override
	public void run() {

		try {
			clientsServerSocket = new ServerSocket(0);
			Client.port = clientsServerSocket.getLocalPort();

			while(true){
				clientClientconn = clientsServerSocket.accept();			
				ClientThread.ListenClient(clientClientconn, clientSocket);
			} 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static void ListenClient(Socket clientClientconn, Socket clientSocket) throws Exception {

		System.out.println("Client accepted connection");
		clientInformationData = (ArrayList<String>) RegisterLoginProcessing.ReceiveDataStream(clientClientconn);			
		//System.out.println(clientInformationData);

		//if some condition satisfied then send the nonce and another nonce back
		String nonce2 = "nonce2";
		String nonce3 = "nonce3";
		clientInformationData.add(nonce2);
		clientInformationData.add(nonce3);
		RegisterLoginProcessing.ServerSendStream(clientClientconn, clientInformationData);

		//See if sent nonce is received back or not
		clientInformationData = (ArrayList<String>) RegisterLoginProcessing.ReceiveDataStream(clientClientconn);
		if(clientInformationData.get(0).equals(nonce3)){

			System.out.println("Authentication successful start receiving messages");
			String ack = "Authentication successful";
			clientInformationData.add(ack);						

			Client.SendDataStream(clientClientconn, ack);

			Object message = Client.ReceiveDataStream(clientClientconn);
			System.out.println("");
			System.out.println(message);

		}	
	}
}
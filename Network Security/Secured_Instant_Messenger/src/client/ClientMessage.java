package client;

import java.util.ArrayList;
import java.util.UUID;
import java.io.IOException;


import generic.*;

/* Thread to handle the messages form other clients
 * */
public class ClientMessage implements Runnable {
	
	private Client client;
	private String clientMessaage;	

	public ClientMessage(String clientMsg, Client client) {
		this.clientMessaage = clientMsg;
		this.client = client;
	}

/*Thnread to implement the functions*/
	
	@Override
	public void run() {
		// Verify packet header length
		if (clientMessaage.length() < 2) {
			System.out.println("Invalid message");

		} else {
			MessageEncoder type = null;
			try {
				type = MessageEncoder.getMessageCode(clientMessaage);

			} catch (Exception e) {
				System.out.println("Unresolvable peer message received");
				return;
			}

			String receivedMessage = clientMessaage.substring(2);

			switch (type) {

			case CLIENT_TO_CLIENT_MESSAGE:
				this.displayClientMessage(receivedMessage);
				break;
			case CLIENT_BYE_MESSAGE:
				this.destroyClientInfo(receivedMessage);
				break;
				
			default:
				break;
			}
		}
	}
	
	//Display the client message on console
	public void displayClientMessage(String clientMessageRec){
		final ArrayList<String> responseParams = MessageMaker.disassemblePacket(clientMessageRec);
		ConnectedClientsData peerInfo = this.client.connectedClients.getClient(UUID.fromString(responseParams.get(0)));
		String messageReceived;

		try {
			messageReceived = CryptoOperations.hmacVerify(peerInfo.getClientSessionSecret(), responseParams.get(1));
		} catch (Exception e) {
			System.out.println("Could not verify the Authenticity of Client");
			e.printStackTrace();
			
			return;
		}

		messageReceived = CryptoOperations.AESDecrypt(peerInfo.getClientSessionSecret(), messageReceived);

		final ArrayList<String> decryptedMessage = MessageMaker.disassemblePacket(messageReceived);

		long timestamp = Long.valueOf(decryptedMessage.get(1));

		if (!(new TimeStamps()).verifyTimeStamps(timestamp)) {
			System.out.println("Message Expired");
			return;
		}

		System.out.println(peerInfo.getClientUsername() + ": "+ decryptedMessage.get(0));
	}
	
	//Removes the connect client data and disconnects it if "bye" message is received
	private void destroyClientInfo(String byeMessage) {
		
		final ArrayList<String> byeMessageValues = MessageMaker.disassemblePacket(byeMessage);
		ConnectedClientsData clientInfo = this.client.connectedClients.getClient(UUID.fromString(byeMessageValues.get(0)));
		//System.out.println("Bye request received...");
		
		this.client.connectedClients.removeClientWithUUID(UUID.fromString(byeMessageValues.get(0)));
		
		//Remove the client information and close the TCP connection
		if(clientInfo != null){
			clientInfo.destroyClientData();
			try {
				clientInfo.getClientConnection().getOut().close();
				clientInfo.getClientConnection().getIn().close();
			} catch (IOException e) {
				System.out.println("ERROR: Closing the TCP connection with client");
				e.printStackTrace();
			}
		}
	}	
}

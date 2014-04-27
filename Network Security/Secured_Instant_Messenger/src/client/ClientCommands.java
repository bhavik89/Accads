package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import generic.*;


/* ClientCommands Handles the client console command like
 * "list", "send", "logout" , "bye" and "whoami"
 * */
public class ClientCommands implements Runnable {

	private Client client;
	
	public enum ClientCommandType{
		LIST, SEND, LOGOUT, BYE, WHOAMI;
	}

	public ClientCommands(Client client) {
		this.client = client;
	}

	public void run() {
		this.readClientCommands();
	}

	public void readClientCommands() {
		String command = "";
		
		boolean clientRunning = true;

		InputStreamReader inputStream = new InputStreamReader(System.in);
		BufferedReader readConsole = new BufferedReader(inputStream);

		while (clientRunning && !Thread.interrupted()) {

			try {
				command = readConsole.readLine();
			} catch (IOException e) {
				System.out.println("ERROR: Unable to read client commands");
				e.printStackTrace();
			}
			
			command = command.trim();

			/* Check for key press */
			if (command.length() == 0)
				continue;
			
			 splitCommandArgs(command);
		}
	}
	
	/*Gets command type from the input string and validates it*/
	private void splitCommandArgs(String command){
		
		String[] splitCommand = command.split(" ", 3);
		int inpLen = splitCommand.length;
				
		switch(inpLen){
			case(1):
				if (splitCommand[0].toUpperCase().equals(ClientCommandType.LIST.toString())) 
					this.getListOfClients();
				
				else if (splitCommand[0].toUpperCase().equals(ClientCommandType.SEND.toString())) 
					this.displayValidCommands();
				
				else if (splitCommand[0].toUpperCase().equals(ClientCommandType.WHOAMI.toString())) {
					this.displayWhoAmI();
				} 
				else if (splitCommand[0].toUpperCase().equals(ClientCommandType.LOGOUT.toString())) {
					this.sendLogout();
					System.exit(0);
				} 
				else {
					this.displayValidCommands();
				}
				
				break;
				
			case(2):
				if (splitCommand[0].toUpperCase().equals(ClientCommandType.BYE.toString())) {
					this.sendByeMessage(splitCommand[1]);
				}		
				else
					this.displayValidCommands();
		
				break;
			
			case(3):
				if (splitCommand[0].toUpperCase().equals(ClientCommandType.SEND.toString()))
					this.sendMessageToClient(splitCommand[1], splitCommand[2]);				
				else 
					this.displayValidCommands();
				break;
			
			default:
				System.out.println("ERROR: Not a valid client command type");
				this.displayValidCommands();
				break;
		}
	}
		
	private void displayValidCommands() {
		System.out.println("Usage:");
		System.out.println("\t list");
		System.out.println("\t logout");
		System.out.println("\t send username <message>");
	}

	/* Requests the list of online user from the server
	 * */
	private void getListOfClients() {
		
		if (!this.client.isLoggedIn()) {
			System.out.print("ERROR: Invalid command type, you need to login first");
			return;
		}

		String[] listRequestMsg = new String[2];
		try {
			listRequestMsg[0] = CryptoOperations.RSAEncrypt(String.valueOf(this.client.getClientUUID()), 
															Client.getClientConfig().getClientServerPublicKeyFile());
			
		} catch (Exception e) {
			System.out.println("ERROR: Unable to encryot UUID");
			e.printStackTrace();
		} 
		
		String[] encryptedListRequest = new String[2];
		encryptedListRequest[0] = "LIST";
		encryptedListRequest[1] = String.valueOf((new TimeStamps()).getTimestamp());
	
		
		listRequestMsg[1] = CryptoOperations.AESEncrypt(this.client.getClientServerSecretKey(), 
														MessageMaker.assemblePacket(encryptedListRequest));
		
		//System.out.println("List request Len: " + MessageMaker.assemblePacket(encryptedListRequest).length());
		//System.out.println("List request Message: " + MessageMaker.assemblePacket(encryptedListRequest));
		
		sendRequest(MessageMaker.assemblePacket(listRequestMsg), MessageEncoder.CLIENT_LIST_REQUEST);
	}
	
	/*Sends Message to given client
	 * Checks if is alredy connected,
	 * if not, requests the ticket to client from server
	 * */
	private void sendMessageToClient(String clientUserName, String msg) {
		
		if (clientUserName.equals(this.client.getUsername())) {
			System.out.println("Talk request Violated...");
			return;
		}
		
		if (this.client.connectedClients.isClientConnected(clientUserName)) {
			ConnectedClientsData clientInfo = this.client.connectedClients.getClientByUserName(clientUserName);
			
			if(clientInfo == null)
				return;
			
			try{
				if(clientInfo.getClientConnection() != null)
					clientInfo.getClientConnection().sendClientMessage(msg);
				
			}catch(Exception e){
				System.out.println("ERROR: Unable to send message to client");
				System.out.println(e.toString());
			}
		} 
		
		else { 
			String[] ticketToClientRequest = new String[2];
			try {
				ticketToClientRequest[0] =  
						CryptoOperations.RSAEncrypt(String.valueOf(this.client.getClientUUID()), 
													Client.getClientConfig().getClientServerPublicKeyFile());
			} catch (Exception e1) {
				System.out.println("ERROR: while encrypting talk request");
				e1.printStackTrace();
			}
					

			String[] encryptTalkPacket = new String[3];
			encryptTalkPacket[0] = "TALK";
			encryptTalkPacket[1] = clientUserName;
			encryptTalkPacket[2] = String.valueOf((new TimeStamps()).getTimestamp());

			try {
				ticketToClientRequest[1] = 
						CryptoOperations.AESEncrypt(this.client.getClientServerSecretKey(), 
													MessageMaker.assemblePacket(encryptTalkPacket));
				
				sendRequest(MessageMaker.assemblePacket(ticketToClientRequest), MessageEncoder.CLIENT_TICKET_REQUEST);
				
			} catch (Exception e) {
				System.out.println("ERROR: While encrypting send command");
				e.printStackTrace();
			}
			this.client.clientMessageToSend.put(clientUserName, msg);
		}
	}
	
	/*Sends bye message to the given client,
	 * Destroys its connections and shared secret
	 * */
	private void sendByeMessage(String clientUserName) {
		
		if (clientUserName.equals(this.client.getUsername())) {
			System.out.println("Bye request Violated...");
			return;
		}
		
		if (this.client.connectedClients.isClientConnected(clientUserName)) {
			ConnectedClientsData clientInfo = this.client.connectedClients.getClientByUserName(clientUserName);
			if(clientInfo == null){
				return;
			}
			try{
				if(clientInfo.getClientConnection() != null)
					clientInfo.getClientConnection().sendClientMessage("bye_request");
				
			} catch(Exception e){
				System.out.println("Unable to send Bye message");
				e.printStackTrace();
			}
		}		
	}
	
	private void displayWhoAmI() {
		System.out.println(client.getUsername());
	}	

	/*Sends Logout message to Sever when logout command is entered*/
	private void sendLogout() {
		String[] logoutMessage = new String[2];
		logoutMessage[0] = this.client.getClientUUID().toString();
		String[] encLogoutMsg = new String[2];
		encLogoutMsg[0] = "LOGOUT";
		encLogoutMsg[1] = String.valueOf((new TimeStamps()).getTimestamp());		
		try {
			logoutMessage[1] = CryptoOperations.AESEncrypt(this.client.getClientServerSecretKey(),
															MessageMaker.assemblePacket(encLogoutMsg));			
			sendRequest(MessageMaker.assemblePacket(logoutMessage), MessageEncoder.CLIENT_LOGOUT_REQUEST);

		} catch (Exception e) {
			System.out.println("ERROR: Cannot encrypt Logout command");
			e.printStackTrace();
		}
	}
	
	/*Sends UDP message to server*/
	private void sendRequest(String message, MessageEncoder messageType) {
		
		try {
			this.client.sendUDPMessage(message, messageType);
		} catch (Exception e) {
			System.out.println("ERROR: While sending message to server");
			e.printStackTrace();
		}
	}
	
}
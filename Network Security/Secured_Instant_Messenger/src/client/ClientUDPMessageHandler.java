package client;


import java.util.ArrayList;
import java.util.UUID;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import java.io.IOException;

import javax.crypto.SecretKey;

import generic.*;



public class ClientUDPMessageHandler {
	
	private String receivedMessage;
	private Client client;
	private int recMessagePort;
	private InetAddress recMesssageIp;

	private static PingServerThread pingServer;
	private static Thread pingServerThread;
		
	public ClientUDPMessageHandler(Client client, String recMessage, InetAddress fromIP, int fromPort) {
		this.client = client;
		this.receivedMessage = recMessage;
		this.recMesssageIp = fromIP;
		this.recMessagePort = fromPort;
	}
	
	public void messageAction() {
		
		if (receivedMessage.length() < 2) {
			System.out.println("Invalid message");
			return;
		} else {
			MessageEncoder messageDecode = null;
			try {
				messageDecode = MessageEncoder.getMessageCode(receivedMessage);

			} catch (Exception e) {
				System.out.println("Unresolved message UDP");
				return;
			}

			String messageID = receivedMessage.substring(0, 2);
			receivedMessage = receivedMessage.substring(2);
			
			switch (messageDecode) {
			case SERVER_COOKIE:
				this.startServerAuth(receivedMessage);
				break;
			case SERVER_AUTHENTCATION_BEGIN:
				this.serverAuthenticated(receivedMessage);
				break;
			case SERVER_AUTHENTICATED:
				this.printLoginSuccess(receivedMessage);
				break;
			case SERVER_LIST_RESPONSE:
				this.displayOnlineUsers(receivedMessage);
				break;
			case SERVER_REQUESTED_TICKET_TO_CLIENT:
				this.sendTicketWithMessage(receivedMessage);
				break;
			case CLIENT_TO_CLIENT_AUTH_BEGIN:
				this.clientToClientAuthenication(receivedMessage);
				break;
			case CLIENT_TO_CLIENT_AUTH_RESPONSE:
				this.clientAuthticationResponse(receivedMessage);
				break;
			case CLIENT_CLIENT_AUTH_SUCCESS:
				this.clientAuthSuccess(receivedMessage);
				break;
			case SERVER_LOGOUT_RESPONSE:
				this.clientLogoutAndDestroy(receivedMessage);
				break;
			case SERVER_RELOGIN_REQUEST:
				this.clientReloginRequest(receivedMessage);
				break;
			case SERVER_PING_RESPONSE:
				this.handlePingResponse();
				break;
			default:
				System.out.println("Unresolvable Message type received.. " + messageID);
				break;
			}
	}
}
	

	/*
	private void sendMessage(String message, MessageTypeHandler messageType, InetAddress destIp, int destPort) throws IOException {
		this.client.sendUDPMessage(message, messageType);
	}*
	/*
	 * Receives the Server Cookie and sends the encrypted user login details to server 
	 */
	private void startServerAuth(String cookie) {
		
		String[] serverCookieResponse = new String[4];				
		
						
		try{
		this.client.setDhKeyPair(CryptoOperations.genDHKeyPair());
		
						
		//Generate AES key
		SecretKey AESKey = CryptoOperations.aesGenerateKey();
								
		//Encrypt AES Secret key with destination's PublicKey
		String RSAencryptedAESKey = CryptoOperations.RSAWrap(AESKey, "serverPublicKey.key");
				
		//Build response with username and passwords hash
		long serverCookieResponseTimestamp = (new TimeStamps()).getTimestamp();
		
		serverCookieResponse[0] = this.client.getUsername();
		serverCookieResponse[1] = CryptoOperations.generateValidationHash(this.client.getPassword());
		serverCookieResponse[2] = new String(this.client.getDhKeyPair().getPublic().getEncoded(), CryptoOperations.ISO_CHARSET);
		serverCookieResponse[3] = String.valueOf(serverCookieResponseTimestamp);
				
		String[] encryptedCookieResponse = new String[3];
		
		encryptedCookieResponse[0] = cookie;
		encryptedCookieResponse[1] = RSAencryptedAESKey;
		encryptedCookieResponse[2] = CryptoOperations.AESEncrypt(AESKey, MessageMaker.assemblePacket(serverCookieResponse));
		
		fwdSendRequest(MessageMaker.assemblePacket(encryptedCookieResponse), MessageEncoder.CLIENT_AUTH_REQUEST);		
		
		}
		catch (Exception e){
			System.out.println("Unable to send serverCookieReplyPacket to server");
			e.printStackTrace();
			return;
		}
	}
	
	/*Establishes a session key between client and server and send encrypted Timestamp with that key 
	 * */
	private void serverAuthenticated(String serverLoginResponse) {
		
		ArrayList<String> response = MessageMaker.disassemblePacket(serverLoginResponse);
		
		try{
			byte[] serverDHpart = response.get(0).getBytes(CryptoOperations.ISO_CHARSET);
			this.client.setClientServerSecretKey(CryptoOperations.genDHSecretKey(this.client.getDhKeyPair().getPrivate(), serverDHpart));
			
			byte[] serverDHPubSign = response.get(1).getBytes(CryptoOperations.ISO_CHARSET);
			
			if(!(CryptoOperations.verifySignature(this.client.getClientServerPubKeyFile(), response.get(0), serverDHPubSign))){
				System.out.println("Server Signature NOT Verified...");
				System.exit(0);
			}
			final ArrayList<String> serverAESDecData = 
					MessageMaker.disassemblePacket(CryptoOperations.AESDecrypt(this.client.getClientServerSecretKey(), 
													response.get(2)));
			
			this.client.setClientUUID(UUID.fromString(serverAESDecData.get(0)));
			
			if(!(new TimeStamps().verifyTimeStamps(Long.valueOf(serverAESDecData.get(2))))){
				System.out.println("Time Stamp Error!!");
				return;
			}
			
			String[] authSuccessResponse = new String[2];
			authSuccessResponse[0] = this.client.getClientUUID().toString();
			authSuccessResponse[1] = CryptoOperations.AESEncrypt(this.client.getClientServerSecretKey(), serverAESDecData.get(2));

			fwdSendRequest(MessageMaker.assemblePacket(authSuccessResponse), MessageEncoder.CLIENT_AUTH_VERIFY);
			this.client.setIsLoggedIn(true);
			
			//Start pinging to server so check if it's online
			pingServer = new PingServerThread(this.client);
			pingServerThread = (new Thread(pingServer));
			pingServerThread.start();
			
			//Start reading commands on client console
			ClientCommands handleClientConsole = new ClientCommands(this.client);			
			(new Thread(handleClientConsole)).start();			
			
		}catch(Exception e){
			System.out.println("Error while sending final authentication to server");
			e.printStackTrace();
		}		
	}	

	/* Prints out the success message on client prompt when successfully authenticated
	 * */
	private void printLoginSuccess(String message) {
		System.out.println("You are now Logged In...");
		(new Thread(new ClientCommands(this.client))).start();		
	}
	
	/* Handler for the list response from server	
	 */
	private void displayOnlineUsers(String listResponse) {
				
			ArrayList<String> response = MessageMaker.disassemblePacket(listResponse);		
			
			try{
			listResponse = CryptoOperations.AESDecrypt(this.client.getClientServerSecretKey(), response.get(0));			
			
			final ArrayList<String> params = MessageMaker.disassemblePacket(listResponse);
			
			if ((new TimeStamps()).verifyTimeStamps(Long.valueOf(params.get(1))))
				System.out.println(params.get(0));
			
			else{
				System.out.println("Invalid TimeStamp");
				return;
			}
			}catch(Exception e){
				System.out.println("Error while decrypting list response");
				e.printStackTrace();
			}
	}
	
    /*Forwards the ticket received from server to the requested client
     * with it's part of DH key
     * */
	private void sendTicketWithMessage(String ticketMessage) {
		
		ConnectedClientsData clientInfo = null;
		String ticketToFwd = null;

		try {
			ticketMessage = CryptoOperations.AESDecrypt(this.client.getClientServerSecretKey(), ticketMessage);
			
			final ArrayList<String> clientDataFromServer = MessageMaker.disassemblePacket(ticketMessage);

			String clientName = clientDataFromServer.get(0);
			InetAddress clientIP = InetAddress.getByName(clientDataFromServer.get(1));
			int clientPort = Integer.valueOf(clientDataFromServer.get(2));
			SecretKey clientTempKey = null;
			try {
				clientTempKey = CryptoOperations.createAESKey(clientDataFromServer.get(3).getBytes(CryptoOperations.ISO_CHARSET));
			} catch (Exception e) {
				System.out.println("Error While generating temp session key");
				e.printStackTrace();
			} 

			UUID clientUUId = UUID.fromString(clientDataFromServer.get(6));

			clientInfo = new ConnectedClientsData(clientName, clientIP, clientPort, clientUUId, clientTempKey);
			
			if (!this.client.connectedClients.isClientConnected(clientUUId)) {
				this.client.connectedClients.addClientInConnections(clientUUId, clientInfo);
			}

			ticketToFwd = clientDataFromServer.get(4);

			if (!( new TimeStamps()).verifyTimeStamps(Long.valueOf(clientDataFromServer.get(5)))) {
				System.out.println("ERROR: Ticket timestamps not verified!");
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			this.client.setDhKeyPair(CryptoOperations.genDHKeyPair());
		} catch (Exception e) {
			System.out.println("ERROR: Generatinf DH key between clients");
			e.printStackTrace();
		}	
		
		String[] clientAuthResponse = new String[2];
		clientAuthResponse[0] = ticketToFwd;
		String[] clientAuthResMsg = new String[5];
		try {
			clientAuthResMsg[0] = "HELLO";
			clientAuthResMsg[1] = this.client.getUsername();
			clientAuthResMsg[2] = clientInfo.getClientUsername();
			clientAuthResMsg[3] = new String(this.client.getDhKeyPair().getPublic().getEncoded(), CryptoOperations.ISO_CHARSET);
			clientAuthResMsg[4] = String.valueOf((new TimeStamps()).getTimestamp());
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		try {
			clientAuthResponse[1] = CryptoOperations.AESEncrypt(clientInfo.getTempKey(), MessageMaker.assemblePacket(clientAuthResMsg));
		} catch (Exception e) {
			System.out.println("ERROR: Encrypting Authentication Response");
			e.printStackTrace();
		}						
		sendMessageToConnection(MessageMaker.assemblePacket(clientAuthResponse), 
								MessageEncoder.CLIENT_TO_CLIENT_AUTH_BEGIN, clientInfo.getConnetedClientIP(), clientInfo.getConnectedClientPort());
	}
	
	/*Takes actions when a ticket form another clients is receives, authenticates client and generates a session key
	 * */
	private void clientToClientAuthenication(String ticketFwdMsg) {
		
		final ArrayList<String> recTicketMessage = MessageMaker.disassemblePacket(ticketFwdMsg);
		
		String ticket = null;
		SecretKey ticketTempSessionKey = null;
		
		String ticketMessage = null;		
		String clientName = null;
		
		UUID clientUUId = null;
		PublicKey clientDHPublic = null;
		
		SecretKey clientSessionKey = null;		
		
		long ticketTimeStamp = 0;		
		
		//Recover tickets
		try {
			ticket = CryptoOperations.AESDecrypt(this.client.getClientServerSecretKey(), recTicketMessage.get(0));			
			final ArrayList<String> ticketValues = MessageMaker.disassemblePacket(ticket);
			
			//Verify Ticket values for username and timestamps
			if (!this.client.getUsername().equals(ticketValues.get(0))) {
				System.out.println("Ticket Username Mismatch ERROR: Invalid Ticket Received");
				return;
			}
			clientName = ticketValues.get(1);
			clientUUId = UUID.fromString(ticketValues.get(2));			
			ticketTempSessionKey = CryptoOperations.createAESKey(ticketValues.get(3).getBytes(CryptoOperations.ISO_CHARSET));

			if (!(new TimeStamps()).verifyTimeStamps(Long.valueOf(ticketValues.get(4)))) {
				System.out.println("ERROR: Ticket timestamps Mismatch");
				return;
			}
		}catch (Exception e) {
			System.out.println("Error while recovering tickets");
			e.printStackTrace();
		}
		
		//Validate timestamps in ticket
		try {
			ticketMessage = CryptoOperations.AESDecrypt(ticketTempSessionKey, recTicketMessage.get(1));
			
			final ArrayList<String> clientTicketValues = MessageMaker.disassemblePacket(ticketMessage);

			if (!clientName.equals(clientTicketValues.get(1))) {
				System.out.println("ERROR: Name mismatch in ticket and sender name");
				return;
			}

			if (!this.client.getUsername().equals(clientTicketValues.get(2))) {
				System.out.println("Ticket ERROR");
	       		return;
			}
			try{
				final KeyPair clientDHKeyPair = CryptoOperations.genDHKeyPair();
				
				final PrivateKey clientDHPrivate = clientDHKeyPair.getPrivate();
				clientDHPublic = clientDHKeyPair.getPublic();
												
				clientSessionKey = CryptoOperations.genDHSecretKey(clientDHPrivate, clientTicketValues.get(3).getBytes(CryptoOperations.ISO_CHARSET));
			}catch (Exception e){
			System.out.println("ERROR:Generating DH key for clients");
			e.printStackTrace();
			}
			ticketTimeStamp = Long.valueOf(clientTicketValues.get(4));
		}
		catch (Exception e) {
			System.out.println("ERROR: Getting ticket values and authenticating client");
			e.printStackTrace();
		}

		ConnectedClientsData clientInfo = new ConnectedClientsData(clientName, this.recMesssageIp, this.recMessagePort, clientUUId, ticketTempSessionKey);
		if (clientInfo != null && clientName != null) {
			this.client.connectedClients.addClientInConnections(clientUUId, clientInfo);
		}

		clientInfo.setClientSessionKey(clientSessionKey);

		String[] clientTicketResponse = new String[3];
		clientTicketResponse[0] = this.client.getClientUUID().toString();
		try {
			String clientDHPart = new String(clientDHPublic.getEncoded(),CryptoOperations.ISO_CHARSET);
			String encryptedDHMessage = CryptoOperations.AESEncrypt(ticketTempSessionKey, clientDHPart);
			clientTicketResponse[1] = encryptedDHMessage;
		} catch (Exception e) {
			System.out.println("ERROR: Encrypting Ticket Response");
			e.printStackTrace();
		} 
		String[] timeStamp = new String[2];
		timeStamp[0] = String.valueOf(ticketTimeStamp);
		timeStamp[1] = String.valueOf((new TimeStamps()).getTimestamp());

		try {
			String ecnTimeStamps = CryptoOperations.AESEncrypt(clientInfo.getClientSessionSecret(), MessageMaker.assemblePacket(timeStamp));
			clientTicketResponse[2] = ecnTimeStamps;
		} catch (Exception e) {
			System.out.println("ERROR: Encrypting ticket timestamps");
			e.printStackTrace();
		}

		sendMessageToConnection(MessageMaker.assemblePacket(clientTicketResponse), 
								MessageEncoder.CLIENT_TO_CLIENT_AUTH_RESPONSE, 
								this.recMesssageIp, 
								this.recMessagePort);
		
		//Add client to the connection list
		ClientConnection clientConnection = new ClientConnection(this.client, clientInfo);
		this.client.clientTCPHandler.addWaitingClients(clientInfo.getClientUUid(), clientConnection);
		clientInfo.setPeerConnection(clientConnection);
		
	}
	
	/* Action to be taken when the ticket response is received,
	 * authenticates client and generates session key between clients
	 * */
	private void clientAuthticationResponse(String ticketResponse) {
		
			final ArrayList<String> ticketResponseReceived = MessageMaker.disassemblePacket(ticketResponse);
			
			UUID clientUUId = UUID.fromString(ticketResponseReceived.get(0));
			
			ConnectedClientsData clientInfo = this.client.connectedClients.getClient(clientUUId);
			
			long clientTimeStamp = 0;

			try {
				String recClientDHPublic = CryptoOperations.AESDecrypt(clientInfo.getTempKey(), 
																		ticketResponseReceived.get(1));
				
				byte[] recClientPublicExp = recClientDHPublic.getBytes(CryptoOperations.ISO_CHARSET);
				
				clientInfo.setClientSessionKey(CryptoOperations.genDHSecretKey(this.client.getDhKeyPair().getPrivate(),
																				recClientPublicExp));

				final ArrayList<String> timestamps = MessageMaker.disassemblePacket(CryptoOperations.AESDecrypt(clientInfo.getClientSessionSecret(),
																												ticketResponseReceived.get(2)));
				if (!(new TimeStamps()).verifyTimeStamps(Long.valueOf(timestamps.get(0)))) {
					System.out.println("Timestamps Unmatched");
					return;
				}
				clientTimeStamp = Long.valueOf(timestamps.get(1));
			} catch (Exception e) {
				System.out.println("ERROR: Decrypting the ticket response");
				e.printStackTrace();
			} 

			String[] ticketAuthReply = new String[2];
			ticketAuthReply[0] = this.client.getClientUUID().toString();
			try {
				ticketAuthReply[1] = CryptoOperations.AESEncrypt(clientInfo.getClientSessionSecret(), String.valueOf(clientTimeStamp + 1));
			} catch (Exception e) {
				System.out.println("ERROR: Generating ticket reply");
				e.printStackTrace();
				return;
			}
			sendMessageToConnection(MessageMaker.assemblePacket(ticketAuthReply), 
									MessageEncoder.CLIENT_CLIENT_AUTH_SUCCESS, 
									this.recMesssageIp, 
									this.recMessagePort);
          
			Socket clientSocket = null;
			try {
				clientSocket = new Socket(clientInfo.getConnetedClientIP(), clientInfo.getConnectedClientPort());
				ClientConnection clientConnection = null;
				if (clientSocket != null) {
					clientConnection = new ClientConnection(this.client, clientInfo);
				}

				clientInfo.setPeerConnection(clientConnection);
				clientConnection.setClientSocket(clientSocket);
				clientConnection.sendClientMessage(this.client.clientMessageToSend.get(clientInfo.getClientUsername()));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR: Client connections could not be established, please check your connections");
				e.printStackTrace();
				return;
			}
	}
	
	/*	Authenticate client for final message
	 * Verify the message time stamp
	 * */
		private void clientAuthSuccess(String clientMessage) {
			
			final ArrayList<String> clientAuthSuccessMsg = MessageMaker.disassemblePacket(clientMessage);
			ConnectedClientsData clientInfo = this.client.connectedClients.getClient(UUID.fromString(clientAuthSuccessMsg.get(0)));

			try {
				String decrypTimeStamp = CryptoOperations.AESDecrypt(clientInfo.getClientSessionSecret(), 
																		clientAuthSuccessMsg.get(1));
				if (!(new TimeStamps()).verifyTimeStamps(Long.valueOf(decrypTimeStamp))){ 
					System.out.println("ERROR: Cannot verify timestamps for authentication");
					return;
				}
			} catch (Exception e) {
				System.out.println("ERROR: Cannot authenticate clients");
				e.printStackTrace();
			}			
		}		
		
		/* Respond to the logout request 
		 * */
		private void clientLogoutAndDestroy(String clientLogoutMsg) {
			String clientlogout = null;
			try {
				clientlogout = CryptoOperations.AESDecrypt(this.client.getClientServerSecretKey(), clientLogoutMsg);
			} catch (Exception e) {
				System.out.println("ERROR: Decrypting logout request");
				e.printStackTrace();
				return;
			}

			if (!(new TimeStamps()).verifyTimeStamps(Long.valueOf(clientlogout))) {
				System.out.println("ERROR: Cannot verify timestamps for logout request");
				return;
			}
			this.client.clientLogout();
		}
		
		/* Client relogin on ping to server
		 * */
		private void clientReloginRequest(String reLoginMsg) {
			if (!this.client.getServerIP().getHostAddress().equals(this.recMesssageIp.getHostAddress()) || 
					this.client.getServerPort() != this.recMessagePort) {
				return;
			}

			System.out.println("Sending re-login" + this.client.getUsername());
			pingServerThread.interrupt();
			try {
				this.client.getLoginDetails();
			} catch (Exception e) {
				System.err.println("ERROR: Failed to re-login...");
			}
		}
		
		/*Handler for the ping response by the server
		 * */
		private void handlePingResponse() {	
			if(pingServer == null)
				return;
			
			pingServer.recievedPing();
		}

		/* Forward the UDP message 
		 * */
		private void fwdSendRequest(String string, MessageEncoder messageType) throws IOException {
			this.client.sendUDPMessage(string, messageType);
		}
		
		private void sendMessageToConnection(String message, MessageEncoder messageType, InetAddress destIp, int destPort) {
			try {
				this.client.sendMessage(message, messageType, destIp, destPort);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	
}

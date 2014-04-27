package server;


import java.util.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.SecretKey;

import generic.*;

/*Class to process the UDP messages received by server */
public class ServerUDPMessageHandler implements Runnable{
	
	private Server server;
	private String udpMessage;
	private InetAddress msgIp;
	private int msgPort;
	private DatagramSocket serverSoc;
	
	public ServerUDPMessageHandler(Server server, String recMessage, InetAddress msgIP, int msgPort, DatagramSocket serversoc) {
		this.server = server;
		this.udpMessage = recMessage;
		this.msgIp = msgIP;
		this.msgPort = msgPort;
		this.serverSoc = serversoc;
	}

	@Override
	public void run() {
		if (udpMessage.length() < 2) {
			System.out.println("ERRRO: Invalid Message received");
		} else {
			MessageEncoder messageCode = null;
			try {
				messageCode = MessageEncoder.getMessageCode(udpMessage);
			} catch (Exception e) {
				System.out.println("ERRRO: Invalid Message received");
				return;
			}
			udpMessage = udpMessage.substring(2);
			
			try{			
			switch (messageCode) {
			case CLIENT_HELLO:
				this.sendCookieToClient(udpMessage);
				break;
			case CLIENT_AUTH_REQUEST:
				this.verifyCookieAndCreds(udpMessage);
				break;
			case CLIENT_AUTH_VERIFY:
				this.finalClientAuthentication(udpMessage);
				break;
			case CLIENT_LIST_REQUEST:
				this.caterListRequest(udpMessage);
				break;
			case CLIENT_TICKET_REQUEST:
				this.caterTicketRequest(udpMessage);
				break;
			case CLIENT_LOGOUT_REQUEST:
				this.caterLogoutRequest(udpMessage);
				break;
			case CLIENT_ONLINE_PACKET:
				this.caterClientOnlineRequest(udpMessage);
				break;
			default:
				System.out.println("ERRRO: Invalid Message received");
				break;
			}
	  }
		catch(Exception e){
			e.printStackTrace();
		}
		}
	}
	
	/*Generates cookie and forwards it to client when client pings for the first time*/
	private void sendCookieToClient(String message) {
		try {
			this.sendClientUDPMessage(String.valueOf(ClientCookies.generateClientCookie(this.msgIp)),
							MessageEncoder.SERVER_COOKIE);
		} catch (Exception e) {
			System.out.println("ERROR: While generating client cookie");
			e.printStackTrace();
			return;
		}
	}
	
	/*Verify the cookie received from client and it's login parameters*/
	private void verifyCookieAndCreds(String message) {
		
		ArrayList<String> clientPacketReceived = MessageMaker.disassemblePacket(message);
        
		if (!ClientCookies.verifyClientCookie(this.msgIp,  clientPacketReceived.get(0))) {
			System.out.print("ERROR: Invalid Cookie received");
			return;
		}		
		//System.out.println("Server AES Key string len: " + response.get(1).length());		
		
		String clientPacket = null;
		try {
			SecretKey AESKey;
			AESKey = CryptoOperations.RSAUnWrap(clientPacketReceived.get(1), this.server.getServerConfig().getServerPrivateKeyFile());
			clientPacket = CryptoOperations.AESDecrypt(AESKey, clientPacketReceived.get(2));		
		} catch (Exception e) {
			System.out.println("ERROR: While recovering secret key");
			e.printStackTrace();
		}
		
		final ArrayList<String> decrypPac = MessageMaker.disassemblePacket(clientPacket);
		final String username = decrypPac.get(0);
		final String clientTimeStamp = decrypPac.get(3);
		PublicKey dhPublicKey = null;
		SecretKey secretKey = null;
		
		if (!this.server.getServerConfig().isUserRegistered(username)) {
			System.out.println(username + " not a valid username");
			return;
		}
		if (this.server.getOnlineUsers().isClientOnline(username)) {
			System.out.println(username + " is already loggedin");
			return;
		}
		
		final ClientsConnected newClientData = new ClientsConnected(username, this.server.getServerConfig().getUserPasswordHash(username));
       	String validatuserPasswordHash = decrypPac.get(1);
				
		if (!validatuserPasswordHash.equals(newClientData.getPwdHash())) {
			System.out.print("ERROR: Incorrect password received for " + newClientData.getUsername());
			return;
		}
        
		if (this.server.getOnlineUsers().isClientOnline(msgPort, msgIp)) {
			System.out.println("ERROR: Multiple clients is online: Same port and IP address");
			return;
		}		

		try {
			final KeyPair dhKeyPair = CryptoOperations.genDHKeyPair();
			final PrivateKey dhPrivateKey = dhKeyPair.getPrivate();
			dhPublicKey = dhKeyPair.getPublic();			
			secretKey = CryptoOperations.genDHSecretKey(dhPrivateKey, decrypPac.get(2).getBytes(CryptoOperations.ISO_CHARSET));
			//System.out.println("DH Secret Key Generated Succesfully");
		}catch (Exception e) {
			System.out.println("ERROR: Generating DH Key");
			e.printStackTrace();
			return;
		}
		
		UUID loggedInUUId = newClientData.getClientUUId();
			if (loggedInUUId != null) {
				this.server.logoutUser(loggedInUUId);
			}

		final UUID newUUID = UUID.randomUUID();
		newClientData.setCleintUUId(newUUID);
		
		final String[] encPacketResponse = new String[3];
		final long serverTimeStamp = (new TimeStamps()).getTimestamp();
		encPacketResponse[0] = newUUID.toString();
		encPacketResponse[1] = clientTimeStamp;
		encPacketResponse[2] = String.valueOf(serverTimeStamp);		 
        
		newClientData.setClientIp(this.msgIp);
		newClientData.setClientPort(this.msgPort);
		newClientData.setClientSessionKey(secretKey);
        
		this.server.registeredUsers.put(newClientData.getUsername(), newClientData);
		
		final String responsePacketValues[] = new String[3];
		try {
			String dhpublicKey = new String(dhPublicKey.getEncoded(), CryptoOperations.ISO_CHARSET);					
			
			responsePacketValues[0] =  dhpublicKey;
			responsePacketValues[1] =  new String(CryptoOperations.signDigital(server.getServerPvtKeyFile(), dhpublicKey),CryptoOperations.ISO_CHARSET);
			responsePacketValues[2] =  CryptoOperations.AESEncrypt(secretKey,(MessageMaker.assemblePacket(encPacketResponse)));
						
		} catch (Exception e) {
			System.out.println("ERROR: While generating response Packet");
			e.printStackTrace();
			return;
		}
		final String responsePacket = MessageMaker.assemblePacket(responsePacketValues);
		sendClientUDPMessage(responsePacket, MessageEncoder.SERVER_AUTHENTCATION_BEGIN);		
	}
	
	/*Verify the timestamp and authenticate client*/
	private void finalClientAuthentication(String recMessage) {
		
		final ArrayList<String> authResponse = MessageMaker.disassemblePacket(recMessage);
		UUID recUUId = UUID.fromString(authResponse.get(0));
		ClientsConnected clientData = this.server.getRegisteredUser(recUUId);
		
		if (clientData == null) {
			System.out.println("ERROR: Given User doesn't exist");
			return;
		}
		try {
			long packetTimestamp = Long.valueOf(CryptoOperations.AESDecrypt(clientData.getClientSessionKey(), authResponse.get(1)));
			if ((new TimeStamps().verifyTimeStamps(packetTimestamp))) {
				System.out.println(clientData.getUsername() + " logged in");
			} else {
				System.out.println("ERROR: Authentication Error, while verifying timestamp");
				return;
			}
		} catch (Exception e) {
			System.out.println("ERROR: Authentication Error, while verifying user data");
			e.printStackTrace();
		} 
		clientData.setClientLastPinged(System.currentTimeMillis());
		this.server.loginUser(recUUId, clientData);
		
		sendClientUDPMessage("You are now Logged In", MessageEncoder.SERVER_AUTHENTICATED);
	}
	
	private void caterListRequest(String listReq) throws Exception {
		final ArrayList<String> listRequest = MessageMaker.disassemblePacket(listReq);
		//System.out.println("Enc Usr ID: " + listRequest.get(0));		
		String RSADecUUID = CryptoOperations.RSADecrypt(listRequest.get(0), this.server.getServerConfig().getServerPrivateKeyFile());
		//System.out.println("RSADecUUID: " + RSADecUUID);		
		ClientsConnected clientData = this.server.onlineUsers.getClientById(UUID.fromString(RSADecUUID));

		if (clientData == null) {
			System.out.println("Requested User is not Online");
			return;
		}

		String listMsg = null;
		try {
			listMsg = CryptoOperations.AESDecrypt(clientData.getClientSessionKey(), listRequest.get(1));
		} catch (Exception e) {
			System.out.println("ERROR: While decrypting List request");
			e.printStackTrace();
			return;
		}

		final ArrayList<String> listMsgValues = MessageMaker.disassemblePacket(listMsg);
		final Long timestamp = Long.valueOf(listMsgValues.get(1));

		if (!(new TimeStamps()).verifyTimeStamps(timestamp)) {
			System.out.println("ERROR: List request timestamp expired");
			return;
		}

		final String[] listReqResponse = new String[2];
		listReqResponse[0] = this.server.onlineUsers.getOnlineClients();
		listReqResponse[1] = String.valueOf(timestamp + 1);

		String encListReqResponse;

		try {
			encListReqResponse = CryptoOperations.AESEncrypt(clientData.getClientSessionKey(), 
															MessageMaker.assemblePacket(listReqResponse));
			
			String[] encryptedReturnRsponse = new String[1];
			encryptedReturnRsponse[0] = encListReqResponse;
			
			sendClientUDPMessage(MessageMaker.assemblePacket(encryptedReturnRsponse), MessageEncoder.SERVER_LIST_RESPONSE);
		} catch (Exception e) {
			System.out.println("ERROR: While encrypting list response");
			e.printStackTrace();
			return;
		}
		
	}	

	/*Responds to ticket request from client*/
	private void caterTicketRequest(String ticketReqMsg) throws Exception {
		final ArrayList<String> ticketReq =MessageMaker.disassemblePacket(ticketReqMsg);
		
		ClientsConnected ticketReqFrom = this.server.onlineUsers.getClientById(UUID.fromString(CryptoOperations.RSADecrypt(ticketReq.get(0), 
																		this.server.getServerConfig().getServerPrivateKeyFile())));				
				
		if (ticketReqFrom == null) {
			System.out.println("ERROR: Ticket request by invalid user");
			return;
		}

		String decTicketReqMsg;
		try {
			decTicketReqMsg = CryptoOperations.AESDecrypt(ticketReqFrom.getClientSessionKey(), ticketReq.get(1));
		} catch (Exception e) {
			System.out.println("ERROR: while decrypting Ticket message");
			e.printStackTrace();
			return;
		}

		final ArrayList<String> decValues = MessageMaker.disassemblePacket(decTicketReqMsg);
		final Long ticketReqTimestamp = Long.valueOf(decValues.get(2));

		if (!(new TimeStamps()).verifyTimeStamps(ticketReqTimestamp)) {
			System.out.println("ERROR: Expired ticket request");
			return;
		}
		ClientsConnected ticketTo = null;
		try {
			String ticketToUserName = decValues.get(1);
			if (!this.server.isRegistered(ticketToUserName)) {
				System.out.println(ticketToUserName + " is not a valid user");
				return;
			}
			
			ticketTo = this.server.onlineUsers.getClientByName(decValues.get(1));
			
			if (ticketTo == null) {
				System.out.println(decValues.get(1) + " is offline");
				return;
			}

			SecretKey tempC2CSessionKey = null;
			try {
				tempC2CSessionKey = CryptoOperations.AESKeyGen();
			} catch (Exception e) {
				System.out.println("ERROR: While Generating temporary key");
				e.printStackTrace();
				return;
			}

			String[] ticketToClient = ClientTickets.generateClientTicket(ticketReqFrom.getUsername(), 
																			ticketTo.getUsername(),
																			ticketReqFrom.getClientUUId(), 
																			tempC2CSessionKey);
			
			String[] ticketResponseValues = new String[7];
			
			ticketResponseValues[0] = ticketTo.getUsername();
			ticketResponseValues[1] = ticketTo.getClientIp().getHostAddress();
			ticketResponseValues[2] = String.valueOf(ticketTo.getClientPort());
			ticketResponseValues[3] = new String(tempC2CSessionKey.getEncoded(), CryptoOperations.ISO_CHARSET);
			ticketResponseValues[4] = CryptoOperations.AESEncrypt(ticketTo.getClientSessionKey(), MessageMaker.assemblePacket(ticketToClient));
			ticketResponseValues[5] = String.valueOf((new TimeStamps()).getTimestamp());
			ticketResponseValues[6] = ticketTo.getClientUUId().toString();
			
			//System.out.println("Enc Ticket: " + ticketResponseValues[4]);
			String messageToSend = CryptoOperations.AESEncrypt(ticketReqFrom.getClientSessionKey(), MessageMaker.assemblePacket(ticketResponseValues));
			sendClientUDPMessage(messageToSend, MessageEncoder.SERVER_REQUESTED_TICKET_TO_CLIENT);

		} catch (Exception e) {
			System.out.println("ERROR: While sending ticket response");
			e.printStackTrace();
			return;
		}		
	}

	/*Responds to the logout request by clients*/
	private void caterLogoutRequest(String logoutReq) {
		
		final ArrayList<String> ResponseReceived = MessageMaker.disassemblePacket(logoutReq);
		UUID clientUUId = UUID.fromString(ResponseReceived.get(0));		
		ClientsConnected clientData = this.server.onlineUsers.getClientById(clientUUId);
		
		if (clientData == null) {
			System.out.println("ERROR: Client is not online");
			return;
		}
		
		String decLogoutReq = null;
		try {
			decLogoutReq = CryptoOperations.AESDecrypt(clientData.getClientSessionKey(), ResponseReceived.get(1));
		} catch (Exception e) {
			System.out.println("ERROR: While decrypting logout request by client");
			e.printStackTrace();
			return;
		}

		final ArrayList<String> logoutReqValues = MessageMaker.disassemblePacket(decLogoutReq);
		final Long logoutReqTimestamp = Long.valueOf(logoutReqValues.get(1));

		if (!(new TimeStamps()).verifyTimeStamps(logoutReqTimestamp)) {
			System.out.println("Expired logout timestamp");
			return;
		}

		String logoutResponse = String.valueOf(logoutReqTimestamp + 1);
		String encLogoutResponce = null;
		try {
			encLogoutResponce = CryptoOperations.AESEncrypt(clientData.getClientSessionKey(), 
															logoutResponse);
		} catch (Exception e) {
			System.out.println("ERROR: While encrypting Logout request");
			e.printStackTrace();
			return;
		}		
		sendClientUDPMessage(encLogoutResponce, MessageEncoder.SERVER_LOGOUT_RESPONSE);
		System.out.println(clientData.getUsername() + " logged out");
		this.server.logoutUser(clientUUId);
	}
	
	/* Responds to ping request */
	private void caterClientOnlineRequest(String pingMessage) {
		final ArrayList<String> pingMessageValues = MessageMaker.disassemblePacket(pingMessage);
		final ClientsConnected clientData = server.getOnlineUser(UUID.fromString(pingMessageValues.get(0)));

		if (clientData == null) {
			sendClientUDPMessage("", MessageEncoder.SERVER_RELOGIN_REQUEST);
			return;
		}

		String decPingMessage;
		try {
			decPingMessage = CryptoOperations.AESDecrypt(clientData.getClientSessionKey(),
															pingMessageValues.get(1));
		} catch (Exception e) {
			System.out.println("ERROR: While Decrypting ping request");
			e.printStackTrace();
			return;
		}

		final ArrayList<String> pingmsg = MessageMaker.disassemblePacket(decPingMessage);
		final Long pintTimestamp = Long.valueOf(pingmsg.get(1));
		final Long currentTime = (new TimeStamps()).getTimestamp();

		if (Math.abs(pintTimestamp - currentTime) >= 2 * 60 * 1000) {
			System.out.println("ERROR: Ping timestamp expired");
			return;
		}
		clientData.setClientLastPinged(currentTime);
		sendClientUDPMessage("", MessageEncoder.SERVER_PING_RESPONSE);
	}

	
	/* Sends the UDP Message to client*/
	private void sendClientUDPMessage(String sendMessage, MessageEncoder messageType) {
		
		sendMessage = messageType.getEncodedMessage(sendMessage);
		byte[] messageBytes = null;
		try {
			messageBytes = sendMessage.getBytes(CryptoOperations.ISO_CHARSET);
		} catch (UnsupportedEncodingException e1) {
			System.out.println("ERROR: While encoding send packet");
			e1.printStackTrace();
		}
		//System.out.println("\nServer Sent Bytes: " + messageBytes.length);
		DatagramPacket sendPacket = new DatagramPacket(messageBytes, messageBytes.length, this.msgIp, this.msgPort);
		try {
			serverSoc.send(sendPacket);
		} catch (IOException e) {
			System.out.println("ERROR: While sending the packet to client");
			e.printStackTrace();
			return;
		}
	}
	
	

	
	

}

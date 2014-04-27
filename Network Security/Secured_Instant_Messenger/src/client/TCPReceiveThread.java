package client;

import generic.CryptoOperations;
import generic.MessageEncoder;
import generic.MessageMaker;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;

/*Handles the TCP received messages
 * */
class TCPReceiveThread implements Runnable {

	private final Socket clientSocket;
	private final ClientTCPHandler clientTCPhandler;
		
	public TCPReceiveThread(Socket clientSocket, ClientTCPHandler clientTCPHandler) {
		this.clientSocket = clientSocket;
		this.clientTCPhandler = clientTCPHandler;
		(new Thread(this)).start();
	}
	
/* Receives messages from other clients and checks it message ID
 * and redirects it to take appropriate action
 * */	
@Override
public void run() {

	try {
		BufferedReader tcpIn = new BufferedReader(
		new InputStreamReader(this.clientSocket.getInputStream()));
		
		String inMessage = tcpIn.readLine();
		
		String tcpMessage = new String(new BigInteger(inMessage, 16).toByteArray(), CryptoOperations.ISO_CHARSET);
		
		if (tcpMessage.length() < 2) {
			System.out.println("ERROR: Invalid message received");
		} 
		else {
			MessageEncoder messageCode = null;
			try {
			messageCode = MessageEncoder.getMessageCode(tcpMessage);
			} catch (Exception e) {
					System.out.println("ERROR: Invalid TCP Message");
			}					
			if (messageCode.equals(MessageEncoder.CLIENT_TO_CLIENT_MESSAGE)) {
					
				ArrayList<String> clientDecodeMessage = MessageMaker.disassemblePacket(tcpMessage.substring(2));						
				UUID clientUUId = UUID.fromString(clientDecodeMessage.get(0));						
				ClientConnection clientConnection = this.clientTCPhandler.removeWaitingClients(clientUUId);
						
				if (clientConnection != null) {
					clientConnection.setClientSocket(clientSocket);
					clientConnection.receiveTCPMessage(tcpMessage);
				}
			}
					
				if (messageCode.equals(MessageEncoder.CLIENT_BYE_MESSAGE)) {
					
					ArrayList<String> clientDecodeByeMessage = MessageMaker.disassemblePacket(tcpMessage.substring(2));
					UUID peerId = UUID.fromString(clientDecodeByeMessage.get(0));						
					ClientConnection clientConnection = this.clientTCPhandler.removeWaitingClients(peerId);
						
					if (clientConnection != null) {
						clientConnection.setClientSocket(clientSocket);
						clientConnection.receiveTCPMessage(tcpMessage);
					}
				}
			}
		} catch (Exception e) {
				System.out.println("ERRRO: TCP Connection Error");
				e.printStackTrace();
			}	
		}
	}
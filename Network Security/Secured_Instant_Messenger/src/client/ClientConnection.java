package client;

import java.math.BigInteger;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.net.Socket;

import generic.*;

/* 
 * ClientConnection thread handles the TCP connections requested by other clients
 * Reads and sends the TCP messages from/to other clients 
 */
public class ClientConnection implements Runnable{
	
	private Client client;
	private Socket clientSocket;
	
	private ConnectedClientsData connectedClientData;
	
	private OutputStreamWriter outTCPStream;
	private OutputStream stream;
	
	private BufferedReader inTCPBuffer;
	

	public ClientConnection(Client client, ConnectedClientsData connectedClientData) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.connectedClientData = connectedClientData;
	}
	
	@Override
	public void run(){
		try {
			if(clientSocket.isConnected()){
				this.inTCPBuffer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				while (true) {
					String message = inTCPBuffer.readLine();
					if (message == null)
						break;
					else if (message.length() != 0) {
						message = new String(new BigInteger(message, 16).toByteArray(), CryptoOperations.ISO_CHARSET);
						(new Thread(new ClientMessage(message, this.client))).start();
				}
			}
			//Remove client info in case the connection is broken or client logs out without sending "bye"
			removeClientConnection();			
		} 
		}catch (Exception e) {
			
		}
	}
	
	/*Start thread to receive TCP message*/
	public void receiveTCPMessage(String message){
		(new Thread(new ClientMessage(message, this.client))).start();
	}
	
	/*Initialise TCP connection with client*/
	public void setClientSocket(Socket clientSocket){
		this.clientSocket = clientSocket;
		
		try {
			this.stream = this.clientSocket.getOutputStream();
			this.outTCPStream = new OutputStreamWriter(stream);
		} catch (IOException e) {
			System.out.println("ERROR: while getting the TCP output stream");
			e.printStackTrace();
		}		
		/* Start TCP Connection thread */
		(new Thread(this)).start();
	}
		
	/*Send message to TCP connected clients*/
	public void sendClientMessage(String message) {
		
		if (this.connectedClientData == null || 
				!this.client.connectedClients.isClientConnected(this.connectedClientData.getClientUsername())) {
			System.out.println("ERROR: "+connectedClientData.getClientUsername()+" is connected");
			return;
		}
		
		String[] clientMessagePacket = new String[2];
		clientMessagePacket[0] = this.client.getClientUUID().toString();
		
		String[] encryptedPacket = new String[2];
		encryptedPacket[0] = message;
		encryptedPacket[1] = String.valueOf((new TimeStamps().getTimestamp()));
		
		String encClientMsg = null;
		try {
			encClientMsg = CryptoOperations.AESEncrypt(this.connectedClientData.getClientSessionSecret(), 
														MessageMaker.assemblePacket(encryptedPacket));
		}catch (Exception e) {
			System.out.println("ERROR: Error while encrypting message to client");
			e.printStackTrace();
			return;
		}
		
		/*Generate HMAC signature for client Authenticity*/
		String sign_HMAC;
		try {
			sign_HMAC = CryptoOperations.getHMACSignature(connectedClientData.getClientSessionSecret(), encClientMsg);
		} catch (Exception e) {
			System.out.println("ERROR: While getting HMAC signature");
			e.printStackTrace();
			return;
		}
		clientMessagePacket[1] = sign_HMAC;		
		String sendPacket = MessageMaker.assemblePacket(clientMessagePacket);
		
		try{
		synchronized (stream) {
			
			String tcpMessage;
			//Destroy client connection if "bye" command is given
			if("BYE_REQUEST".equalsIgnoreCase(message)){				
				tcpMessage = 
					String.format("%x",
							new BigInteger(MessageEncoder.CLIENT_BYE_MESSAGE.getEncodedMessage(sendPacket).getBytes(CryptoOperations.ISO_CHARSET)));
				outTCPStream.write(tcpMessage + "\n");
				outTCPStream.flush();
				removeClientConnection();
			}
			else{
				tcpMessage = 
					String.format("%x",new BigInteger(MessageEncoder.CLIENT_TO_CLIENT_MESSAGE.getEncodedMessage(sendPacket).getBytes(CryptoOperations.ISO_CHARSET)));
			outTCPStream.write(tcpMessage + "\n");
			outTCPStream.flush();
			}
		}
		} catch(Exception e){
			System.out.println("ERROR: " + connectedClientData.getClientUsername() + " is not online");
			this.client.connectedClients.removeClientWithUUID(connectedClientData.getClientUUid());
			this.connectedClientData.destroyClientData();
			try {
				if(this.outTCPStream != null && this.clientSocket != null){
					outTCPStream.close();
					this.clientSocket.close();
				}
			} catch (IOException e1) {
				System.out.println("ERROR: While destroying client connection");
				e1.printStackTrace();
				return;
			}
		}
	}
	
	/* Disconnects TCP socket to the client in case if a client logs out or sends bye request
	 * */
	public void removeClientConnection(){
		System.out.println(this.connectedClientData.getClientUsername() + " is no longer in your connection");
		this.client.connectedClients.removeClientWithUUID(connectedClientData.getClientUUid());
		this.connectedClientData.destroyClientData();
		try {
			this.clientSocket.close();
			this.inTCPBuffer.close();
			this.outTCPStream.close();
		} catch (IOException e) {
			System.out.println("Failed to close socket connection with client");
			e.printStackTrace();
		}
		
	}
	
	/*Getter and Setters*/
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public ConnectedClientsData getPeerInfo() {
		return connectedClientData;
	}

	public void setPeerInfo(ConnectedClientsData peerInfo) {
		this.connectedClientData = peerInfo;
	}

	public Socket getPeerSocket() {
		return clientSocket;
	}

	public void setPeerSocket(Socket peerSocket) {
		this.clientSocket = peerSocket;
	}

	public OutputStreamWriter getOut() {
		return outTCPStream;
	}

	public void setOut(OutputStreamWriter out) {
		this.outTCPStream = out;
	}

	public OutputStream getStream() {
		return stream;
	}

	public void setStream(OutputStream stream) {
		this.stream = stream;
	}
	
	public BufferedReader getIn() {
		return inTCPBuffer;
	}

	public void setIn(BufferedReader in) {
		this.inTCPBuffer = in;
	}

}


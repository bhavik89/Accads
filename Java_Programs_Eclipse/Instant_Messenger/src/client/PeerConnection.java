package src.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.Socket;

import src.common.CryptoLibrary;
import src.common.HeaderHandler;
import src.common.CryptoLibrary.EncryptionException;
import src.common.CryptoLibrary.HmacException;
import src.common.MessageType;

/* PeerConnection handler the TCP connection between the client and
 * its peer. Reads and write to the steam and handle received 
 * messages to TCPHandler
 */
public class PeerConnection implements Runnable{
	
	private Client client;
	private PeerInfo peerInfo;
	private Socket peerSocket;
	private OutputStreamWriter out;
	private OutputStream stream;
	
	public PeerConnection(Client client, PeerInfo peerInfo) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.peerInfo = peerInfo;
	}
	
	public void setSocket(Socket peerSocket){
		this.peerSocket = peerSocket;
		try {
			this.stream = this.peerSocket.getOutputStream();
			this.out = new OutputStreamWriter(stream);
		} catch (IOException e) {
			System.out.println("Error creating output stream");
			e.printStackTrace();
		}
		
		/* Start receiving messages */
		(new Thread(this)).start();
	}
	
	public void receiveMessage(String message){
		(new Thread(new TCPMessageHandler(message, this.client))).start();
	}
	
	public void sendMessage(String message) {
		
		if (this.peerInfo == null || !this.client.peers.isExist(this.peerInfo.getPeerUsername())) {
			System.out.println(peerInfo.getPeerUsername() + "is not online");
			return;
		}
		
		String[] messageParams = new String[2];
		messageParams[0] = this.client.clientInfo.getUserId().toString();
		
		String[] encryptedMessageParams = new String[]{message, String.valueOf(System.currentTimeMillis())};
		String encryptedMessage = null;
		try {
			encryptedMessage = CryptoLibrary.aesEncrypt(
				this.peerInfo.getSecretKey(),
				HeaderHandler.pack(encryptedMessageParams)
			);
		} catch (EncryptionException e) {
			System.out.println("Error encrypting message");
			e.printStackTrace();
			return;
		}
		
		String hMac;
		try {
			hMac = CryptoLibrary.hmacCreate(
				peerInfo.getSecretKey(), encryptedMessage
			);
		} catch (HmacException e) {
			System.out.println("Error generating hmac for message");
			e.printStackTrace();
			return;
		}
		messageParams[1] = hMac;
		
		String response = HeaderHandler.pack(messageParams);
		
		try{
		synchronized (stream) {
			String string = String.format(
				"%x", 
				new BigInteger(MessageType.CLIENT_CLIENT_MESSAGE.createMessage(response).getBytes(CryptoLibrary.CHARSET))
			);
			out.write(string + "\n");
			out.flush();
		}
		} catch(Exception e){
			System.out.println(peerInfo.getPeerUsername() + " is not online anymore");
			this.client.peers.removePeer(peerInfo.getPeerUserId());
			this.peerInfo.destroy();
			try {
				if(this.out != null && this.peerSocket != null){
					out.close();
					this.peerSocket.close();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return;
			}
		}
	}
	
	@Override
	public void run(){
		try {
			BufferedReader in = new BufferedReader(
									new InputStreamReader(peerSocket.getInputStream()));
			while (true) {
				String message = in.readLine();
				if (message == null) { break; }
				else if (message.length() != 0) {
					message = new String(new BigInteger(message, 16).toByteArray(), CryptoLibrary.CHARSET);
					(new Thread(new TCPMessageHandler(message, this.client))).start();
				}
			}
			System.out.println(this.peerInfo.getPeerUsername() + " logged out");
			this.client.peers.removePeer(peerInfo.getPeerUserId());
			this.peerInfo.destroy();
			this.peerSocket.close();
			in.close();
			out.close();
		} catch (Exception e) {
			
		}
	}
}

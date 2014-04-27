package client;


import java.net.DatagramPacket;
import java.io.IOException;

import generic.*;

/* Ping Server for every given interval and tell server that client is online 
 * */

public class PingServerThread extends RunActions {
	private static long PING_TIME = 2 * 60 * 1000;
	private Long pingDuration;
	private boolean replyFromServer = false;
	private Client client;
	
	/*Constructor*/
	public PingServerThread(Client client) {
		super(PING_TIME);
		this.client = client;
	}
	
	/* Sends ping packet to server every minute
	 * */
	@Override
	protected void doTask() {
		this.replyFromServer = false;
		try {
			while (!this.replyFromServer) {
				pingServer();
				Thread.sleep(60 * 1000);
			}
		} catch (Exception e) {
		}
	}

	private void pingServer() {
		Long timeStampCurr = (new TimeStamps()).getTimestamp();
		this.pingDuration = timeStampCurr;

		final String[] pingPacket = new String[2];
		pingPacket[0] = "PING";
		pingPacket[1] = String.valueOf(timeStampCurr);

		String encPingPacket;
		try {
			encPingPacket = CryptoOperations.AESEncrypt(this.client.getClientServerSecretKey(),
															MessageMaker.assemblePacket(pingPacket));
		} catch (Exception e) {
			System.out.println("ERROR: While encrypting ping request");
			e.printStackTrace();
			return;
		}
		final String[] pingmessage = new String[2];
		pingmessage[0] = this.client.getClientUUID().toString();
		pingmessage[1] = encPingPacket;

		final String message = MessageEncoder.CLIENT_ONLINE_PACKET.getEncodedMessage(MessageMaker.assemblePacket(pingmessage));

		byte[] pingMessagebytes;
		try {
			pingMessagebytes = message.getBytes(CryptoOperations.ISO_CHARSET);
		} catch (Exception e) {
			System.out.println("ERROR: While encoding ping Bytes");
			e.printStackTrace();
			return;
		}
		
		DatagramPacket pingSocPacket = new DatagramPacket(pingMessagebytes, 
															pingMessagebytes.length,
															this.client.getServerIP(),
															this.client.getServerPort());

		try {
			this.client.getClientSocket().send(pingSocPacket);
		} catch (IOException e) {
			System.out.println("ERRRO: While sending ping packet to server");
			e.printStackTrace();
			return;
		}
	}
	
	/* Received the reply from server
	 * */
	public void recievedPing() {
		this.replyFromServer = true;
	}

	/*
	 * Time for last ping packet
	 */
	public Long getPingDuration() {
		return this.pingDuration;
	}
}

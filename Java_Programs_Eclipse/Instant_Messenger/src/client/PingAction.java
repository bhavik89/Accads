package src.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;

import src.common.Action;
import src.common.CryptoLibrary;
import src.common.CryptoLibrary.EncryptionException;
import src.common.HeaderHandler;
import src.common.MessageType;

/*
 * A recurring job that sends a ping to the server every interval, and
 * continues to send on every three minutes if no response is received
 * from the server.
 */
public class PingAction extends Action {
	private static long PING_JOB_INTERVAL = 3 * 60 * 1000; // 3 minutes
	private boolean receivedPong = false;
	private ClientInfo clientInfo;
	private Long pingTime;

	public PingAction(ClientInfo clientInfo) {
		super(PING_JOB_INTERVAL);
		this.clientInfo = clientInfo;
	}

	/*
	 * Indicate that a response was received from the server.
	 */
	public void recievedPong() {
		this.receivedPong = true;
	}

	/*
	 * @return The time the last ping was sent.
	 */
	public Long getPingTime() {
		return this.pingTime;
	}

	@Override
	protected void performAction() {
		this.receivedPong = false;

		try {
			while (!this.receivedPong) {
				sendPing();
				Thread.sleep(30 * 1000);
			}
		} catch (InterruptedException e) {
		}
	}

	private void sendPing() {
		Long currentTime = System.currentTimeMillis();

		this.pingTime = currentTime;

		final String[] encryptedParams = new String[2];
		encryptedParams[0] = "PING";
		encryptedParams[1] = String.valueOf(currentTime);

		String encryptedMessage;
		try {
			encryptedMessage = CryptoLibrary.aesEncrypt(
					this.clientInfo.getSecretKey(),
					HeaderHandler.pack(encryptedParams));
		} catch (EncryptionException e) {
			System.out.println("Error encrypting ping job");
			e.printStackTrace();
			return;
		}

		final String[] messageParams = new String[2];
		messageParams[0] = this.clientInfo.getUserId().toString();
		messageParams[1] = encryptedMessage;

		final String message = MessageType.CLIENT_SERVER_PING
				.createMessage(HeaderHandler.pack(messageParams));

		byte[] messageBytes;
		try {
			messageBytes = message.getBytes(CryptoLibrary.CHARSET);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return;
		}
		DatagramPacket packet = new DatagramPacket(messageBytes,
				messageBytes.length, this.clientInfo.getConnectionInfo()
						.getServerIp(), this.clientInfo.getConnectionInfo()
						.getServerPort());

		try {
			this.clientInfo.getConnectionInfo().getClientSocket().send(packet);
		} catch (IOException e) {
			System.out.println("Error sending ping packet");
			e.printStackTrace();
			return;
		}
	}
}

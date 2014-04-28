package src.client;

import java.util.ArrayList;
import java.util.UUID;

import src.common.CryptoLibrary;
import src.common.HeaderHandler;
import src.common.MessageType;
import src.common.TimestampManager;
import src.common.CryptoLibrary.DecryptionException;
import src.common.CryptoLibrary.HmacException;
import src.common.MessageType.UnsupportedMessageTypeException;

/* Handles the TCP messages between the client and peer 
 * Send encrypted messages and verify incoming messages
 */
public class TCPMessageHandler implements Runnable {

	private String message;
	private Client client;

	public TCPMessageHandler(String message, Client client) {
		this.message = message;
		this.client = client;
	}

	@Override
	public void run() {
		// Verify packet header length
		if (message.length() < 2) {
			System.out.println("Invalid message");

		} else {
			MessageType type = null;
			try {
				type = MessageType.getMessageType(message);

			} catch (UnsupportedMessageTypeException e) {
				return;
			}

			message = message.substring(2);

			switch (type) {

			case CLIENT_CLIENT_MESSAGE:
				this.communicate(message);
				break;

			default:
				break;
			}
		}
	}
	
	public void communicate(String message){
		final ArrayList<String> responseParams = HeaderHandler.unpack(message);
		PeerInfo peerInfo = this.client.peers.getPeer(UUID
				.fromString(responseParams.get(0)));
		String content;

		try {
			content = CryptoLibrary.hmacVerify(peerInfo.getSecretKey(),
					responseParams.get(1));
		} catch (HmacException e) {
			e.printStackTrace();
			return;
		}

		try {
			content = CryptoLibrary
					.aesDecrypt(peerInfo.getSecretKey(), content);
		} catch (DecryptionException e) {
			return;
		}

		final ArrayList<String> decryptedMessage = HeaderHandler
				.unpack(content);

		long timestamp = Long.valueOf(decryptedMessage.get(1));

		if (TimestampManager.isExpired(timestamp)) {
			System.out.println("Message Expired");
			return;
		}

		System.out.println(peerInfo.getPeerUsername() + ": "
				+ decryptedMessage.get(0));
	}
	
}

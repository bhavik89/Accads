package src.common;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

import javax.crypto.SecretKey;

/* Handles the Ticket for the server and client, generates a ticket 
 * for server and verify the incoming ticket for client.
 */
public class TicketManager {
	
	private static final int TIMESTAMP_LIMIT = 1 * 60 * 1000;
	
	public static String[] getTicket(String fromUserName, String to, UUID fromUserId, SecretKey key){
		long currentTime = System.currentTimeMillis();
		Ticket ticket = new Ticket(fromUserName, to, fromUserId, key, currentTime);
		String[] generatedTicket = new String[5];
		generatedTicket[0] = ticket.getToUserName();
		generatedTicket[1] = ticket.getFromUserName();
		generatedTicket[2] = String.valueOf(ticket.getFromUserId());
		try {
			generatedTicket[3] = new String(ticket.getTempSecretKey().getEncoded(), CryptoLibrary.CHARSET);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			System.out.println("Error including secret key in ticket");
			e.printStackTrace();
		}
		
		generatedTicket[4] = String.valueOf(ticket.getTimestamp());
		
		return generatedTicket;
	}
	
	public boolean verifyTicket(ArrayList<String> decryptedTicket, UUID userId){
	
		if(!userId.equals(UUID.fromString(decryptedTicket.get(2)))){
			return false;
		}
		
		final long timestamp = Long.valueOf(decryptedTicket.get(4));
		final Long currentTime = System.currentTimeMillis();

		if (Math.abs(timestamp - currentTime) >= TIMESTAMP_LIMIT) {
			System.out.println("Expired ticket");
			return false;
		}
		
		return true;
	}
	
	public static class Ticket{
		private String to;
		private String from;
		private UUID fromUserId;
		private long timestamp;
		private SecretKey tempSecretKey;
		
		public Ticket(String from, String to,UUID userId, SecretKey key, long timestamp){
			this.to = to;
			this.from = from;
			this.fromUserId = userId;
			this.tempSecretKey = key;
			this.timestamp = timestamp;
		}
		
		public String getFromUserName(){
			return this.from;
		}
		
		public String getToUserName(){
			return this.to;
		}
		
		public UUID getFromUserId(){
			return this.fromUserId;
		}
		
		public SecretKey getTempSecretKey(){
			return this.tempSecretKey;
		}
		
		public long getTimestamp(){
			return this.timestamp;
		}
	}
}

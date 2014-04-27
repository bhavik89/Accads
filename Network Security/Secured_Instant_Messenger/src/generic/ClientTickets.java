package generic;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.UUID;

import javax.crypto.SecretKey;

/*
 * Handles the tickets for clients 
 * */
public class ClientTickets {
	
	public static class Ticket{
		private String ticketTo;
		private String ticketFrom;
		private UUID ticketFromUUId;
		private long ticketTimestamp;
		private SecretKey tempSessionKey;
		
		public Ticket(String clientFrom, 
						String clientTo,
						UUID cleintFromUUId, 
						SecretKey sessionKey, 
						long timestamp){
			
			this.ticketTo = clientTo;
			this.ticketFrom = clientFrom;
			this.ticketFromUUId = cleintFromUUId;
			this.tempSessionKey = sessionKey;
			this.ticketTimestamp = timestamp;
		}
		
		public String getTicketFromUsername(){
			return this.ticketFrom;
		}
		
		public String getTicketToUsername(){
			return this.ticketTo;
		}
		
		public UUID getTicketFromUUId(){
			return this.ticketFromUUId;
		}
		
		public SecretKey getTempSessioney(){
			return this.tempSessionKey;
		}
		
		public long getTicketTimestamp(){
			return this.ticketTimestamp;
		}
	}
	
	private static final int TICKET_EXP_TIME = 1 * 60 * 1000;
	
	/*Generates the ticket to client */
	public static String[] generateClientTicket(String clientFrom, String clientTo, UUID clientFromUUID, SecretKey sessionKey){
		
		long ticketTime = (new TimeStamps().getTimestamp());
		
		Ticket ticket = new Ticket(clientFrom, clientTo, clientFromUUID, sessionKey, ticketTime);
		
		String[] cleintTicketValues = new String[5];
		cleintTicketValues[0] = ticket.getTicketToUsername();
		cleintTicketValues[1] = ticket.getTicketFromUsername();
		cleintTicketValues[2] = String.valueOf(ticket.getTicketFromUUId());
		try {
			cleintTicketValues[3] = new String(ticket.getTempSessioney().getEncoded(), CryptoOperations.ISO_CHARSET);
		} catch (UnsupportedEncodingException e) {
			System.out.println("ERROR: While generating the client ticket");
			e.printStackTrace();
		}		
		cleintTicketValues[4] = String.valueOf(ticket.getTicketTimestamp());		
		return cleintTicketValues;
	}
	
	/*Verifies the ticket received ticket*/
	public boolean verifyReceivedTicket(ArrayList<String> recTicketvalues, UUID ticketUUId){
	
		if(!ticketUUId.equals(UUID.fromString(recTicketvalues.get(2))))
			return false;		
		
		final long ticketTimestamp = Long.valueOf(recTicketvalues.get(4));
		final Long currTime = (new TimeStamps()).getTimestamp();

		if (Math.abs(ticketTimestamp - currTime) >= TICKET_EXP_TIME) {
			return false;
		}		
		return true;
	}
	
	
}

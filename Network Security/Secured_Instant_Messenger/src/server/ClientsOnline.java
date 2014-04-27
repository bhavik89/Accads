package server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

/* Class to keep record of currently online clients
 * Logout them if they haven't pinged for the given time
 * */
public class ClientsOnline {
	
	public static final int CLIENT_TIMEOUT = 5 * 60 * 1000; 	
	
	private final ConcurrentHashMap<UUID, ClientsConnected> onlineClients = new ConcurrentHashMap<UUID, ClientsConnected>();
	
	private String onlineClinetList = "";
	
	private static boolean reLoginClient = false;
	
	public ClientsOnline() {
		new Thread(new LogoutClientThread()).start();
	}
	
	public String getOnlineClients(){
		synchronized (onlineClients) {
			if(reLoginClient){
				renewOnlineClientsList();
				reLoginClient = false;
			}
		}
		return onlineClinetList;
	}	
	
	
	public boolean isClientOnline(String username) {
		if(this.onlineClients == null){
			return false;
		}
		
		for (ClientsConnected user : this.onlineClients.values()) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isClientOnline(UUID clientUUId){
		return this.onlineClients.containsKey(clientUUId);
	}
	
	public boolean isClientOnline(int cleintPort, InetAddress clientIP) {
		if(this.onlineClients == null)
			return false;
				
		for (ClientsConnected user : this.onlineClients.values()) {
			if (user.getClientPort() == cleintPort && user.getClientIp().equals(clientIP)) {
				return true;
			}
		}
		return false;
	}
	
	public ClientsConnected getClientByName(String clientuserName){
		if(this.onlineClients == null){
			return null;
		}
		
		for (ClientsConnected user : this.onlineClients.values()) {
			if (user.getUsername().equals(clientuserName)) {
				return user;
			}
		}		
		return null;
	}
	
	public ClientsConnected getClientById(UUID clientID){
		return onlineClients.get(clientID);
	}	
	
	public void addClientToList(UUID clientUUId, ClientsConnected client){
		if(clientUUId == null){
			throw new IllegalArgumentException("ERROR: Invalid User ID");
		}
		
		synchronized (onlineClients) {
			if(!onlineClients.containsKey(clientUUId)){
				onlineClients.put(clientUUId, client);
			}
			renewOnlineClientsList();
		}
	}
	
	public void removeClientFromList(UUID clientUUid){
		ClientsConnected client = null;
		
		synchronized (onlineClients) {	
			
			if(!onlineClients.contains(clientUUid)){
				client = onlineClients.remove(clientUUid);
			}
			
			if(client != null){
				renewOnlineClientsList();
				client.setClientLastPinged(-1);
				client.removeCleintSessionKey();
				client.setCleintUUId(null);
			}
			
		}
	}
	
	public void renewOnlineClientsList(){
		final StringBuilder clinetList = new StringBuilder();
		
		final ArrayList<String> usernames = new ArrayList<String>();
		
		for(ClientsConnected user: onlineClients.values()){
			usernames.add(user.getUsername());
		}
		
		Collections.sort(usernames);
		
		final Iterator<String> iter = usernames.iterator();
		
		while(iter.hasNext()){
			clinetList.append(iter.next());
			
			if (iter.hasNext()) {
				clinetList.append(",");
			}
		}		
		onlineClinetList = clinetList.toString();
	}
	
	/* Remove Client record if the client havn't pinged for the given interval
	 * */
	private class LogoutClientThread extends RemoveRecords<UUID, ClientsConnected> {
		
		public LogoutClientThread(){
			super(CLIENT_TIMEOUT, onlineClients);
		}
		@Override
		protected boolean cookieExpired(ClientsConnected client, long removalTimeout){
			final boolean removeClient = client.getClientLastPinged() <= removalTimeout;
			
			if(removeClient){
				client.setClientLastPinged(-1);
				client.removeCleintSessionKey();
				client.setCleintUUId(null);
				
				System.out.println("Logged out " + client.getUsername() + " due to no ping response");
					reLoginClient = true;
			}			
			return removeClient;
		}
	}
}

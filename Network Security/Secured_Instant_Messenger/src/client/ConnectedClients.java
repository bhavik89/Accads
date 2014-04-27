package client;

import java.util.HashMap;
import java.util.UUID;

/* Class for a client connected with other clients
 */
public class ConnectedClients {
	
	public HashMap<UUID,ConnectedClientsData> connectedClientMap = new HashMap<UUID, ConnectedClientsData>();
		
	public ConnectedClientsData getClientByUserName(String username){
		if(this.connectedClientMap == null)
			return null;
				
		for(ConnectedClientsData connectedClient: this.connectedClientMap.values()){
			if(connectedClient.getClientUsername().equals(username)){
				return connectedClient;
			}
		}		
		return null;
	}
	
	public ConnectedClientsData getClient(UUID userId){
		if(this.connectedClientMap == null){
			return null;
		}		
		return this.connectedClientMap.get(userId);
	}
	
	public boolean isClientConnected(UUID userId){
		return this.connectedClientMap.containsKey(userId);
	}
	
	public boolean isClientConnected(String username){
		if(this.connectedClientMap == null)
			return false;
				
		for (ConnectedClientsData connectedClient: this.connectedClientMap.values()) {
			if (connectedClient.getClientUsername().equals(username)) 
				return true;			
		}
		return false;
	}
	
	public void addClientInConnections(UUID userId, ConnectedClientsData peerInfo){
		this.connectedClientMap.put(userId, peerInfo);
	}
	
	public void removeClientWithUUID(UUID userId){
		this.connectedClientMap.remove(userId);
	}
	
	public void clear(){
		this.connectedClientMap.clear();
	}
	
	
}

package src.client;

import java.util.HashMap;
import java.util.UUID;

/* Peers are the online users and share the
 * secret key with client.
 */
public class Peers {
	
	public HashMap<UUID,PeerInfo> peers = new HashMap<UUID, PeerInfo>();
	
	public void addPeer(UUID userId, PeerInfo peerInfo){
		this.peers.put(userId, peerInfo);
	}
	
	public void removePeer(UUID userId){
		this.peers.remove(userId);
	}
	
	public void clear(){
		this.peers.clear();
	}
	
	public boolean isExist(UUID userId){
		return this.peers.containsKey(userId);
	}
	
	public boolean isExist(String username){
		if(this.peers == null){
			return false;
		}
		
		for (PeerInfo peer: this.peers.values()) {
			if (peer.getPeerUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public PeerInfo getPeerByUserName(String username){
		if(this.peers == null){
			return null;
		}
		
		for(PeerInfo peer: this.peers.values()){
			if(peer.getPeerUsername().equals(username)){
				return peer;
			}
		}
		
		return null;
	}
	
	public PeerInfo getPeer(UUID userId){
		if(this.peers == null){
			return null;
		}
		
		return this.peers.get(userId);
	}
}

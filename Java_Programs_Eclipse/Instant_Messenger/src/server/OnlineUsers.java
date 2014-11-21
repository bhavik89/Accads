package src.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;

import src.common.PruneAction;

/* Users currently logged in onto the server and logout users
 * who haven't pinged the server in last 10 minutes*/
public class OnlineUsers {
	/*
	 * The interval in milliseconds in which the job to prune logged in users
	 * is run. If a user has not pinged the server within the last
	 * <code>LOGOUT_USER_JOB_INTERVAL</code> milliseconds they will be logged 
	 * out.
	 */
	public static final int LOGOUT_USER_JOB_INTERVAL = 10 * 60 * 1000; // 10 minutes
	
	private final ConcurrentHashMap<UUID, UserInfo> users = 
		new ConcurrentHashMap<UUID, UserInfo>();
	private String userListCache = "";
	private static boolean regenerateCache = false;
	
	public OnlineUsers() {
		new Thread(new LogoutUserJob()).start();
	}
	
	public String getUsers(){
		synchronized (users) {
			if(regenerateCache){
				regenerateUserListCache();
				regenerateCache = false;
			}
		}
		return userListCache;
	}
	
	public boolean isOnline(UUID userId){
		return this.users.containsKey(userId);
	}
	
	public boolean isOnline(String username) {
		if(this.users == null){
			return false;
		}
		
		for (UserInfo user : this.users.values()) {
			if (user.getUsername().equals(username)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isOnline(int port, InetAddress ip) {
		if(this.users == null){
			return false;
		}
		
		for (UserInfo user : this.users.values()) {
			if (user.getUserPort() == port && user.getUserIp().equals(ip)) {
				return true;
			}
		}
		return false;
	}
	
	public UserInfo getUser(UUID userId){
		return users.get(userId);
	}
	
	public UserInfo getUser(String userName){
		if(this.users == null){
			return null;
		}
		
		for (UserInfo user : this.users.values()) {
			if (user.getUsername().equals(userName)) {
				return user;
			}
		}
		
		return null;
	}
	
	public void addUser(UUID userId, UserInfo user){
		if(userId == null){
			throw new IllegalArgumentException("id not set on user");
		}
		
		synchronized (users) {
			if(!users.containsKey(userId)){
				users.put(userId, user);
			}
			regenerateUserListCache();
		}
	}
	
	public void removeUser(UUID id){
		UserInfo user = null;
		synchronized (users) {	
			if(!users.contains(id)){
				user = users.remove(id);
			}
			if(user != null){
				regenerateUserListCache();
				user.setLastPinged(-1);
				user.destroySessionKey();
				user.setUserId(null);
			}
		}
	}
	
	public void regenerateUserListCache(){
		final StringBuilder userList = new StringBuilder();
		final ArrayList<String> usernames = new ArrayList<String>();
		
		for(UserInfo user: users.values()){
			usernames.add(user.getUsername());
		}
		
		Collections.sort(usernames);
		final Iterator<String> usernameIterator = usernames.iterator();
		
		while(usernameIterator.hasNext()){
			userList.append(usernameIterator.next());
			
			if (usernameIterator.hasNext()) {
				userList.append(",");
			}
		}
		
		userListCache = userList.toString();
	}
	
	/*
	 * Job to logout users who haven't pinged the server in the last
	 * <code>LoggedInUsers.LOGOUT_USER_JOB_INTERVAL</code> 10 minutes.
	 */
	private class LogoutUserJob extends PruneAction<UUID, UserInfo> {
		
		public LogoutUserJob(){
			super(LOGOUT_USER_JOB_INTERVAL, users);
		}
		@Override
		protected boolean isPrunable(UserInfo user, long pruneBefore){
			final boolean logout = user.getLastPinged() <= pruneBefore;
			
			if(logout){
				user.setLastPinged(-1);
				user.destroySessionKey();
				user.setUserId(null);
				System.out.println("Logged out " + user.getUsername() + 
						" for expired ping");
					regenerateCache = true;
			}
			
			return logout;
		}
	}
}

package server;

import java.util.concurrent.ConcurrentHashMap;
import java.net.InetAddress;
import java.security.SecureRandom;

public class ClientCookies {

	//Time Interval for Cookie removal
	public static final int REMOVE_COOKIES_TIME = 3 * 60 * 1000; // 3 minutes
	
	/*Synchronised HashMap to add and drop cookies*/
	private static ConcurrentHashMap<InetAddress, Cookie> clientCookiesStore = new ConcurrentHashMap<InetAddress, Cookie>();
	
	/*Cookie Class to store cookie information*/
	public static class Cookie {
		private long cookieTime;
		private long cookieValue;
		//private static Random rand = new Random(System.currentTimeMillis());
		private static SecureRandom secRandom = new SecureRandom();
		
		public Cookie() {
			cookieTime = System.currentTimeMillis();
			cookieValue = secRandom.nextLong();
		}

		public long getCookieTime() {
			return cookieTime;
		}

		public long getCookieValue() {
			return cookieValue;
		}
	}
	
	/*Returns cookie for the given seed as clientIP address*/
	public static long generateClientCookie(InetAddress cleintIP) {
		Cookie cookie = new Cookie();
		clientCookiesStore.put(cleintIP, cookie);
		return cookie.getCookieValue();
	}
	
	/*Verify the cookie returned from the client against their IP address*/
	public static boolean verifyClientCookie(InetAddress clientIP, String receivedCookie) {
		
		//System.out.println(receivedCookie.replaceAll("\0", "").length());
		
		Cookie cookieStored = clientCookiesStore.get(clientIP);
		Long returnedCookie = null;
		try{
		returnedCookie = Long.valueOf(receivedCookie.replaceAll("\0", ""));
		}catch  (NumberFormatException e){
			System.out.println("ERROR: Invalid Cookie Format");
			e.printStackTrace();
		}

		if (cookieStored != null && cookieStored.getCookieValue() == returnedCookie) {
			clientCookiesStore.remove(clientIP);
			return true;
		}
		return false;
	}

	//Cookie removal class	
	public static class RemoveOldCookie extends RemoveRecords<InetAddress, Cookie> {

		public RemoveOldCookie() {
			super(REMOVE_COOKIES_TIME, clientCookiesStore);
		}
		@Override
		protected boolean cookieExpired(Cookie cookie, long pruneBefore) {
			return cookie.getCookieTime() <= pruneBefore;
		}
	}
	
	// Start thread to remove cookies from previous clients
		static {
			new Thread(new RemoveOldCookie()).start();
		}
}
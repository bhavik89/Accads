package src.common;

import java.net.InetAddress;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/* Manages the cookies for the server.
 * Cookie is pseudo random number
 */
public class CookieManager {

	/*
	 * The interval in minutes for the cookie pruning job to run. Cookies
	 * will live for at least this amount of time and at most for twice this
	 * amount of time.
	 */
	public static final int PRUNE_ACTION_INTERVAL = 3 * 60 * 1000; // 3 minutes

	/* Concurrent Hashmap so that we could remove the cookies safely */
	private static ConcurrentHashMap<InetAddress, Cookie> cookies = new ConcurrentHashMap<InetAddress, Cookie>();

	public static long generateCookie(InetAddress ip) {
		Cookie cookie = new Cookie();
		cookies.put(ip, cookie);

		return cookie.getCookie();
	}

	public static boolean verifyCookie(InetAddress ip, String cookie) {
		Cookie storedCookie = cookies.get(ip);
		Long inputCookie = Long.valueOf(cookie);

		if (storedCookie != null && storedCookie.getCookie() == inputCookie) {
			cookies.remove(ip);
			return true;
		}

		return false;
	}

	// start the prune cookie job running
	static {
		new Thread(new PruneCookieAction()).start();
	}

	public static class Cookie {
		private long creationTime;
		private long cookie;
		private static Random rand = new Random(System.currentTimeMillis());

		public Cookie() {
			creationTime = System.currentTimeMillis();
			cookie = rand.nextLong();
		}

		public long getCreationTime() {
			return creationTime;
		}

		public long getCookie() {
			return cookie;
		}
	}

	public static class PruneCookieAction extends
			PruneAction<InetAddress, Cookie> {

		public PruneCookieAction() {
			super(PRUNE_ACTION_INTERVAL, cookies);
		}

		@Override
		protected boolean isPrunable(Cookie cookie, long pruneBefore) {
			return cookie.getCreationTime() <= pruneBefore;
		}
	}
}
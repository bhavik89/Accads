package src.server;

import java.util.ArrayList;

import src.server.UserInfo;

import src.common.ConfigReader;

/* Congiruation reader for reading the server config file */
public class ServerConfigReader extends ConfigReader {
	
	 /* The port the server will listen on */
	public int port;
	
	/*The location of the server's private key. */
	public String privateKeyLocation;
	
	/*The list of users.*/
	public ArrayList<UserInfo> users = new ArrayList<UserInfo>();
	
	/*
	 * Parse the config file of the given name.
	 * @param filename The filename of the config file to parse.
	 * @throws ConfigReaderException Thrown for all errors.
	 */
	public ServerConfigReader(String filename)
			throws ConfigReaderException 
	{
		super.parseFile(new ConfigEntryAction[] {
			new PortEntry(),
			new UserEntry(),
			new PrivateKeyEntry()
		}, filename);
	}

	/* Config entry action for the server's port.*/
	private class PortEntry extends ConfigEntryAction {
		public PortEntry() { super("port"); }

		@Override
		public void performAction(String value) {
			port = Integer.valueOf(value);		
		}
	}
	
	/* Config entry action for the user entries.*/
	private class UserEntry extends ConfigEntryAction {
		public UserEntry() { super("user"); }

		@Override
		public void performAction(String value) {
			String params[] = value.split(":", 2);
			users.add(new UserInfo(params[0], params[1]));
		}
	}
	
	/* Config entry action for the server's private key location.*/
	private class PrivateKeyEntry extends ConfigEntryAction {
		public PrivateKeyEntry() { super("privateKey"); }

		@Override
		public void performAction(String value) {
			privateKeyLocation = value;
		}
	}

}

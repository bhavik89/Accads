package src.client;

import java.net.InetAddress;
import java.net.UnknownHostException;

import src.common.ConfigReader;


/*
 * Config file reader for the client.
 */
public class ClientConfigReader extends ConfigReader {
	private int port;
	private int serverPort;
	private InetAddress serverAddress;
	private String publicKeyLocation;
	
	/*
	 * Parse the config file in the given location.
	 * @param filename The location of the config file to parse.
	 * @throws ConfigReaderException Thrown for all errors.
	 */
	public ClientConfigReader(String filename)
			throws ConfigReaderException 
	{
		super.parseFile(new ConfigEntryAction[] {
			new PortEntry(),
			new ServerPortEntry(),
			new ServerAddressEntry(),
			new PublicKeyEntry()
		}, filename);
	}

	public int getPort(){
		return this.port;
	}
	
	public int getServerPort(){
		return this.serverPort;
	}
	
	public InetAddress getServerAddress(){
		return this.serverAddress;
	}
	
	public String getPublicKeyLocation(){
		return this.publicKeyLocation;
	}
	
	/*
	 * The config entry action for the client's port.
	 */
	private class PortEntry extends ConfigEntryAction {
		public PortEntry() { super("port"); }

		@Override
		public void performAction(String value) {
			port = Integer.valueOf(value);		
		}
	}
	
	/*
	 * The config entry action for the server's port.
	 */
	private class ServerPortEntry extends ConfigEntryAction {
		public ServerPortEntry() { super("serverPort"); }

		@Override
		public void performAction(String value) {
			serverPort = Integer.valueOf(value);		
		}
	}
	
	/*
	 * The config entry action for the server's address.
	 */
	private class ServerAddressEntry extends ConfigEntryAction {
		public ServerAddressEntry() { super("serverAddress"); }

		@Override
		public void performAction(String value) throws ConfigReaderException {
			try {
				serverAddress = InetAddress.getByName(value);
			} catch (UnknownHostException e) {
				throw new ConfigReaderException(e);
			}		
		}
	}
	
	/*
	 * The config entry action for the server's public key location.
	 */
	private class PublicKeyEntry extends ConfigEntryAction {
		public PublicKeyEntry() { super("publicKey"); }

		@Override
		public void performAction(String value) {
			publicKeyLocation = value;
		}
	}
}

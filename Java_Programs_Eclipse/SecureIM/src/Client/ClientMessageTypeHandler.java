package Client;

public enum ClientMessageTypeHandler {
	
	CLIENT_SERVER_HELLO("00"),
	SERVER_CLIENT_COOKIE("01"),
	CLIENT_SERVER_AUTH("02"),
	SERVER_CLIENT_AUTH("03"),
	CLIENT_SERVER_VERIFY("04");
	
	private final String messageID;
	
	/* Constructor */
	private ClientMessageTypeHandler(String messageID) {
		// TODO Auto-generated constructor stub
		this.messageID = messageID;
	}
	
	/* getter */
	public String getMessageId() {
		return this.messageID;
	}
	
	/* Return the input message appended to message id */
	public String createMessage(String message){
		StringBuffer out = new StringBuffer();
		out.append(this.messageID);
		out.append(message);
		return out.toString();
	}
	

}

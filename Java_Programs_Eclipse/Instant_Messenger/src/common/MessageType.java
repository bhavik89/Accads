package src.common;

/* Types of messages exchanged between client 
 * and server, client and client.
 */
public enum MessageType{
	
	CLIENT_SERVER_HELLO("00"),
	SERVER_CLIENT_COOKIE("01"),
	CLIENT_SERVER_AUTH("02"),
	SERVER_CLIENT_AUTH("03"),
	CLIENT_SERVER_VERIFY("04"),
	CLIENT_SERVER_LIST("05"),
	SERVER_CLIENT_LIST("06"),
	CLIENT_SERVER_TALK_REQUEST("07"),
	SERVER_CLIENT_TICKET("08"),
	CLIENT_CLIENT_HELLO("09"),
	CLIENT_CLIENT_HELLO_RESPONSE("10"),
	CLIENT_CLIENT_MUTH_AUTH("11"),
	CLIENT_CLIENT_MESSAGE("12"),
	CLIENT_SERVER_LOGOUT("13"),
	SERVER_CLIENT_LOGOUT("14"),
	CLIENT_SERVER_PING("15"),
	SERVER_CLIENT_PING_RESPONSE("16"),
	SERVER_CLIENT_REAUTHENTICATE("17");
	
	private final String id;
	
	/* Constructor */
	private MessageType(String id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	/* getter */
	public String getId() {
		return this.id;
	}
	
	/* Return the input message appended to message id */
	public String createMessage(String message){
		StringBuffer out = new StringBuffer();
		out.append(this.id);
		out.append(message);
		return out.toString();
	}
	
	public static MessageType getMessageType(String message) throws UnsupportedMessageTypeException{
		final String id = message.substring(0,2);
		
		for(MessageType type: MessageType.values()){
			if(type.getId().equals(id)){
				return type;
			}
		}
		
		throw new UnsupportedMessageTypeException(id);
	}
	
	@SuppressWarnings("serial")
	public static class UnsupportedMessageTypeException extends Exception{
		public UnsupportedMessageTypeException(String typeId){
			super("UnsupportedMessageType:" + typeId);
		}
	}
}
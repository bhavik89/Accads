package Generic;

import java.util.List;

public enum MessageTypeHandler {

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
	private MessageTypeHandler(String id) {
		// TODO Auto-generated constructor stub
		this.id = id;
	}
	
	/* getter */
	public String getId() {
		return this.id;
	}
	
	/* Return the input message appended to message id */
	public String createMessage(List<Object> response){
		StringBuffer out = new StringBuffer();
		out.append(this.id);
		out.append(response);
		return out.toString();
	}
	
	public String createMessage(String response){
		StringBuffer out = new StringBuffer();
		out.append(this.id);
		out.append(response);
		return out.toString();
	}
	
	public static MessageTypeHandler getMessageType(String message) throws UnsupportedMessageTypeException{
		final String id = message.substring(0,2);
		
		for(MessageTypeHandler type: MessageTypeHandler.values()){
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

package generic;

/* Enum class for message codes */
public enum MessageEncoder {

	CLIENT_HELLO("00"),
	SERVER_COOKIE("01"),
	CLIENT_AUTH_REQUEST("02"),
	SERVER_AUTHENTCATION_BEGIN("03"),
	CLIENT_AUTH_VERIFY("04"),
	CLIENT_LIST_REQUEST("05"),
	SERVER_LIST_RESPONSE("06"),
	CLIENT_TICKET_REQUEST("07"),
	SERVER_REQUESTED_TICKET_TO_CLIENT("08"),
	CLIENT_TO_CLIENT_AUTH_BEGIN("09"),
	CLIENT_TO_CLIENT_AUTH_RESPONSE("10"),
	CLIENT_CLIENT_AUTH_SUCCESS("11"),
	CLIENT_TO_CLIENT_MESSAGE("12"),
	CLIENT_LOGOUT_REQUEST("13"),
	SERVER_LOGOUT_RESPONSE("14"),
	CLIENT_ONLINE_PACKET("15"),
	SERVER_PING_RESPONSE("16"),
	SERVER_RELOGIN_REQUEST("17"),
	SERVER_AUTHENTICATED("18"),
	CLIENT_BYE_MESSAGE("19");
	
	
	private final String messageID;
	
	/* Constructor */
	private MessageEncoder(String messID) {
		this.messageID = messID;
	}	
	
	/*Returns the Message code for the given message*/
	public static MessageEncoder getMessageCode(String codedMessage){
		final String msgID = codedMessage.substring(0,2);
		
		for(MessageEncoder messageType: MessageEncoder.values()){
			if(messageType.getMessageID().equals(msgID)){
				return messageType;
			}
		}
		return null;	
	}
	
	/*Generate a message with message code*/
	public String getEncodedMessage(String response){
		StringBuffer outBuffer = new StringBuffer();
		outBuffer.append(this.messageID);
		outBuffer.append(response);
		return outBuffer.toString();
	}
	
	
	
	/* getter */
	public String getMessageID() {
		return this.messageID;
	}
	
	
}

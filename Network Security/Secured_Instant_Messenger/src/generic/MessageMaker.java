package generic;

import java.util.ArrayList;


public final class MessageMaker {
	
	/* Assembles the packet contents and constructs a string with the lenghts and delimiter*/
	public static String assemblePacket(String[] messageValues){
		StringBuilder lenghts = new StringBuilder();
		StringBuilder actualMessage = new StringBuilder();
		
		char messageDelimiter = '|';
		
		int index = 0;
		for(int i=0; i<messageValues.length; i++){
			final String parameter = messageValues[i];
						
			actualMessage.append(parameter);
			index += parameter.length();
			
			lenghts.append(index);
			messageDelimiter = (i == messageValues.length - 1) ? ';' : '|';
			lenghts.append(messageDelimiter);
		}
		//System.out.println(header.append(content).toString());
		return lenghts.append(actualMessage).toString();
	}
	
	/*Remove the message contents from the given message with lengths and delimiter*/
	public static ArrayList<String> disassemblePacket(String messageWithLnD){
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<Integer> contentIndexes = new ArrayList<Integer>();
		
		int index = 0;
		int splitIndex = 0;
		char delimeterChar;
		boolean messageRecovered = false;
		while(!messageRecovered && index < messageWithLnD.length()){
			delimeterChar = messageWithLnD.charAt(index);
			
			switch (delimeterChar) {
			
			case ';':
				messageRecovered = true;
				break;
			
			case '|':
				contentIndexes.add(splitIndex);
				splitIndex = 0;
				break;
			
			default:
				splitIndex *= 10;
				splitIndex += Integer.valueOf(new String(new char[] {delimeterChar}));
				break;
			}
			index ++;
		}
		
		int startIndex = index;
		int lastIndex = startIndex;
		//Split the message and get the message strings
		for (Integer split: contentIndexes){
				lastIndex = split + index;
				values.add(messageWithLnD.substring(startIndex,lastIndex));
				startIndex = lastIndex;
		}
		values.add(messageWithLnD.substring(startIndex));
		
		return values;
	}
		
}
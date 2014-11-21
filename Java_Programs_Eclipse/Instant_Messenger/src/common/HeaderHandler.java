package src.common;

import java.util.ArrayList;

/* Handles the header of messages, prefix the message lengths 
 * to message and unwrap the incoming messages.
 */
public final class HeaderHandler {
	
	/* Pack the content with header in format 
	 * length1| length2| length3;content1content2content3 */
	public static String pack(String[] params){
		StringBuilder header = new StringBuilder();
		StringBuilder content = new StringBuilder();
		
		char delimiter = '|';
		
		int index = 0;
		for(int i=0; i<params.length; i++){
			final String parameter = params[i];
			
			content.append(parameter);
			index += parameter.length();
			
			header.append(index);
			delimiter = (i == params.length - 1) ? ';' : '|';
			header.append(delimiter);
		}
		return header.append(content).toString();
	}
	
	/* unpack the packed message content into separate parts*/
	public static ArrayList<String> unpack(String message){
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Integer> splits = new ArrayList<Integer>();
		
		int index = 0;
		int splitPoint = 0;
		char character;
		boolean done = false;
		while(!done && index < message.length()){
			character = message.charAt(index);
			switch (character) {
			case ';':
				done = true;
				break;
			case '|':
				splits.add(splitPoint);
				splitPoint = 0;
				break;
			default:
				splitPoint *= 10;
				splitPoint += Integer.valueOf(new String(new char[] {character}));
				break;
			}
			index ++;
		}
		
		int begin = index;
		int end = begin;
		for (Integer split: splits){
			end = split + index;
			params.add(message.substring(begin,end));
			begin = end;
		}
		params.add(message.substring(begin));
		
		return params;
	}
}
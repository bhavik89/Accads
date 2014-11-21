

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegisterLoginProcessing implements Serializable {


	public static String HashTheString(String val) throws NoSuchAlgorithmException{

		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");		
		messageDigest.update(val.getBytes());
		byte encryptedpasswd [] = messageDigest.digest();
		//System.out.println(encryptedpasswd);

		StringBuilder sb = new StringBuilder();
		for(int i = 0; i< encryptedpasswd.length; i++)
		{
			sb.append(Integer.toString((encryptedpasswd[i] & 0xff) + 0x100, 16).substring(1));
		}

		String generatedVal = sb.toString();
		return generatedVal;
	}





	public static void ServerSendStream(Socket connectionSocket, Object dataSent ) throws Exception{

		ObjectOutputStream sendStream = new ObjectOutputStream(connectionSocket.getOutputStream());
		sendStream.writeObject(dataSent);
	}	


	public static Object ReceiveDataStream(Socket connectionSocket) throws Exception{

		ObjectInputStream getMessage = new ObjectInputStream(connectionSocket.getInputStream());
		Object receiveData = getMessage.readObject();
		//System.out.println(receiveData);
		return receiveData;		
	}

}

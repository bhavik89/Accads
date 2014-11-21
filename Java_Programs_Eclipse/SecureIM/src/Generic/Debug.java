package Generic;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.*;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class Debug {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {
		
		//Generate AES key object for AES Encryption
		KeyGenerator genAESKey = KeyGenerator.getInstance("AES");		
		//Generate AES key size of key size specified above (here it is 128 bits)
		genAESKey.init(256);		
		//Generate AES key
		SecretKey AESKey = genAESKey.generateKey();
		System.out.println("Generated AES Key...");	
		System.out.println("AES Key :" + new String(AESKey.getEncoded(), CryptoOperations.CHARSET));
		//Encrypt AES Secret key with destination's PublicKey
		String RSAencryptedAESKey = rsaEncrypt("Hellowassup","serverPublicKey.key");
		
		String DecKey = rsaDecryptAES(RSAencryptedAESKey,  "serverPrivateKey.key");
		
		System.out.println("DecKey :" + DecKey);
		
	}
	
	
private static String rsaEncrypt(String aESKey, String receiverPublicKey) 
			throws NoSuchAlgorithmException, 
				   NoSuchPaddingException, 
			       InvalidKeyException, 
			       IllegalBlockSizeException, 
			       IOException, BadPaddingException {

	//byte[] dataToEncrypt = aESKey.toString().getBytes();
	String encryptedData = null;
	//get receiver's public key
	PublicKey pubKey = (PublicKey) readKeyFromFile(receiverPublicKey, "public");

	//Generate RSA cipher for AES Secret key
	Cipher cipher = Cipher.getInstance("RSA");
	cipher.init(Cipher.ENCRYPT_MODE, pubKey);

	encryptedData =  new String(cipher.doFinal(aESKey.getBytes(CryptoOperations.CHARSET)), CryptoOperations.CHARSET);

	return encryptedData;
	//byte[] cipherData = cipher.wrap((Key) serverCookieResponse);
	//return cipherData;		
}
	
	
private static String rsaDecryptAES(String rSAencryptedAESKey, String ServerPrivateKeyFile) {
		
		byte[] decryptedData = null;
		
		try{
			
			PrivateKey serverPrivate = getServerPrivateKeyFromFile(ServerPrivateKeyFile);
			Cipher RSACipher = Cipher.getInstance("RSA");
			
			RSACipher.init(Cipher.DECRYPT_MODE, serverPrivate); 
			
			return new String(RSACipher.doFinal(rSAencryptedAESKey.getBytes(CryptoOperations.CHARSET)), CryptoOperations.CHARSET);
			
			//return (SecretKey) RSACipher.unwrap(rSAencryptedAESKey, "AES", Cipher.SECRET_KEY);
			//decryptedData = RSACipher.doFinal(data);  
            //System.out.println("Decrypted Data: " + new String(decryptedData)); 			
			
		}catch (Exception e){
			e.printStackTrace();
		}			
		return null;
	}
	

public PublicKey getServerPublicKeyFromFile(String ServerPublicKeyFile) {
	try {
		return (PublicKey) readKeyFromFile(ServerPublicKeyFile, "public");
	} catch (Exception e) {
		System.out.println("Unable to read server's public key");
		throw new RuntimeException(e);
	}
}

public static PrivateKey getServerPrivateKeyFromFile(String ServerPrivateKeyFile) {
	try {
		return (PrivateKey) readKeyFromFile(ServerPrivateKeyFile, "private");
	} catch (Exception e) {
		System.out.println("Unable to read server's private key");
		throw new RuntimeException(e);
	}
}

private static Object readKeyFromFile(String keyFile, String fileType) throws IOException {
	InputStream in = new FileInputStream(keyFile);
		  ObjectInputStream oin =
		    new ObjectInputStream(new BufferedInputStream(in));
		  try {
		    BigInteger m = (BigInteger) oin.readObject();
		    BigInteger e = (BigInteger) oin.readObject();
		    KeyFactory fact = KeyFactory.getInstance("RSA");
		    
		    if (fileType == "public"){
		    	RSAPublicKeySpec keySpec = new RSAPublicKeySpec(m, e);			    
		    	return fact.generatePublic(keySpec);
		    }
		    else if (fileType == "private"){
			    RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(m, e);			    
			    return fact.generatePrivate(keySpec);
			    }
		    else
		    	return null;
		  } catch (Exception e) {
		    throw new RuntimeException("Spurious serialisation error", e);
		  } finally {
		    oin.close();
		  }
}
	
}

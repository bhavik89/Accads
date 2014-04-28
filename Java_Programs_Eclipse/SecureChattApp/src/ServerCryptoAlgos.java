

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class ServerCryptoAlgos {

		static KeyGenerator keyGenerator;
		private final static IvParameterSpec ivParameters = new IvParameterSpec(new byte[] {3, 15, 59, 23, 78, 26, 64, 29, 45, 95, 10, 81, 14, 8, 28, 49});
		String cipherData = new String();
		static String decryptedData = new String();
		String DataToBeEncrypted = new String(); 
		
		public static SecretKey obtainSecretKey(){
			//Generating a secret key
			try{
				keyGenerator = KeyGenerator.getInstance("AES");
				keyGenerator.init(128);
			}
			catch(Exception exp)
			{
				System.out.println(" Exception by Keygenerator " +exp);
			}
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey;
		}
		
		//Creating Ciphers
		public static SealedObject dataEncryption(Object dataToEncrypt, Key keyUsed, String Algorithm) {
			
			SealedObject objEncrypted = null;
			
			try {
				Cipher cipher = Cipher.getInstance(Algorithm);
				
				if(Algorithm.equals("AES"))
				{
					cipher.init(Cipher.ENCRYPT_MODE,keyUsed);
				}
				else if(Algorithm.equals("RSA/ECB/PKCS1Padding")){
					cipher.init(Cipher.ENCRYPT_MODE,keyUsed);
				} 
				
				//Encryption process 
				objEncrypted = new SealedObject((Serializable) dataToEncrypt, cipher);
			}
			catch (NoSuchAlgorithmException e)
			{
				 e.printStackTrace();
			}
			catch (NoSuchPaddingException e)
			{
				e.printStackTrace();
			}
			catch (InvalidKeyException e)
			{
				e.printStackTrace();
			}
			catch (IllegalBlockSizeException e)
			{
				e.printStackTrace();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return objEncrypted;
		}
		
		public static Object decryptData(SealedObject dataToDecrypt, Key key, String Algorithm) {
			
			byte[] decryptedText = new byte[200];
			Object objDecrypted = null;
			
			try{	
				
				Cipher cipher = Cipher.getInstance(Algorithm);
				
				if(Algorithm.equals("AES")){
					cipher.init(Cipher.DECRYPT_MODE,key);
				}
				else if(Algorithm.equals("RSA/ECB/PKCS1Padding")){
					cipher.init(Cipher.DECRYPT_MODE,key);
				} 
				
				objDecrypted = dataToDecrypt.getObject(cipher);
		}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
			catch (NoSuchPaddingException e)
			{
				e.printStackTrace();
			}
			catch (InvalidKeyException e)
			{
				e.printStackTrace();
			}
			catch (BadPaddingException e)
			{
				e.printStackTrace();
			}
			catch (IllegalBlockSizeException e)
			{
				e.printStackTrace();
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return objDecrypted;
		}		
	}


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.management.RuntimeErrorException;


public class fcrypt{
	
	//AES Key Size
	private static int AESKeySize = 128;
	
	//AES Secret key field
	private static SecretKey AESKey;
	
	//AES Cipher field
	private static Cipher AESCipher;
	
	//RSA Cipher field
	private static Cipher RSACipher;
	
	//Field for saving given file type
	private static String fileExtensionText;
	
	//Main method to parse the command line arguments and call required method
	public static void main(String[] args) 
			throws InvalidKeyException, 
				   NoSuchAlgorithmException, 
				   NoSuchPaddingException, 
				   IllegalBlockSizeException, 
				   BadPaddingException, 
				   InvalidKeySpecException, 
				   SignatureException, 
				   IOException, 
				   InvalidAlgorithmParameterException {
		
		//Parse the command line arguments and validate them
		if (args.length < 4){
			System.out.println("Invalid arguments!!");
			System.out.println("Usage for Encryption: java fcrypt -e destination_public_key_filename sender_private_key_filename input_plaintext_file output_ciphertext_file");
			System.out.println("Usage for Decryption: java fcrypt -d destination_private_key_filename sender_public_key_filename input_ciphertext_file output_plaintext_file");
			return;
		}
		
		//If argument asks for encryption of file, call EncryptFile Method 
		else if(args[0].equalsIgnoreCase("-e"))
			encryptFile(args[1], args[2], args[3], args[4]);
		
		//If argument asks for decryption of file, call DecryptFile Method
		else if(args[0].equalsIgnoreCase("-d"))
			decryptFile(args[1], args[2], args[3], args[4]);
		
		//If invalid arguments, print error and return
		else{
			System.out.println("Invalid arguments!!");
			System.out.println("Usage for Encryption: java fcrypt -e destination_public_key_filename sender_private_key_filename input_plaintext_file output_ciphertext_file");
			System.out.println("Usage for Decryption: java fcrypt -d destination_private_key_filename sender_public_key_filename input_ciphertext_file output_plaintext_file");
			return;
		}
	}
	
	/**
	 * EncryptFile function, takes the key files names, proceses them and encrypts plaintext file given   
	 * */
	public static void encryptFile(String receiverPublicKey,String senderPrivateKeyFile, String inputFile, String outputFile) 
			throws 	NoSuchAlgorithmException, 
					NoSuchPaddingException, 
					InvalidKeyException, 
					IllegalBlockSizeException, 
					BadPaddingException, 
					IOException, 
					InvalidKeySpecException, 
					SignatureException{		
		
		//Get the file extension of the file given
		String fileExtension = null;
		int dotLastIndex = inputFile.lastIndexOf('.');
		if (dotLastIndex >= 0) {
		    fileExtension = "." + inputFile.substring(dotLastIndex + 1);
		}
				
		//FileOutputStream fileOut = new FileOutputStream("extension.txt");
		//fileOut.write(FileExtension.getBytes());
		
		//Generate AES key object for AES Encryption
		KeyGenerator genAESKey = KeyGenerator.getInstance("AES");
		
		//Generate AES key size of key size specified above (here it is 128 bits)
		genAESKey.init(AESKeySize);
		
		//Generate AES key
		AESKey = genAESKey.generateKey();
		System.out.println("Generated AES Key...");
		
		
		//Generate AES Cipher with CBC mode
		AESCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AESCipher.init(Cipher.ENCRYPT_MODE, AESKey);		
		byte[] iv = AESCipher.getIV();
		
		//Get the plaintext in byte array
		byte[] plainText = getFileDataBytes(inputFile);
		
		//encrypt plaintext data with AES encryption
		byte[] encryptedData = AESCipher.doFinal(plainText);
		
		//Encrypt the file extension using AES
		byte[] fileExtensionByte = AESCipher.doFinal(fileExtension.getBytes("UTF-8"));	
		System.out.println("AES Cipher generated...");
		
		//Encrypt AES Secret key with destination's PublicKey
		byte[] RSAencryptedAESKey = rsaEncrypt(AESKey, receiverPublicKey);
		
		//Sign the plain text data with sender's private key
		byte[] RSASignature = signDigital(senderPrivateKeyFile, inputFile);
		System.out.println("File contents Signed...");
		
		//Write the encrypted data to file
		writeEncryptedToFile(RSASignature,RSAencryptedAESKey, encryptedData, iv, fileExtensionByte, outputFile);
			
	}	
	
	/**
	 *DncryptFile function, takes the key files names, proceses them and decrypts the cipher file to plaintext and verifies signature 
	 **/
	private static void decryptFile(String destPrivKeyFile, String senderPubKeyFile,
			String encryptedFile, String outPlainTextFile) 
					throws IOException, 
						   NoSuchAlgorithmException, 
						   InvalidKeyException, 
						   SignatureException, 
						   NoSuchPaddingException, 
						   InvalidAlgorithmParameterException, 
						   IllegalBlockSizeException, 
						   BadPaddingException {
	
		byte[] encryptedSecretKey, encryptedTxt, iv, signaturebyt, fileExtensionByte;
				
		//Read the cipher file and extract signature bytes, AESSecretKey bytes, Encryptedtext bytes, IV bytes and fileExtension bytes
		DataInputStream din = new DataInputStream(new FileInputStream(encryptedFile));
		signaturebyt = new byte [din.readInt()];
		encryptedSecretKey = new byte[din.readInt()];
		encryptedTxt = new byte[din.readInt()];
		iv = new byte[din.readInt()];
		din.read(signaturebyt);
		din.read(encryptedSecretKey);
		din.read(encryptedTxt);
		din.read(iv);
		fileExtensionByte = new byte[din.read()];
		din.read(fileExtensionByte);
		din.close();		
		
		//Decrypt the cipher file and write the plain text to the file
		doDecryption(destPrivKeyFile, senderPubKeyFile, encryptedSecretKey, encryptedTxt, iv, fileExtensionByte ,outPlainTextFile);	
			System.out.println("File Decrypted Successfully...");
		
		//Verify the Signature bytes received with signature from decrypted file
		if (verifySignature(senderPubKeyFile, signaturebyt, outPlainTextFile))
			System.out.println("Signature Verfied...");
		else
			System.out.println("Invalid Signature...Original file contents modified!");
	}

	//Decrypt the cipher file and write decrypted output to file
	private static void doDecryption(String destPrivKeyFile,
									 String senderPubKeyFile, 
									 byte[] encryptedSecretKey,
									 byte[] encryptedTxt, 
									 byte[] iv, 
									 byte[] fileExtension, 
									 String outPlainTextFile) 
					throws IOException, 
						   NoSuchAlgorithmException, 
						   NoSuchPaddingException, 
						   InvalidKeyException, 
						   InvalidAlgorithmParameterException, 
						   IllegalBlockSizeException, 
						   BadPaddingException {
		
		//Get the destination Private Key
		PrivateKey destPrivateKey = (PrivateKey) readKeyFromFile(destPrivKeyFile, "private");
		
		//Generate RSA objects for decrypting the AES Secret key encrypted with destination public key
		RSACipher = Cipher.getInstance("RSA");
		RSACipher.init(Cipher.UNWRAP_MODE, destPrivateKey);
		
		//Obtain AES Secret key 
		AESKey = (SecretKey) RSACipher.unwrap(encryptedSecretKey, "AES", Cipher.SECRET_KEY);
		
		//AES Cipher object to decrypt the plain text encrypted with AES secret key
		AESCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AESCipher.init(Cipher.DECRYPT_MODE, AESKey, new IvParameterSpec(iv));
		
		//Decrypt plaintext bytes
		byte[] decryptedText = AESCipher.doFinal(encryptedTxt);
		
		//Decrypt fileExtension bytes bytes
		byte[] fileExtensionByte = AESCipher.doFinal(fileExtension);
		
		//Write decrypted contents to file 
		generateDecryptedFile(outPlainTextFile, decryptedText, fileExtensionByte);
	}
	
	//Write the plaintext bytes to the Decrypted file
	private static void generateDecryptedFile(String outPlainTextFile, 
												byte[] decryptedText, 
												byte[] fileExtensionByte) 
												
												throws IOException {		
		
		//Get file extension
		fileExtensionText = new String(fileExtensionByte, "UTF-8");
		
		//generate the output file name
		String decryptedFile = outPlainTextFile + fileExtensionText;
		
		//write the data to output file
		DataOutputStream dataOut = new DataOutputStream(new FileOutputStream(decryptedFile));			
		dataOut.write(decryptedText);
		dataOut.close();
		
		System.out.println("Output decrypted to file: " + decryptedFile);
	}

	//Sign the plaintext file contents with sender's private key  
	private static byte[] signDigital(String senderPrivateKeyFile, String inputFile) 
			throws 	NoSuchAlgorithmException, 
					IOException, 
					InvalidKeySpecException, 
					InvalidKeyException, 
					SignatureException {		
		
		//Sign the file contents using SHA256 for RSA
		Signature sign  = Signature.getInstance("SHA256WithRSA");		
		
		//get sender's private key
		PrivateKey senderPrivateKey = ((PrivateKey) readKeyFromFile(senderPrivateKeyFile, "private"));
		
		//Sign the contents with obtained private key
		sign.initSign(senderPrivateKey);
		
		FileInputStream fin = new FileInputStream(inputFile);
		byte[] databytes = new byte[fin.available()];
				
		for(int len = 0; len <= databytes.length; len++)
		{
			fin.read(databytes);
			sign.update(databytes);
		}
		return sign.sign();	
	}
		
	//Verify the received Signature bytes with signature from plaintext files 	
	private static boolean verifySignature(String senderPubKeyFile,
											byte[] signaturebyt, 
											String outPlainTextFile) 
													throws NoSuchAlgorithmException, 
														   IOException, 
														   InvalidKeyException, 
														   SignatureException {
		
		//Get the output file name
		String decryptedFile = outPlainTextFile + fileExtensionText; 
		
		//Initialize Signature for the file contents using SHA256 for RSA
		Signature sign = Signature.getInstance("SHA256WithRSA");
		
		//get the destination's public key
		PublicKey senderPubKey = (PublicKey) readKeyFromFile(senderPubKeyFile, "public");
		
		//Verify the signature
		sign.initVerify(senderPubKey);
		
		FileInputStream fin = new FileInputStream(decryptedFile);
		
		//BufferedInputStream bufin = new BufferedInputStream(fin);
		//byte[] databytes = new byte[fin.available()];
		byte[] databytes = getFileDataBytes(decryptedFile);
		//System.out.println(databytes.toString());
		int len;
		for(len = 0; len <= databytes.length; len++)
		{
			fin.read(databytes);
			sign.update(databytes);
		}
		
		return sign.verify(signaturebyt);		
	}

	//Write the encrypted data bytes to file
	private static void writeEncryptedToFile(byte[] RSASignature,
											 byte[] RSAencryptedAESKey, 
											 byte[] encryptedData, 
											 byte[] iv, 
											 byte[] fileExtensionByte, 
											 String outputFile) 
													 throws IOException {
		
		//Generate text file for writing encrypted contents
		DataOutputStream outData = new DataOutputStream(new FileOutputStream(outputFile + ".txt"));
		
		//Write the encrypted contents to file
		outData.writeInt(RSASignature.length);
		outData.writeInt(RSAencryptedAESKey.length);
		outData.writeInt(encryptedData.length);
		outData.writeInt(iv.length);
		outData.write(RSASignature);
		outData.write(RSAencryptedAESKey);
		outData.write(encryptedData);
		outData.write(iv);
		outData.write(fileExtensionByte.length);
		outData.write(fileExtensionByte);
		outData.close();
		
		System.out.println("Cipher Generated...");
		System.out.println("Cipher File: " + outputFile + ".txt");
	}
	
	//Encrypt the Secret AES key with receiver's public key
	private static byte[] rsaEncrypt(SecretKey AESKey, String receiverPublicKey) 
								throws NoSuchAlgorithmException, 
									   NoSuchPaddingException, 
								       InvalidKeyException, 
								       IllegalBlockSizeException, 
								       IOException {
		  
		  //get receiver's public key
		  PublicKey pubKey = (PublicKey) readKeyFromFile(receiverPublicKey, "public");
		  
		  //Generate RSA cipher for AES Secret key
		  Cipher cipher = Cipher.getInstance("RSA");
		  cipher.init(Cipher.WRAP_MODE, pubKey);
		  byte[] cipherData = cipher.wrap(AESKey);
		  return cipherData;		
	}

	//Gets the required key from the given keyFile, return key Object
	private static Object readKeyFromFile(String keyFile, String fileType) 
											throws IOException {
		
		//Input stream for the given key file 
		InputStream fileStream = new FileInputStream(keyFile);
		
		ObjectInputStream input =   new ObjectInputStream(new BufferedInputStream(fileStream));
		
		try {
			    BigInteger mod = (BigInteger) input.readObject();
			    BigInteger exp = (BigInteger) input.readObject();
			    KeyFactory fact = KeyFactory.getInstance("RSA");
			    
			    //Get public key
			    if (fileType == "public"){
			    	RSAPublicKeySpec keySpec = new RSAPublicKeySpec(mod, exp);			    
			    	return fact.generatePublic(keySpec);
			    }
			    //Get private key
			    else if (fileType == "private"){
				    RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(mod, exp);			    
				    return fact.generatePrivate(keySpec);
				    }
			    else
			    	return null;
			  } catch (Exception e) {
			    throw new RuntimeException("Runtime Error", e);
			  } finally {
				  input.close();
			  }
		}

	//read the bytes from the file given 
	private static byte[] getFileDataBytes(String fileName) 
			throws IOException {
		
		FileInputStream fin = new FileInputStream(fileName);
		byte[] databytes = new byte[fin.available()];
		fin.read(databytes);	
		return databytes;
	}
		

}

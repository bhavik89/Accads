package generic;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;


import java.math.BigInteger;

import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidParameterSpecException;

import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

import java.util.ArrayList;
import java.util.Random;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/* Class to handle all the cryptographic functions
 * */
public class CryptoOperations {

	/*Constants used for Cipher algorithms*/
	private static final String DIFFIEHELLMANCIPHER = "DH";
	public static final String AESCIPHER = "AES";
	public static final String AESCIPHERMODE = "AES/CBC/PKCS5Padding";
	private static final String RSACIPHERMODE = "RSA/ECB/NOPadding";
	private static final String RSASHA1CIPHER = "SHA1withRSA";		
	public static final int AES_KEY_SIZE = 128;
	public static final int AES_KEY_BYTES = AES_KEY_SIZE / 8;	
	//private static final String SHA1 = "SHA1";
	private static final String SHA1HMAC = "HmacSHA1";
	public static final String ISO_CHARSET = "ISO-8859-1";
	//private static Random rand = new Random(System.currentTimeMillis());
	private static SecureRandom secRandom = new SecureRandom();
	
	private static final BigInteger DH_PUB_MOD = new BigInteger(			
			"1457740368604460328023304295251134382565893803800691147782887163550"
			+ "411518956403751908215843899117895923666775808701317133732951458193"
			+ "2642505059224605577749503822125522249892967436448937452513101522976"
			+ "0332878867848830923825588013751854034628938794222385701905933799034"
			+ "399193946413437339945994731134027989165577");	
	
	private static final BigInteger DH_PUB_BASE = new BigInteger(
			"14892445182998161261165285893666851488920058141299404258541365685915535"
			+ "776314823023733165312200587041432514150140535791778629875426745849450"
			+ "3317713993734440450735267119027838244612977460458198163139677681736691"
			+ "8187040402606564764302255604361694474087765110331656820211482190828665"
			+ "9757048964985036482105544396");
	
	private static final int DH_LEN = 1023;

	

	

	/*
	 * Generate SHA-256 Hash of the given string,
	 * Typically used to send Hash of the client's password
	 */
	public static String generateValidationHash(String password) {
		try {
			return String.format("%x",new BigInteger(hash(password+hash(password)).getBytes(CryptoOperations.ISO_CHARSET)));
	} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	/*
	 * Generate SHA-256 Hash
	 */
	public static String hash(String value) {
		try {
			MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

			return new String(sha256.digest(value.getBytes(CryptoOperations.ISO_CHARSET)),
									CryptoOperations.ISO_CHARSET);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	/* Sign the input using the private key of the sender for integrity */
	public static byte[] sign(PrivateKey privateKey, String input) throws Exception {
		Signature signature = Signature.getInstance(RSASHA1CIPHER);
		signature.initSign(privateKey);

		signature.update(input.getBytes());
		return signature.sign();
	}

	/*
	 * Verify the digital signature using the Public Key of the sender and
	 * compare both the message digests to verify
	 */
	public static boolean verify(PublicKey publicKey,
			String input, byte[] sigBytes) throws Exception {
		Signature signature = Signature.getInstance(RSASHA1CIPHER);
		
		signature.initVerify(publicKey);
		signature.update(input.getBytes());

		if (signature.verify(sigBytes)) {
			return true;
		} 
		return false;
	}
	
	/*
	 * Generate HMAC Signature for integrity check
	 */
	public static String getHMACSignature(SecretKey secret, String messageToSign) 
															throws HmacException {
		String[] hmacValues = new String[] { messageToSign, hmacGenerate(secret, messageToSign)};		
		return MessageMaker.assemblePacket(hmacValues);
	}
	
	/*
	 * Verifies the Integrity of message by verifying the HMAC Signature
	 */
	public static String hmacVerify(SecretKey secret, String messageToVerify) 
												throws HmacException {
		final ArrayList<String> signedValues = MessageMaker.disassemblePacket(messageToVerify);
		
		messageToVerify = signedValues.get(0);
		final String hmacSignature = signedValues.get(1);		
		String hmacToVerify;
		
		try {
			hmacToVerify = hmacGenerate(secret, messageToVerify);		
		} catch (Exception e) {
			throw new HmacException(e);
		}
		
		if (hmacToVerify.equals(hmacSignature)) {
			return messageToVerify;
		} else {
			throw new HmacException();
		}
	}

	/*
	 * Generates an HMAC Signature 
	 */
	public static String hmacGenerate(SecretKey secret, String messageForHMAC)
															throws HmacException {
		try {
			final Mac sha1HMAC = Mac.getInstance(SHA1HMAC);
			sha1HMAC.init(secret);
			return new String(sha1HMAC.doFinal(messageForHMAC.getBytes(CryptoOperations.ISO_CHARSET)),
												CryptoOperations.ISO_CHARSET);
		} catch (Exception e) {
			System.out.println("ERROR: While generating HMAC Signature");
			throw new HmacException(e);
		}
	}

	
	/*
	 * Generates 128 bits AES Secret Key
	 */
	public static SecretKey aesGenerateKey() throws KeyCreationException {
		try {
			final KeyGenerator kgen = KeyGenerator.getInstance(AESCIPHER);
			kgen.init(128);
			return kgen.generateKey();
		} catch (Exception e) {
			System.out.println("ERROR: Error while generating AES Key");
			throw new KeyCreationException(e);
		}
	}
	
	//Exceptions for Key generations and HMACs
		@SuppressWarnings("serial")
		public static class KeyCreationException extends Exception {
			public KeyCreationException(Exception e) {
				super(e);		}

			public KeyCreationException(String msg) {
				super(msg);
			}
		}

		@SuppressWarnings("serial")
		public static class HmacException extends Exception {
			public HmacException() {
				super("HMAC Signature not valid");
				}

			public HmacException(Exception e) {
				super(e);
			}
		}

	/*
	 * Get the key from given Key Bytes
	 */
	public static SecretKeySpec createAESKey(byte[] key)
			throws KeyCreationException {
		return new SecretKeySpec(key, AESCIPHER);
	}
	
	/* Generate AES Secret Key */	
	public static SecretKey AESKeyGen() throws NoSuchAlgorithmException{
		
		//Generate AES key object for AES Encryption
		KeyGenerator genAESKey = KeyGenerator.getInstance(AESCIPHER);						
		//Generate AES key size of key size specified above (here it is 256 bits)
		genAESKey.init(128);							
		//Generate AES key
		SecretKey AESKey = genAESKey.generateKey();
		return AESKey;
	}
	
	/* Encrypt message with AES in CBC mode*/
	public static String AESEncrypt(SecretKey AESKey, String data){
		
		try {			
			//Random rand = new Random(System.currentTimeMillis());
			final byte[] initVectorBytes = new byte[16];
			secRandom.nextBytes(initVectorBytes);		
			final IvParameterSpec initVector = new IvParameterSpec(initVectorBytes);		
			
			Cipher AESCipher = Cipher.getInstance(AESCIPHERMODE);
			AESCipher.init(Cipher.ENCRYPT_MODE, AESKey,initVector);
			//System.out.println("Enc IV: " + new String(initVector.getIV()));
			
			byte[] EncryptedBytes = AESCipher.doFinal(data.getBytes(ISO_CHARSET));
			//System.out.println("EncBytes enctypted: "  + EncryptedBytes.length);
			//new String(Base64.encode(EncryptedBytes));
			return (new String(initVectorBytes, ISO_CHARSET) + new String(EncryptedBytes, ISO_CHARSET)); 
			
		} catch (Exception e) {
			System.out.println("ERROR While Encrypting AES Cipher");
			e.printStackTrace();
		} 
		return null;		
		
	}
	
	/*Decrypt AES Encrypted Message*/
	public static String AESDecrypt(SecretKey key, String encryptedData) {
		
		try{
		final IvParameterSpec initVector = new IvParameterSpec(encryptedData.substring(0, 16).getBytes(ISO_CHARSET));					
		final Cipher AESCipher = Cipher.getInstance(AESCIPHERMODE);
		
		AESCipher.init(Cipher.DECRYPT_MODE, key, initVector);
		//System.out.println("Dec IV: " + new String(initVector.getIV()));
		
		byte[] encBytes = encryptedData.substring(16).getBytes(ISO_CHARSET);
				//base64Decoder(encryptedData.substring(16));
		
		//System.out.println("DEC Byte len: " + encBytes.length);
		
		return new String (AESCipher.doFinal(encBytes), ISO_CHARSET);
		}catch(Exception e){
			System.out.println("ERRRO: while decrypting AES!");
			e.printStackTrace();
		}
		return null;
		
	}
	
	/*Wrap the AES Secret key with RSA Cipher*/
	public static String RSAWrap(SecretKey data, String receiverPublicKey) 
			throws NoSuchAlgorithmException, 
				   NoSuchPaddingException, 
			       InvalidKeyException, 
			       IllegalBlockSizeException, 
			       IOException, BadPaddingException {

				
		//get receiver's public key
		PublicKey pubKey = (PublicKey) readKeyFromFile(receiverPublicKey, "public");

		//Generate RSA cipher for AES Secret key
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.WRAP_MODE, pubKey);

		return new String(cipher.wrap(data), ISO_CHARSET);
		
	}	
	
	/*Unwrap AES Secret Key*/
	public static SecretKey RSAUnWrap(String data, String ServerPrivateKeyFile) throws Exception {
				
		try{			
			PrivateKey serverPrivate = getServerPrivateKeyFromFile(ServerPrivateKeyFile);
			final Cipher RSACipher = Cipher.getInstance("RSA");
			
			RSACipher.init(Cipher.UNWRAP_MODE, serverPrivate); 
			return  (SecretKey) (RSACipher.unwrap(data.getBytes(ISO_CHARSET), "AES", Cipher.SECRET_KEY));
						
		}catch (Exception e){
			throw new Exception(e);
		}
		
	}
	
	/* Encrypt message with RSA Public Key*/
	public static String RSAEncrypt(String data, String receiverPublicKey) 
			throws NoSuchAlgorithmException, 
				   NoSuchPaddingException, 
			       InvalidKeyException, 
			       IllegalBlockSizeException, 
			       IOException, BadPaddingException {

				
		//get receiver's public key
		PublicKey pubKey = (PublicKey) readKeyFromFile(receiverPublicKey, "public");

		//Generate RSA cipher for AES Secret key
		final Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return new String(cipher.doFinal(data.getBytes(ISO_CHARSET)), ISO_CHARSET);
	}

	/*Decrypt message with RSA private Key*/
	public static String RSADecrypt(String data, String ServerPrivateKeyFile) throws Exception {
				
		try{			
			PrivateKey serverPrivate = getServerPrivateKeyFromFile(ServerPrivateKeyFile);
			final Cipher RSACipher = Cipher.getInstance("RSA");
			
			RSACipher.init(Cipher.DECRYPT_MODE, serverPrivate); 
			return  new String(RSACipher.doFinal(data.getBytes(ISO_CHARSET)), ISO_CHARSET);
						
		}catch (Exception e){
			throw new Exception(e);
		}
		
	}
	
	/*Gets public key from file*/
	public static PublicKey getServerPublicKeyFromFile(String ServerPublicKeyFile) {
		try {
			return (PublicKey) readKeyFromFile(ServerPublicKeyFile, "public");
		} catch (Exception e) {
			System.out.println("Unable to read server's public key");
			throw new RuntimeException(e);
		}
	}
	
	/*Gets Private key from file*/
	public static PrivateKey getServerPrivateKeyFromFile(String ServerPrivateKeyFile) {
		try {
			return (PrivateKey) readKeyFromFile(ServerPrivateKeyFile, "private");
		} catch (Exception e) {
			System.out.println("Unable to read server's private key");
			throw new RuntimeException(e);
		}
	}
	
	/* Reads the Public/Private Key from file*/
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
	
	/*Digitallu Signs message with private Key*/
	public static byte[] signDigital(String PrivateKeyFile, String dataToSign) throws 
																						NoSuchAlgorithmException, 
																						IOException, 
																						InvalidKeyException, 
																						SignatureException {
		
		Signature sign  = Signature.getInstance("SHA1WithRSA");		
		PrivateKey senderPrivateKey = ((PrivateKey) readKeyFromFile(PrivateKeyFile, "private"));
		sign.initSign(senderPrivateKey);
		InputStream is = new ByteArrayInputStream(String.valueOf(dataToSign.hashCode()).getBytes(ISO_CHARSET));
		BufferedInputStream bufin = new BufferedInputStream(is);
		byte[] buffer = new byte[1024];
		int len;
		
		while((len = bufin.read(buffer)) >= 0)
		{
		sign.update(buffer, 0, len);
		}
		bufin.close();	
		
		return sign.sign();
	}
	
	/* Verify the Digital signature with public key*/
	public static boolean verifySignature(String publicKeyFile, String signedData, byte[] signBytes){
		
		try{
		Signature sign = Signature.getInstance("SHA1WithRSA");
		PublicKey PubKey = (PublicKey) readKeyFromFile(publicKeyFile, "public");
		sign.initVerify(PubKey);
		
		//hash = plainText.hashCode();		
		//System.out.println("HashCode: " +(String.valueOf(signedData.hashCode())));
		InputStream is = new ByteArrayInputStream(String.valueOf(signedData.hashCode()).getBytes(ISO_CHARSET));
		BufferedInputStream bufin = new BufferedInputStream(is);
		byte[] buffer = new byte[1024];
		int len;
		
		while (bufin.available() != 0) {
		    len = bufin.read(buffer);
		    sign.update(buffer, 0, len);
		};

		bufin.close();
		
		return sign.verify(signBytes);
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/* Generate the Public BigIntegers for DH Key exchange */
	public static void genDhParams() {
		try {
			
			AlgorithmParameterGenerator genDHParameters = AlgorithmParameterGenerator.getInstance(DIFFIEHELLMANCIPHER);
			genDHParameters.init(1024);

			
			AlgorithmParameters DHValues = genDHParameters.generateParameters();
			DHParameterSpec DHSpecs = (DHParameterSpec) DHValues.getParameterSpec(DHParameterSpec.class);

			//Get the DH Parameters
			System.out.println(DHSpecs.getP());
			System.out.println(DHSpecs.getG());
			System.out.println(DHSpecs.getL());
			
		} catch (NoSuchAlgorithmException e) {
		} catch (InvalidParameterSpecException e) {
		}
	}
	
	/*
	 * Generate DH keypair for key exchange
	 */
	public static KeyPair genDHKeyPair() throws KeyCreationException {
		try {
			KeyPairGenerator dhKeyGen = KeyPairGenerator.getInstance(DIFFIEHELLMANCIPHER);
			DHParameterSpec dhKeySpec = new DHParameterSpec(DH_PUB_MOD, DH_PUB_BASE, DH_LEN);
			dhKeyGen.initialize(dhKeySpec);
			return dhKeyGen.generateKeyPair();
		} catch (Exception e) {
			throw new KeyCreationException(e);
		}
	}
	
	/*
	 * Given DH private and public DH Parameters, generate Secret Key (g^ab MOD p)
	 */
	public static SecretKey genDHSecretKey(PrivateKey dhPriv, byte[] dhPub) 
								throws KeyCreationException {
		try {
			final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(dhPub);
			final KeyFactory factory = KeyFactory.getInstance(DIFFIEHELLMANCIPHER);
			final PublicKey publicKey = factory.generatePublic(x509KeySpec);

			KeyAgreement keyAgremnt = KeyAgreement.getInstance(DIFFIEHELLMANCIPHER);
			keyAgremnt.init(dhPriv);
			keyAgremnt.doPhase(publicKey, true);

			return keyAgremnt.generateSecret(AESCIPHER);
		} catch (Exception e) {
			throw new KeyCreationException(e);
		}
	}

}
	

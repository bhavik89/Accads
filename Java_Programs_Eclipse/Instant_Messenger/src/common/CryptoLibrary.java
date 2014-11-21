package src.common;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* This library handles the encryption, decryption,
 * digital signatures, hash, and diffie hellman key
 * generation, password hash generation.
 */
public class CryptoLibrary {

	public static final String CHARSET = "ISO-8859-1";
	public static final String AES_CIPHER = "AES";
	public static final String AES_MODE = AES_CIPHER + "/CBC/PKCS5Padding";
	public static final int AES_KEY_SIZE_BITS = 256;
	public static final int AES_KEY_SIZE_BYTES = AES_KEY_SIZE_BITS / 8;
	private static final int IV_LENGTH = 16;
	private static final String RSA_CIPHER = "RSA";
	private static final String RSA_SHA_CIPHER = "SHA1withRSA";
	private static final String SHA1 = "SHA1";
	private static final String HMAC_MODE = "HmacSHA1";
	private static final String DIFFIE_HELLMAN = "DH";

	private static final BigInteger DIFFIE_HELLMAN_P = new BigInteger(
			"1308250237222530600227310986010237098930303416639424533858940"
					+ "8878958885650819728833722280756986251190068995534082342223861"
					+ "4647846829399058069171051995424719373196527286659026670391754"
					+ "6545907203687350999492766472542316266368269022760793744599778"
					+ "3104872360364905685795242181182362048214420117731674996866055"
					+ "6611");
	private static final BigInteger DIFFIE_HELLMAN_G = new BigInteger(
			"1147770508259560289482546219832811976345915530740486161183719"
					+ "1285581786371925467264272617255724056028006943899656647898897"
					+ "0560054569265468614344543518442619199332549094160584717875636"
					+ "8244873736681203657198634474598015786422753282033630980469049"
					+ "7017876540910881817106573582293672057794409053683905673703355"
					+ "6594");
	private static final int DIFFIE_HELLMAN_L = 1023;

	private static Random rand = new Random(System.currentTimeMillis());

	/* this method is used to generate above parameters */
	public static void genDhParams() {
		try {
			// Create the parameter generator for a 1024-bit DH key pair
			AlgorithmParameterGenerator paramGen = AlgorithmParameterGenerator
					.getInstance("DH");
			paramGen.init(1024);

			// Generate the parameters
			AlgorithmParameters params = paramGen.generateParameters();
			DHParameterSpec dhSpec = (DHParameterSpec) params
					.getParameterSpec(DHParameterSpec.class);

			// Return the three values in a string
			System.out.println(dhSpec.getP());
			System.out.println(dhSpec.getG());
			System.out.println(dhSpec.getL());
		} catch (NoSuchAlgorithmException e) {
		} catch (InvalidParameterSpecException e) {
		}
	}

	/*
	 * Generate the validation hash for a given password.
	 * @param password The password to generate a validation hash for.
	 * @return The validation hash for the given password.
	 */
	public static String generateValidationHash(String password) {
		try {
			return String.format("%x",
					new BigInteger(hash(password+hash(password))
							.getBytes(CryptoLibrary.CHARSET)));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}
	}

	/*
	 * Hash the given value using the SHA-1 hash function.
	 * @param value The value to hash
	 * @return The hashed version of value.
	 */
	public static String hash(String value) {
		try {
			MessageDigest sha1 = MessageDigest.getInstance(SHA1);

			return new String(sha1.digest(value.getBytes(CryptoLibrary.CHARSET)),
					CryptoLibrary.CHARSET);
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}
	
	/*
	 * Create an HMAC signature for the given message with the given key.
	 * @param key The key to use in the HMAC.
	 * @param message The message to create an HMAC for.
	 * @return The message appended with the HMAC using 
	 * <code>HeaderHandler</code>.
	 * @throws HmacException Thrown for all errors.
	 */
	public static String hmacCreate(SecretKey key, String message) 
		throws HmacException 
	{
		String[] params = new String[] { message, hmacGenerate(key, message) };
		
		return HeaderHandler.pack(params);
	}
	
	/*
	 * Verify that the given HMAC is correct for the given message using
	 * the given key.
	 * @param key The key to verify the HMAC with.
	 * @param message The message to verify the HMAC for.  This should be
	 * a string created by <code>HeaderHandler</code> that contains
	 * the message as the first parameter and the HMAC as the second.
	 * @return The contents of the message without the HMAC appended.
	 * @throws HmacException Thrown for all errors or if the HMAC is invalid.
	 */
	public static String hmacVerify(SecretKey key, String message) 
	throws HmacException 
	{
		final ArrayList<String> params = HeaderHandler.unpack(message);
		
		message = params.get(0);
		final String hmac = params.get(1);
		
		String generatedHmac;
		try {
			generatedHmac = hmacGenerate(key, message);
		
		} catch (Exception e) {
			throw new HmacException(e);
		}
		
		if (generatedHmac.equals(hmac)) {
			return message;
		} else {
			throw new HmacException();
		}
	}

	/*
	 * Generate an HMAC for the given message.
	 * @param key The key to generate the HMAC.
	 * @param msg The message to generate the HMAC for.
	 * @return The HMAC.
	 * @throws HmacException Thrown for all errors.
	 */
	public static String hmacGenerate(SecretKey key, String msg)
			throws HmacException {
		try {
			final Mac mac = Mac.getInstance(HMAC_MODE);

			mac.init(key);
			return new String(mac.doFinal(msg.getBytes(CryptoLibrary.CHARSET)),
					CryptoLibrary.CHARSET);

		} catch (Exception e) {
			throw new HmacException(e);
		}
	}

	/*
	 * Encrypt a message with RSA encryption.
	 * @param key The public key used to encrypt the message.
	 * @param msg The message to encrypt.
	 * @return The given message encrypted with the given key.
	 * @throws EncryptionException Thrown for all errors.
	 */
	public static String rsaEncrypt(PublicKey key, String msg)
			throws EncryptionException {
		try {
			final Cipher cipher = Cipher.getInstance(RSA_CIPHER);

			cipher.init(Cipher.ENCRYPT_MODE, key);

			return new String(cipher.doFinal(msg.getBytes(CryptoLibrary.CHARSET)), CryptoLibrary.CHARSET);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}

	/*
	 * Decrypt a message with the RSA algorithm.
	 * @param key The private key to decrypt the message with.
	 * @param msg The message to decrypt.
	 * @return The decrypted version of the given message using the given key.
	 * @throws DecryptionException Thrown for all errors.
	 */
	public static String rsaDecrypt(PrivateKey key, String msg)
			throws DecryptionException {
		try {
			final Cipher cipher = Cipher.getInstance(RSA_CIPHER);

			cipher.init(Cipher.DECRYPT_MODE, key);

			return new String(cipher.doFinal(msg.getBytes(CryptoLibrary.CHARSET)), CryptoLibrary.CHARSET);
		} catch (Exception e) {
			throw new DecryptionException(e);
		}
	}
	
	/* Sign the input using the private key of the sender for integrity */
	public static byte[] sign(PrivateKey privateKey, String input) throws Exception {
		Signature signature = Signature.getInstance(RSA_SHA_CIPHER);
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
		Signature signature = Signature.getInstance(RSA_SHA_CIPHER);
		
		signature.initVerify(publicKey);
		signature.update(input.getBytes());

		if (signature.verify(sigBytes)) {
			return true;
		} 
		return false;
	}

	/*
	 * Generate a random byte array to use as an initialization vector.
	 * @return The initialization vector in byte array format.
	 */
	private static byte[] generateInitVector() {
		final byte[] initVector = new byte[IV_LENGTH];

		rand.nextBytes(initVector);

		return initVector;
	}

	/*
	 * Generate an AES key.
	 * @return The newly generated AES key.
	 * @throws KeyCreationException Thrown for all errors.
	 */
	public static SecretKeySpec aesGenerateKey() throws KeyCreationException {
		try {
			final KeyGenerator kgen = KeyGenerator.getInstance(AES_CIPHER);

			kgen.init(AES_KEY_SIZE_BITS);
			return aesCreateKey(kgen.generateKey().getEncoded());
		} catch (Exception e) {
			throw new KeyCreationException(e);
		}
	}

	/*
	 * Create an AES key from the given byte array.
	 * @param key The byte array representing the key.
	 * @return The key created from the given byte array.
	 * @throws KeyCreationException Thrown for all errors.
	 */
	public static SecretKeySpec aesCreateKey(byte[] key)
			throws KeyCreationException {
		if (key.length != AES_KEY_SIZE_BYTES) {
			throw new KeyCreationException("Invalid key length");
		}

		return new SecretKeySpec(key, AES_CIPHER);
	}

	/*
	 * Encrypt the given message using the AES algorithm.
	 * @param key The secret key.
	 * @param msg The message to encrypt.
	 * @return The 256 bit initialization variable appended to the encrypted
	 * message.
	 * @throws EncryptionException Thrown for all errors.
	 */
	public static String aesEncrypt(SecretKey key, String msg)
			throws EncryptionException {
		try {
			final byte[] initVectorBytes = generateInitVector();
			final IvParameterSpec initVector = new IvParameterSpec(
					initVectorBytes);
			final Cipher cipher = Cipher.getInstance(AES_MODE);

			cipher.init(Cipher.ENCRYPT_MODE, key, initVector);

			return new String(initVectorBytes, CryptoLibrary.CHARSET)
					+ new String(cipher.doFinal(msg
							.getBytes(CryptoLibrary.CHARSET)),
							CryptoLibrary.CHARSET);
		} catch (Exception e) {
			throw new EncryptionException(e);
		}
	}

	/*
	 * Decrypt the given message using the AES algorithm.
	 * @param key The secret key.
	 * @param msg The 256 bit initialization variable appended to the message.
	 * @return The decrypted message.
	 * @throws DecryptionException Thrown for all errors.
	 */
	public static String aesDecrypt(SecretKey key, String msg)
			throws DecryptionException {
		try {
			final IvParameterSpec initVector = new IvParameterSpec(msg
					.substring(0, IV_LENGTH).getBytes(CryptoLibrary.CHARSET));
			
			final Cipher cipher = Cipher.getInstance(AES_MODE);

			msg = msg.substring(IV_LENGTH);
			cipher.init(Cipher.DECRYPT_MODE, key, initVector);

			return new String(
					cipher.doFinal(msg.getBytes(CryptoLibrary.CHARSET)),
					CryptoLibrary.CHARSET);
		} catch (Exception e) {
			throw new DecryptionException(e);
		}
	}

	/*
	 * Generate a diffie-hellman key pair.
	 * @return The generated key pair.
	 * @throws KeyCreationException Thrown for all errors.
	 */
	public static KeyPair dhGenerateKeyPair() throws KeyCreationException {
		try {
			KeyPairGenerator keyGen = KeyPairGenerator
					.getInstance(DIFFIE_HELLMAN);
			DHParameterSpec dhSpec = new DHParameterSpec(DIFFIE_HELLMAN_P,
					DIFFIE_HELLMAN_G, DIFFIE_HELLMAN_L);
			keyGen.initialize(dhSpec);
			return keyGen.generateKeyPair();
		} catch (Exception e) {
			throw new KeyCreationException(e);
		}
	}

	/*
	 * Generate a secret key using diffie-hellman.
	 * @param privateKey The private key for the diffie-hellman algorithm.
	 * @param publicKeyBytes The byte array representing the public key.
	 * @return The secret key generated.
	 * @throws KeyCreationException Thrown for all errors.
	 */
	public static SecretKey dhGenerateSecretKey(PrivateKey privateKey,
			byte[] publicKeyBytes) throws KeyCreationException {
		try {
			final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(
					publicKeyBytes);
			final KeyFactory keyFact = KeyFactory.getInstance(DIFFIE_HELLMAN);
			final PublicKey publicKey = keyFact.generatePublic(x509KeySpec);

			KeyAgreement ka = KeyAgreement.getInstance(DIFFIE_HELLMAN);
			ka.init(privateKey);
			ka.doPhase(publicKey, true);

			return ka.generateSecret(AES_CIPHER);
		} catch (Exception e) {
			throw new KeyCreationException(e);
		}
	}

	/*
	 * Read a private key in from the file at the given location.
	 * @param filename The location of the private key
	 * @return The private key.
	 * @throws Exception Thrown for all errors.
	 */
	public static PrivateKey readPrivateKey(String filename) throws Exception {
		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);

		int len = dis.readInt();
		byte[] keyBytes = new byte[len];
		dis.readFully(keyBytes);
		dis.close();

		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(RSA_CIPHER);
		//System.out.println("RSA Private key loaded from " + f.getPath());
		return kf.generatePrivate(spec);
	}

	/*
	 * Read a public key in from the file at the given location.
	 * @param filename The location of the public key.
	 * @return The public key.
	 * @throws Exception Thrown for all errors.
	 */
	public static PublicKey readPublicKey(String filename) throws Exception {

		File f = new File(filename);
		FileInputStream fis = new FileInputStream(f);
		DataInputStream dis = new DataInputStream(fis);

		int len = dis.readInt();
		byte[] keyBytes = new byte[len];
		dis.readFully(keyBytes);
		dis.close();

		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance(RSA_CIPHER);
		//System.out.println("RSA Public key loaded from " + f.getPath());
		return kf.generatePublic(spec);
	}

	@SuppressWarnings("serial")
	public static class EncryptionException extends Exception {
		public EncryptionException(Exception e) {
			super(e);
		}
	}

	@SuppressWarnings("serial")
	public static class DecryptionException extends Exception {
		public DecryptionException(Exception e) {
			super(e);
		}
	}

	@SuppressWarnings("serial")
	public static class KeyCreationException extends Exception {
		public KeyCreationException(Exception e) {
			super(e);
		}

		public KeyCreationException(String msg) {
			super(msg);
		}
	}

	@SuppressWarnings("serial")
	public static class HmacException extends Exception {
		public HmacException() {
			super("Invalid HMAC");
		}

		public HmacException(Exception e) {
			super(e);
		}
	}

}
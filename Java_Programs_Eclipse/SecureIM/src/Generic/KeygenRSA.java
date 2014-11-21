package Generic;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;


public class KeygenRSA {
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		
		
		generateRSAKeys("server" , "RSA");
		//generateRSAKeys("receiver", "RSA");		
	}

	private static void generateRSAKeys(String node, String algorithm) 
			throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
		
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance(algorithm);
		
		keyGen.initialize(1024);	
		
		KeyPair RSAKeys = keyGen.generateKeyPair();
		Key privateKey = RSAKeys.getPrivate();
		Key publicKey = RSAKeys.getPublic();
		
		KeyFactory fact = KeyFactory.getInstance(algorithm);
		
		RSAPublicKeySpec pub = fact.getKeySpec(publicKey, RSAPublicKeySpec.class);
		RSAPrivateKeySpec priv = fact.getKeySpec(privateKey, RSAPrivateKeySpec.class);
		
		saveKeysToFile(node+"PublicKey.key", pub.getModulus(), pub.getPublicExponent());
		saveKeysToFile(node+"PrivateKey.key", priv.getModulus(), priv.getPrivateExponent());
		
	}

	private static void saveKeysToFile(String fileName, BigInteger modulus,
			BigInteger exponent) throws IOException {
		
		ObjectOutputStream oout = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(fileName)));
		try{
			oout.writeObject(modulus);
			oout.writeObject(exponent);
		}catch (Exception e){
			throw new IOException("Unexpected Error", e);
		}finally{
			oout.close();
		}
	}
}


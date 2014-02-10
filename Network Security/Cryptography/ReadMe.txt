Bhavik Gandhi, bhavik@ccs.neu.edu
NETWORK SECURITY - CS 6740
Problem Set - 2: Application of Cryptography

Steps to run program:

Code for this application is written in java.

1. First generate RSA keys using KeygenRSA.java 
	(This step is optional, the zipped folder already contains key files)
	
	- Compile KeygenRSA.java using "javac KeygenRSA.java" on commandLine
	
	- A KeygenRSA.class file would be generated
	
	- Execute "java KeygenRSA" to generate key files 
		This would generate for key files viz. sender_PublicKey.key, sender_PrivateKey.key,  destination_PrivateKey.key and destination_PublicKey.key

2. To encrypt/decrypt files, compile fcrypt.java by executing "javac fcrypt.java" on commandLine

	- To Encrypt a file execute:
		"java fcrypt -e destination_PublicKey.key sender_PrivateKey.key <name of file to be encrypted> <Encrypted filename>" on commandline
		
	- A text file would be generated with cipher text, the name of the filename would be same as name given for Encrypted filename

	- To Decrypt file, execute:
		"java fcrypt -d destination_PrivateKey.key sender_PublicKey.key <encrypted filename>.txt <Decrypted filename>"
		
	- A decrypted file would be generated having the plain text that was encrypted, filename would be same as name given for Decrypted filename

Justification of usage of algorithms, key sizes and modes:
	
	1. For encrypting plain text data, the program uses AES (keysize = 256 bits) with CBC mode encryption
		- This encryption is Computationally less intensive as compared to public-key cryptography using RSA.
		- The algorithm uses 256 bits keysize, so it's more secured as compared to DES or 3DES the other symmetric key algorithms.
		- Its difficult to decrypt key of 256 bits by brute force hence larger key size.
		- It can be used for both block ciphers or stream ciphers.
		- Variable block size for variable key sizes are available viz. 128,192,256.
		- It is easy to implement for hardware as well as for software.	
	
	2. AES secret key, used for encrypting plaintext is encrypted using RSA asymmetric algorithm with 2048 bit key size.
		- It requires exponential time to break the RSA key, also the security of RSA is based on the difficulty of factoring the large Modulus.
		- Program uses 2048 bit keysize hence it would be very difficult to factorize it.		
		- Sender encrypts the AES Key with receiver's public key hence, only the receiver can decrypt it using it's private key		
		- RSA encryption is slow hence the program only encrypts the secret key using RSA, thus reducing the overhead.
	
	3. AES Encryption uses CBC mode to encrypt
		- This mode requires a  initialization vector, allowing data to in encrypted in size of 64 bits
		- Using CBC ensures that if the plain text block is repeated, cipher text is different 
		
	3. The data is encrypted using Cipher block chaining mode, which requires a initialization vector.
		- Allows for encryption of data in blocks of size 64-bits
		- If same blocks repeats in the plain text, it will not be repeated in cipher text.
		- Also it ensure integrity of plaintext  i.e. it detects the tampering of data on it's way
	
	4. The plain text data bytes are Sign with the sender's private key for 
		proper authenticity of the data received. The receiver checks for the signature using sender's public key
       
	
References:
	
	- For using crypto java libraries
	http://docs.oracle.com/javase/7/docs/api/javax/crypto/Cipher.html	
	
	- For signing files
	https://www.owasp.org/index.php/Digital_Signature_Implementation_in_Java#Algorithm_for_Implementing_Digital_signature_using_RSA_Algorithm
	http://docs.oracle.com/javase/tutorial/security/apisign/index.html 
	
	- For java debugging
	http://stackoverflow.com
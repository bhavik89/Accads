Instant Messenger
=================

How to run Client and Server in Terminal:
========================================
Go to Gandhi_Gokani_Final_Project/

Compile into bin folder:
javac -d bin src/generic/*.java src/server/*.java src/client/*.java

Run:
java -cp bin client.Client
java -cp bin server.Server

Note: Make sure your current directory is Gandhi_Gokani_Final_Project

Optional Command line arguments -:
===================================
java -cp bin server.Server serverPort

There are 3 different ways by which you could run client. Client would read these arguments from config files if you don't specify them.
java -cp bin client.Client clientPort
java -cp bin client.Client clientPort serverIP 
java -cp bin client.Client clientPort serverIP serverPort

Config File -:
===============
All the config files exist in Gandhi_Gokani_Final_Project/ and use config file to specify server IP, server port and client Port if you are not using the command line options.
1) Specify the server port in server.xml file
2) Specify the client port and server Ip in client.xml
3) Configuration files locates server public and private key.

Registered Users:
========================
Usernames	Password
------------------------
Admin		admin
Bhavik		bhavik
Mirav		mirav
UserTest	usertest

User specific commands -:
========================

list 			 	To display all users currently signed into the system 
whoami 				To display your own username in-case of multiple usenames 
send <USER> <MESSAGE>		To send the user USER a message, MESSAGE 
Bye  <USER>			To end communication from one client to another
logout				To logout from the server

Extra Paramters:
================

-d directory: To specify where to put class files during compilation
-cp directory: Specify the classpath, where to search for the files.



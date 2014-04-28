Instant Messenger
=================

How to run Client and Server in Terminal:
========================================
Go to Instant_Messenger/

Compile into bin folder:
javac -d bin src/common/*.java src/server/*.java src/client/*.java

Run:
java -cp bin server.Server
java -cp bin client.Client

Note: Make sure your current directory is Instant_Messenger

Optional Command line arguments -:
===================================
java -cp bin server.Server serverPort

There are 3 different ways by which you could run client. Client would read these arguments from config files if you don't specify them.
java -cp bin client.Client clientPort
java -cp bin client.Client clientPort serverIP 
java -cp bin client.Client clientPort serverIP serverPort

Config File -:
===============
All the config files exist in Instant_Messenger/src/resources/ and use config file to specify server IP, server port and client Port if you are not using the command line options.
1) Specify the server port in server.cfg file
2) Specify the client port and server Ip in client.cfg
3) Configuration files locates server public and private key.

Registered Users:
========================
Usernames	Password
------------------------
user1		password1
user2		password2
user3		password3
user4		password4
user5		password5

Extra Paramters:
================
-d directory: To specify where to put class files during compilation
: -> Use colon as separater on Unix(MacOS or Linux)
; -> Use semicolon as separater on Windows
-cp directory: Specify the classpath, where to search for the files.
package.MainClass -> java package.MainClass to excute and javac to compile.

Contact us:
===========
Mandeep Singh: singh.man@husky.neu.edu
Srivatsa Srivatsa: srivatsa@ccs.neu.edu

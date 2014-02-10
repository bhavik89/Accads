>>>>>>>>>README<<<<<<<<<



Server Client application is written python



TO run this server-client applicaiton, run server.py first by:



>>>>>RUN SERVER<<<<<



> On Linux terminal:


> CD into the directory where server.py and client.py files are located


> Run following command:



> "python Server.py <server ip> <server port>"

> 
	
	If server is initialized successfully you'll get following message:


> "Socket created...
   Server successfully initialized..."



>>>>RUN CLIENT<<<<<



> Open another terminal and run client using following command



> "python Client.py <server ip> <server port>"



> If client initialized successfully initialized you'll get following message:



> "Client Socket created, you can start broadcasting messages..."



> To register client on server send "GREETING" message
> ("GREETING" is case insensitive)



> Once the client is registered, you can start broadcasting messages using 



> "MESSAGE <Your message>" 
> ("MESSAGE" is case insensitive)



> Server will broadcast the message extracting "MESSAGE" part



> Similarly, You can run more clients, server will broadcast message 


> from one client to all the clients including the client which sent the message.


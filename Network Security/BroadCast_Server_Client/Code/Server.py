# UDP Server
"""
Import socket and system libraries.
socket: provides access to socket interface
sys: provides access to variables used or maintained by the interpreter 
     and to functions that interact strongly with the interpreter 
""" 
import socket, sys

"""
run_server function, accepts no arguments.

This function receives data sent by clients on server socket, 
Checks for the message type i.e. "GREETING" (case insensitive) or 
"MESSAGE" (case insensitive) and takes appropriate action.
It ignores any other message type
"""
def run_server():
	
	# receive data from the socket	
	data = server_socket.recvfrom(RECV_BUFFER)
	
	# If the message type is "GREETING", validate the client and add it to client_list
	if data[0].lower().startswith("greeting"):
		add_client(data[1])               	            
               	
             
        # If the message type is "MESSAGE", validate it for valid client and broadcast it
	elif data[0].lower().startswith("message"):
		send_message(data[0], data[1])
	
	# Ignore any other message type
	else:
		return	
		
"""
add_client function, takes client address (client_addr) as argument.

This function adds the given client to the client list if it is not 
already present in the list
"""
def add_client(client_addr):
	global client_list
	
	# If client is not present in client_list, add it to the list and 
	# print success message
	if client_addr not in client_list:
		client_list.append(client_addr)
		print "Client " + str(client_addr) + " connected" 
			                

"""
send_message function, takes Message (message) and client address (client_addr) as argument

Checks if the given client address is presnt client list, if so, broadcasts 
the message to all registered clients in client list.
"""
def send_message(message, client_addr):
	
	# check for valid client by checking if is client is registered in client list
	# if not registered, do nothing and return
	if client_addr not in client_list:
		return

	# if client is present in client list, extract message and remove type "MESSAGE" 
	# and broadcast it so all clients present in client list
	else:	
		message = message.strip().split(' ',1)[1]
		sys.stdout.write( "Message from Client " + str(client_addr) + ":" + message + "\n" )
		broadcast_data("Message from Client " + str(client_addr) + ":" + message + "\n")
 
"""
broadcast_data function, takes Message (message) as argument

Broadcasts the given message to all the client present in the client list(client_list)
"""
def broadcast_data (message):
	for c in client_list:			
		server_socket.sendto(message, c)
     		sys.stdout.flush()

"""
main function: 
Program starts executing from here

This function initiates the server sockets.
The server socket details (server_ip(host) & server_port) should be provided in 
command line arguements.
If not provided, server throws error for requirement of arguements

If the arguments are valid, and if server socket is created and 
binding of ip and port number happens correctly,
server prints the success message and waits for clients to connect to it.

When the connection to server socket is made, 
client has to first register itself on server to revcieve data bradcasted by server

To register client on server, send "GREETING"(case insensitive) message to server

Once the client is registeres on server it is able to send messages and receive 
messages from server

To send message, use "MESSAGE <Your Message>"(case insensitive) format so that 
server recognizes it as a valid message and can broadcast it.

The server checks if the message type is "GREETING" or "MESSAGE" and 
takes appropriate action and ignores all other messages
"""
if __name__ == "__main__":
    
    # check for number of arguments, if not valid throw error and exit
    if(len(sys.argv) < 3) :
        print 'Usage : python Server.py hostname port'
        sys.exit()
    
    # initialize server port and  server ip address from the arguments	
    server_port = int(sys.argv[2])
    server_ip = sys.argv[1]

    # initialize client list that are registered on server and buffer for 
    # receiving data from client.	
    client_list = []
    RECV_BUFFER = 65535     
    
    # Create a UDP socket for server, if created successfully, print success message
    # else print failure message and exit.	 
    try :
    	server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    	print ('Socket created...')
    except socket.error as err_msg: 
   	print ('Failed to create socket. Error Code : ' + str(err_msg[0]) + ' Message: ' + err_msg[1])
    	sys.exit()
    
    # Bind (Give name) the UDP socket created with the IP and Port address obtained from agruments
    # If binding is successfull, print out success message else print failure message and exit
    try:
    	server_socket.bind((server_ip, server_port))
	print ('Server successfully initialized...')
    except socket.error as msg:
    	print ('Binding failure. Error : ' + str(msg[0]) + ', Message: ' + msg[1])
    	sys.exit()
    
    # Run server indifinately to receive and send messages from clients.
    while 1:       	
	run_server()

    # Close server socket incase of any failure.
    server_socket.close()

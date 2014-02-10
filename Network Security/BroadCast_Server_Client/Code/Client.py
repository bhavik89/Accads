"""
Import socket, system and thread libraries.
socket: provides access to socket interface
sys: provides access to variables used or maintained by the interpreter 
     and to functions that interact strongly with the interpreter 
thread: provides access to thread interface for multithreading
"""
import socket, sys, thread

"""
receive_message thread:

Takes no argument.

This thread runs in parallel with the main program.
It checks for any received message from tha Server and validates it.
If the message is valid or the server has not stopped running,
it prints out the message on client console.

The messages received are mainly the broadcast messages from server
i.e. Messages from other clients registered on server

"""
def receive_message():
     # infinately run thread to receive messages from server	
     while 1:
	#read the data sent by server on socket connection
	in_data = client_socket.recvfrom(RECVBUFFER)

	#validate data, if not valid, exit else print on client console
	if not in_data :
        	print '\nServer connection closed...'
                sys.exit()
	else:
		sys.stdout.write(in_data[0])

"""
main function:

Program starts executing from here.

This function initiates the client sockets and makes connection to server socket.
The server socket details (server_ip(host) & server_port) should be provided in 
command line arguements.
If not provided, client throws error for requirement of arguements

If the arguments are valid, it establishes socket connection with server on provided
server_ip and server_port.

When the connection to server socket is made, receive_message thread is started 
to start revceiving data from the server.
Note: Client only receives data if it is registered on the server to revceive data

To register client on server, send "GREETING"(case insensitive) message to server

Once the client is registeres on server it is able to send messages and receive 
messages from server

To send message, use "MESSAGE <Your Message>"(case insensitive) format so that 
server recognizes it as a valid message and can broadcast it.
"""
if __name__ == "__main__":
     
    if(len(sys.argv) < 3) :
        print 'Usage : python client.py hostname port'
        sys.exit()
    RECVBUFFER = 65536 
    host = sys.argv[1]
    port = int(sys.argv[2])
    
    # Try to establish UDP socket connection to server, 
    # if not throw an error message and exit
    try: 
    	client_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    	sys.stdout.write("Client Socket created, you can start broadcasting messages...\n")
    except socket.error:
    	print ('Failed to create socket')
    	sys.exit()

    # if the socket connetion is established with server, 
    # start the thread to receive server messages	
    thread.start_new(receive_message, ())		
    
    # infinately run client to send and receive messages
    while 1:
	# read client messages from command line	
	client_msg = sys.stdin.readline()
	
	# Send the read messages to server
	client_socket.sendto(client_msg,(host, port))
	sys.stdout.flush()
		


        
                

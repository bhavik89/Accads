import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class MyServer {

	public static void main(String[] args) throws IOException {
		Socket clientSocket = null;
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(4444);
			System.out.println("server started....");
			clientSocket = serverSocket.accept();
		} catch (Exception e) {
		} // read & display the message
		// BufferedReader in = new BufferedReader(new
		// InputStreamReader(clientSocket.getInputS­tream()));
		
		while (true) {
			
			Scanner in1 = new Scanner(clientSocket.getInputStream());
			String mes;
			
			if (in1.hasNext()) {
				mes = in1.nextLine();
				System.out.println("Client message :" + mes);
			}
		}
	}

}
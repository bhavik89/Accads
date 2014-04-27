package generic;


import java.net.InetAddress;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ServerClientConfigReader {
	
	private static final String clientFile = "Client.xml";
	private static final String serverFile = "Server.xml";
	private Element elem;
	
	
	public ServerClientConfigReader(String fileType) {
		
		DocumentBuilderFactory docFactory;
		DocumentBuilder docBuilder;
		org.w3c.dom.Document doc;
		String filePath = null;
		
		if("client".equalsIgnoreCase(fileType))
			filePath = clientFile;
		else if ("server".equalsIgnoreCase(fileType))
			filePath = serverFile;
		else{
			System.out.println("Invalid Config file name");
			System.exit(0);
		}		
		
		docFactory = DocumentBuilderFactory.newInstance();
		docBuilder = null;
		doc = null;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(filePath);
			elem = doc.getDocumentElement();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	public static void main(String[] args) {
	
		ServerClientConfigReader XOpS = new ServerClientConfigReader("Server");		
		ServerClientConfigReader XOpC = new ServerClientConfigReader("Client");
		
		System.out.println("---Server Info---");
		System.out.println(XOpS.getServerPort());
		System.out.println(XOpS.getServerIp());
		System.out.println(XOpS.getServerPrivateKeyFile());
		System.out.println(XOpS.getServerPublicKeyFile());		
		System.out.println(XOpS.getUserPasswordHash("Bhavik"));
		System.out.println(XOpS.isUserRegistered("Mirav"));
		System.out.println(XOpS.isUserRegistered("xxx"));		
		
		System.out.println("\n---ClientInfo---");
		System.out.println(XOpC.getClientPort());
		System.out.println(XOpC.getClientServerPort());
		System.out.println(XOpC.getClientServerPublicKeyFile());
		System.out.println(XOpC.getClientIp());
		
}
	
	public int getServerPort(){		
		NodeList nodelist  = traverseToNode("ServerInfo" , "ServerDefaultPort");
		return Integer.parseInt(nodelist.item(0).getChildNodes().item(0).getNodeValue());
	}
	
	public InetAddress getServerIp(){
		NodeList nodelist  = traverseToNode("ServerInfo" , "ServerDefaultIP");
		try {
			return InetAddress.getByName(nodelist.item(0).getChildNodes().item(0).getNodeValue());
		} catch (Exception e) {
			System.out.println("Cannot Resolve server IP address");
			e.printStackTrace();
		}
		return null;
	}
	
	public String getServerPrivateKeyFile(){
		NodeList nodelist  = traverseToNode("ServerInfo" , "ServerPrivateKeyFile");
		return nodelist.item(0).getChildNodes().item(0).getNodeValue();
	}
	
	public String getServerPublicKeyFile(){
		NodeList nodelist  = traverseToNode("ServerInfo" , "ServerPublicKeyFile");
		return nodelist.item(0).getChildNodes().item(0).getNodeValue();
	}
	
	public String getUserPasswordHash(String userName){
		
		NodeList userList = elem.getElementsByTagName("User");
		
		if(userList != null && userList.getLength() > 0){
			for(int i=0; i < userList.getLength(); i++){				
				Node node = userList.item(i);				
				if (node.getNodeType() == Node.ELEMENT_NODE) {					
					Element e = (Element) node;					
					NodeList nodelist = e.getElementsByTagName("UserName");					
					String XMlUserName = nodelist.item(0).getChildNodes().item(0).getNodeValue();
					nodelist = e.getElementsByTagName("UserPasswordHash");
					String XMlPasswordHash = nodelist.item(0).getChildNodes().item(0).getNodeValue();
					
					if(XMlUserName.equals(userName)){
						return XMlPasswordHash;
					}					
				}
			}
		}		
		return null;
	}
	
	public boolean isUserRegistered(String userName){
		NodeList userList = elem.getElementsByTagName("User");
		
		if(userList != null && userList.getLength() > 0){
			for(int i=0; i < userList.getLength(); i++){				
				Node node = userList.item(i);				
				if (node.getNodeType() == Node.ELEMENT_NODE) {					
					Element e = (Element) node;					
					NodeList nodelist = e.getElementsByTagName("UserName");					
					String XMlUserName = nodelist.item(0).getChildNodes().item(0).getNodeValue();
															
					if(XMlUserName.equals(userName)){
						return true;
					}					
				}
			}
		}		
		return false;
	}
	
	
	public int getClientServerPort(){		
		NodeList nodelist  = traverseToNode("ClientInfo" , "ServerDefaultPort");
		return Integer.parseInt(nodelist.item(0).getChildNodes().item(0).getNodeValue());
	}
	
	public int getClientPort(){		
		NodeList nodelist  = traverseToNode("ClientInfo" , "ClientDefaultPort");
		return Integer.parseInt(nodelist.item(0).getChildNodes().item(0).getNodeValue());
	}	
	
	public InetAddress getClientIp(){
		NodeList nodelist  = traverseToNode("ClientInfo" , "ClientDefaultIP");
		try {
			return InetAddress.getByName(nodelist.item(0).getChildNodes().item(0).getNodeValue());
		} catch (Exception e) {
			System.out.println("Cannot Resolve server IP address");
			e.printStackTrace();
		}
		return null;
	}
	
	public String getClientServerPublicKeyFile(){
		NodeList nodelist  = traverseToNode("ClientInfo" , "ServerPublicKeyFile");
		return nodelist.item(0).getChildNodes().item(0).getNodeValue();
	}
	
	private NodeList traverseToNode(String parentNode, String childNodeName) {
		NodeList serverInfo = this.elem.getElementsByTagName(parentNode);
		
		if(serverInfo != null && serverInfo.getLength() > 0){
			for(int i=0; i < serverInfo.getLength(); i++){				
				Node node = serverInfo.item(i);				
				if (node.getNodeType() == Node.ELEMENT_NODE) {					
					Element e = (Element) node;					
					return e.getElementsByTagName(childNodeName);
				}
			}
		}
		return null;
	}

	public InetAddress getClientServerIP() {
		NodeList nodelist  = traverseToNode("ClientInfo" , "ServerDefaultIP");
		try {
			return InetAddress.getByName(nodelist.item(0).getChildNodes().item(0).getNodeValue());
		} catch (Exception e) {
			System.out.println("Cannot Resolve server IP address");
			e.printStackTrace();
		}
		return null;
	}
}
	


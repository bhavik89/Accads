package webappLogin.com;

import java.util.*;
import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.xpath.*;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.sun.xml.internal.txw2.Document;

public class DbManager {
	
	
	public static void InsertXML(GetsSets set) throws TransformerException {
		 
		   try {
			String filepath = "users.xml";
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc = docBuilder.parse(filepath);
			
			Element root = doc.getDocumentElement();
			
			Element rootElement = doc.getDocumentElement();
			
			Element user = doc.createElement("user");
			rootElement.appendChild(user);
			
			Element fname = doc.createElement("firstname");
			fname.appendChild(doc.createTextNode(set.getFname()));
			user.appendChild(fname);
			
			Element email = doc.createElement("email");
			email.appendChild(doc.createTextNode(set.getEmail()));
			user.appendChild(email);
			
			Element password = doc.createElement("password");
			password.appendChild(doc.createTextNode(set.getPassword()));
			user.appendChild(password);
			
			Element country = doc.createElement("country");
			country.appendChild(doc.createTextNode(set.getCountry()));
			user.appendChild(country);
			
			
			rootElement.appendChild(user);			
			
			DOMSource source = new DOMSource(doc);

	        TransformerFactory transformerFactory = TransformerFactory.newInstance();
	        Transformer transformer = transformerFactory.newTransformer();
	        StreamResult result = new StreamResult("users.xml");
	        transformer.transform(source, result);
	        
			System.out.println("Done");
	 
		   } catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		   } catch (IOException ioe) {
			ioe.printStackTrace();
		   } catch (SAXException sae) {
			sae.printStackTrace();
		   }
		}
	
	public static void deleteEntry(GetsSets set) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException, TransformerException{
		
		String filepath = "users.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(filepath);
		
		Element elem = doc.getDocumentElement();
		NodeList userList = elem.getElementsByTagName("user");
		
		if(userList != null && userList.getLength() > 0){
			for(int i=0; i < userList.getLength(); i++){
				
				Node node = userList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element e = (Element) node;
					
					NodeList nodelist = e.getElementsByTagName("email");					
					String emailElem = nodelist.item(0).getChildNodes().item(0).getNodeValue();
															
					if(emailElem.equals(set.getEmail())){
						e.getParentNode().removeChild(e);
					}					
				}
			}
		}		
        
        DOMSource source = new DOMSource(doc);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("users.xml");
        transformer.transform(source, result);
		
	}
	
	public static void updateInfo(GetsSets set) throws SAXException, IOException, ParserConfigurationException, TransformerException{
		
		String filepath = "users.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(filepath);
		
		Element elem = doc.getDocumentElement();
		NodeList userList = elem.getElementsByTagName("user");
		
		if(userList != null && userList.getLength() > 0){
			for(int i=0; i < userList.getLength(); i++){
				
				Node node = userList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element e = (Element) node;
					
					NodeList nodelist = e.getElementsByTagName("email");					
					String emailElem = nodelist.item(0).getChildNodes().item(0).getNodeValue();
															
					if(emailElem.equals(set.getEmail())){
						NodeList childList = e.getChildNodes();
						for (int j = 0; j < childList.getLength(); j++) {
							 
			           Node childNode = childList.item(j);
			 					   
					   if ("firstname".equals(childNode.getNodeName()) && (set.getFname() != "")) {						   
						   childNode.setTextContent(set.getFname());
					   }
					   if ("password".equals(childNode.getNodeName()) && (set.getPassword() != "")){						   
						   childNode.setTextContent(set.getPassword());
					   }
					   if ("country".equals(childNode.getNodeName()) && (set.getCountry() != "")) {						   
						   childNode.setTextContent(set.getCountry());					   
					   }
					   }
					   
					}					
				}
			}
		}		
        
        DOMSource source = new DOMSource(doc);

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("users.xml");
        transformer.transform(source, result);
		
		
	}
	
	
	public static boolean checkLogin(GetsSets set) throws ParserConfigurationException, SAXException, IOException{
		
		String filepath = "users.xml";
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		org.w3c.dom.Document doc = docBuilder.parse(filepath);
		
		Element elem = doc.getDocumentElement();
		
		NodeList userList = elem.getElementsByTagName("user");
		
		if(userList != null && userList.getLength() > 0){
			for(int i=0; i < userList.getLength(); i++){
				
				Node node = userList.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element e = (Element) node;
					
					NodeList nodelist = e.getElementsByTagName("email");					
					String emailElem = nodelist.item(0).getChildNodes().item(0).getNodeValue();
					
					nodelist = e.getElementsByTagName("password");
					String passwordElem = nodelist.item(0).getChildNodes().item(0).getNodeValue();
					
					if(emailElem.equals(set.getEmail()) && passwordElem.equals(set.getPassword())){
						return true;
					}					
				}
			}
		}		
		
		return false;
	}
	
	
}

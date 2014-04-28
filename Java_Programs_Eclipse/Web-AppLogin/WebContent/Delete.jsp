<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    
    import="javax.xml.parsers.DocumentBuilderFactory,javax.xml.parsers.DocumentBuilder,org.w3c.dom.*" errorPage="" 
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Delete</title>
<style type="text/css">
        body
        {
          
          height:90%;
          width:80%;
          margin: auto;
          margin-top:2%;
         -moz-box-shadow: 0 0 5px 5px #888;
         -webkit-box-shadow: 0 0 5px 5px#888;
          box-shadow: 0 0 5px 5px #888;   
          padding-left: 3%; 
          padding-right: 3%;           
          padding-bottom: 3%;
          padding-top: 3%;      
          
         }
         
         .heading
        {
            text-align: center;
            padding-top:2%;
            font-size: xx-large;
        }
               
    </style>
</head>
<body>
<%
String userEmail = null;
String fname = null;
String country = null;

Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
    if(cookie.getName().equals("userEmail")) userEmail = cookie.getValue();
}

if(userEmail == null) response.sendRedirect("login.jsp");

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
												
			if(emailElem.equals(userEmail)){
				NodeList childList = e.getChildNodes();
				for (int j = 0; j < childList.getLength(); j++) {
					 
	           Node childNode = childList.item(j);
	 				
	           
			   if ("firstname".equals(childNode.getNodeName()))
				   fname = childNode.getFirstChild().getNodeValue();
				   
			   		  
			   if ("country".equals(childNode.getNodeName())) 
				   country = childNode.getFirstChild().getNodeValue();				   
			   
			   }
			   
			}		
		}
	}
}


}

%>

<form action="RegServlet" name="delete" method="post">
<input type="hidden" name="pagename" value="delete" />
	<table>
		<tr>
		<td>Hi <%=fname %>, are you sure you want to delete account associated with email: <%=  userEmail%>></td>
		</tr>
	</table>
	<table>	
		<tr>
		<td><input type="submit" value="Yes Delete!"/> </td>
		<td><a href="loginSucces.jsp">No, Go back!</a></td>
		</tr>
	</table>
</form>	
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
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
<form action="RegServlet" name="login" method="post">
<input type="hidden" name="pagename" value="login" />
<p class="heading"><em>Welcome to Galatea Associates Web App </em></p>
	<table>
		<tr>
		<td>Email:</td>
		<td><input type="text" name="loginEmail"></td>
		</tr>
		
		<tr>
		<td>Password:</td>
		<td><input type="password" name="loginPassword"></td>
		</tr>
		
		<tr>
		<td></td>
		<td><input type="submit" value="Login"/> 
		<a href="register.jsp">Register New User</a></td>
		</tr>

	</table>
</form>	
</body>
</html>
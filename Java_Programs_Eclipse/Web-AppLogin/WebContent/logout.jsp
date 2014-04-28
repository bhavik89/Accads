<% 

Cookie[] cookies = request.getCookies();

for(Cookie cookie : cookies){
    if(cookie.getName().equals("userEmail")) 
    	cookie.setMaxAge(0);
}
response.sendRedirect("login.jsp");
%>
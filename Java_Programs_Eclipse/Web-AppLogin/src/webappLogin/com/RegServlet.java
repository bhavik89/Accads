package webappLogin.com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

/**
 * Servlet implementation class RegServlet
 */
@WebServlet("/RegServlet")
public class RegServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String hiddenParam = request.getParameter("pagename");
		System.out.println(hiddenParam);
		
		if(hiddenParam.equals("register")){			
		
		String fname = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String counrty = request.getParameter("country");
		
		GetsSets sets = new GetsSets();
		
		sets.setFname(fname);
		sets.setEmail(email);
		sets.setPassword(password);
		sets.setCountry(counrty);
		
		try {
			DbManager.InsertXML(sets);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		response.setContentType("Text/html");
		PrintWriter out = response.getWriter();
		out.println("<h1> Account Created</h1></br><a href='login.jsp'>Login</a>");
		
	}
		
		
		if(hiddenParam.equals("login")){
			
					
			String email = request.getParameter("loginEmail");
			String password = request.getParameter("loginPassword");
						
			if(email.equals("admin") && password.equals("admin")){
				response.sendRedirect("admin.jsp");
			}else{
				GetsSets set = new GetsSets();
				
				set.setEmail(email);
				set.setPassword(password);
				
				try {
					if(DbManager.checkLogin(set)){
						
						Cookie loginCookie = new Cookie ("userEmail", email);
						loginCookie.setMaxAge(30*60);
						response.addCookie(loginCookie);	
						response.sendRedirect("loginSucces.jsp");
						//response.setContentType("Text/html");
						//PrintWriter out = response.getWriter();
						//out.println("<h1 color=red>Welcome " + email + "</h1>");
					}
					else{
						response.setContentType("Text/html");
						PrintWriter out = response.getWriter();
						out.println("<h1 color=red>Login Error! </h1>");
					}
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		if(hiddenParam.equals("delete")){
			
			String email = null;
			
			Cookie[] cookies = request.getCookies();
			
			for(Cookie cookie : cookies){
			    if(cookie.getName().equals("userEmail")) email = cookie.getValue();
			}
			
			GetsSets set = new GetsSets();			
			set.setEmail(email);
			
			try {
				DbManager.deleteEntry(set);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			for(Cookie cookie : cookies){
			    if(cookie.getName().equals("userEmail")) 
			    	cookie.setMaxAge(0);
			}
			
			response.setContentType("Text/html");
			PrintWriter out = response.getWriter();
			out.println("<h1><font color=red>Account Deleted! </font></h1>");
			
		}
		
		
		if(hiddenParam.equals("update")){
			
			String userEmail = null;					
			String fname = request.getParameter("name");
			String password = request.getParameter("password");
			String counrty = request.getParameter("country");
			
			Cookie[] cookies = request.getCookies();
			
			for(Cookie cookie : cookies){
			    if(cookie.getName().equals("userEmail")) userEmail = cookie.getValue();
			}
			
			if(fname == "")
				System.out.println("Null Firstname");
						
			GetsSets sets = new GetsSets();
			
			sets.setFname(fname);
			sets.setEmail(userEmail);
			sets.setPassword(password);
			sets.setCountry(counrty);
			
			try {
				DbManager.updateInfo(sets);
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TransformerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			response.sendRedirect("loginSucces.jsp");
			
		}
		
	}

}

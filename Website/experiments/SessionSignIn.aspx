<%--<%@ Page Language="C#" AutoEventWireup="true" CodeFile="SignIn.aspx.cs" Inherits="experiments_SignIn" %>--%>

<%@ Page Language="C#"%>
<%@ Import Namespace="edu.neu.ccis.bhavik.loginDetails"%>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="System.Linq" %>
<%@ Import Namespace="System.Data.Linq" %>

<!DOCTYPE html>

<script runat="server">
    
    loginDetailsDataContext cont = new loginDetailsDataContext();
    
    protected void login_button(object sender, EventArgs e)
  {
    errorlbl.Text = "";
    var emailValid = false;
    var passValid = false;

    var user = from tab in cont.loginDetails select tab;

    if (user.FirstOrDefault() != null)
    {
        foreach (var r in user)
        {
            if (r.email == emailtxt.Text.Trim() && r.password == passtxt.Text.Trim())
            {
                emailValid = true;
                passValid = true;
                
            }
            if (emailValid == true && emailValid == true)
            {
                
                HttpCookie cookie = Request.Cookies["Preferences"];
                if (cookie == null)
                {
                    cookie = new HttpCookie("Preferences");
                }

                cookie.Name = r.name;
                cookie.Expires = DateTime.Now.AddYears(1);
                Response.Cookies.Add(cookie);
                
                
                Session["x"] = r.name;
                Session.Timeout = 1;
                Response.Redirect("UserHome.aspx");
                break;
            }
                
            else
                loggedIn.Text = "Not Logged in";
        }
    }


    if (passValid == false && emailValid == false)
        errorlbl.Text = "Login Id or password is invalid";
    else if (passValid == false)
        errorlbl.Text = "Incorrect Password";
    else if (emailValid == false)
        errorlbl.Text = "Wrong Credentials";
    else
    {
        errorlbl.Text = "Login Successful!!";
    }
        
}

    protected void Page_Load(object sender, EventArgs e)
    {

    }
</script>


<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <style type="text/css">
        .style1
        {
            width: 60%;
            height: 84px;
        }
        .style2
        {
            width: 146px;
        }
        .style3
        {
            text-align: center;
            color: #0000FF;
        }
        .style4
        {
            font-size: x-large;
            text-align: center;
        }
    </style>
</head>
<body style="width: 1000px">
    <form id="SignIn" runat="server">
    <div>
    <p class="style4"><strong><em>Experiment: Cookies and Sessions for Web Application</em></strong></p>
        <p><strong><em>Experiment:</em></strong></p>
        <p>This experiment demostrates the creation and use of Sessions and cookies for Web 
            Application.</p><hr/>
        <p><strong><em>Documentation:</em></strong></p>
        <p style="width: 868px">A cookie is often used to identify a user. A cookie is a 
            small file that the server embeds on the user&#39;s computer. Each time the same 
            computer requests a page with a browser, it will send the cookie too. </p>
        <p style="width: 868px">The cookie is created by the following code :</p>
        <p style="width: 868px"><code> HttpCookie cookie = Request.Cookies["Preferences"];</br>
                if (cookie == null)</br>
                {</br>
                    cookie = new HttpCookie("Preferences"); //Create new http cookie</br>
                }</br>

                cookie.Name = [name of cookie];</br>
                cookie.Expires = DateTime.Now.AddYears(1); // cookie expires in one year</br>
                Response.Cookies.Add(cookie); // Adds cookie to browser</br></code>;</p>

                <p>
                    The Session object stores information about, or change settings for a user 
                    session.</p>
        <p>
            Variables stored in a Session object hold information about one single user, and 
            are available to all pages in one application.
        </p>
        <p>
            A session starts when:</p>
        <ul>
            <li>A new user requests an ASP file, and the Global.asa file includes a 
                Session_OnStart procedure</li>
            <li>A value is stored in a Session variable</li>
            <li>A user requests an ASP file, and the Global.asa file uses the &lt;object&gt; tag to 
                instantiate an object with session scope</li>
        </ul>
        <p>
            A session ends if a user has not requested or refreshed a page in the 
            application for a specified period. By default, this is 20 minutes.
        </p>
        <p>
            If you want to set a timeout interval that is shorter or longer than the 
            default, use the <b>Timeout</b> property.</p>

                <p> 
                    The Session is created in following manner:</p>
        <code>
              Session["x"] = [session name];</br>
                Session.Timeout = 1; //the session will be expired in 1 minute</br>
                Response.Redirect("redirect url"); //URL to be redirected</br>
        
        </code>
        
        <p style="width: 1000px"> 
                On the redirected URL the session will be Identified and will display the 
                session name. Also there is a logout button to end the session. When logout is 
                pressed, The Session is ended and the user is redirected to Signin page or if 
                there is no activity for one minute, the session is ended.</p>
        <p> 
                This is done using following piece of code:</p>
                <code>
              //Identifies the sesion element and displays it on page</br>
                if (Session["x"] == null)</br>
                Response.Redirect("SignIn.aspx");</br>
            else</br>
                username.Text = Session["x"].ToString();  </br>
                </br></br>
                //Session logout</br>
                Session.Remove("x");</br>
            if (Session["x"] == null)</br>
                Response.Redirect("SignIn.aspx");</br>
                </code>
    <hr/>
    <p>
        <strong><em>Implementation:
    </em></strong>
    </p>
        <p>
            Sign in with your Account details you entered on 
            <a href="aspformvalid.aspx" target="_blank">Login Page</a> Or use the testing 
            credentials given below</p>
        <table class="style1">
            <tr>
                <td class="style3" colspan="2">
                    <strong><em>SignIn Form</em></strong></td>
            </tr>
            <tr>
                <td class="style2">
                    Email:</td>
                <td>
                    <asp:TextBox ID="emailtxt" runat="server" Width="200px"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="style2">
                    Password</td>
                <td>
                    <asp:TextBox ID="passtxt" runat="server" Width="200px" TextMode="Password"></asp:TextBox>
                </td>
            </tr>
            <tr>
                <td class="style2">
                    <asp:Button ID="login" runat="server" Text="LogIn" Width="90px" OnClick="login_button"/>
                </td>
                <td>
                    <asp:Label ID="errorlbl" runat="server" Font-Italic="True" ForeColor="Red"></asp:Label>
                </td>
            </tr>
        </table>
    
        <br />
        <asp:Label ID="loggedIn" runat="server" Font-Bold="True" Font-Italic="True" 
            Font-Names="Bradley Hand ITC" Font-Size="Larger"></asp:Label>
        <br />
    
    </div>
    </form>
    <p>
        For testing you can use follwing details or you can create an account and login 
        with that</p>
    <p>
        Email Id : guest@ccs.neu.edu</p>
    <p>
        Password: guestpass</p><hr/>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/SessionSignIn.aspx" target=_blank>
        <strong>Page Source</strong></a></p>
    <p>
        <strong><em>References :</em></strong></p>
    <p>
        <a href="www.w3schools.com/">W3schools</a></p>
    <p>
        <a href="http://www.youtube.com/">Youtube videos</a></p>
</body>
</html>

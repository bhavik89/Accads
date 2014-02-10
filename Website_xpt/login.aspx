<%@Page Language="C#"%>
<%@ Import Namespace="System" %>
<%@ Import Namespace="System.Collections.Generic" %>

<!DOCTYPE html>

<script runat="server">
protected void Page_Load(object sender, EventArgs e)
    {
    
    
    }

</script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Login</title>
<style>
 body
        {
         width:70%;
         margin-left:auto;
         margin-right:auto;
         margin-top:10%;
         -moz-box-shadow: 0 0 5px 5px #888;
         -webkit-box-shadow: 0 0 5px 5px#888;
          box-shadow: 0 0 5px 5px #888;   
          padding-top:10%;
          padding-left: 10%; 
          padding-bottom: 10%;
         }

</style>  


</head>
<body>
    <form id="form1" runat="server">

         
       <asp:Login
            ID="Login1"
            runat="server"
            LoginButtonText="Login"
            TitleText="Login"
            DestinationPageUrl="Home.aspx"
            DisplayRememberMe="False"
            VisibleWhenLoggedIn="false" 
            RememberMeSet="true">
            </asp:Login>

            <asp:LoginView
             ID="LoginView0"
             Runat="server">

            <LoggedInTemplate>
            <asp:LoginStatus
            ID="LoginStatus1"
            runat="server"
            LogoutAction="Redirect"
            LogoutPageUrl="Home.aspx" />
             <p class="bold">
                    <asp:LoginName
                        ID="LoginName1"
                        Runat="server"
                        FormatString="Logged in as {0}" />
                </p>
            </LoggedInTemplate>
            </asp:LoginView>

<%--<div>

<asp:Login
ID="Login1"
runat="server"
LoginButtonText="Login"
TitleText="Login"
DestinationPageUrl="aspLogin.aspx"
DisplayRememberMe="False"
VisibleWhenLoggedIn="false" ></asp:Login>
</div>

<div>
<asp:LoginView
ID="LoginView0"
Runat="server">

<LoggedInTemplate>
<asp:LoginStatus
ID="LoginStatus1"
runat="server"
LogoutAction="Redirect"
LogoutPageUrl="aspLogin.aspx" />
 <p class="bold">
        <asp:LoginName
            ID="LoginName1"
            Runat="server"
            FormatString="Logged in as {0}" />
    </p>
</LoggedInTemplate>
</asp:LoginView>
 </div>--%>
  </form>
</body>
</html>

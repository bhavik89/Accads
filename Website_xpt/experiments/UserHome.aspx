<%--<%@ Page Language="C#" AutoEventWireup="true" CodeFile="UserHome.aspx.cs" Inherits="experiments_UserHome" %>--%>

<%@ Page Language="C#"%>
<%@ Import Namespace="System.Collections.Generic" %>
<%@ Import Namespace="System.Linq" %>
<%@ Import Namespace="System" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Home</title>
    <script runat="server">
    
    
    
        protected void Page_Load(object sender, EventArgs e)
        {
            if (Session["x"] == null)
                Response.Redirect("SessionSignIn.aspx");
            else
                username.Text = Session["x"].ToString();           
        }

        protected void Button1_Click(object sender, EventArgs e)
        {

            Session.Remove("x");
            if (Session["x"] == null)
                Response.Redirect("SessionSignIn.aspx");
        }
</script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
    Hello 
        <asp:Label ID="username" runat="server"></asp:Label> &nbsp;!!
        <br />
        <br />
        <asp:Button ID="logout" runat="server" onclick="Button1_Click" Text="Log Out" />
        <p>You will be automatically logged out in 1 minute. Use Logout button to manually logout from the session</p>
    </div>
    </form>
</body>
</html>

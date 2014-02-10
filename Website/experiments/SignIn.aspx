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
               loggedIn.Text = "Welcome " + r.name;
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
    </style>
</head>
<body style="width: 699px">
    <form id="SignIn" runat="server">
    <div>
    
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
        Password: guestpass</p>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/SignIn.aspx" target=_blank>Page Source</a></p>
</body>
</html>

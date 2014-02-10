<%@ Page Language="C#" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script runat="server">

</script>
<link href="CSS/moreInfo.css" rel="stylesheet" media="screen, projection" />
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>MoreInfo</title>
</head>
<body class="pad" style="background-color: rgb(255, 255, 255);">
    <form id="form1" runat="server">
    <div>
    <ul class = "master_navigation">
    <li>
    <a href="maps.aspx">
    <img src="_images/logo_noback.png" style="position:absolute; z-index:100; margin-top:-45px; margin-left: -2%;" />
    </a>
    </li>
    <li style="padding-left:8%;"><a href="#">Home</a></li>
    <li><a href="#">Documentation</a></li>      
           <li>
           
        <asp:LoginView ID="LoginView0" Runat="server">
        <LoggedInTemplate>
           
        <asp:LoginName
            ID="Login1"
            Runat="server"
            FormatString="Welcome <span class='red'>{0}</span>" />
        </LoggedInTemplate>
        </asp:LoginView>
           
        <asp:LoginStatus ID="LoginStatus2" runat="server" LoginText="Sign In">     
        </asp:LoginStatus>
        </li>      
        </ul>
</div>
    </form>
</body>
</html>

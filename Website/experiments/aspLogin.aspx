<%@Page Language="C#"%>
<%@ Import Namespace="edu.neu.ccis.bhavik.commentData" %>
<%@ Import Namespace="System" %>
<%@ Import Namespace="System.Collections.Generic" %>
<!DOCTYPE html>
<script runat="server">
  
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Session["update"] = Server.UrlEncode(System.DateTime.Now.ToString());

            commentDataDataContext context = new commentDataDataContext();
            commentData newcomment = new commentData();
            var commentTable = from tabl in context.commentData select tabl;

            name.Text = "";
            comment.Text = "";

            commentSpace.InnerHtml = "";

            foreach (var c in commentTable)
            {
                commentSpace.InnerHtml += "<u><i>";
                commentSpace.InnerHtml += "<b>" + c.username + "</b>";
                commentSpace.InnerHtml += " posted on ";
                commentSpace.InnerHtml += c.date;
                commentSpace.InnerHtml += " at ";
                commentSpace.InnerHtml += c.time;
                commentSpace.InnerHtml += "</u></i>";
                commentSpace.InnerHtml += "</br>";
                commentSpace.InnerHtml += "</br>";
                commentSpace.InnerHtml += c.comments;
                commentSpace.InnerHtml += "<hr>";
                commentSpace.InnerHtml += "</br>";
                commentSpace.InnerHtml += "</br>";
            }

        }

        if (Page.User.Identity.IsAuthenticated)
        {

            name.Enabled = true;
            comment.Enabled = true;
            post.Enabled = true;

        }
    }


    protected void post_Click(object sender, EventArgs e)
    {
        //  if (Session["update"].ToString() == ViewState["update"].ToString())
        //{
        
        if (Session["update"].ToString() == ViewState["update"].ToString())
        {
            
            if (name.Text != null && comment.Text != null)
            {
                commentDataDataContext context = new commentDataDataContext();
                commentData newcomment = new commentData();

                var date = System.DateTime.Now.ToLongDateString();

                newcomment.username = name.Text;
                newcomment.comments = comment.Text;
                newcomment.date = System.DateTime.Now.ToLongDateString();
                newcomment.time = System.DateTime.Now.ToShortTimeString();

                context.commentData.InsertOnSubmit(newcomment);

                foreach (var control in this.Controls)
                {
                    var textbox = name as TextBox;
                    var commentbox = comment as TextBox;

                    if (textbox != null)
                    {
                        textbox.Text = "name";
                        comment.Text = "Comment";
                    }
                }
                
                context.SubmitChanges();
                //Response.Redirect(Request.Url.PathAndQuery, true);
            }


            Session["update"] = Server.UrlEncode(System.DateTime.Now.ToString());
            //ClientScript.RegisterStartupScript(GetType(), "hwa", "alert('Hello World');", true);
        }
            

        else
        {
            //ClientScript.RegisterStartupScript(GetType(), "hwa", "alert('Hello World');", true);
        }

        name.Text = "";
        comment.Text = "";
        
       
    }

    protected override void OnPreRender(EventArgs e)
    {
        base.OnPreRender(e);
        ViewState["update"] = Session["update"];

        commentDataDataContext context1 = new commentDataDataContext();
        commentData newcomment1 = new commentData();
        var commentTable = from tabl in context1.commentData select tabl;


        commentSpace.InnerHtml = "";

        foreach (var c in commentTable)
        {
            commentSpace.InnerHtml += "<u><i>";
            commentSpace.InnerHtml += "<b>" + c.username + "</b>";
            commentSpace.InnerHtml += " posted on ";
            commentSpace.InnerHtml += c.date;
            commentSpace.InnerHtml += " at ";
            commentSpace.InnerHtml += c.time;
            commentSpace.InnerHtml += "</u></i>";
            commentSpace.InnerHtml += "</br>";
            commentSpace.InnerHtml += "</br>";
            commentSpace.InnerHtml += c.comments;
            commentSpace.InnerHtml += "<hr>";
            commentSpace.InnerHtml += "</br>";
            commentSpace.InnerHtml += "</br>";
        }

        name.Text = "";
        comment.Text = "";
    }
    
    
    

</script>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">

    <title>Login</title>

    <style type="text/css">
body
{
 max-width:980px;
 width:auto;
 margin-left:auto;
 margin-right:auto;
}    
        .style1
        {
            font-size: x-large;
        }
        .style2
        {
            font-size: x-large;
            text-align: center;
        }
        .style4
        {
            font-size: medium;
            text-align: center;
            font-family: "Times New Roman", Times, serif;
        }
        .style5
        {
            font-size: medium;
            font-family: "Times New Roman", Times, serif;
        }
    </style>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript">
//        var objDiv = document.getElementById("commentSpace");
//
        //        objDiv.scrollTop = objDiv.scrollHeight;
        $(document).ready(function () {
           // $('#commentSpace').scrollTop($('#commentSpace')[0].scrollHeight);
            $('#commentSpace').animate({ scrollTop: $('#commentSpace')[0].scrollHeight }, 1000);
        });
    </script>



</head>
<body>
    <form id="form1" runat="server">
    <p class="style2"><strong><em>Experiment: ASP.NET Login with login rights</em></strong></p>
    <p><strong><em>E</em></strong><span class="style5"><strong><em>xperiment:</em></strong> 
        This experiment demonstrates the login controls of the ASP.net and also the user 
        rights upon log in.</span></p><hr />
    <p class="style5"><strong><em>Documentation:</em></strong></p>
    <p class="style5">In this experiment, a login form is created using the asp.net 
        login controls. The steps basic steps given on Prof. Rasala&#39;s site is followed 
        configure the log in controls. Various user accounts are created using asp.net 
        configuration. </p>
    <p class="style5">Also, this experiment demontrates the user rights upon login. 
        Initially when the user is not logged in, the input to comment box is disabled. 
        The user can only insert the comments if logged in. To check whether a user is 
        logged in a simple C# code is used. If the user is logged in, the comment box 
        controls are enabled.</p><hr />
    <p class="style5"><strong><em>Experiment Demo: </em></strong>Sign in using following credentials</p>
    <p class="style5">Username: ws-all</p>
    <p class="style5">Password: pass@123</p>



    
    <div>
        <asp:Login ID="Login1" runat="server" LoginButtonText="Login" TitleText="Sign In to Post Comment" DestinationPageUrl="aspLogin.aspx"
            DisplayRememberMe="true" VisibleWhenLoggedIn="false" >
        </asp:Login>
    </div>
    <div>
        <asp:LoginView ID="LoginView0" runat="server">
            <LoggedInTemplate>
                <asp:LoginStatus ID="LoginStatus1" runat="server" LogoutAction="Redirect" LogoutPageUrl="aspLogin.aspx" />
                <p class="bold">
                    <asp:LoginName ID="LoginName1" runat="server" FormatString="Logged in as {0}" />
                </p>
            </LoggedInTemplate>
        </asp:LoginView>
    </div>
    <asp:ScriptManager ID="ScriptManager1" runat="server">
    </asp:ScriptManager>
    <asp:UpdatePanel ID="commentUpdate" runat="server">
        <ContentTemplate>
            <div id="commentSpace" runat="server" style="border-bottom: silver 1px solid; text-align: left;
                border-left: silver 1px solid; background-color: #f4f4f4; margin: 20px 0px 10px;
                width: 58.7%; max-height: 200px; overflow: auto; border-top: silver 1px solid;
                border-right: silver 1px solid; padding-top: 5px; padding-bottom: 5px; padding-left: 5px;
                padding-right: 5px">
            </div>
        </ContentTemplate>
        <Triggers>
            <asp:AsyncPostBackTrigger ControlID="post" EventName="Click" />
        </Triggers>
    </asp:UpdatePanel>
    <table class="style1">
        <tr>
            <td class="style5">
                Name:
            </td>
            <td>
                <asp:TextBox ID="name" runat="server" Width="200px" CssClass="style5" Enabled="false"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="name"
                    ErrorMessage="Please Enter Your Name" Font-Italic="True" Font-Names="Aparajita"
                    ForeColor="Red" CssClass="style5"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td class="style5">
                Comment:
            </td>
            <td>
                <asp:TextBox ID="comment" runat="server" EnableTheming="False" Height="80px" TextMode="MultiLine" Width="548px" CssClass="style5" Enabled="false"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td class="style4">
                &nbsp;
            </td>
            <td>
                <asp:Button ID="post" runat="server" OnClick="post_Click" Text="Post Comment" Width="131px" CssClass="style5" Enabled="false" />
            </td>
        </tr>
    </table>
    <p class="style5">
        <asp:Label ID="error" runat="server" Text=""></asp:Label>
    </p>
    </form>
    <p class="style5">
        <a href="../fileview/Default.aspx?~/experiments/aspLogin.aspx" target="_blank">
        <strong><em>Page Source</em></strong></a></p>
    <p class="style5">
        <strong><em>References:</em></strong></p>
    <p class="style5">
        <a href="http://www.ccs.neu.edu/teaching/web/connection_strings.htm">Prof. Rasala&#39;s Site</a></p>
    <p class="style5">
        <a href="https://http://msdn.microsoft.com/en-us/library/ms178329(v=vs.100).aspx">MSDN</a></p>
</body>
</html>

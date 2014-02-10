<%--<%@ Page Language="C#" AutoEventWireup="true" CodeFile="comment.aspx.cs" Inherits="experiments_comment" %>--%>

<%@ Page Language="C#"%>
<%@ Import Namespace="edu.neu.ccis.bhavik.commentData"%>
<%@ Import Namespace="System" %>
<%@ Import Namespace="System.Collections.Generic" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

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

                context.SubmitChanges();
                name.Text = "";
                comment.Text = "";
            }

           
            Session["update"] = Server.UrlEncode(System.DateTime.Now.ToString());
        }

        else
        {
        }

        name.Text = "";
        comment.Text = "";
    }

    
    //}

    //  protected void post_PreRender(object sender, EventArgs e)
    //  {
    //      ViewState["update"] = Session["update"];
    //  }
 
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
          commentSpace.InnerHtml += "<b>" + c.username + "</b>" ;
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
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <style type="text/css">
        .style1
        {
            width: 100%;
        }
        .style2
        {
            width: 92px;
        }
        .style3
        {
            width: 92px;
            text-align: right;
        }
        .style4
        {
            text-align: center;
            font-size: large;
        }
        .style5
        {
            text-align: left;
            font-size: medium;
        }
    </style>
</head>
<body>
<form id="form1" runat="server">
<p class="style4">
    <strong><em>Experiment: Comment Box for WebSite uing ASP.NET, C# and AJAX

</em></strong>

</p>
<p class="style5">
    <strong><em>Experiment:</em></strong> This experiment demonstrated a basic site 
    comment box for letting the comment and share theri views or ideas.</p>
<p class="style5">
    <strong><em>Documentation: </em></strong>

</p>
<p class="style5">
    This experiment uses LINQ to SQL for managing the databse of comments and users 
    entering the comments.

</p>
<p class="style5">
    Once the user enters the user name and comment in the box, it is validated for 
    username using ASP required field validator. Once the validation is done, the 
    user name and his/her comments are stored in the database and it is also 
    simultaneously seen in the coment box.</p>
<p class="style5">
    When you load the page for the first time, the comment database is iterated for 
    every comment and is represented in the comment box. Once the page is loaded and 
    you enter the comment, the comment is inserted into the database and using AJAX, 
    the new comment is added in the comment box&nbsp; with the timestamp, without refreshing the page again.</p>
<p class="style5">
    <strong><em>Implementation:</em></strong></p>
<p class="style5">
    Please Share your views about this experiment.</p>
<asp:ScriptManager ID="SM1" runat="server"> </asp:ScriptManager>
<asp:UpdatePanel ID="commentUpdate" runat="server">
<ContentTemplate>
<div id="commentSpace" runat="server" style="border-bottom: silver 1px solid; 
    text-align: left; border-left: silver 1px solid; 
    background-color: #f4f4f4; margin: 20px 0px 10px; 
    width: 58.7%;  
    max-height: 200px; overflow: auto; border-top: silver 1px solid; 
    border-right: silver 1px solid; padding-top:5px; padding-bottom:5px; padding-left: 5px; padding-right: 5px">
</div>
</ContentTemplate>
<Triggers>
<asp:AsyncPostBackTrigger ControlID="post" EventName="Click" />
</Triggers>
</asp:UpdatePanel>

    
    <table class="style1">
        <tr>
            <td class="style3">
                Name::</td>
            <td>
                <asp:TextBox ID="name" runat="server" Width="200px"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" 
                    ControlToValidate="name" ErrorMessage="Please Enter Your Name" 
                    Font-Italic="True" Font-Names="Aparajita" ForeColor="Red"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td class="style3">
               Comment:</td>
            <td>
                <asp:TextBox ID="comment" runat="server" EnableTheming="False" Height="80px" 
                    TextMode="MultiLine" Width="700px"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td class="style2">
                &nbsp;</td>
            <td>
                <asp:Button ID="post" runat="server" onclick="post_Click" Text="Post Comment" 
                    Width="97px" />
            </td>
        </tr>
    </table>
    </form>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/comment.aspx" target=_blank>Page Source </a></p>
    <p><strong><em>References:</em></strong><p>
    <a href="http://www.ccs.neu.edu/teaching/web/linq.htm" target="_blank">Prof. 
    Rasala&#39;s Site</a> for LINQ connection<p>
        <a href="http://msdn.microsoft.com/en-us/library/bb907622.aspx" target="_blank">
        MSDN</a> for LINQ with ASP.NET<p>
</body>
</html>

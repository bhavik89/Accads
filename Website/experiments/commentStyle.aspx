<%@ Page Language="C#" AutoEventWireup="true" CodeFile="commentStyle.aspx.cs" Inherits="experiments_commentStyle" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>

    <style type="text/css">
    
    body
{
 max-width:980px;
 width:auto;
 margin-left:auto;
 margin-right:auto;
} 
    
    .comment
    {
    max-width:70%;
    width:auto;
    margin-top:0.5%;
    border-radius:10px;
    padding-left:10px;
    padding-top:10px;
    padding-right:10px;
    padding-bottom:3%;
    height:auto;
    margin:auto;
        
  /*  height:120%; */
   /* background-color: #c0c0c0; */	

/* IE10 Consumer Preview */ 
background-image: -ms-linear-gradient(top, #DBDBDB 0%, #454545 100%);

/* Mozilla Firefox */ 
background-image: -moz-linear-gradient(top, #DBDBDB 0%, #454545 100%);

/* Opera */ 
background-image: -o-linear-gradient(top, #DBDBDB 0%, #454545 100%);

/* Webkit (Safari/Chrome 10) */ 
background-image: -webkit-gradient(linear, left top, left bottom, color-stop(0, #DBDBDB), color-stop(1, #454545));

/* Webkit (Chrome 11+) */ 
background-image: -webkit-linear-gradient(top, #DBDBDB 0%, #454545 100%);

/* W3C Markup, IE10 Release Preview */ 
background-image: linear-gradient(to bottom, #DBDBDB 0%, #454545 100%); 
    -moz-box-shadow: 0 0 5px 5px #888;
-webkit-box-shadow: 0 0 5px 5px#888;
box-shadow: 0 0 5px 5px #888;
        
}
  
  
      
      #hoverColor:hover
      {
         background-color:#FF0000;
      }
      
.comment-header {
font-size: 18px;
background-color: #f1f1f1;
border-bottom: 1px solid #e3e3e3;
padding: 5px;
}

 .comment-block {
margin-left: 65px;
position: relative;
border: 5px solid #e3e3e3;
border-radius: 8px;
}

.comment-content {
text-align: justify;
}
      
      
</style>
</head>
<body>
    <form id="form1" runat="server">
    <%--<div class="comment">
    <div style="width:20%; float:left;">--%>
    <%--<p class="comment">
    This is a comment asdkasdlkas asdlkasjdlkasd akdjlkasdjals kajdlkasd askdja askjaslkd laksjdkas dadnias d
    </p--%>
   
    <%--</div>
    <div style="clear:left";></div>
    </div>--%>


<%--<div id="hoverColor" >
<p >Hello world</p>
<p>Hello world</p>
<p>Hello world</p>
<p>Hello world</p>
<p>Hello world</p>
</div>--%>

<div class='comment-block'>
<div class='comment-header'>Comment Header</div>
<div class='comment-content'>Hello!! this is the comment content</div>
</div>


    </form>
</body>
</html>

<%--<%@ Page Language="C#" AutoEventWireup="true" CodeFile="aspformvalid.aspx.cs" Inherits="experiments_AspForm" %>--%>

<%@ Page Language="C#" %>
<%@ Import Namespace="edu.neu.ccis.bhavik.loginDetails"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script runat="server">

        
    protected void loadData(object sender, EventArgs e)
    {
        
        
        SQLData.Insert();
        name.Text = "";
        email.Text = "";
        pass.Text = "";

        accountCreated.Text = "Account successfully created!!";
                   
    }

    protected void Button2_Click(object sender, EventArgs e)
    {
        SQLData.Delete();
    }

    protected void Page_Load(object sender, EventArgs e)
    {

    }
</script>

<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
    <title>AspForm</title>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.validate.js" type="text/javascript"></script>
       

    <style type="text/css">
        
    #createAcc label.error {
	margin-left: 10px;
	width: auto;
	display: inline-block;
	color: Red;
    font-style:italic;
    text-align:left;
}

        .style1
        {
            width: 69%;
        }
        .style2
        {
            width: 142px;
        }

        .style3
        {
            font-size: large;
        }

        .style4
        {
            text-align: left;
            color: #0000FF;
        }

        </style>
    
    <script type="text/javascript">
        $(document).ready(function() {
            $("#createAcc").validate({
                rules: {
                    <%=name.UniqueID %>: {
                        minlength: 1,
                        required: true
                        },
                     <%=email.UniqueID %>: {                        
                        minlength: 4,
                        required: true,
                        email : true
                    },
                     <%=pass.UniqueID %>: {                        
                        minlength: 5,
                        required: true                        
                    }
//                     <%=passConf.UniqueID %>: {                        
//                        minlength: 5,
//                        required: true,
//                        equalto : "#<%=pass.UniqueID %>"
//                    }
                }, 
                messages: {
                    <%=name.UniqueID %>:{ 
                        required: "Please Enter Your Name" 
                        },

                     <%=email.UniqueID %>:{ 
                        required: "Please Enter Your Email Address", 
                        minlength: "Please Enter Your valid Email address",
                        email: "Please Enter Your valid Email address"
                    },
                    <%=pass.UniqueID %>: {                        
                        minlength: "Too Short",
                        required: "Please Enter the desired Password(Minimum length 5)"                        
                    }
//                     <%=passConf.UniqueID %>: {                        
//                        minlength: "Enter the password same as above",
//                        required: "Enter the password same as above",
//                        equalto : "Your both passwords doesnt match"
//                    }
                }
            });
        });
    </script>


</head>
<body>
 
    <form id="createAcc" runat="server">
    <p align="center" class="style3"><strong><em>Experiment: Login Form with SQL and LINQ</em></strong></p>
    <p><strong><em>Experiment:&nbsp; </em></strong></p>
    <p>Here a login for to create an account 
        and to sign in with the credentials is demonstrated.</p>
    <p><strong><em>Documentation: </em></strong>  </p>
    <p>Here, a simple asp signup form is created, with validation. The 
        data entered in the form is stored in the database using simple SQL Insert query.  </p>
    <p>Once Signed Up, You can login by clickin the link below the Sign Up form. This 
        will take you to another form for Signing In.  </p>
    <p>When you put the correct credentials in the SignIn form, you are authenticated 
        and a message with your Name in the database is displayed.</p>
    <p>The SignIn form uses LINQ to check each entries in the database against what you 
        have entered in the form. If the credentials are wrong, an error message is 
        displayed.</p>
    <p>To establish a LINQ connection, steps given by Prof. Rasala is followed up. The 
        method for credentials checking in LINQ is designed with the reference of TA 
        Rachna Chettri&#39;s experiment for Login Form
        <a href="http://net4.ccs.neu.edu/home/rachna8/Experiments/ASP/validateRecord.aspx" 
            target="_blank">
        here.</a></p>
    <p><strong><em>Experiment:</em></strong></p>
    <table class="style1">
        <tr>
            <td class="style4" colspan="2">
                <strong><em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                Sign Up form</em></strong></td>
        </tr>
        <tr>
            <td class="style2">

    <label for="name">
     Name:&nbsp;</label></td>
            <td>
     <asp:TextBox ID="name" MaxLength="30" runat="server" Width="200px" />
            </td>
        </tr>
        <tr>
            <td class="style2">
    <label for="email">
     Email Id:&nbsp;
     </label>
            </td>
            <td>
    <label for="email">
     <asp:TextBox ID="email" runat="server" Width="200px"></asp:TextBox>
     </label>
            </td>
        </tr>
        <tr>
            <td class="style2">
     <label for="pass">
     Password :&nbsp;</label></td>
            <td>
     <asp:TextBox ID="pass" runat="server" TextMode="Password" Width="200px" ></asp:TextBox>

            </td>
        </tr>
        <tr>
            <td class="style2">

     <label for="passConf">
     Confirm Password :&nbsp;
    </label>
            </td>
            <td>
     <asp:TextBox ID="passConf" runat="server" TextMode="Password" Width="200px"></asp:TextBox>
     <asp:CompareValidator ID="CompareValidator1" runat="server" 
        ControlToCompare="pass" ControlToValidate="passConf" 
        ErrorMessage="Password Does not match" ForeColor="Red" Font-Italic="True">Password Does not match</asp:CompareValidator>
            </td>
        </tr>
        <tr>
            <td class="style2">
    <asp:Button ID="add_btn" runat="server" Text="Sign Up" OnClick="loadData" />         
            </td>
            <td>
                &nbsp;</td>
        </tr>
    </table>
    <label for="name">
     &nbsp;</label><asp:SqlDataSource ID="SQLData" runat="server" 
        ConnectionString="<%$ ConnectionStrings:bhavikCS %>"
            
            SelectCommand="SELECT * FROM bhavik.[loginDetails]"
            InsertCommand="INSERT into bhavik.loginDetails (name, email, password)
            VALUES (@name, @email, @pass)"
            DeleteCommand="DELETE FROM bhavik.loginDetails WHERE name = @name and email = @email and password = @pass ">
        
            <InsertParameters>
            <asp:FormParameter Name="name" FormField="name"/>
            <asp:FormParameter Name="email" FormField="email"/>
            <asp:FormParameter Name="pass" FormField="pass"/>
          </InsertParameters>

          <DeleteParameters>
            <asp:FormParameter Name="name" FormField="name"/>
            <asp:FormParameter Name="email" FormField="email"/>
            <asp:FormParameter Name="password" FormField="pass"/>
        </DeleteParameters>

          </asp:SqlDataSource>


    <asp:Label ID="accountCreated" runat="server" Font-Bold="True" Font-Italic="True" 
        Font-Names="Calibri" Font-Size="Medium" ForeColor="Blue"></asp:Label>
    <br />


    <p>Have an account ? <a href="SignIn.aspx" target="_blank">SignIn Here</a>   </p>


    <p><a href="../fileview/Default.aspx?~/experiments/aspformvalid.aspx" target=_blank>Page Source</a></p>
    <p>
    &nbsp;<strong><em>References:</em></strong></p>
 <p><a href="http://www.ccs.neu.edu/teaching/web/linq.htm" target="_blank">Prof. 
    Rasala&#39;s Site</a> for LINQ connection</p>
        <p><a href="http://msdn.microsoft.com/en-us/library/bb907622.aspx" target="_blank">
        MSDN</a> for LINQ with ASP.NET</p>
       <p> <a href="http://net4.ccs.neu.edu/home/rachna8/Experiments/ASP/validateRecord.aspx" 
            target="_blank">Rachna Chettri&#39;s Experiment</a></p></form>
  
</body>
</html>

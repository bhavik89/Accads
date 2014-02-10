<%@ Page Language="C#" AutoEventWireup="true" CodeFile="AspForm.aspx.cs" Inherits="experiments_AspForm" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>AspForm</title>
    <script src="js/jquery.js" type="text/javascript"></script>
    <script src="js/jquery.validate.js" type="text/javascript"></script>
       

    <style type="text/css">
        
    #form1 label.error {
	margin-left: 10px;
	width: auto;
	display: inline-block;
	color: Red;
    font-style:italic;
    text-align:left;
}

        .style1
        {
            font-size: medium;
            text-align: left;
        }
        .style2
        {
            font-size: large;
            text-align: center;
        }

    </style>
    

    <script type="text/javascript">
        $(document).ready(function() {
            $("#form1").validate({
                rules: {
                    <%=num1.UniqueID %>: {
                        minlength: 1,
                        required: true,
                        number: true
                    },
                     <%=num2.UniqueID %>: {                        
                        minlength: 1,
                        required: true,
                        number: true
                    }
                }, messages: {
                    <%=num1.UniqueID %>:{ 
                        required: "Please Enter a Number", 
                        minlength: "Please Enter a Number",
                        Number: "Please Enter a Number"
                    },

                     <%=num2.UniqueID %>:{ 
                        required: "Please Enter a Number", 
                        minlength: "Please Enter a Number",
                        Number: "Please Enter a Number"
                    }
                }
            });
        });
    </script>
</head>
<body>
<p class="style2">
    <strong><em style="text-align: center">Experiment : Simple Calculator in 
    ASP.NET, C# and jQuery</em></strong></p>
    <p class="style1">
        <strong><em>Experiment:</em> </strong>

</p>
    <p class="style1">
        This experiment shows the simple combination of using ASP.net, C# and jQuery</p>
    <p class="style1">
        <strong><em>Documentation:</em></strong></p>
    <p class="style1">
        Over here, a simple Calculator is made. The elements of the form are asp 
        elements, each field of the form is validated using jQuery and the simple (add, 
        subtract, multiply and division) calulations are made using C# code.</p>
    <p class="style1">
        Validation are done on the Client side itself so as to avoid round-tour time to 
        validate the form using Serverside validation.</p>
    <p class="style1">
        The form elements are referenced by their respective IDs.

</p>
    <p class="style1">
        To reference an element in jQuery a special asp.net selector (&lt;%= 
        (elementID.UniqueID) %&gt;) is used to work with jQuery validation plugin.</p>
    <p class="style1">
        Simple calculations are performed on clicking of the required button. Functions 
        for performing the caluctions are written in C# file.</p>
    <p class="style1">
        <strong><em>Experiment :</em></strong></p>

 
    <form id="form1" runat="server">
    <label for="number1">
    Enter Number 1:&nbsp;
    </label>
     <asp:TextBox ID="num1" MaxLength="30" runat="server" /><br />
    <label for="number1">
     Enter Number 2:&nbsp;
    </label>
     <asp:TextBox ID="num2" runat="server"></asp:TextBox>
     <br />
     <br />
    <asp:Button ID="add_btn" runat="server" Text="ADD" 
         onclick="add_btn_Click" />
         &nbsp;&nbsp;&nbsp; &nbsp;<asp:Button ID="subtract" runat="server" 
         Text="Subtract" onclick="subtract_Click" />
     &nbsp;&nbsp;&nbsp; &nbsp;<asp:Button ID="multiply" runat="server" 
         Text="Multiply" onclick="multiply_Click" />
     &nbsp;&nbsp;&nbsp; &nbsp;<asp:Button ID="divide" runat="server" Text="Divide" 
         onclick="divide_Click" /> 
          &nbsp;<br />
          <p>
         <asp:Label ID="given" runat="server"></asp:Label>
         &nbsp
         <asp:Label ID="ans" runat="server"></asp:Label>
     </p>
    </form>

    <p>
        <strong><em>Source Code:</em></strong></p>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/AspForm.aspx" target="_blank">
        Page Source</a></p>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/AspForm.aspx.cs" target="_blank">C# file</a></p>
    <p>
        <strong><em>References:</em></strong></p>
    <p>
        <a href="https://www.lynda.com" target="_blank">Lynda</a></p>
    <p>
        <a href="https://msdn.microsoft.com" target="_blank">MSDN</a></p>

</body>
</html>

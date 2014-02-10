<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" Runat="Server">

</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" Runat="Server">
    <script type="text/javascript">
        $(document).ready(function() {
            $("#aspnetForm").validate({
                rules: {
                    <%=txtName.UniqueID %>: {
                        minlength: 2,
                        required: true
                    },
                     <%=txtEmail.UniqueID %>: {                        
                        required: true,
                        email:true
                    }
                }, messages: {
                    <%=txtName.UniqueID %>:{ 
                        required: "* Required Field *", 
                        minlength: "* Please enter atleast 2 characters *" 
                    }
                }
            });
        });
    </script>
    
    Name: <asp:TextBox ID="txtName" MaxLength="30" runat="server" /><br />
    Email: <asp:TextBox ID="txtEmail" runat="server"></asp:TextBox><br />
    <asp:Button ID="btnSubmit" runat="server" Text="Submit" />
</asp:Content>


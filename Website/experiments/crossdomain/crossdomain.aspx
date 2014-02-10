<%@ Page Language="C#" AutoEventWireup="true" CodeFile="crossdomain.aspx.cs" Inherits="experiments_crossdomain_crossdomain" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
    <script src=https://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js> 
    </script>
    <script src="crossDomain.js" type="text/javascript"></script>
    <script type="text/javascript">
         
        $.ajax({
            url: 'http://net4.ccs.neu.edu/home/rasala/Default.aspx',
            type: 'GET',
            contentType: 'application/html',
            success: function (res) {
                var headline = $(res.responseText).text();
                alert(headline);
            },
            error: function (e) {
                alert(e);
            }
        });
 
    
    
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <div>
    
    </div>
    </form>
</body>
</html>

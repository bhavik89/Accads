<%@ Page Language="C#" %>

<!DOCTYPE html>

<%--<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>
--%>
<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">

    $().ready(function () {

        $("#form1").validate({
            rules: {
                dividend: {
                    required: true,
                    number: true
                },


                divisor: {
                    required: true,
                    number: true
                }
            },
            messages: {
                dividend: {
                    required: "Please enter a number",
                    number: "Please enter only integers"
                },

                divisor: {
                    required: "Please enter a number",
                    number: "Please enter only integers"
                }
            }
        });

        $("#divide").click(function () {

            var dividend = $('#dividend').val();
            var divisor = $('#divisor').val();

            $('#quotient').html(Math.floor(dividend / divisor));
            $('#remainder').html(dividend % divisor);

        });

    });

    

</script>
<html>
<head runat="server">
    <title>Division</title>
    <style type="text/css">
 
 #form1 label.error {
	margin-left: 10px;
	width: auto;
	display: block;
	color: Red;
    font-style:italic;
    text-align:left;
}
            
        .style1
        {
            text-align: left;
        }
        .style2
        {
            font-size: x-large;
        }
        #Button1
        {
            width: 80px;
        }
    </style>
</head>
<body>
    <form id="form1">

    <div>
   
        <p class="style1">
            <strong>Integer Division</strong></p>
        <p class="style1">
            &nbsp;</p>
        <p class="style1">
        <label for="dividend"></label>
            <input id="dividend" type="text" placeholder="Dividend" />&nbsp;<strong><span class="style2">&nbsp;
                &#247&nbsp; </span></strong>
        <label for="divisor"></label>
            <input id="divisor" type="text" placeholder="Divisor" />
            = <span id="quotient"></span>
        </p>
        <p class="style1">
            Remainder: <span id="remainder"></span>&nbsp;</p>

            <p class="style1">
        <input id="divide" type="button" value="Divide" /></p>
    </div>
  
    </form>
    
</body>
</html>

<%@ Page Language="C#" AutoEventWireup="true" CodeFile="form.aspx.cs" Inherits="experiments_form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>jQuery form validation</title>


<%--<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>--%>

<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="//ajax.aspnetcdn.com/ajax/jquery.validate/1.11.1/jquery.validate.js"></script>

<script type="text/javascript">

    $.validator.setDefaults({
        submitHandler: function () { alert("You have submitted your Info."); }
    });

    $().ready(function () {

        // validate signup form
        $("#signupForm").validate({
            rules: {
                firstname: {
                    required: true,
                    minlength: 2
                },

                lastname: "required",

                username: {
                    required: true,
                    minlength: 2
                },

                password: {
                    required: true,
                    minlength: 5
                },
                confirm_password: {
                    required: true,
                    minlength: 5,
                    equalTo: "#password"
                },
                email: {
                    required: true,
                    email: true
                },

                phone: {
                    number: true,
                    minlength: 10,
                    maxlength: 10
                },
                //	topic: {
                //required: "#Intrests:checked",
                //minlength: 1
                //       },
                agree: "required"
            },
            messages: {
                firstname:
            {
                required: "Please enter your firstname",
                minlength: "Your FirstName should be atleast 2 character long"
            },

                lastname: "Please enter your lastname",
                username: {
                    required: "Please enter a username",
                    minlength: "Your username must consist of at least 2 characters"
                },
                password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long"
                },
                confirm_password: {
                    required: "Please provide a password",
                    minlength: "Your password must be at least 5 characters long",
                    equalTo: "Please enter the same password as above"
                },

                phone:
{
    number: "Enter your 10 digit phone number",
    minlength: "Enter your 10 digit phone number",
    maxlength: "Enter your 10 digit phone number"
},

                topic:
{
    required: "Please select atleast one option",
    minlength: "Please select atleast one option"
},
                email: "Please enter a valid email address",
                agree: "Please accept the aggrement"
            }
        });

        // Create a username by combining firstname and lastname
        $("#username").focus(function () {
            var firstname = $("#firstname").val();
            var lastname = $("#lastname").val();
            if (firstname && lastname && !this.value) {
                this.value = firstname + "." + lastname;
            }
        });

        //code to hide topic selection
        var Intrests = $("#Intrests");

        // Intrests topics are optional, hide at first
        var inital = Intrests.is(":checked");
        var topics = $("#Intrests_topics")[inital ? "removeClass" : "addClass"]("gray");
        var topicInputs = topics.find("input").attr("disabled", !inital);

        Intrests.click(function () {
            topics[this.checked ? "removeClass" : "addClass"]("gray");
            topicInputs.attr("disabled", !this.checked);
        });
    });
</script>

<style type="text/css">
    
label
{
display:inline-block;
width:150px;
}    

#signupForm { width: 700px; }

#signupForm label.error {
	margin-left: 10px;
	width: auto;
	display: block;
	color: Red;
    font-style:italic;
    text-align:left;
}
#Intrests_topics label.error 
{
    text-align:left;
	display: inline-block;
	/*margin-left: 103px;*/
}
    .style1
    {
        text-align: left;
    }
    .style2
    {
        text-align: center;
    }
</style>

</head>
<body>

<div id="main">

<form id="signupForm" method="get" action="">
	<fieldset>
		<legend>This form will be Validated using jQuery </legend>
		<p>
			<label for="firstname"></label>
			<input id="firstname" name="firstname" type="text" />
		</p>
		<p>
			<label for="lastname"></label>
			<input id="lastname" name="lastname" type="text"/>
		</p>
		<p>
			<label for="username"></label>
			<input id="username" name="username" type="text" />
		</p>
		<p>
			<label for="password"></label>
			<input id="password" name="password" type="password" />
		</p>
		<p>
			<label for="confirm_password">Confirm password</label>
			<input id="confirm_password" name="confirm_password" type="password" />
		</p>
		<p>
			<label for="email"></label>
			<input id="email" name="email" type="email" />
		</p>

        <p>
			<label for="email"></label>
			<input id="phone" name="phone" type="number" />
		</p>

		
		<p>
			<input class="submit" type="submit" value="Submit"/>
		</p>
	</fieldset>
</form>
</div>


</body>
</html>

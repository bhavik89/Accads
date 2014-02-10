<%@ Page Language="C#" AutoEventWireup="true" CodeFile="form.aspx.cs" Inherits="experiments_form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>jQuery form validation</title>

<style>
     
    
    </style>


<script src="js/jquery.js" type="text/javascript"></script>
<script src="js/jquery.validate.js" type="text/javascript"></script>

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
<h3 class="style2"> <em>Experiment : Form validation with jQuery Plugin </em> </h3>
    <p class="style1"> <strong><em>Experiment:</em></strong></p>
    <p class="style1"> This experiment is about the validating of a signup form using 
        jQuery plugin.</p>
    <p class="style1"> <strong><em>Documentation:</em></strong></p>
    <p class="style1"> In this experiment, the validation have made use of the jQuery 
        validate plugin available online.</p>
    <p class="style1"> Here, we created a normal html form having ID for each field then 
        using these IDs of all the filelds in javascript to address each field.</p>
    <p class="style1"> Here, for validating the form, a jQuery plugin has been dloaded 
        and installed in the file.</p>
    <p class="style1"> We set certain rules for each IDs tags and also give the messages 
        for all the rules if the are violated.</p>
    <p class="style1"> Ex. of some rules are : </p>
    <p class="style1"> <code>required: </code> Indicates this field is required</p>
    <p class="style1"> <code>minlength:</code> Indicates the required minimum length of the field.</p>
    <p class="style1"> The following rules are set for these fields:</p>
    <p class="style1"> First Name : required, minlength : 1;</p>
    <p class="style1"> Last Nmae: required</p>
    <p class="style1"> User Name: required , user name is initiallly auto generated&nbsp; 
        in the given format : &quot;Firstname&quot; + &quot;.&quot; + &quot;LastName&quot;</p>
    <p class="style1"> Password : Should be atleast 5 character long and confirm 
        password must match the given password</p>
    <p class="style1"> Email: required and should be a valid email address</p>
    <p class="style1"> Phone: optional and validated for 10 digit number</p>
    <p class="style1"> One must agree the policy by checking the checkbox</p>
    <p class="style1"> If one checks to subscribe to mails, he/she will get the option 
        to select his/her interests.</p>
    <p class="style1"> <em><strong>References: </strong></em> </p>
    <p class="style1"> jQuery plugin downloaded from
        <a href="http://bassistance.de/jquery-plugins/jquery-plugin-validation/">here</a></p>
    <p class="style1"> <a href="https://wwww.w3schools.com" target="_blank">w3schools.com</a></p>
    <p class="style1"> <a href="http://www.roseindia.net/" target="_blank">RoseIndia</a></p>
    <p class="style1"> <a href="../fileview/Default.aspx?~/experiments/form.aspx" target="_blank">
            Page Source</a></p>
    <p class="style1"> <a href="../fileview/Default.aspx?~/experiments/js/jquery.js" target="_blank">jQuery.js</a>
    &nbsp;&nbsp;&nbsp;<a href="../fileview/Default.aspx?~/experiments/js/jquery.validate.js" target="_blank"> jquery.validate.js</a></p>
    <p class="style1"> <strong><em>Experiment:</em></strong></p>

<div id="main">

<form id="signupForm" method="get" action="">
	<fieldset>
		<legend>This form will be Validated using jQuery </legend>
		<p>
			<label for="firstname">First Name</label>
			<input id="firstname" name="firstname" type="text" />
		</p>
		<p>
			<label for="lastname">Last Name</label>
			<input id="lastname" name="lastname" type="text"/>
		</p>
		<p>
			<label for="username">Username</label>
			<input id="username" name="username" type="text" />
		</p>
		<p>
			<label for="password">Password</label>
			<input id="password" name="password" type="password" />
		</p>
		<p>
			<label for="confirm_password">Confirm password</label>
			<input id="confirm_password" name="confirm_password" type="password" />
		</p>
		<p>
			<label for="email">Email</label>
			<input id="email" name="email" type="email" />
		</p>

        <p>
			<label for="email">Phone(optional)</label>
			<input id="phone" name="phone" type="number" />
		</p>

		<p>
			<label for="agree">Please agree to our policy</label>
			<input type="checkbox" class="checkbox" id="agree" name="agree" />
		</p>
		<p>
			<label for="Intrests" style="width:200px"> I'd like to subscribe to mails</label>
			<input type="checkbox" class="checkbox" id="Intrests" name="Intrests" />
		</p>
		<fieldset id="Intrests_topics">
			<legend>Select at least two - of your Interest</legend>
			<label for="label_sports">
				<input type="checkbox" id="sports" value="sports" name="topic" />
				Sports
			</label>
			<label for="label_news">
				<input type="checkbox" id="news" value="news" name="topic" />
				News
			</label>
			<label for="label_tech">
				<input type="checkbox" id="tech" value="tech" name="topic" />
				Technology
			</label>
			
		</fieldset>
		<p>
			<input class="submit" type="submit" value="Submit"/>
		</p>
	</fieldset>
</form>
</div>


</body>
</html>

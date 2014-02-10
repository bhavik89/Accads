<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Dialog</title>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.8.3.js"></script>
<script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script>


<style type="text/css">
    
label
{
display:inline-block;
width:150px;
}    

#signupForm { width: 600px; }

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
        text-align: center;
    }
    .style2
    {
        text-align: left;
    }
    </style>

<script>
//Script for Date Picker
    $(function () {
        $("#datepicker").datepicker();
    });
</script>

 <script>

 //Script for auto complete
     $(function () {
         var CountryList = [
"Afghanistan",
"Albania",
"Argentina ",
"Australia",
"Austria ",
"Bahamas",
"Bangladesh",
"Belgium",
"Bhutan",
"Brazil",
"China",
"Colombia",
"Denmark",
"Egypt",
"Finland",
"France",
"Gremany",
"Greece",
"Hong Kong",
"Iceland",
"India",
"Italy",
"Japan",
"Macau",
"Malaysia",
"Nepal",
"New Zealand",
"Panama",
"Poland",
"Russia",
"Singapore",
"Sri Lanka",
"United Arab Emirates",
"United States",
"Zimbabwe"
];
         $("#Countries").autocomplete({
             source: CountryList
         });
     });
</script>

 <script>

 //Script for slider
     $(function () {
         $("#slider-range").slider({
             orientation: "horizontal",
             range: true,
             max: 1000,
             min:0,
             values: [10, 500],
             slide: function (event, ui) {
                 $("#amount").val("$" + ui.values[0] + " - $" + ui.values[1]);
             }
         });
         $("#amount").val("$" + $("#slider-range").slider("values", 0) +
" - $" + $("#slider-range").slider("values", 1));
     });
</script>

<script>
//Script for login form

    $(document).ready(function () {


        $("#login").click(function () {
            $("#D_Box").dialog({
                width: 700,
                height: 550,
                modal: true,
                buttons: [
                {
                    text: "Submit",
                    click: function () {
                        alert('Your info has been submitted');
                    }
                },
                {
                    text: "Cancel",
                    click: function () {
                        $(this).dialog('close');
                    }
                }
                ]

            });
        });

        $("#signupForm").validate({
            rules: {
                firstname: {
                    required: true,
                    minlength: 2
                },

                messages: {
                    firstname:
            {
                required: "Please enter your firstname",
                minlength: "Your FirstName should be atleast 2 character long"
            }
                }
            }
        });

       });
</script>

</head>
<body>

<h3 class="style1"> <em>Experiment : UIs in Jquery </em> </h3>
    <p> <strong><em>Experiment:</em></strong> This experiment is about creating the 
        different User Interfaces using jQuery</p>
    <p> <strong><em>Documentation: </em></strong></p>
    <p> jQuery UIs are used for creating an highly user interactive Web Applications 
        with different effects and styles. This enables user to navigate through web 
        sites easily.</p>
    <p> Diferent types of UIs are Dialog box, Slider, Autocomplete, Datepicker, Buttons, 
        Menubar, Tabs etc. In this expriment Some of the UIs are demonstrated.</p>
    <p> The main jQuery UI library is available online on jQueryui.com, you can download 
        it or can provide a link for that library in javascript <code>src</code>. The 
        description of the UIs demonstrated is given as under.</p>
    <p> <strong><em>Dialog Box:</em></strong></p>
    <p> Dialog boxes can also used as alert to warnings, or can even be used to display 
        some information or take user decisions. Over here, a signup form is displayed 
        in the dialog box.</p>
        <p>PS: The form over here is not validated as it is demonstrated in earlier experiment.</p>

          
  <a href="#" id="login">Sign Up Form</a>

 <div id="D_Box" title="Sign Up Form" style="display:none">
 <form id="signupForm" method="get" action="">
	<fieldset>
		<legend>This form Appears in the Dialog box </legend>
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
			<label for="phone">Phone(optional)</label>
			<input id="phone" name="phone" type="number" />
		</p>

		<p>
			<label for="agree">Please agree to our policy</label>
			<input type="checkbox" class="checkbox" id="agree" name="agree" />
		</p>
		
			
		<%--</fieldset>
		<p>
			<input class="submit" type="submit" value="Submit"/>
		</p>
	</fieldset>--%>
</form>

   </div>

<div style="display:inline-block">    

</div>
</form>
  
    <br />
    <br />
    <p>
    <strong><em>Datepicker:
    <br />
    </p>
    </em></strong>
    <p> A date picker opens an interactive calendar in a small overlay. The date is 
        picked by clickin on the appropriate date from the calender appeared.</p>
  
<p>Date: <input type="text" id="datepicker" />
</p>

<p><em><strong>Auto Complete:</strong></em></p>
    <p>The Autocomplete UI provides suggestions while you type into the field. Here it 
        gives the sugesstions of the countries while you are entering the name of your 
        country.</p>
<p>Enter your country: <input id="Countries" />
</p>
    <p><strong><em>Slider:</em></strong></p>
    <p>A slider provides a range of functions. A slider can move by the mouse pointer or 
        it is most useful on touch devices to scroll or select some values form the 
        range of values provided. A slider can either be a range selecter or the upper 
        limit selector. Here, a slider to select the range of values is provided.</p>

 <p style="display:inline-block">Enter your Buying range in $:
<input type="text" id="amount" style="border:0px; color: red; font-weight: bold;" />
</p>

<div id="slider-range" style="height: 12px; width: 100px; margin-left:30px"></div>
<br>

   <p class="style2"> <a href="../fileview/Default.aspx?~/experiments/jQuery_UI.aspx" target="_blank">
            Page Source</a></p>

    <p>References</p>
    <a href="https://www.jqueryui.com">jQuery UI </a>
    <br />
    <a href="https:www.jquery4u.com">jQuery4U</a><br />
    <a href="http://jream.com">Video Tutorials here</a>
</body>
</html>

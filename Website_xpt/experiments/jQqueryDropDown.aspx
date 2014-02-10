<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>jQueryDropDown</title>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {

            $('.nav').hover(

function () {

    $('ul', this).fadeIn();
},

function () {

    $('ul', this).fadeOut();
}
);
        });
    </script>
    <style type="text/css">
        .nav ul
        {
            position: relative;
            display: none;
            list-style: none;
        }
        
        .nav ul li
        {
            text-align: right;
        }
        
        .style1
        {
            text-decoration: none;
        }
        
        .style2
        {
            font-size: x-large;
        }
        .style4
        {
            text-align: center;
        }
        
        .style5
        {
            text-align: left;
        }
        
        .style6
        {
            text-align: left;
            font-family: "Times New Roman" , Times, serif;
        }
        .style7
        {
            font-family: "Times New Roman" , Times, serif;
        }
        .style8
        {
            font-family: "Times New Roman" , Times, serif;
            font-size: large;
        }
        .style9
        {
            text-align: left;
            font-family: "Times New Roman" , Times, serif;
            font-size: large;
        }
        .style10
        {
            font-size: large;
        }
    </style>
</head>
<body>
    <h4 class="style4">
        <em><span class="style2">Experiment: jQuery Dropdown menu
    </h4>
    </span></em>&nbsp;
    <p class="style9">
        <strong><em>Experiment:</em></strong></p>
    <p class="style9">
        This experiment demonstrates the simple dropdown menu on mouse over using jQuery</p>
    <p class="style9">
        <strong><em>Documentation:</em></strong></p>
    <p class="style9">
        This experiment uses simple jquery properties for having mouse over events. Some
        are listed as below :</p>
    <code>$(&#39;.nav&#39;).hover</code><span class="style8">: Enter this function when
        mouse enters over </span><code><span class="style8">nav</span></code><span class="style8">
            class.</span></p>
    <p class="style5">
        <code>$(&#39;ul&#39;, this).fadeIn()</code><span class="style8">: fadeIn shows the sub-menu
            of selected (</span><code><span class="style8">this</span></code><span class="style8">)
                element</span></p>
    <p class="style5">
        <code>$(&#39;ul&#39;, this).fadeOut()</code><span class="style8">: fadeOut lets the
            sub-meu to disappear when mouse is out of the selected (</span><code><span class="style8">
                this</span></code><span class="style8">) element</span></p>
    <p class="style6">
        <p class="style9">
            <a href="../fileview/Default.aspx?~/experiments/jQqueryDropDown.aspx" target="_blank">
                Page Source</a></p>
        <em><strong><span class="style10">Experiment :</span></strong></em></p>
    <ul style="float: left; list-style: none; width: 282px; display: inline">
        <li style="width: 223px" class="style7"><a href="#" class="style1">This does not drop</a></li>
        <li class="nav" style="width: 227px; background-color: Gray;"><span class="style7"><a
            href="#" class="style1">This will drop on mouse over</a></span>
            <ul>
                <li class="style7"><a href="#" class="style1">Link_1</a></li>
                <li class="style7"><a href="#" class="style1">Link_2</a></li>
                <li class="style7"><a href="#" class="style1">Link_3</a></li>
                <li class="style7"><a href="#" class="style1">Link_4</a></li>
                <li class="style7"><a href="#" class="style1">Link_5</a></li>
            </ul>
        </li>
        <li class="nav" style="width: 230px"><span class="style7"><a href="#" class="style1">
            This will also drop on mouse over</a></span>
            <ul>
                <li class="style7"><a href="#" class="style1">Link_1</a></li>
                <li class="style7"><a href="#" class="style1">Link_2</a></li>
                <li class="style7"><a href="#" class="style1">Link_3</a></li>
                <li class="style7"><a href="#" class="style1">Link_4</a></li>
                <li class="style7"><a href="#" class="style1">Link_5</a></li>
            </ul>
        </li>
    </ul>

    <p>
        <a href="../fileview/Default.aspx?~/experiments/jQqueryDropDown.aspx" target="_blank">Page Source</a></p>

</body>
</html>

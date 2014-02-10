<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<title>TabbedContent</title>

<style type="text/css">
body
{
 max-width:980px;
 width:auto;
 margin-left:auto;
 margin-right:auto;
}    

#tabs ul {
padding: 0px;
margin-left: 10px;
list-style-type: none;
        width: 518px;
        margin-right: 0px;
        margin-top: 0px;
        margin-bottom: 0px;
    }

#tabs ul li {
display: inline-block;
clear: none;
float: left;
height: 24px;
}

#tabs ul li a {
position: relative;
margin-top: 16px;
display: block;
margin-left: 6px;
line-height: 24px;
padding-left: 10px;
background: #f6f6f6;
z-index: 9999;
border: 1px solid #ccc;
border-bottom: 0px;
-moz-border-radius-topleft: 4px;
border-top-left-radius: 4px;
-moz-border-radius-topright: 4px;
border-top-right-radius: 4px;
width: 130px;
color: #000000;
text-decoration: none;
font-weight: bold;
}

#tabs ul li a:hover {
text-decoration: underline;
}

#tabs #Content_Area {
padding: 0 15px;
clear:both;
overflow:hidden;
line-height:19px;
position: relative;
top: 20px;
z-index: 5;
height: 150px;
overflow: hidden;
background-color: Gray;
width:500px;

}

p { padding-left: 15px; 
     max-width:900px;
    }
    .style1
    {
        font-size: x-large;
        text-align: center;
    }
    .style2
    {
        font-size: medium;
    }
</style>

<script type="text/javascript">
    function tab(tab) {
        document.getElementById('tab1').style.display = 'none';
        document.getElementById('tab2').style.display = 'none';
        document.getElementById('li_tab1').setAttribute("class", "");
        document.getElementById('li_tab2').setAttribute("class", "");
        document.getElementById(tab).style.display = 'block';
        document.getElementById('li_' + tab).setAttribute("class", "active");
    }
</script>

</head>

<body>
<p class="style1"><strong><em>Experiment: Tabbed page layout</em></strong></p>
    <p><strong><em><span class="style2">Experiment:</span></em></strong><span 
            class="style2"> This experiment demonstrates the simple tabbed page layout giving 
        cool effect to the page layout.</span></p><hr />
    <p class="style2"><strong><em>Documentation:</em></strong></p>
    <p class="style2">Tabbed content is a great way to handle this issue and has been widely used on 
        blogs recently. This experiment is designed to be used for the project to 
        display the list of places found and the directions obtained in a single div, 
        and also to preserve the contents of each tab while other is viewed.</p>
    <p class="style2">A simple javascript and CSS helped to create the tabbed layout. The most important 
        CSS property : z-index is used to display the contents of the current tab. When 
        the tab is selected, it&#39;s z-index is maximised so as to bring it&#39;s content in 
        front of other tabs.</p>
        <hr />
        <p class="style2">
            <strong><em>Experiment Demo: </em></strong>
        </p>
<div id="tabs">
<ul>
<li id="li_tab1" onclick="tab('tab1')" onmouseover="this.style.cursor='pointer'"><a>Tab 1</a></li>
<li id="li_tab2" onclick="tab('tab2')" onmouseover="this.style.cursor='pointer'"><a>Tab 2</a></li>
</ul>
<div id="Content_Area">
<div id="tab1">
<p>Contents of Tab 1:</p>
<p>Hi you are currently viewing the tab 1 contents. To view the contents of tab 2, please click above on tab 2</p>
</div>

<div id="tab2" style="display: none;">
<p>Contents of Tab 2:</p>
<p>What you wanna visit ?</p>
<ul>
<li><a href="https://www.google.com/">Google</a></li>
<li><a href="https://www.facebook.com/">facebook</a></li>
<li><a href="https://www.youtube.com/">Youtube</a></li>
</ul>
</div>
</div>
</div>
&nbsp
<p>
<a href="../fileview/Default.aspx?~/experiments/tabbedContent.aspx" target="_blank">
    <span class="style2"><strong><em>Page Source</em></strong></span></a><span 
        class="style2"> </span>
</p>
<p class="style2"><strong><em>References:</em></strong></p>

<p class="style2"><a href="http://net.tutsplus.com/tutorials/html-css-techniques/how-to-create-a-slick-tabbed-content-">Tabbed page layout tutorial</a></p>
<p class="style2"><a href="https://www.w3schools.com">w3school</a></p>

</body>
</html>
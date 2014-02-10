<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="experiments_Default" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>FlipAnimation</title>
    <link href="css/flipanimation.css" rel="stylesheet" type="text/css">
    
    <style type="text/css">
        .style1
        {
            text-decoration: none;
        }
        .style2
        {
            font-family: "Times New Roman" , Times, serif;
            font-size: large;
            text-align: left;
        }
        .style3
        {
            font-family: "Times New Roman" , Times, serif;
            font-size: x-large;
            text-align: center;
        }
        .style4
        {
            font-size: large;
        }
    </style>
</head>
<body>
    <form id="form1" runat="server">
    <p class="style3">
        <strong><em>Experiment : Playing with CSS for button animation </em></strong>
    </p>
    <p class="style2">
        <strong><em>Experiment:</em></strong></p>
    <p class="style2">
        This experiment, demonstrates the flip-button and drop-button effect using
        CSS properties for link menu.</p>
    <p class="style2">
        <strong>Documentation:</strong></p>
    <p class="style2">
        Various CSS properties used :
    </p>
    <ul>
        <li>
            <p class="style2">
                transform: Used to create a flip like rotate animation</p>
        </li>
        <li>
            <p class="style2">
                border-radius: To smoothen the edges of the button
            </p>
        </li>
        <li>
            <p class="style2">
                transition: This is used to add the changing style effect, and adding time property
                to animation</p>
        </li>
        <li>
            <p class="style2">
                gradient-effect : This is used to give the gradient coloring to buttons.</p>
        </li>
    </ul>
    <p>
        <strong><span class="style4">Experiment:</span> </strong>
    </p>
    <p>
        Flipping buttons</p>
    <ul style="display: inline-block; text-align:center">
        <li style="display: inline-block; text-align: center"><a href="http://www.google.com"
            target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    
                        <div class="front" style="position:absolute">
                            Google
                        </div>
                        <div class="back" style="position:absolute">
                            Google
                        </div>
                    
                </div>
            </div>
        </a></li>
        <li style="display: inline-block; text-align: center"><a href="http://www.facebook.com"
            target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    <div class="front"  style="position:absolute">
                        facebook
                    </div>
                    <div class="back"  style="position:absolute">
                        facebook
                    </div>
                </div>
            </div>
        </a></li>
        <li style="display: inline-block; text-align: center"><a href="http://www.yahoo.com"
            target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    <div class="front"  style="position:absolute">
                        Yahoo
                    </div>
                    <div class="back"  style="position:absolute">
                        Yahoo
                    </div>
                </div>
            </div>
        </a></li>
        <li style="display: inline-block; text-align: center"><a href="http://www.wikipedia.org"
            target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    <div class="front"  style="position:absolute">
                        Wikipedia
                    </div>
                    <div class="back"  style="position:absolute">
                        Wikipedia
                    </div>
                </div>
            </div>
        </a></li>
    </ul>

    <p>
        Flipping + Dropdown buttons</p>
    <ul style="display: inline-block; text-align:center">
        <li style="display: inline-block; text-align: center"><a href="http://www.google.com"
            target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    
                        <div class="front"  style="position:relative">
                            Google
                        </div>
                        <div class="back" style="position:relative">
                            Google
                        </div>
                    
                </div>
            </div>
        </a></li>
        <li style="display: inline-block; text-align: center"><a href="http://www.facebook.com"
            target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    <div class="front" style="position:relative">
                        facebook
                    </div>
                    <div class="back" style="position:relative">
                        facebook
                    </div>
                </div>
            </div>
        </a></li>
        <li style="display: inline-block; text-align: center"><a href="http://www.yahoo.com"
            target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    <div class="front" style="position:relative">
                        Yahoo
                    </div>
                    <div class="back" style="position:relative">
                        Yahoo
                    </div>
                </div>
            </div>
        </a></li>
        <li style="display: inline-block; text-align: center">
        <a href="http://www.wikipedia.org" target="_blank" class="style1">
            <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                <div class="flipper">
                    <div class="front" style="position:relative">
                        Wikipedia
                    </div>
                    <div class="back" style="position:relative">
                        Wikipedia
                    </div>
                   
                </div>
            </div>
        </a></li>
    </ul>

    </form>
    <p style="height: 1px">
        &nbsp;</p>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/Default.aspx" target="_blank">
        Page Source</p></a>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/CSS/flipanimation.css" target="_blank">
        CSS Code</a></p>
</body>
</html>

﻿<%@ Page Language="C#" AutoEventWireup="true" CodeFile="home.aspx.cs" Inherits="home" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Bhavik</title>
    <link rel="shortcut icon" href="Images/icons/page_icon.ico" type="image/x-icon" />
    <link rel="Stylesheet" type="text/css" href="css/home_expt.css" />
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script type="text/javascript">

        $(document).ready(function () {

            $('.nav').hover(

function () {

    $('li', this).fadeIn();
},

function () {

    $('li', this).fadeOut();
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
        
        
       a.glow, a.glow:hover, a.glow:focus
{
	text-decoration: none;
	color: #333;
	-webkit-transition: 300ms linear 0s;
	-moz-transition: 300ms linear 0s;
	-o-transition: 300ms linear 0s;
	transition: 300ms linear 0s;
	
}

a.glow:hover, a.glow:focus
{
	color: gray;
	text-shadow: -1px 1px 8px #ffc, 1px -1px 8px #fff;
}
        .style2
        {
            text-decoration: none;
        }
               
        
        
        .style4
        {
            font-family: "Copperplate Gothic Bold";
        }
         
         .style5
        {
            font-family: "papyrus";
            font-size:95%;
        }      
        
        .style6
        {
            color: #000000;
        }
        
        </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <em>Bhavik Gandhi </em>
        </div>
        <ul style:"text-align:center;" class="style5">
            <li><a href="source/" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute">
                            Source
                        </div>
                        <div class="back" style="position: absolute">
                            Source
                        </div>
                    </div>
                </div>
            </a></li>
            <li><a href="statistics/" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute">
                            Statistics
                        </div>
                        <div class="back" style="position: absolute">
                            Statistics
                        </div>
                    </div>
                </div>
            </a></li>
            <li><a href="sitestatistics/" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute;">
                            SiteStatistics
                        </div>
                        <div class="back" style="position: absolute">
                            SiteStatistics
                        </div>
                    </div>
                </div>
            </a></li>
            <li><a href="search/" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute">
                            Search
                        </div>
                        <div class="back" style="position: absolute">
                            Search
                        </div>
                    </div>
                </div>
            </a></li>
            <li><a href="filelist.aspx" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute">
                            FileList
                        </div>
                        <div class="back" style="position: absolute">
                            FileList
                        </div>
                    </div>
                </div>
            </a></li>
            <li><a href="autofile.aspx" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute">
                            AutoFile
                        </div>
                        <div class="back" style="position: absolute">
                            AutoFile
                        </div>
                    </div>
                </div>
            </a></li>
            <li><a href="images/autoimage.aspx" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute">
                            Images
                        </div>
                        <div class="back" style="position: absolute">
                            Images
                        </div>
                    </div>
                </div>
            </a></li>
            <li><a href="blog/" target="_blank">
                <div class="flip-container" ontouchstart="this.classList.toggle('hover');">
                    <div class="flipper">
                        <div class="front" style="position: absolute">
                            Blog
                        </div>
                        <div class="back" style="position: absolute">
                            Blog
                        </div>
                    </div>
                </div>
            </a></li>
        </ul>

<div class="profile_left">
<a href="#"><img src = "Images/noback.png" width=90%;  height=90%; title="Bhavik Gandhi"> </a></img>
<br>
<ul style="text-align: left; text-decoration:none; letter-spacing:3px; font-size:95%">
    <span class="style4">My Work</span>

</br>
<li class="body_font"  style="text-align: left; text-decoration:none; letter-spacing:3px; font-size:95%">
<strong><a href="default.aspx" target="_blank" class="glow">Project</a> </strong></li>

    <li  class="body_font"  style="text-align: left; text-decoration:none; letter-spacing:3px; font-size:95%">
    <strong><a href="story/index.htm?../experiments/story.txt" target="_blank" 
            class="glow">Experiments</a></strong></li>
            <li  class="body_font"  style="text-align: left; text-decoration:none; letter-spacing:3px; font-size:95%">
            <strong><a href="story/index.htm?../experiments/story.txt" target="_blank" 
            class="glow">References</a></strong></li>
    </ul>
</div>

<div class="profile_right body_font">

 <p>Hi, This is Bhavik Gandhi, graduate student at graduate student at 
        <em><a class=" glow style2" href="http://www.neu.edu"> <span class="style6">Northeastern University</span></a></em> pursuing my major in 
     <a class=" glow style2" href="http://www.ccs.neu.edu"> <span class="style6"> 
     <em>Computer Science</em> </span> </a>  
        
      <p >
          I have Designed this web page as a part of the Web Development course under the guidance of prof. Richard Rasala and Prof. Jose AnnunZiato
          
      </p>

      <p>
         Here is a link to the
<a class=" glow style2" href="http://www.northeastern.edu/rasala/webstories.htm"
        target="_blank" style="text-decoration:none"><em> <span class="style6"> Web Development Stories</em></a> </span>
so that you may see a good collection of online documentation.

</p>

<p>
    This provides a model for using stories for documentation and
for collections of&nbsp experiments.</p>
    <p>
    &nbsp;</p>
    <p>
        Note: This page is under going construction, this page may not be available 
        sometimes.

</p>


</div>

        <div id="bottom-menu" class="body_font">
            <ul id="navmenu" style="position: relative;">
                <li>Thanks,</li>
                <li>Bhavik Gandhi</li>
                <li>Contact:
       <a href="mailto:bhavik@ccs.neu.edu" style:"color=white" class="style2">bhavik@ccs.neu.edu
       </a></li>
                <li><a href="https://www.facebook.com/Gandhi.Bhavik" target="_blank" class="style2">
                    <img src="Images/icons/facebook.png" height="25px" width="25px" title="Find me on facebook"> </img>
                </a></li>
                <li><a href="resume/Bhavik_Resume.pdf" target="_blank" class="style2">
                    <img src="Images/icons/resume_pdf.jpg" height="28px" width="28px" title="My Resume">
                </a></li>
            </ul>
        </div>
    </div>
</body>
</html>

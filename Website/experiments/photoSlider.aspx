<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1"><title>

</title>
  
    <script type="text/javascript" src="fancybox/jquery-1.9.0.min.js"></script>  
    <script type="text/javascript" src="fancybox/jquery.fancybox.js?v=2.1.4"></script>
    <script type="text/javascript" src="fancybox/jquery.fancybox-thumbs.js?v=1.0.7"></script>
      

    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox.css?v=2.1.4" media="screen" />
    <link rel="stylesheet" href="fancybox/jquery.fancybox.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-thumbs.css?v=1.0.7" />
    
    
<style type="text/css">
    body
    {
        width: 90%;
    }
    .wrapper
    {
        min-width: 320px;
        max-width: 500px;
        margin: 0 auto;
        padding: 20px;
    }
    .font
    {
        font-family: Century Gothic;
        font-size: 15px;
        color: black;
        font-weight: bold;
        text-align: left;
    }
    
    .font1
    {
        font-family: Century Gothic;
        font-size: 15px;
        color: black;
        text-align: left;
    }
    .container
    {
        padding-left: 50px;
    }
    .style1
    {
        font-family: "Times New Roman", Times, serif;
        font-size: medium;
    }
    .style2
    {
        font-family: "Times New Roman", Times, serif;
        font-size: x-large;
        text-align: center;
    }
    .style3
    {
        font-size: medium;
    }
</style>


<script type="text/javascript">
    var j = jQuery.noConflict();
   j(document).ready(function () {
   j(".fancybox").fancybox({
            openEffect: 'fade',
            closeEffect: 'elastic',
            width: 30,
            height: 50,
            autoScale: false,
            autoDimensions: true,
            scrolling: 'yes',
            transitionIn: 'none',
            transitionOut: 'none',
            display: 'inline',
            helpers: {
                media: true
            }

        });
    });
//        
 //   
</script>
<link href="fancybox/tinyscrollbar.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="fancybox/jquery.tinyscrollbar.js" type="text/javascript"></script>    
    <script>
        jscroll = jQuery.noConflict();

        jscroll.ready(function () {
            jscroll('#scrollbar2').tinyscrollbar({ axis: 'x' });
        });
    </script>
    


</head>
<body>
    <p class="style2"><strong><em>Expriment: Photo Slider in jQuery</em></strong></p>
    <p class="style1"><strong><em>Experiment: </em></strong></p>
    <p class="style1">This experiment deomstrates the simple Photo slider used to view 
        the photos on webpage interactively.</p><hr>
    <p class="style1"><strong><em>Documentation:</em></strong></p>
    <p class="style1">The photoslider helps the user to easily navigate through the 
        photos on the webpage. It makes the web page lively. </p>
    <p class="style1">The photoslider is built using the fancybox jQuery plugin and the 
        styling associated with it. The styling gives the smooth transitions between the 
        photos and also gives the navigation controls.</p><hr>
    <p class="style1"><strong><em>Experiment Demo:</em></strong></p>

  <div id="scrollbar2">
    <div class="scrollbar"><div class="track"><div class="thumb"><div class="end"></div></div></div></div>
    <div class="container wrapper">
       <a class="fancybox" rel="gallery1" href="fancybox/images/img1.jpg">
            <img src="fancybox/images/img1.jpg" width="150px" height="150px" alt="" /></a>
            <a class="fancybox" rel="gallery1" href="fancybox/images/img2.jpg">
            <img src="fancybox/images/img2.jpg" width="150px" height="150px" alt="" />
        </a><a class="fancybox" rel="gallery1" href="fancybox/images/img3.jpg">
            <img src="fancybox/images/img3.jpg" width="150px" height="150px" alt="" />
        </a><a class="fancybox" rel="gallery1" href="fancybox/images/img4.jpg">
            <img src="fancybox/images/img4.jpg" width="150px" height="150px" alt="" />
        </a><a class="fancybox" rel="gallery1" href="fancybox/images/img5.jpg">
            <img src="fancybox/images/img5.jpg" width="150px" height="150px" alt="" />
        </a> 
    </div>
    </div>
    
    <p class="style3">
        <strong><em> <a href="../fileview/Default.aspx?~/experiments/photoSlider.aspx" target="_blank">PageSource</a></em></strong></p>
    <p class="style3">
        <strong><em>References:</em></strong></p>
    <p class="style3">
        <a href="http://www.roseindia.net/tutorial/jquery/fancybox.html">Roseindia.net</a></p>
    <p>
        &nbsp;</p>
    
</body>
</html>
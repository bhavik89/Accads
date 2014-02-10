<!DOCTYPE html>
<script runat="server">

</script>
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>moreInfo</title>
    <link rel="shortcut icon" href="_images/logo_noback.ico" type="image/x-icon" />
    <link href="CSS/moreInfo.css" rel="stylesheet" media="screen, projection" />
    <link href="CSS/style_media.css" rel="stylesheet" type="text/css" />
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places,geometry"
        type="text/javascript"></script>
    <script src="JS/getDetails.js" type="text/javascript"></script>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.js"></script>
    <script src="fancybox/jquery.mousewheel-3.0.4.pack.js" type="text/javascript"></script>
    <script src="fancybox/jquery.fancybox-1.3.4.pack.js" type="text/javascript"></script>
    <link href="fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" />
    <script src="JS/pagefancybox.js" type="text/javascript"></script>
    <script src="JS/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="JS/jQuery.fitmaps.js" type="text/javascript"></script>
    <script src="JS/fitmaps.js" type="text/javascript"></script>
    <style type="text/css">
        #info
        {
            height: auto;
            background-color: #f1f1f1;
        }
        
        #info_left
        {
            max-width: 40%;
            width: auto;
            float: left;
            padding: 10px 0 0 10px;
            background-color: inherit;
        }
        
        #name
        {
            font-style: italic;
            font-weight: bold;
            font-family: Lucida Handwriting;
            font-size: 150%;
            color: #c41200;
        }
        
        #photos
        {
            padding-bottom: 10px;
        }
    </style>
</head>
<body class="pad">
    <form id="form1" runat="server">
    <div>
        <ul class="master_navigation">
            <li><a href="#">
                <img id="logo" src="_images/logo_noback.png" alt="Find Places" />
            </a></li>
            <li id="home-li"><a href="Default.aspx">Home</a></li>
            <li><a href="../story/index.htm?../Project/Documentation/story.txt">Documentation</a></li>
        </ul>
    </div>
    <%--~~~~END OF HEADER~~~~--%>

<%--place info--%>

    <div id="info">
        <div id="info_left">
            <p id="name">
            </p>
            <p id="address">
            </p>
            <p id="tel">
            </p>
            <p id="in_tel">
            </p>
            <p id="open_now">
            </p>
            <p id="rating">
            </p>
            <p id="price_level">
            </p>
            <p id="website">
            </p>
        </div>
        <div id="map-canvas">
        </div>
        <div class="clear_both">
        </div>
    </div>

<%--photos--%>

    <div class="rounded">
        <div id="photosName">
        </div>
        <div class="container wrapper">
            <div id="photos">
            </div>
        </div>
    </div>

<%-- reviews--%>

    <div class="comment-box-block">
        <div id="reviewsPlaceName" class="comment-box-header">
        </div>
        <div id="reviews" class="comment-box-content">
        </div>
    </div>

<%--footer--%>
    <footer id="pageFooter">
      <nav class="footerNav">
            <p>Find Places | &copy;Copyright 2013 | Bhavik Gandhi | All rights reserved</p>    
</footer>
    </form>
</body>
</html>

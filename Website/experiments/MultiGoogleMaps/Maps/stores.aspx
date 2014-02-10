<%@ Page Language="C#" AutoEventWireup="true" CodeFile="stores.aspx.cs" Inherits="experiments_GoogleMaps_stores" %>

<!doctype html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Google Maps</title>
    <!--[if lt IE 9]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
    <!--[if lt IE 9]>
<script src="../_scripts/respond.min.js"></script>
<![endif]-->
    <%--<link href="_css/main.css" rel="stylesheet" media="screen, projection">--%>
    <meta name="viewport" content="initial-scale=1.0" />
    <script src="_scripts/jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript" src="_scripts/store-locator.compiled.js"></script>
    <script type="text/javascript" src="_scripts/roux-static-ds.js"></script>
    <script type="text/javascript" src="_scripts/panel.js"></script>
    <link rel="stylesheet" href="_css/storelocator.css">
    </head>
<body id="blogPage">
    <form id="form1" runat="server">
    <div id="map-canvas"></div>
    </form>
</body>
</html>

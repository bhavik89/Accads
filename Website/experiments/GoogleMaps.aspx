<%@ Page Language="C#" AutoEventWireup="true" CodeFile="GoogleMaps.aspx.cs" Inherits="experiments_GoogleMaps" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Google Maps</title>
<script type="text/javascript" src="googleJS/jquery-1.8.2.min.js"></script>
<script type="text/javascript" charset="utf-8" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript" charset="utf-8" src="googleJS/store-locator.compiled.js"></script>
<script type="text/javascript" charset="utf-8" src="googleJS/panel.js"></script>
</head>
<body>
    <form id="form1" runat="server">
   
    <div id="mapCanvas" style="width:500px; height:500px; border-style:solid; border-color:Gray;" ></div> 
    <div id="locations" style="width: 300px; height:500px; margin-left: 550px; border-style:solid; border-color:Gray;" ></div>
    </form>
</body>
</html>

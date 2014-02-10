<%@ Page Language="C#" AutoEventWireup="true" CodeFile="PlacesAndStore.aspx.cs" Inherits="PlacesStore_PlacesAndStore" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Store locator with Panel</title>
    <script type="text/javascript" charset="utf-8"
      src="//maps.googleapis.com/maps/api/js?sensor=false&libraries=places"></script>
    <script type="text/javascript" charset="utf-8"
      src="//ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.min.js">
    </script>
    <script type="text/javascript" charset="utf-8"
      src="store-locator.compiled.js"></script>
    <script src="places.js" type="text/javascript"></script>
    <link rel="stylesheet" href="storelocator.css" />
    <style type="text/css">
      body { font-family: sans-serif; }
      #map-canvas, #panel { height: 500px; }
      #panel { width: 300px; float: left; margin-right: 10px; }
      #panel .feature-filter label { width: 130px; }
      p.attribution, p.attribution a { color: #666; }
    </style>
  </head>
<body>
    <form id="form1" runat="server">
    <h1>Places API</h1>
    <div id="panel"></div>
    <div id="map-canvas"></div>
    </form>
</body>
</html>

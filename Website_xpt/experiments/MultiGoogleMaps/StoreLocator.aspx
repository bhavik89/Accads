<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StoreLocator.aspx.cs" Inherits="experiments_GoogleMaps_StoreLocator" %>

<!DOCTYPE html>

<html>
<head runat="server">
    <title>Store Locator</title>
    <meta name="viewport" content="initial-scale=1.0" />
    
    <script type="text/javascript" src="jquery-1.8.2.min.js"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript" src="store-locator.compiled.js"></script>
    <script type="text/javascript" src="getData.js"></script>
    <script type="text/javascript" src="panel.js"></script>
    <link rel="stylesheet" href="storelocator.css" />
    <style type="text/css">
        .style1
        {
            font-size: large;
            text-align: center;
        }
    </style>
</head>
<body id="blogPage">
    <form id="form1" runat="server">
    <p class="style1"><strong><em>Experiment: Google Maps API with Store Locator</em></strong></p>
    <p><strong><em>Experiment:</em></strong> This experiment demonstrates the 
        implementation of Google API with the Store locator utility provided by the 
        google.</p>
    <p><strong><em>Documentation:</em></strong></p>
    <p>&nbsp; In this experiement, I have used google maps API and store locator utility 
        to point out the 5 Dunkin Donuts outlets near the Northeastern university. I have followed the steps given in the google maps 
        API documentation and also the Lynda.com tutorial are very helpful in 
        understanding the concepts. The experiment uses the javascript files provided by Google API for maps implementation and using store-locator utility.</p>
    <p>When you open this experiment in the browser, it asks you to share your location. 
        If you click on opt in for sharing the location, the Google geolocation takes 
        your current location and zooms the map to your current location.</p>
    <p>If you deny to share your location, the default location pre set is to center of 
        USA.</p>
    <p>This is done by <code>center: new google.maps.LatLng(39, -97) </code>&nbsp;(39 and -97 is the 
        latitude and longitude of center of USA) in panel.js file.When you zoom in to Boston near 
        Northeastern university, you can see the five different Dunkin Donuts outlets. 
        The map takes the data from .csv file. The dunkinData.csv file contains 
        information about the store locations. The .csv file is given as the input to a 
        javascript file (getData.js) for parsing and making the DataSource object for the map. </p>
    <p>The javascript file panel.js creates a new map to represent on the page and also the panel listing to show the store data. 
        The function in javascript file adds the DOM lisner to the google maps object. 
        The map type in this file is set to roadmap.The datasource create in this file 
        (DunkinDataSource) pulls up the information from the getData.js file in order to 
        represent it on the map.</p>
    <p>Finally, a CSS file given by the google maps API is used to represent the data on the map as well as the side panel for store listings.</p>
    <p>When you click on any bubble on the map, a pop-up it gives you information about 
        the place address. You can add additional functionality or filter by any store 
        features. You can even find the direction to the store from any place by 
        selecting the get direction option on the pop-up that appears when you click on 
        a bubble.</p>
    <p><strong><em>Implementation:</em></strong></p>
    
       <asp:DropDownList ID="CoffeeSelect" runat="server">
       <asp:ListItem Value="1">Dunkin</asp:ListItem>
       <asp:ListItem Value="2">StarBucks</asp:ListItem>
        </asp:DropDownList>
    <p> 
        <asp:Label ID="selected" runat="server" Text=""></asp:Label> </p>

        <div>
    <div id="map-canvas"></div>
    <div id="panel"></div>
    </div>
    <div>
    <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
        <p><strong><em>Source:</em></strong></p>
    <p><a href="../../../fileview/Default.aspx?~/experiments/GoogleMaps/StoreLocator.aspx" target="_blank">View page Source</a></p>
    <p><a href="../../../fileview/Default.aspx?~/experiments/GoogleMaps/getData.js" target="_blank">getData.js </a> - Javascript file for getting data from csv file and parsing it</p>
    <p><a href="../../../fileview/Default.aspx?~/experiments/GoogleMaps/panel.js" target="_blank">panel.js </a> - Javascript file creating map and store panel listing</p>
    

    <p><strong><em>Referenes: </em></strong> </p>
    <p><a href="http://www.Lynda.com" target="_blank">Lynda tutorials for geolocation</a></p>
    <p><a href="http://storelocator.googlecode.com/git/index.html" target="_blank">Google maps API documentation</a></p>
    </div>

</p>
    </form>
</body>
</html>

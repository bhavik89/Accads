<%@ Page Language="C#" AutoEventWireup="true" CodeFile="StoreLocator.aspx.cs" Inherits="experiments_GoogleMaps_StoreLocator" %>

<!DOCTYPE html>
<html>
<head runat="server">
    <title>Store Locator</title>
    <meta name="viewport" content="initial-scale=1.0" />
    <script src="jquery-1.8.2.min.js"></script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript" src="store-locator.compiled.js"></script>
    <script type="text/javascript">
    
    google.maps.event.addDomListener(window, 'load', function () {
    
    //var map = map;
    var map = new google.maps.Map(document.getElementById('map-canvas'), {
        center: new google.maps.LatLng(39, -97),
        zoom: 3,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var panelDiv = document.getElementById('panel');

    var data = new DunkinDataSource;

    var view = new storeLocator.View(map, data);

    new storeLocator.Panel(panelDiv, {
        view: view,
        locationSearchLabel: 'Specify a location for directions (optional):'
    });

    var request = {
                location: map.center,
                name: "Dunkin",
                radius: 200
                //types: ['cafe']
            };  

var service = new google.maps.places.PlacesService(map);
service.nearbySearch(request, callback);


function callback() {
document.getElementById("info").innerHTML ="<p>" + "hello" + "</p>";
}
   
    function DunkinDataSource() {
    $.extend(this, new storeLocator.StaticDataFeed);
   
    var that = this;
    $.get('dunkinData.csv', function (data) {
        that.setStores(that.parse_(data));
    });
}


DunkinDataSource.prototype.FEATURES_ = new storeLocator.FeatureSet();


DunkinDataSource.prototype.getFeatures = function() {
  return this.FEATURES_;
};

DunkinDataSource.prototype.parse_ = function(csv) {
  var stores = [];
  var rows = csv.split('\n');
  var headings = this.parseRow_(rows[0]);

  for (var i = 1, row; row = rows[i]; i++) {
    row = this.toObject_(headings, this.parseRow_(row));
    
   
    var features = new storeLocator.FeatureSet;

    var position = new google.maps.LatLng(row.Ycoord, row.Xcoord);

   
	var city_state = this.join_([row.City, row.State],', ');
    var city_state_zip = this.join_([city_state, row.Postcode], '  ');

    var store = new storeLocator.Store(row.uuid, position, features, {
      title: row.Store_name,
      address: this.join_([row.Street_add, city_state_zip, row.Hrs_of_bus], '<br>'),
    });
    stores.push(store);
  }
  return stores;
};


DunkinDataSource.prototype.join_ = function(arr, sep) {
  var parts = [];
  for (var i = 0, ii = arr.length; i < ii; i++) {
    arr[i] && parts.push(arr[i]);
  }
  return parts.join(sep);
};

/**
 * Very rudimentary CSV parsing - we know how this particular CSV is formatted.
 * IMPORTANT: Don't use this for general CSV parsing!
 * @private
 * @param {string} row
 * @return {Array.<string>}
 */
DunkinDataSource.prototype.parseRow_ = function(row) {
  // Strip leading quote.
  if (row.charAt(0) == '"') {
    row = row.substring(1);
  }
  // Strip trailing quote. There seems to be a character between the last quote
  // and the line ending, hence 2 instead of 1.
  if (row.charAt(row.length - 1) == '"') {
    row = row.substring(0, row.length - 1);
  }

  row = row.split('","');

  return row;
};

/**
 * Creates an object mapping headings to row elements.
 * @private
 * @param {Array.<string>} headings
 * @param {Array.<string>} row
 * @return {Object}
 */
DunkinDataSource.prototype.toObject_ = function(headings, row) {
  var result = {};
  for (var i = 0, ii = row.length; i < ii; i++) {
    result[headings[i]] = row[i];
  }
  return result;


};
});
    </script>
    <%--<script type="text/javascript" src="panel.js"></script>--%>
    <link rel="stylesheet" href="storelocator.css">
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
    <div>
        <div id="map-canvas">
        </div>
        <div id="panel">
        </div>
    </div>
    <div>

    <div id="info"></div>
    </form>
</body>
</html>

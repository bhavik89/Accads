google.maps.event.addDomListener(window, 'load', function() {
  var map = new google.maps.Map(document.getElementById('map-canvas'), {
    center: new google.maps.LatLng(39, -97),
    zoom: 3,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });

  var panelDiv = document.getElementById('panel');

  var data = new RouxDataSource;

var ICON = new google.maps.MarkerImage('_images/roux.png', null, null,
    new google.maps.Point(14, 13));

var SHADOW = new google.maps.MarkerImage('_images/roux-shadow.png', null, null,
    new google.maps.Point(14, 13));

  var view = new storeLocator.View(map, data, {
    geolocation: true,
    features: data.getFeatures()
  });

  view.createMarker = function(store) {
    var markerOptions = {
      position: store.getLocation(),
      icon: ICON,
      shadow: SHADOW,
      title: store.getDetails().title
    };
    return new google.maps.Marker(markerOptions);
  }


  new storeLocator.Panel(panelDiv, {
    view: view,
	locationSearchLabel: 'Specify a location for directions (optional):'
  });
});

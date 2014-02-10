google.maps.event.addDomListener(window, 'load', function() {
    var map = new google.maps.Map(document.getElementById('mapCanvas'), {
    center: new google.maps.LatLng(39, -97),
    zoom: 3,
    mapTypeId: google.maps.MapTypeId.ROADMAP
  });

var panelDiv = document.getElementById('locations');

  var data = new RouxDataSource;

  var view = new storeLocator.View(map, data, {
    features: data.getFeatures()
  });

  new storeLocator.Panel(panelDiv, {
    view: view,
	locationSearchLabel: 'Specify a location for directions (optional):'
  });
});

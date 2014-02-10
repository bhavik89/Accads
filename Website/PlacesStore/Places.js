// A simple demo showing how to grab places using the Maps API Places Library.
// Useful extensions may include using "name" filtering or "keyword" search.

google.maps.event.addDomListener(window, 'load', function () {

    navigator.geolocation.getCurrentPosition(
    function (position) {
    var maploc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
    //var maploc = new google.maps.LatLng(39, -97);
    plotmap(maploc);
    });
});

function plotmap(maploc){

    var map = new google.maps.Map(document.getElementById('map-canvas'), {
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: maploc,
        zoom: 20
    });

    var panelDiv = document.getElementById('panel');

    var data = new PlacesDataSource(map);

    var view = new storeLocator.View(map, data);

    var markerSize = new google.maps.Size(24, 24);
    view.createMarker = function (store) {
        return new google.maps.Marker({
            position: store.getLocation(),
            icon: new google.maps.MarkerImage(store.getDetails().icon, null, null,
          null, markerSize)
        });
    };

    new storeLocator.Panel(panelDiv, {
        view: view
    });
}

/**
* Creates a new PlacesDataSource.
* @param {google.maps.Map} map
* @constructor
*/
function PlacesDataSource(map) {
    this.service_ = new google.maps.places.PlacesService(map);
}
//var maploc = new new google.maps.LatLng(42.34037, -71.088735);

//var request = {
//    location: maploc,
//                name: 'Starbucks',
//                radius: 50
//                //types: ['cafe']
//            };

/**
* @inheritDoc
*/
PlacesDataSource.prototype.getStores = function (bounds, features, callback) {
    this.service_.nearbySearch({ bounds: bounds },
   function (results, status) {
       var stores = [];

       for (var i = 0, result; result = results[i]; i++) {
           if (result.name == 'Starbucks') {

               var latLng = result.geometry.location;
               var store = new storeLocator.Store(result.id, latLng, null, {
                   title: result.name,
                   address: result.vicinity, //.types.join(', '),
                   icon: result.icon
               });
               stores.push(store);
           }
       }

       callback(stores);
   });
};
<!DOCTYPE html>
<html>
  <head>
    <style type="text/css">
      html, body { margin: 0; padding: 0; height: 100% }
      #map_canvas { margin: 0; padding: 0; width: 50%; height: 100% }
      #directionsPanel { position: absolute; top: 0px; right: 0px; width: 50% }
    </style>
    <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script type="text/javascript">

   var geocoder;
   var directionsService;
   var directionsDisplay;

   function initialize() {
    var latlng = new google.maps.LatLng(33.929011, -84.361);
    var myOptions = {
        zoom: 15,
        center: latlng,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    };

    var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);
    var marker = new google.maps.Marker({
        position: new google.maps.LatLng(33.929011, -84.361),
        map: map,
        title: 'Atlanta/Sandy Springs',
        clickable: true,
    });

    var infowindow = new google.maps.InfoWindow({
      content: "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>"
    });

    google.maps.event.addListener(marker, 'click', function() {
      infowindow.open(map, marker);
    });
    geocoder = new google.maps.Geocoder;
    directionsService = new google.maps.DirectionsService();
    directionsDisplay = new google.maps.DirectionsRenderer({
      suppressMarkers: false
    });
    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(document.getElementById("directionsPanel"));
   }

   function getDirections() {
     geocoder.geocode( { 'address': document.getElementById('clientAddress').value },
     function(results, status) {
       if(status == google.maps.GeocoderStatus.OK) {
         var origin = results[0].geometry.location;
         var destination = new google.maps.LatLng(33.929011, -84.361);

         var request = {
           origin: origin,
           destination: destination,
           travelMode: google.maps.DirectionsTravelMode.DRIVING
         };

         directionsService.route(request, function(response, status) {
           if (status == google.maps.DirectionsStatus.OK) {
             directionsDisplay.setDirections(response);
           }
         });

         } else {
          document.getElementById('clientAddress').value = 
            "Directions cannot be computed at this time.";
       }
     });
   }

    </script>
  </head>
  <body onload="initialize()">
    <div id="map_canvas"></div>
    <div id="directionsPanel"></div>
  </body>
</html>
<!DOCTYPE html>
<html>
  <head>
    <title>Google Maps JavaScript API v3 Example: Place Details</title>
    <meta charset="utf-8">
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
    <style>
      #map-canvas {
        height: 400px;
        width: 600px;
        border: 1px solid #333;
        margin-top: 0.6em;
      }

      #text {
        width: 600px;
        overflow: auto;
      }
    </style>

    <script>
        function initialize() {
            var map = new google.maps.Map(document.getElementById('map-canvas'), {
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: new google.maps.LatLng(42.34037, -71.088735),
                zoom: 14
            });

            var request = {
                reference: 'CoQBdwAAAK92gFmARePvBX2JnA4h1jTQNsqly6BiD-Qg1Gjgt-aMtuH-WdNFsIcyDRW7AtRnJVEk9RoJq8nyzPEfJDyd0rpiMOvRaRWsLJoQXEs5LAqGXLBDInfT0Cl65EHe1Xmq8QpWA07Ad9qVQIyMSbUU1OiAtjfoVqSeO821Vb_vjv0dEhAGyQpLl0wPq9TPit0hwbuOGhRZlXvVya3E7KWP2NVg_nd1H3UqaQ'
            };

            var infowindow = new google.maps.InfoWindow();
            var service = new google.maps.places.PlacesService(map);

            service.getDetails(request, function (place, status) {
                if (status == google.maps.places.PlacesServiceStatus.OK) {
                    var marker = new google.maps.Marker({
                        map: map,
                        position: place.geometry.location
                    });
                    google.maps.event.addListener(marker, 'click', function () {
                        infowindow.setContent(place.name);
                        infowindow.open(map, this);
                    });
                    var d1 = new Date(0);
                    var displayDate;
                    for (var i = 0; i < place.reviews.length; i++) {
                        alert(place.reviews[i].text);
                        displayDate = d1.setUTCSeconds(place.reviews[i].time);
                        alert(displayDate);
                    }
                }
                createPhotoMarker(place);

            });

            function createPhotoMarker(place) {
                var photos = place.photos;
                if (!photos) {
                    return;
                }

                var marker = new google.maps.Marker({
                    map: map,
                    position: place.geometry.location,
                    title: place.name,
                    icon: photos[3].getUrl({ 'maxWidth': 200, 'maxHeight': 200 })
                });
            }


        }

        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
  </head>
  <body>
    <div id="map-canvas"></div>
    <div id="text">
<%--      <pre>
var request = {
  reference: 'CnRkAAAAGnBVNFDeQoOQHzgdOpOqJNV7K9-c5IQrWFUYD9TNhUmz5-aHhfqyKH0zmAcUlkqVCrpaKcV8ZjGQKzB6GXxtzUYcP-muHafGsmW-1CwjTPBCmK43AZpAwW0FRtQDQADj3H2bzwwHVIXlQAiccm7r4xIQmjt_Oqm2FejWpBxLWs3L_RoUbharABi5FMnKnzmRL2TGju6UA4k'
};
    </pre>--%>
  </body>
</html>
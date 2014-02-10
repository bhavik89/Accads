<!DOCTYPE html>
<html>
  <head>
    <title>Google Maps JavaScript API v3 Example: Places Autocomplete</title>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>

    <style>
      body {
        font-family: sans-serif;
        font-size: 14px;
      }
      #map-canvas {
        height: 400px;
        width: 600px;
        margin-top: 0.6em;
      }
      input {
        border: 1px solid  rgba(0, 0, 0, 0.5);
      }
      input.notfound {
        border: 2px solid  rgba(255, 0, 0, 0.4);
      }
        .style1
        {
            font-size: x-large;
            text-align: center;
        }
        .style2
        {
            font-family: "Times New Roman", Times, serif;
        }
    </style>

    <script type="text/javascript">
        function initialize() {

            navigator.geolocation.getCurrentPosition(
               function (position) {

                   var maploc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                   
                  plotmap(maploc);
               });
           }

        function  plotmap(maploc){

            map = new google.maps.Map(document.getElementById('map-canvas'), {
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: maploc,
                zoom:15
            });

            var newmarker = new google.maps.Marker({
                map: map,
                title: "You are Here",
                position: maploc,
                //size: 20
            });

            //var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

            var input = document.getElementById('TextField');
            var autocomplete = new google.maps.places.Autocomplete(input);

            autocomplete.bindTo('bounds', map);

            var infowindow = new google.maps.InfoWindow();
            var marker = new google.maps.Marker({
                map: map
            });

            google.maps.event.addListener(autocomplete, 'place_changed', function () {
                infowindow.close();
                marker.setVisible(false);
                input.className = '';
                var place = autocomplete.getPlace();
                if (!place.geometry) {
                    // Inform the user that the place was not found and return.
                    input.className = 'notfound';
                    return;
                }

                // If the place has a geometry, then present it on a map.
                if (place.geometry.viewport) {
                    map.fitBounds(place.geometry.viewport);
                } 
                else {
                    map.setCenter(place.geometry.location);
                    map.setZoom(17);  // Why 17? Because it looks good.
                }

                var image = {
                    url: place.icon,
                    size: new google.maps.Size(71, 71),
                    origin: new google.maps.Point(0, 0),
                    anchor: new google.maps.Point(17, 34),
                    scaledSize: new google.maps.Size(35, 35)
                };

                marker.setIcon(image);
                marker.setPosition(place.geometry.location);
                marker.setVisible(true);

                if(place.rating)
                infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + place.formatted_address + '<br>Ratings: ' + place.rating);
                
                else
                infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + place.formatted_address);

                infowindow.open(map, marker);
            });

            
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
  </head>
  <body>
     <div>
    <p class="style1"><span class="style2"><strong><em>Experiment: Using AutoComplete service of 
        Google maps API</em></strong></p>
         <p><strong><em>Experiment: </em></strong></p>
         <p>Here the usage of auto complete service by the google maps API is demonstrated.</p>
         <hr />
         <p><strong><em>Documentation:</em></strong></p>
         <p>AutoComplete service is used for the user interaction and to suggest the user the 
             nearby places. If the used is unsure about the name of a place. the auto 
             complete service lists out the nearby places matching the search keywords.</p>
         <p>
             As text is entered, Autocomplete returns Place predictions to the application in 
             the form of a drop-down pick list. You can use the Places Autocomplete feature 
             to help users find a specific location, or assist them with filling out address 
             fields in online forms.</p>
         <p>
             When a user selects a Place from the list, information about the Place is 
             returned to the Autocomplete object, and can be retrieved by your application.</p>
         <p>The Autocomplete constructor takes two arguments: </p>
         <ul>
             <li>&nbsp;Text Input given by the user, any changes to this field is monitored and 
                 the predictions are given out.</li>
             <li>Other options arguments can include various parameters.</li>
         </ul>
         <p>
             The Map at the startup will ask you to share your location and will center the 
             map based on your current location it will place the marker on map. When you 
             search for the places in the text box, it will list out places in your nearby 
             location. When you move the map to another location and try to search for the 
             places, it will give the list of nearby places based on the cnter of map.</p>
             <hr />
         <p>
             <strong><em>Experiment Demo.</em></strong></p>
         </span>
    
      <input id="TextField" type="text" size="50">
      
    </div>
    
    <div id="map-canvas"></div>
    </ContentTemplate>
    <hr />
      <p class="style2">
          <a href="../fileview/Default.aspx?~/experiments/GoogleAutofill.aspx" target="_blank">PageSource</a></p>
      <p class="style2">
          <strong><em>References : </em></strong>
      </p>
      <p class="style2">
          <a href="https://developers.google.com/maps/documentation/javascript/places" target="_blank">Google Places Documentation</a></p>
      <p class="style2">
          &nbsp;</p>
    
  </body>
</html>

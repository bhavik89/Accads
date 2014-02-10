<!DOCTYPE html>
<html>
<head>
    <title>Direction's APIs</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"
        type="text/javascript"></script>
    <style type="text/css">
        body
        {
            font-family: sans-serif;
            font-size: 14px;
        }
        
        #map-canvas
        {
            height: 400px;
            width: 600px;
            float: left;
        }
        
        #text
        {
            height: 600px;
            width: 250px;
            margin-left: 680px;
            float: left;
        }
        input
        {
            border: 1px solid rgba(0, 0, 0, 0.5);
        }
        
        input.notfound
        {
            border: 2px solid rgba(255, 0, 0, 0.4);
        }
        .style1
        {
            font-family: "Times New Roman", Times, serif;
            font-size: medium;
        }
        .style2
        {
            font-family: "Times New Roman", Times, serif;
        }
        .style3
        {
            font-size: x-large;
            text-align: center;
            width: 900px;
        }
    </style>
    <script type="text/javascript">

        var locs = [];
        var map;
        var infowindow;
        var prev = 0;
        var activeInfoWindow;
        var map_marker = [];
        var geocoder;
        var directionsService;
        var directionsDisplay;
        var dest;

        function initialize() {

           // map = null;

            navigator.geolocation.getCurrentPosition(
               function (position) {
                   maploc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                   AutoCheck(maploc);
               });
        }



        function AutoCheck(maploc) {
            if (document.getElementById("AutoFillCheck").checked)
                AutoComplete(maploc);

            else
                placesSearch(maploc);
        }


        function placesSearch(maploc) {

            map = new google.maps.Map(document.getElementById('map-canvas'), {
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: maploc,
                zoom: 13
            });

            deleteOverlays();

            var newmarker = new google.maps.Marker({
                map: map,
                title: "You are Here",
                icon: "../Images/CurrentLoc.png",
                position: maploc,
                animation: google.maps.Animation.BOUNCE

            });


            //alert('InTextSearch');
            if (document.getElementById("nearbySearch").checked) {
                document.getElementById("radii").disabled = false;
                SearchNearBy(maploc);
            }
            else {
                document.getElementById("radii").disabled = true;
                searchByText(maploc);
            }
        }
        
        function searchByText(maploc) {

            var place_inp = document.getElementById("searchTextField").value;
            var request = {
                query: [place_inp],
                location: maploc,
                radius: '500'
            };

            infowindow = new google.maps.InfoWindow();

            var service = new google.maps.places.PlacesService(map);
            service.textSearch(request, callback);

            document.getElementById("text").innerHTML = "";
        }

        function callback(results, status) {

            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    {
                        createMarker(results[i]);
                        // document.getElementById("text").innerHTML += "<div><strong><p>" + results[i].name + "</strong></br>" + results[i].geometry.location + "</p></div>";
                        document.getElementById("text").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onClick=" + "\"alertMarker(" + i + ");\"" + "><strong>" +
                         results[i].name + "</strong></br>" + results[i].formatted_address + "</br>" + results[i].geometry.location + "</p>";
                    }
                }
            }
        }

        /*  function createMarker(place) {
        var placeLoc = place.geometry.location;
        var marker = new google.maps.Marker({
        map: map,
        position: place.geometry.location,
        animation: google.maps.Animation.DROP
        });

        google.maps.event.addListener(marker, 'click', function () {

        var rating = place.rating;
        if (rating == undefined)
        rating = "N/A";
        else
        rating = rating;

        infowindow.setContent("<div><strong><p>" + place.name + "</strong></br>Ratings: " + rating + "</p></div>");
        infowindow.open(map, this);
        });
        
        
        }*/

        function createMarker(place) {
            
            var placeLoc = place.geometry.location;
            var newmarker = new google.maps.Marker({
                map: map,
                position: place.geometry.location
            });
            var rating = place.rating;
            if (rating == undefined)
                rating = "N/A";
            else
                rating = rating;

           //alert(place.geometry);
           // markerLoc;
            //var markerlat = place.geometry.location.lat + "";
            //var markerlng = place.geometry.location.lng + "";
            //alert(markerlat);
            //alert(markerlng);
           

            newmarker['infowindow'] = new google.maps.InfoWindow({
                content: '<strong>' + place.name + '</strong>' + "</br>" + place.formatted_address + "</br> Ratings: " + rating + "</br>" +
                "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>"
            });


            google.maps.event.addListener(newmarker, 'click', function () {
               // alert('Click Function');
                dest = this.position;
               // alert(dest); //place.geometry.location;

                if (activeInfoWindow == this['infowindow'])
                    return;

                if (activeInfoWindow) {
                    activeInfoWindow.close();
                }

                this['infowindow'].open(map, this);
                activeInfoWindow = this['infowindow'];

                this['infowindow'].open(map, this);

            });

            map_marker.push(newmarker);
            geocoder = new google.maps.Geocoder;
            directionsService = new google.maps.DirectionsService();
            directionsDisplay = new google.maps.DirectionsRenderer({
                suppressMarkers: false
            });
            directionsDisplay.setMap(map);
            document.getElementById("directionsPanel").innerHTML = "";
            directionsDisplay.setPanel(document.getElementById("directionsPanel"));
        }
        
        function alertMarker(mark) {
            //alert(mark);
            // for(var mv = 0; mv <= results.length; mv++){
            // map_marker[mv]['infowindow'].close();
            // }

            if ((prev != 0 && mark != 0) || (prev == 0 && mark != 0) || (prev != 0 && mark == 0))
                map_marker[prev]['infowindow'].close();

            if (activeInfoWindow) {
                activeInfoWindow.close();
            }

            map_marker[mark]['infowindow'].open(map, map_marker[mark]);

            activeInfoWindow = map_marker[mark]['infowindow'];
            prev = mark;
        }



        function SearchNearBy(maploc) {

            // alert('SearchNearBy');
            var place_inp = document.getElementById("searchTextField").value;
            var rad_inp = document.getElementById("radii").options[document.getElementById("radii").selectedIndex].value;
            var request = {
                location: maploc,
                name: [place_inp],
                radius: rad_inp//'500'
                //types: ['cafe']
            };

            infowindow = new google.maps.InfoWindow();

            var service = new google.maps.places.PlacesService(map);
            service.nearbySearch(request, nearbyCallback);

            document.getElementById("text").innerHTML = "";
        }

        function nearbyCallback(results, status) {
            // alert('InCallBack');
            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    {
                        nearbyCreateMarker(results[i]);
                        //document.getElementById("text").innerHTML += "<div><strong><p>" + results[i].name + "<strong></br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p></div>";

                        document.getElementById("text").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onclick=" + "\"alertMarker(" + i + ");\"" + "><strong>" +
                         results[i].name + "</strong></br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p>";
                    }
                }
            }
        }

        function nearbyCreateMarker(place) {

            var placeLoc = place.geometry.location;
            var newmarker = new google.maps.Marker({
                map: map,
                position: place.geometry.location
            });
            var rating = place.rating;
            if (rating == undefined)
                rating = "N/A";
            else
                rating = rating;

            newmarker['infowindow'] = new google.maps.InfoWindow({
                content: '<strong>' + place.name + '</strong>' + "</br>" + place.vicinity + "</br> Ratings: " + rating + "</br>" +
                "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>"
            });


            google.maps.event.addListener(newmarker, 'click', function () {
                dest = this.position;

                if (activeInfoWindow == this['infowindow'])
                    return;

                if (activeInfoWindow) {
                    activeInfoWindow.close();
                }

                this['infowindow'].open(map, this);
                activeInfoWindow = this['infowindow'];

                this['infowindow'].open(map, this);

            });

            map_marker.push(newmarker);
            geocoder = new google.maps.Geocoder;
            directionsService = new google.maps.DirectionsService();
            directionsDisplay = new google.maps.DirectionsRenderer({
                suppressMarkers: false
            });
            directionsDisplay.setMap(map);
            document.getElementById("directionsPanel").innerHTML = "";
            directionsDisplay.setPanel(document.getElementById("directionsPanel"));
        }


        function AutoComplete(maploc) {
            //var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

            var input = document.getElementById('searchTextField');
            var autocomplete = new google.maps.places.Autocomplete(input);

            autocomplete.bindTo('bounds', map);

            deleteOverlays();

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
                    map.setZoom(17);  // 17 -> it looks good.
                }


                marker.setPosition(place.geometry.location);
                marker.setVisible(true);
                marker.setAnimation(google.maps.Animation.DROP);
                dest = place.geometry.location;

                if (activeInfoWindow) {
                    activeInfoWindow.close();
                }

                document.getElementById("text").innerHTML = "";
                var address = '';
                if (place.address_components) {
                    address = [
              (place.address_components[0] && place.address_components[0].short_name || ''),
              (place.address_components[1] && place.address_components[1].short_name || ''),
              (place.address_components[2] && place.address_components[2].short_name || '')
                      ].join(' ');
                }

                infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + place.formatted_address + '<br>' +
                "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>");
                infowindow.open(map, marker);
                document.getElementById("text").innerHTML += "<div><strong><p>" + place.name + "</strong><br>" + place.formatted_address + "</br>" + place.geometry.location + "</p></div>";
            });

        }

        //delete all the markers and clear the overlays
        function deleteOverlays() {
            if (map_marker) {
                for (i in map_marker) {
                    map_marker[i].setMap(null);
                }
                map_marker.length = 0;
            }
        }


        function getDirections() {

            //alert('in Getdirection');
            //alert(dest);
            geocoder.geocode({ 'address': document.getElementById('clientAddress').value },
            function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    var origin = results[0].geometry.location;
                    //var finalDestination = dest;
                     //new google.maps.LatLng(latitude, longitude);

                    var request = {
                        origin: origin,
                        destination: dest,
                        travelMode: google.maps.DirectionsTravelMode.DRIVING
                    };

                    directionsService.route(request, function (response, status) {
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


        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <p class="style3"><span class="style2"><strong><em>Experiment: Maps API with Get Direction Service</em></strong></p>
    </span><span class="style1">
    <p style="width: 900px"><strong><em>Experiment: </em></strong> </p>
    <p style="width: 900px">This&nbsp; experiment demonstrates the get direction service provided by the 
        google maps API to get directions from a source location to desticaiotn for 
        different modes of travel.</p><hr>
    <p><strong><em>Documentation:</em></strong></p>
    <p style="width: 901px">For accesing the direction service, a request consisting of the origin point 
        (entered by the user in infowindow textbox), the destination point and the 
        travel mode is formed. The google&#39;s get directions service is called with the 
        request formed to get the path. if the directions is obtained, it is lisetd out 
        on another right handside panel. It the directions are not found, a failure 
        message is displayed in the textbox in infowindow.</p><hr>
    <p><strong><em>Experiment Demo: </em></strong></p>
    <p style="width: 900px">PS: This experiment only gives the direction for Driving mode of travel, you can 
        play with the request to change it.</span></p>
    <div>
        <input id="searchTextField" type="text" size="50">
        <asp:Button ID="find" runat="server" Text="Search Places" OnClientClick="initialize()" />
        <asp:CheckBox ID="AutoFillCheck" runat="server" OnClick="initialize()" Text="Use AutoComplete" />
        <asp:CheckBox ID="nearbySearch" runat="server" Text="Search Nearby" OnClick="initialize()" />
        &nbsp;Travel Distance(in meters)&nbsp;
        <select id="radii" onchange="initialize()" disabled>
            <option>10</option>
            <option>100</option>
            <option>500</option>
            <option>1000</option>
            <option>5000</option>
            <option>10000</option>
        </select>
    </div>
    <table border="0">
        <tr>
            <td>
                <asp:ScriptManager ID="SM1" runat="server">
                </asp:ScriptManager>
                <asp:UpdatePanel ID="map_update" runat="server">
                    <ContentTemplate>
                        <span id="map-canvas"></span>
                    </ContentTemplate>
                    <Triggers>
                        <asp:AsyncPostBackTrigger ControlID="nearbySearch" EventName="CheckedChanged" />
                        <asp:AsyncPostBackTrigger ControlID="AutoFillCheck" EventName="CheckedChanged" />
                        <asp:AsyncPostBackTrigger ControlID="find" EventName="Click" />
                        <%--  <asp:AsyncPostBackTrigger ControlID="radii" EventName="SelectedIndexChanged" />--%>
                    </Triggers>
                </asp:UpdatePanel>
            </td>
            <td>
                <span id="text" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 250px; max-height: 380px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding: 10 10 10 10;"></span>
            </td>
            <td>
            <div id="directionsPanel" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 250px; max-height: 380px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding: 10 10 10 10;"></div>            
            </td>
        </tr>
    </table>
    </form>
    <hr />
    <p>
        &nbsp;</p>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/GetDirection.aspx" target="_blank">
        <strong><em>Page
            Source</em></strong></a></p>
    <p>
        <strong><em>References</em></strong></p>
    <p>
        <a href="https://developers.google.com/maps/documentation/javascript/places">Google
            Places API Documentation</a></p>
</body>
</html>

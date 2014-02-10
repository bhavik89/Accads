<!DOCTYPE html>
<html>
<head>
    <title>Reviews&Photos</title>
    <script type="text/javascript" src="fancybox/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="fancybox/jquery.fancybox.js?v=2.1.4"></script>
    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox.css?v=2.1.4"
        media="screen" />
    <link rel="stylesheet" href="fancybox/jquery.fancybox.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-thumbs.css?v=1.0.7" />
    <script type="text/javascript" src="fancybox/jquery.fancybox-thumbs.js?v=1.0.7"></script>
    <%--<script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>--%>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places,geometry"
        type="text/javascript"></script>
    <%--<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false"> </script>--%>
    <style type="text/css">
        body
        {
            font-family: sans-serif;
            font-size: 14px;
        }
        
        #slider
        {
            min-width: 320px;
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
            padding-left: 50px;
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
        
        .image_carousel
        {
            padding: 15px 0 15px 40px;
        }
        .image_carousel img
        {
            border: 1px solid #ccc;
            background-color: white;
            padding: 9px;
            margin: 7px;
            display: block;
            float: left;
        }
        .image_carousel a
        {
            display: block;
            float: left;
        }
        .clearfix
        {
            float: none;
            clear: both;
        }
        
        .style1
        {
            font-family: "Times New Roman" , Times, serif;
        }
        .style2
        {
            font-size: medium;
        }
        
        .style3
        {
            font-size: x-large;
            text-align: center;
            width: 900px;
        }
        
        .style4
        {
            font-family: "Times New Roman" , Times, serif;
            font-size: medium;
        }
    </style>
    <%-- <script src="fancybox/jquery-1.4.2.min.js" type="text/javascript"></script>
 <script src="fancybox/jquery.jcarousel.min.js" type="text/javascript"></script>
 <link href="fancybox/skin.css" rel="stylesheet" type="text/css" />

 <script type="text/javascript">
     var jc = jQuery.noConflict();
     jc(document).ready(function () {
         jc('#mycarousel').jcarousel({
             wrap: 'circular'
         });
     });
</script>--%>
    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox.css?v=2.1.4" media="screen" />
    <link rel="stylesheet" href="fancybox/jquery.fancybox.css" type="text/css" />
    <link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-thumbs.css?v=1.0.7" />
    <script type="text/javascript" src="fancybox/jquery-1.9.0.min.js"></script>
    <script type="text/javascript" src="fancybox/jquery.fancybox.js?v=2.1.4"></script>
    <script type="text/javascript" src="fancybox/jquery.fancybox-thumbs.js?v=1.0.7"></script>
    <script type="text/javascript">
        var jf = jQuery.noConflict()
        jf(document).ready(function () {
            jf(".fancybox").fancybox({
                openEffect: 'fade',
                closeEffect: 'elastic',
                width: 30,
                height: 50,
                autoScale: false,
                autoDimensions: true,
                scrolling: 'yes',
                transitionIn: 'none',
                transitionOut: 'none',
                display: 'inline',
                helpers: {
                    media: true
                }
            });
        });
    </script>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.0/themes/base/jquery-ui.css" />
    <script src="http://code.jquery.com/jquery-1.8.3.js"></script>
    <script src="http://code.jquery.com/ui/1.10.0/jquery-ui.js"></script>
    <script type="text/javascript">

        var user_radius = 500;
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
        var marker_reference;
        var marker_pos;
        var map_center;
        var d1 = 0;
        my_slider_fun = null;

        $(function () {
            $("#slider-range").slider({
                orientation: "horizontal",
                range: "min",
                max: 50000,
                min: 0,
                value: 500,
                slide: function (event, ui) {
                    $("#distance").val("Distance in Meters:" + ui.value);
                    user_radius = ui.value;
                   // initialize();
                },
                change: initialize,
              });
            $("#distance").val("Distance in Meters:" + $("#slider-range").slider("value"));
        });



        function initialize() {
            //alert(user_radius);
            d1 = 0;
            deleteOverlays();

            // map = null;

            navigator.geolocation.getCurrentPosition(
               function (position) {
                   maploc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                   map_center = maploc;
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
                //document.getElementById("radii").disabled = false;
                $("#slider-range").slider("option", "disabled", false);
                SearchNearBy(maploc);
            }
            else {
                //document.getElementById("radii").disabled = true;
                $("#slider-range").slider("option", "disabled", true);
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

            document.getElementById("tab1").innerHTML = "";
        }

        function callback(results, status) {
        var placeDistance;
            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    {
                        createMarker(results[i]);
                        // document.getElementById("tab1").innerHTML += "<div><strong><p>" + results[i].name + "</strong></br>" + results[i].geometry.location + "</p></div>";
                        placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

                        document.getElementById("tab1").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onClick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer'\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].formatted_address + "</br>Distance: " + ((placeDistance*0.621).toFixed(2)) + " miles</p>";
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
            var placeDistance;
            var placeLoc = place.geometry.location;
            var newmarker = new google.maps.Marker({
                map: map,
                position: place.geometry.location,
                reference : place.reference
            });
            marker_pos = place.geometry.location;
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

            calcMapZoom();
            placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);
            newmarker['infowindow'] = new google.maps.InfoWindow({
                content: '<strong>' + place.name + '</strong>' + "</br>" + place.formatted_address + "</br> Ratings: " + rating +
                "</br>Distance: " + ((placeDistance*0.621).toFixed(2)) + " miles</br>" +
                "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>"
            });


            google.maps.event.addListener(newmarker, 'click', function () {
                // alert('Click Function');
                dest = this.position;
                marker_reference = this.reference;
                map.setCenter(this.getPosition());
                //map.setZoom(3);
                //alert(marker_reference);
                // alert(dest); //place.geometry.location;

                if (activeInfoWindow == this['infowindow'])
                    return;

                if (activeInfoWindow) {
                    activeInfoWindow.close();
                }

                this['infowindow'].open(map, this);
                activeInfoWindow = this['infowindow'];

                this['infowindow'].open(map, this);
                getReviews();
                createPhoto();

            });

            map_marker.push(newmarker);
            geocoder = new google.maps.Geocoder;
            directionsService = new google.maps.DirectionsService();
            directionsDisplay = new google.maps.DirectionsRenderer({
                suppressMarkers: false
            });
            directionsDisplay.setMap(map);
            document.getElementById("tab2").innerHTML = "";
            directionsDisplay.setPanel(document.getElementById("tab2"));
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

            marker_reference = map_marker[mark].reference;
            map_marker[mark]['infowindow'].open(map, map_marker[mark]);

            activeInfoWindow = map_marker[mark]['infowindow'];
            prev = mark;

            getReviews();
            createPhoto();

        }


        function SearchNearBy(maploc) {

            // alert('SearchNearBy');
            var place_inp = document.getElementById("searchTextField").value;
            //var rad_inp = document.getElementById("radii").options[document.getElementById("radii").selectedIndex].value;
            var request = {
                location: maploc,
                name: [place_inp],
                radius: user_radius    
              };

            infowindow = new google.maps.InfoWindow();

            var service = new google.maps.places.PlacesService(map);
            service.nearbySearch(request, nearbyCallback);

            document.getElementById("tab1").innerHTML = "";
        }

        function nearbyCallback(results, status) {
            // alert('InCallBack');
            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    {
                        nearbyCreateMarker(results[i]);
                        //document.getElementById("tab1").innerHTML += "<div><strong><p>" + results[i].name + "<strong></br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p></div>";

                        document.getElementById("tab1").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onclick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer'\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p>";
                    }
                }
            }
        }

        function nearbyCreateMarker(place) {

            var placeLoc = place.geometry.location;
            var newmarker = new google.maps.Marker({
                map: map,
                position: place.geometry.location,
                reference:place.reference
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
                marker_reference = this.reference;
                if (activeInfoWindow == this['infowindow'])
                    return;

                if (activeInfoWindow) {
                    activeInfoWindow.close();
                }

                this['infowindow'].open(map, this);
                activeInfoWindow = this['infowindow'];

                this['infowindow'].open(map, this);
                getReviews();
                createPhoto();

            });

            map_marker.push(newmarker);
            geocoder = new google.maps.Geocoder;
            directionsService = new google.maps.DirectionsService();
            directionsDisplay = new google.maps.DirectionsRenderer({
                suppressMarkers: false
            });
            directionsDisplay.setMap(map);
            document.getElementById("tab2").innerHTML = "";
            directionsDisplay.setPanel(document.getElementById("tab2"));
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

                marker_reference = place.reference;
                marker.setPosition(place.geometry.location);
                marker.setVisible(true);
                marker.setAnimation(google.maps.Animation.DROP);
                dest = place.geometry.location;

                if (activeInfoWindow) {
                    activeInfoWindow.close();
                }

                document.getElementById("tab1").innerHTML = "";
                var address = '';
                if (place.address_components) {
                    address = [
              (place.address_components[0] && place.address_components[0].short_name || ''),
              (place.address_components[1] && place.address_components[1].short_name || ''),
              (place.address_components[2] && place.address_components[2].short_name || '')
                      ].join(' ');
                }


                activeInfoWindow = infowindow;
                infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + place.formatted_address + '<br>' +
                "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>");
                infowindow.open(map, marker);
                document.getElementById("tab1").innerHTML += "<div><strong><p>" + place.name + "</strong><br>" + place.formatted_address + "</br>" + place.geometry.location + "</p></div>";
            });

            if (marker_reference) {
                getReviews();
                createPhoto();
            }
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

           // alert('in Getdirection');
           // alert(dest);
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


        function getReviews() {
            document.getElementById("reviews").innerHTML = "";
            document.getElementById("reviewsPlaceName").innerHTML = "";
            var request = {
                reference: marker_reference
            };

            var infowindow = new google.maps.InfoWindow();
            var service = new google.maps.places.PlacesService(map);

            service.getDetails(request, function (place, status) {
                if (status == google.maps.places.PlacesServiceStatus.OK) {
                   
                    if (!place.reviews)
                        return;
                    var d1 = new Date(0);
                    var displayDate;
                    document.getElementById("reviewsPlaceName").innerHTML = "<p><i><strong>" + place.name + "'s</strong> Reviews</i></p>";
                    for (var i = 0; i < place.reviews.length; i++) {
                        if (place.reviews[i].text)
                        document.getElementById("reviews").innerHTML += "<p><strong><i>" + place.reviews[i].author_name + "</strong> says:</i></p><p>" + place.reviews[i].text + "</br></p>";
                    }
                }
             });
            }


             function createPhoto() {
                 document.getElementById("photos").innerHTML = "";
                 document.getElementById("photosName").innerHTML = "";

                 var request = {
                     reference: marker_reference
                 };

                 var infowindow = new google.maps.InfoWindow();
                 var service = new google.maps.places.PlacesService(map);

                 service.getDetails(request, function (place, status) {
                     if (status == google.maps.places.PlacesServiceStatus.OK) {
                         var photos = place.photos;
                         if (!photos) {
                             return;
                         }
                         document.getElementById("photosName").innerHTML = "<p><i><strong>" + place.name + "'s</strong> Photos</i></p>";
                         for (var i = 0; i < place.photos.length; i++) {
                             document.getElementById("photos").innerHTML += "<p><img src = '" + photos[i].getUrl({ 'maxWidth': 200, 'maxHeight': 200 }) + "'/></p>"
                             //document.getElementById("slider").innerHTML += "<a class='fancybox' rel='gallery1' href='" + photos[i].getUrl({ 'maxWidth': 500, 'maxHeight': 400 }) + "'><img src= '" + photos[i].getUrl({ 'maxWidth': 500, 'maxHeight': 400 }) + "' width='150px' height='150px' alt=\"\" /></a>"
//                             document.getElementById("mycarousel").innerHTML += "<li><a class=\"fancybox\" rel='gallery1' href='" + photos[i].getUrl({ 'maxWidth': 500, 'maxHeight': 400 }) + "'><img src='" + photos[i].getUrl({ 'maxWidth': 500, 'maxHeight': 400 }) + "' width='75' height='75' alt=\"\" /></a></li>"
                                }
                     }
                 });
                                      
                }

                function calcMapZoom() {

                    
                    var dist = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);
                    

                    var bounds = new google.maps.LatLngBounds();

                    if(dist > d1){
                    bounds.extend(map_center);
                    bounds.extend(marker_pos);
                    map.fitBounds(bounds);
                     d1 = dist;
                     alert(d1);
                    }

                    //alert(d1);
                    
                    //if(dist > 1000)
                    // map.setZoom(1);
                }

//                    function sliderFun() {
//                        $(".fancybox").fancybox({
//                            openEffect: 'fade',
//                            closeEffect: 'elastic',
//                            helpers: {
//                                media: true
//                            }
//                        });
//                    }
                   // my_slider_fun = sliderFun;
               // });


        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
    <style type="text/css">
        #tabs ul
        {
            padding: 0px;
            margin: 0px;
            margin-left: 0px;
            list-style-type: none;
        }
        
        #tabs ul li
        {
            display: inline-block;
            clear: none;
            float: left;
            height: 24px;
        }
        
        #tabs ul li a
        {
            position: relative;
            margin-top: 16px;
            display: block;
            margin-left: 0px;
            line-height: 24px;
            padding-left: 10px;
            background: #f6f6f6;
            z-index: 9999;
            border: 1px solid #ccc;
            border-bottom: 0px;
            -moz-border-radius-topleft: 4px;
            border-top-left-radius: 4px;
            -moz-border-radius-topright: 4px;
            border-top-right-radius: 4px;
            width: 130px;
            color: #000000;
            text-decoration: none;
            font-weight: bold;
        }
        
        #tabs ul li a:hover
        {
            text-decoration: underline;
        }
        
        #tabs #Content_Area
        {
            padding: 0 15px;
            clear: both;
            
            line-height: 19px;
            position: relative;
            top: 20px;
            z-index: 5;
            
            overflow: auto;
            background-color: Gray;
            
            border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 250px; max-height: 380px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding: 10 10 10 10;
            
        }
        
        p
        {
            padding-left: 15px;
        }
    </style>
    <script type="text/javascript">
        function tab(tab) {
            document.getElementById('tab1').style.display = 'none';
            document.getElementById('tab2').style.display = 'none';
            document.getElementById('li_tab1').setAttribute("class", "");
            document.getElementById('li_tab2').setAttribute("class", "");
            document.getElementById(tab).style.display = 'block';
            document.getElementById('li_' + tab).setAttribute("class", "active");
        }
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <p class="style3">
        <span class="style1"><strong><em>Experiment: Obtaining Place's reviews and Photos</em></strong></p>
    <span class="style2">
        <p>
            <strong><em>Experiment: </em></strong>
        </p>
        <p style="width: 900px">
            This Experiment is basically same as the previous one, except it also provides the
            user reviews and photos from theGoogle Place detail service.</p>
        <hr />
        <p style="width: 900px">
            <strong><em>Documentation:</em></strong></p>
        <p style="width: 900px">
            The Google&#39;s place details service gives the detailed informations about the
            places like, detailed address of the place location, Google user reviews, url to
            google page and photos.</p>
        <p style="width: 900px">
            In this experiment I have extracted the google user&#39;s reviews about the places
            and the places photo&#39;s. They are displayed once a marker on the map or a particular
            place on the list. Note: the reviews and photos are only displayed if the API has
            it for that place.</p>
        <p style="width: 899px">
            For generating the place detail request, we need the reference if the particular
            place. This reference id is obtained from the results obtained by the place search
            request (nearBySearch or textSearch).
        </p>
        <p style="width: 900px">
            Once you have a <code>reference</code>
        from a Place Search, you can request more details about a particular establishment
        or point of interest by initiating a Place Details request. A Place Details request
        returns more comprehensive information about the indicated place such as its complete
        address, phone number, user rating and reviews.</span></p>
    <p style="width: 900px" class="style2">
        Also, the distance selector select box is replaced by cool jQuery UI slider. This
        is disabled at the page load, once you check the near by search option, it allows
        you to select the distance in meters you want to travel.</span></p>
    <hr />
    <p>
        <span class="style4"><strong><em>Experiment Demo:</em></strong></p>
    <p style="width: 900px">
        Search for a place and select the place on map or the list-panel for the reviews
        and photos</span></p>
    <div>
        <input id="searchTextField" type="text" size="50" />
        <asp:Button ID="find" runat="server" Text="Search Places" OnClientClick="initialize()" />
        <asp:CheckBox ID="AutoFillCheck" runat="server" OnClick="initialize()" Text="Use AutoComplete" />
        <asp:CheckBox ID="nearbySearch" runat="server" Text="Search Nearby" OnClick="initialize()" />
        <%--&nbsp;Travel Distance(in meters)&nbsp;
        <select id="radii" onchange="initialize()" disabled>
            <option>10</option>
            <option>100</option>
            <option>500</option>
            <option>1000</option>
            <option>5000</option>
            <option>10000</option>
        </select>--%>
    </div>
    <input type="text" id="distance" style="border: 0px; color: red; font-weight: bold;
        width: 500px" />
    <div id="slider-range" style="height: 12px; width: 500px; margin-left: 30px">
    </div>
    <br />
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
                <%--<span id="text" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 250px; max-height: 380px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding: 10 10 10 10;"></span>--%>
                <div id="tabs">
                    <ul>
                        <li id="li_tab1" onclick="tab('tab1')"><a>Place List</a></li>
                        <li id="li_tab2" onclick="tab('tab2')"><a>Directions</a></li>
                    </ul>
                    <div id="Content_Area">
                        <div id="tab1">
                            
                        </div>
                        <div id="tab2" style="display: none;">
                            
                        </div>
                    </div>
                </div>
            </td>
            <td>
                <div id="directionsPanel" style="border-bottom: silver 1px solid; text-align: left;
                    border-left: silver 1px solid; background-color: #f4f4f4; margin: 20px 0px 10px;
                    max-width: 250px; max-height: 380px; overflow: auto; border-top: silver 1px solid;
                    border-right: silver 1px solid; padding: 10 10 10 10;">
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <div id="reviewsPlaceName">
                </div>
                <div id="reviews" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 600px; max-height: 250px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding: 10 10 10 10;">
                </div>
            </td>
            <td>
                <div id="photosName">
                </div>
                <div id="photos" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 500px; max-height: 250px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding-left: 10px; padding-right: 10px;">
                </div>
            </td>
        </tr>
    </table>
    <%--    <ul id="mycarousel" class="jcarousel-skin-tango"></ul>

    <ul id="list"></ul>--%>
    </form>
    <hr />
    <p>
        &nbsp;</p>
    <p>
        <a href="../fileview/Default.aspx?~/experiments/ReviewsPhotos.aspx" target="_blank">
            <strong><em>Page Source</em></strong></a></p>
    <p>
        <strong><em>References</em></strong></p>
    <p>
        <a href="https://developers.google.com/places/documentation/details">Google Place Detail
            API Documentation</a></p>
</body>
</html>

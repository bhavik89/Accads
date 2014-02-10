<!DOCTYPE html>
<html>
<head>
    <title>Autocomplete</title>
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
            margin-top: 0.6em;
        }
        
        input
        {
            border: 1px solid rgba(0, 0, 0, 0.5);
        }
        
        input.notfound
        {
            border: 2px solid rgba(255, 0, 0, 0.4);
        }
    </style>
    <script type="text/javascript">

    var locs = [];
    var map;
    var infowindow;

    function initialize() {

        navigator.geolocation.getCurrentPosition(
               function (position) {
                   maploc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                   AutoCheck(maploc);
               });
            }   
                 
            /*    map = new google.maps.Map(document.getElementById('map-canvas'), {
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: maploc,
                zoom:15
            }); */
            

           /* var newmarker = new google.maps.Marker({
                map: map,
                title: "You are Here",
                position: maploc,
                //size: 20
            });
           });*/
                

                 function AutoCheck(maploc){
                 if(document.getElementById("AutoFillCheck").checked)
                     AutoComplete(maploc);
                 
                 else
                     textSearchMap(maploc);
                    }
                   

        function textSearchMap(maploc) {

           map = new google.maps.Map(document.getElementById('map-canvas'), {
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: maploc,
                zoom: 13
            }); 
            

            var newmarker = new google.maps.Marker({
                map: map,
                title: "You are Here",
                position: maploc,
                //size: 20
            });

            alert('InTextSearch');
              
            var place_inp = document.getElementById("searchTextField").value ;

            var request = {
                query: [place_inp],
                location : maploc,
               // location: (42.34037,-71.088735),                
                radius: '500'
                //types: ['cafe']
            };            

           

            infowindow = new google.maps.InfoWindow();
            
            var service = new google.maps.places.PlacesService(map);
            service.textSearch(request, callback);
             
            document.getElementById("text").innerHTML = "";
        }

        function callback(results, status) {
       // alert('InCallBack');
            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    {
                        createMarker(results[i]);
                       document.getElementById("text").innerHTML += "<div><strong><p>" + results[i].name + "</strong></br>" + results[i].formatted_address + "</br>" + results[i].geometry.location + "</p></div>";
                    }
                }
            }
        }

        function createMarker(place) {
       // alert('InCallBack');
            var placeLoc = place.geometry.location;
            var marker = new google.maps.Marker({
                map: map,
                position: place.geometry.location
            });

            google.maps.event.addListener(marker, 'click', function () {

                var rating = place.rating;
                if (rating == undefined)
                rating = "N/A";
                else
                rating = rating;

                infowindow.setContent("<div><strong><p>" + place.name + "</strong></br>" + place.formatted_address + "</br> Ratings: " + rating + "</p></div>");
                infowindow.open(map, this);
            });
        }


         
         function  AutoComplete(maploc){

            

            //var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

            var input = document.getElementById('searchTextField');
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
                document.getElementById("text").innerHTML = "";
                var address = '';
                if (place.address_components) {
                    address = [
              (place.address_components[0] && place.address_components[0].short_name || ''),
              (place.address_components[1] && place.address_components[1].short_name || ''),
              (place.address_components[2] && place.address_components[2].short_name || '')
                      ].join(' ');
                }

                infowindow.setContent('<div><strong>' + place.name + '</strong><br>' + place.formatted_address);
                infowindow.open(map, marker);
                document.getElementById("text").innerHTML += "<div><strong><p>" + place.name + "</strong><br>" + place.formatted_address + "</br>" + place.geometry.location + "</p></div>";
            });

            // Sets a listener on a radio button to change the filter type on Places
            // Autocomplete.
          /*  function setupClickListener(id, types) {
                var radioButton = document.getElementById(id);
                google.maps.event.addDomListener(radioButton, 'click', function () {
                    autocomplete.setTypes(types);
                });
            }

            setupClickListener('changetype-all', []);
            setupClickListener('changetype-establishment', ['establishment']);
            setupClickListener('changetype-geocode', ['geocode']); */
        }
        google.maps.event.addDomListener(window, 'load', initialize);
    </script>
</head>
<body>
    <form id="form1" runat="server">
    <div>
        <input id="searchTextField" type="text" size="50">
        <%-- <input type="radio" name="type" id="changetype-all" checked="checked">
      <label for="changetype-all">All</label>

      <input type="radio" name="type" id="changetype-establishment">
      <label for="changetype-establishment">Establishments</label>

      <input type="radio" name="type" id="changetype-geocode">
      <label for="changetype-geocode">Geocodes</lable> --%>
        <asp:Button ID="find" runat="server" Text="Search Places" OnClientClick="initialize()" />
        <asp:CheckBox ID="AutoFillCheck" runat="server" OnClick="initialize()" Text="Use AutoComplete" />
        <%--<input id="AutoFillCheck" type="checkbox"  /> --%>
    </div>
    <asp:ScriptManager ID="SM1" runat="server">
    </asp:ScriptManager>
    <asp:UpdatePanel ID="map_update" runat="server">
        <ContentTemplate>
            <div id="map-canvas">
            </div>
        </ContentTemplate>
        <Triggers>
            <asp:AsyncPostBackTrigger ControlID="AutoFillCheck" EventName="CheckedChanged" />
            <asp:AsyncPostBackTrigger ControlID="find" EventName="Click" />
        </Triggers>
    </asp:UpdatePanel>
    <div id="text" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
        background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 200px; max-height: 200px;
        overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;">
    </div>
    </form>
</body>
</html>

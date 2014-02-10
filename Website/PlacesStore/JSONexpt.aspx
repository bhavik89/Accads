<%@ Page Language="C#" %>

<!DOCTYPE html>

<html>
<head runat="server">
   

    <meta charset="utf-8">
    <title>Google Maps JavaScript API v3 Example: Place Search</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <%-- <script type="text/javascript" charset="utf-8"  src="store-locator.compiled.js"></script>--%>
     <script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places"></script>
    
    <style>
      #map {
        height: 400px;
        width: 600px;
        border: 1px solid #333;
        margin-top: 0.6em;
      }
        .style1
        {
            font-size: x-large;
            text-align: center;
        }
        .style2
        {
            color: rgb(0, 0, 0);
        }
        .displayInline
        { 
            display:inline;
            position:relative;
        }
    </style>

    <script type="text/javascript">
        var map;
        var infowindow;
        var prev = 0;
        var activeInfoWindow;
        var map_marker = [];
        var geocoder;
        var directionsService;
        var directionsDisplay;
        var nearBySel;
        var textSel;

        function initialize() {

            //   if (navigator.geolocation) {
          
                navigator.geolocation.getCurrentPosition(
               function (position) {
                  
                   var maploc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                    //       var maploc = new google.maps.LatLng(39, -97);
                   plotmap(maploc);
               });
      //         }
      //       else {
      //          var maploc = new google.maps.LatLng(39, -97);
      //          alert("plotting default");
      //          plotmap(maploc);
      //      }
        }
        
        function plotmap(maploc) {


            map = new google.maps.Map(document.getElementById('map'), {
                mapTypeId: google.maps.MapTypeId.ROADMAP,
                center: maploc,
                zoom:15
            });


            var newmarker = new google.maps.Marker({
                map: map,
                title : "You are Here",
                position: maploc,
                size: 20
             });
            
            var rad_inp = document.getElementById("radii").options[document.getElementById("radii").selectedIndex].value;
           // document.getElementById("valueSel").innerHTML = rad_inp;
            var place_inp = document.getElementById("place_input").value ;

            var nearbySearch = document.getElementById("nearBy").checked;
            var textSearch = document.getElementById("textSearch").checked;

            textSel = textSearch;
            searchSel = nearbySearch;
            //alert(searchSel);

            if (nearbySearch) 
            {
                var request = {
                    location: maploc,
                    name: place_inp,
                    radius: rad_inp
                    // types: ['store']
                };
            }

            if (textSearch) 
            {
                var request = {
                    location: maploc,
                    query: place_inp,
                    radius: '200'
                                        // types: ['store']
                };
            }
            

            infowindow = new google.maps.InfoWindow();


            if (nearbySearch) {
                var service = new google.maps.places.PlacesService(map);
                service.nearbySearch(request, callback);
            }

            if (textSearch) {
                var service = new google.maps.places.PlacesService(map);
                service.textSearch(request, callback);
            }

            document.getElementById("text").innerHTML = "";
        }

        function callback(results, status) {
            if (status == google.maps.places.PlacesServiceStatus.OK) {
            map_marker = [];
                for (var i = 0; i < results.length; i++) 
                    {
                        createMarker(results[i]);
                        if (searchSel) {
                          document.getElementById("text").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onMouseover=" + "\"alertMarker(" + i + ");\"" + ">" +
                         results[i].name + "</br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p>";
                        }
                        if (textSearch) {
                            document.getElementById("text").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onMouseover=" + "\"alertMarker(" + i + ");\"" + ">" +
                         results[i].name + "</br>" + results[i].formatted_address + "</br>" + results[i].geometry.location + "</p>";
                        }
                    }

                 }
            }
        

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

                if (searchSel) {

                    newmarker['infowindow'] = new google.maps.InfoWindow({
                        content: place.name + "</br>" + place.vicinity + "</br> Ratings: " + rating + "</br>" +
                        "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>"
                    });
                }
                if (textSearch) {
                    newmarker['infowindow'] = new google.maps.InfoWindow({
                        content: place.name + "</br>" + place.formatted_address + "</br> Ratings: " + rating + "</br>" +
                        "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>"
                    });
                }

            google.maps.event.addListener(newmarker, 'click', function () {
            if(activeInfoWindow == this['infowindow'])
            return;

            if (activeInfoWindow ) {
            activeInfoWindow.close(); 
            }

            this['infowindow'].open(map, this);
            activeInfoWindow = this['infowindow'];
           
           this['infowindow'].open(map, this);
            
            });

            map_marker.push(newmarker);
        }

        function alertMarker(mark){
        alert(mark);
       // for(var mv = 0; mv <= results.length; mv++){
       // map_marker[mv]['infowindow'].close();
       // }
                
       if((prev != 0  && mark != 0) || (prev == 0 && mark !=0) || (prev != 0  && mark == 0))
            map_marker[prev]['infowindow'].close();
       
       if (activeInfoWindow ) {
            activeInfoWindow.close(); 
         }

        map_marker[mark]['infowindow'].open(map, map_marker[mark]);
        
        activeInfoWindow = map_marker[mark]['infowindow'];
        prev = mark;
        }


    geocoder = new google.maps.Geocoder;
    directionsService = new google.maps.DirectionsService();
    directionsDisplay = new google.maps.DirectionsRenderer({
      suppressMarkers: false
    });
    directionsDisplay.setMap(map);
    directionsDisplay.setPanel(document.getElementById("directionsPanel"));
  


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

       google.maps.event.addDomListener(window, 'load', initialize);
    </script>

</head>
<body>
    <form id="form1" runat="server">
    
    <asp:ScriptManager ID="SM1" runat="server"> </asp:ScriptManager>
<asp:UpdatePanel ID="map_update" runat="server">
<ContentTemplate>
    <div id="map"></div>
    <div id="text" style="border-bottom: silver 1px solid; 
    text-align: left; border-left: silver 1px solid; 
    background-color: #f4f4f4; margin: 20px 0px 10px; 
    width: 58.7%;  
    max-height: 300px; overflow: auto; border-top: silver 1px solid; 
    border-right: silver 1px solid; max-width:200px; float:left"></div>

    </ContentTemplate>
<Triggers>
<asp:AsyncPostBackTrigger ControlID="find" EventName="Click" />

</Triggers>
</asp:UpdatePanel>

    <br />
    Search Places:&nbsp;

    <asp:TextBox ID="place_input" runat="server"></asp:TextBox>
    &nbsp;Travel Distance(in meteres)&nbsp;
    <asp:DropDownList ID="radii" runat="server">
     <asp:ListItem Selected></asp:ListItem>
    <asp:ListItem>10</asp:ListItem>
    <asp:ListItem >100</asp:ListItem>
    <asp:ListItem>1000</asp:ListItem>
    <asp:ListItem>5000</asp:ListItem>
    <asp:ListItem>10000</asp:ListItem>
    <asp:ListItem>20000</asp:ListItem>
    <asp:ListItem>40000</asp:ListItem>
    <asp:ListItem>50000</asp:ListItem>
    </asp:DropDownList>

    
    <input type="radio"  id="nearBy" name= "searchType" value="nearBy" checked />Search Nearby
    <input type="radio"  id="textSearch" name = "searchType" value="textSearch" />Search by Text
   
    <br />
    <br />



    <asp:Button ID="find" runat="server" Text="Find Places" OnClientClick="initialize()"/>
    <div id="directionsPanel"></div>
    <%--<button ID="find" OnClick="initialize()">Find Places</button>--%>
    <div id="valueSel"></div>
    
    
    </form>
    <p>
    
</body>
</html>

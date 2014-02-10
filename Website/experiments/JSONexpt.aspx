<%@ Page Language="C#" %>

<!DOCTYPE html>

<html>
<head runat="server">
   

    <meta charset="utf-8">
    <title>Google Maps JavaScript API v3 Example: Place Search</title>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>

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
    </style>

    <script>
        var map;
        var infowindow;


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
                zoom: 12
            });

            var rad_inp = document.getElementById("radii").options[document.getElementById("radii").selectedIndex].value;
           // document.getElementById("valueSel").innerHTML = rad_inp;
            var place_inp = document.getElementById("place_input").value ;

            var request = {
                location: maploc,
                name: [place_inp],
                radius: rad_inp,
                //types: ['cafe']
            };

            

            infowindow = new google.maps.InfoWindow();
            var service = new google.maps.places.PlacesService(map);
            service.nearbySearch(request, callback);
            document.getElementById("text").innerHTML = "";
        }

        function callback(results, status) {
            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    {
                        createMarker(results[i]);
                        document.getElementById("text").innerHTML += "<p>" + results[i].name + "</br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p>";
                    }
                }
            }
        }

        function createMarker(place) {
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

                infowindow.setContent(place.name + "</br>" + place.vicinity + "</br> Ratings: " + rating);
                infowindow.open(map, this);
            });
        }

       google.maps.event.addDomListener(window, 'load', initialize);
    </script>

</head>
<body>
    <form id="form1" runat="server">
    <p class="style1">
        <strong><em>Experiment: Generating Dynamic Queries for Places in Google Maps</em></strong></p>
        <hr />
    <p style="width: 987px">
        <strong><em>Experiment:</em></strong> This experiment deals with generating the dynamic query based on the 
        user inputs and preferences for the places, and plotting it on the maps.</p>
        <hr />
    <p>
        <strong><em>Documentation:</em></strong>
    </p>
    <p style="width: 1001px">
        Unlike the previous experiment, In this experiment, a dynamic Query is generated 
        by the user for the place he/she wants to visit by giving the name of the place 
        and the distance in meters he/she wishes to travel from the current location.</p>
    <p style="width: 1001px">
        This experiment demonstrates the usage of Google Maps API with the places 
        library. For locating a place, the user inputs the name of the place he/she 
        wishes to visit and also the distance in meters of the circle&#39;s radius around 
        the current position, and based on this inputs from user, the javascript 
        generates the requests for the places nearby matching the user requirements. It 
        uses the places utility provided by the Google API to find the places nearby the 
        current location.
    </p>
    <p style="width: 1000px">
        The result is obtained in JSON. Important information of the places like Name, 
        Location, Street address is parsed from this JSON and represented on the map.</p>
    <p style="width: 1000px">
        The experiment uses AJAX, so that only the Map is refreshed when the user 
        requests for new location.</p>
        <hr />
    <p style="width: 1000px">
        <strong><em>Implementation:</em></strong></p>
    <p style="width: 1000px">
        The Map Will take your current location, (Please allow to Share your location 
        when prompted by the browser), and will center around this location.
    </p>
    <p style="width: 1000px">
        Type the name of the place you want to visit, for example, Starbucks, Qdoba etc 
        or any keywords like Pizza, Coffee, Prudential etc and select the distance from 
        the dropdown menu you wish to travel from your location and hit find places 
        button to get the location.
    </p>
    <p style="width: 1000px">
        The information about the places found will be shown below the map.</p>
    <hr />

    <asp:ScriptManager ID="SM1" runat="server"> </asp:ScriptManager>
<asp:UpdatePanel ID="map_update" runat="server">
<ContentTemplate>
    <div id="map"></div>

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
    <asp:ListItem>10</asp:ListItem>
    <asp:ListItem>100</asp:ListItem>
    <asp:ListItem>1000</asp:ListItem>
    <asp:ListItem>5000</asp:ListItem>
    <asp:ListItem>10000</asp:ListItem>
        <asp:ListItem>20000</asp:ListItem>
        <asp:ListItem>40000</asp:ListItem>
        <asp:ListItem>50000</asp:ListItem>
        </asp:DropDownList>



    <br />
    <br />



    <asp:Button ID="find" runat="server" Text="Find Places" OnClientClick="initialize()"/>

    <%--<button ID="find" OnClick="initialize()">Find Places</button>--%>
    <div id="valueSel"></div>
    <div id="text" style="border-bottom: silver 1px solid; 
    text-align: left; border-left: silver 1px solid; 
    background-color: #f4f4f4; margin: 20px 0px 10px; 
    width: 58.7%;  
    max-height: 200px; overflow: auto; border-top: silver 1px solid; 
    border-right: silver 1px solid;"></div>
    
    </form>
    <p>
    <hr />
        <strong><em>Sources: </em></strong> 
    <p>
        <a href="../fileview/Default.aspx?~/experiments/JSONexpt.aspx" target="_blank">Page Source</a></p>
    <p>
        <strong><em>References :</em></strong></p>
    <p>
        <a href="https://developers.google.com/maps/documentation/javascript/places?hl=en#place_details" target="_blank">Google API Documentation</a></p>
    <p>
        <a href="http://net4.ccs.neu.edu/home/rasala/" target="_blank">Prof. Rasala&#39;s experiements on Maps</a></p>
    <p>
        <span style="color: rgb(0, 0, 0); font-family: 'Times New Roman'; font-style: normal; font-variant: normal; font-weight: normal; letter-spacing: normal; line-height: normal; orphans: auto; text-align: justify; text-indent: 0px; text-transform: none; white-space: normal; widows: auto; word-spacing: 0px; -webkit-text-size-adjust: auto; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255);">
        <span class="style2">
        <a href="http://net4.ccs.neu.edu/home/rasala/shivanshsingh/webapis/documentation/DocumentationOverview.htm" 
            target="_blank">Shivansh 
        Singh Experiments on Web APIs</a></span></span></p>
</body>
</html>

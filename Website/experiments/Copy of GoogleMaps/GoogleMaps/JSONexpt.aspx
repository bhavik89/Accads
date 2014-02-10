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
                for (var i = 0; i < results.length; i++) 
                    {
                        createMarker(results[i]);
                        document.getElementById("text").innerHTML += "<p>" + results[i].name + "</br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p>";
                    }

                     var stores = [];

           for (var i = 0, result; result = results[i]; i++) {
           var latLng = result.geometry.location;
           var store = new storeLocator.Store(result.id, latLng, null, {
               title: result.name,
               address: result.types.join(', '),
               icon: result.icon
           });
           stores.push(store);
       }

       callback(stores);


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
    max-height: 300px; overflow: auto; border-top: silver 1px solid; 
    border-right: silver 1px solid; max-width:200px"></div>
    
    </form>
    <p>
    
</body>
</html>

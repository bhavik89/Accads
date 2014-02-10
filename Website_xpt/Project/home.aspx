
<%--<%@ Page Language="C#" %>

<%@ Import Namespace="edu.neu.ccis.bhavik.commentData" %>
<%@ Import Namespace="System" %>
<%@ Import Namespace="System.Collections.Generic" %>--%>
<!DOCTYPE html>
<%--<script runat="server">
  
    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            Session["update"] = Server.UrlEncode(System.DateTime.Now.ToString());

            commentDataDataContext context = new commentDataDataContext();
            commentData newcomment = new commentData();
            var commentTable = from tabl in context.commentData select tabl;

            name.Text = "";
            comment.Text = "";

            commentSpace.InnerHtml = "";

            foreach (var c in commentTable)
            {
                commentSpace.InnerHtml += "<u><i>";
                commentSpace.InnerHtml += "<b>" + c.username + "</b>";
                commentSpace.InnerHtml += " posted on ";
                commentSpace.InnerHtml += c.date;
                commentSpace.InnerHtml += " at ";
                commentSpace.InnerHtml += c.time;
                commentSpace.InnerHtml += "</u></i>";
                commentSpace.InnerHtml += "</br>";
                commentSpace.InnerHtml += "</br>";
                commentSpace.InnerHtml += c.comments;
                commentSpace.InnerHtml += "<hr>";
                commentSpace.InnerHtml += "</br>";
                commentSpace.InnerHtml += "</br>";
            }

        }

        if (Page.User.Identity.IsAuthenticated)
        {
            loggedin.Text = "logged in!!";
            name.Enabled = true;
            comment.Enabled = true;
            post.Enabled = true;

        }
    }


    protected void post_Click(object sender, EventArgs e)
    {
        //  if (Session["update"].ToString() == ViewState["update"].ToString())
        //{
        if (Session["update"].ToString() == ViewState["update"].ToString())
        {


            if (name.Text != null && comment.Text != null)
            {
                commentDataDataContext context = new commentDataDataContext();
                commentData newcomment = new commentData();

                var date = System.DateTime.Now.ToLongDateString();

                newcomment.username = name.Text;
                newcomment.comments = comment.Text;
                newcomment.date = System.DateTime.Now.ToLongDateString();
                newcomment.time = System.DateTime.Now.ToShortTimeString();

                context.commentData.InsertOnSubmit(newcomment);

                context.SubmitChanges();
                name.Text = "";
                comment.Text = "";
            }


            Session["update"] = Server.UrlEncode(System.DateTime.Now.ToString());
        }

        else
        {
        }

        name.Text = "";
        comment.Text = "";
    }
    //}

    //  protected void post_PreRender(object sender, EventArgs e)
    //  {
    //      ViewState["update"] = Session["update"];
    //  }

    protected override void OnPreRender(EventArgs e)
    {
        base.OnPreRender(e);
        ViewState["update"] = Session["update"];

        commentDataDataContext context1 = new commentDataDataContext();
        commentData newcomment1 = new commentData();
        var commentTable = from tabl in context1.commentData select tabl;


        commentSpace.InnerHtml = "";

        foreach (var c in commentTable)
        {
            commentSpace.InnerHtml += "<u><i>";
            commentSpace.InnerHtml += "<b>" + c.username + "</b>";
            commentSpace.InnerHtml += " posted on ";
            commentSpace.InnerHtml += c.date;
            commentSpace.InnerHtml += " at ";
            commentSpace.InnerHtml += c.time;
            commentSpace.InnerHtml += "</u></i>";
            commentSpace.InnerHtml += "</br>";
            commentSpace.InnerHtml += "</br>";
            commentSpace.InnerHtml += c.comments;
            commentSpace.InnerHtml += "<hr>";
            commentSpace.InnerHtml += "</br>";
            commentSpace.InnerHtml += "</br>";
        }

        name.Text = "";
        comment.Text = "";
    }

    protected void loginViewfn(object sender, EventArgs e)
    {


    }
</script>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head id="Head1" runat="server">
  <title>NearMe</title>
    <link href="CSS/home.css" rel="stylesheet" media="screen, projection" />
    <style type="text/css">
       
    </style>
    <link href="CSS/selectbox.css" rel="stylesheet" type="text/css" />
    <script src="JS/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="JS/jquery.selectbox-0.2.js" type="text/javascript"></script>
    <script type="text/javascript">
        var selbox = jQuery.noConflict();
        selbox(document).ready(function () {
            selbox(function () {
                selbox(".select_typ").selectbox();
            });

            selbox(document).keypress(function (e) {
                var event = e || window.event;
                var key = event.charCode || event.keyCode
                if (key == 13 || key == 10) {
                    if (document.getElementById("searchTextField").value)
                        initialize();
                }
            });
        });
    </script>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js" type="text/javascript"></script>
    <script src="https://maps.googleapis.com/maps/api/js?v=3.exp&sensor=false&libraries=places,geometry" type="text/javascript"></script>
    <style type="text/css">
       
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

    <style type="text/css">
    

</style>

<%--<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox.css?v=2.1.4" media="screen" />
<link rel="stylesheet" href="fancybox/jquery.fancybox.css" type="text/css" />
<link rel="stylesheet" type="text/css" href="fancybox/jquery.fancybox-thumbs.css?v=1.0.7" />
<script type="text/javascript" src="fancybox/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.js"></script>
<script type="text/javascript" src="fancybox/jquery.fancybox.js?v=2.1.4"></script>
<script type="text/javascript" src="fancybox/jquery.fancybox-thumbs.js?v=1.0.7"></script>--%>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.4.4.js"></script>
    <script src="fancybox/jquery.mousewheel-3.0.4.pack.js" type="text/javascript"></script>
    <script src="fancybox/jquery.fancybox-1.3.4.pack.js" type="text/javascript"></script>
    <link href="fancybox/jquery.fancybox-1.3.4.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

    var fancy = jQuery.noConflict();

    fancy(document).ready(function () {
        fancy(".fancybox").fancybox({
            openEffect: 'fade',
            closeEffect: 'elastic',
            scrolling: 'yes',
            helpers: {
                media: true
            }
        });
    });



</script>


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
        var marker_reference;
        var marker_pos;
        var map_center;
        var selbox_index;
        var d1 = 0;

        function foodstuff_fn() {
            alert('in food!');
            selbox_index = 1;
            initialize();
        }


        function liesure_fn() {
            alert('In Liesure');
            selbox_index = 2;
            initialize();
        }

        function other_fn() {
            selbox_index = 3;
            initialize();
        }



        function initialize() {
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
                animation: google.maps.Animation.DROP

            });

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
            if (!place_inp) {
                switch (selbox_index) {
                    case 1:
                        {
                            place_inp = document.getElementById("foodStuff").options[document.getElementById("foodStuff").selectedIndex].value;
                        }
                        break;
                    case 2:
                        {

                            place_inp = document.getElementById("liesure").options[document.getElementById("liesure").selectedIndex].value;
                        }
                        break;
                    case 3:
                        {
                            place_inp = document.getElementById("other").options[document.getElementById("other").selectedIndex].value;
                        }
                        break;
                }
            }

            var request = {
                query: [place_inp],
                location: maploc,
                radius: 10,
                rankby: google.maps.places.RankBy.DISTANCE
            };

            infowindow = new google.maps.InfoWindow();

            var service = new google.maps.places.PlacesService(map);
            service.textSearch(request, callback);

            document.getElementById("tab1").innerHTML = "";
        }

        function callback(results, status, pagination) {
            var openNow;
            var openNowText = "N/A";
            if (status == google.maps.places.PlacesServiceStatus.OK) {

                for (var i = 0; i < results.length; i++) {
                    createMarker(results[i]);
                    // document.getElementById("tab1").innerHTML += "<div><strong><p>" + results[i].name + "</strong></br>" + results[i].geometry.location + "</p></div>";
                    placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

                    if (results[i].opening_hours) {
                        openNow = results[i].opening_hours.open_now;
                        if (openNow)
                            openNowText = "Open :D";
                        else
                            openNowText = "Closed :(";
                    }


                    document.getElementById("tab1").innerHTML += "<div class='hoverColor'><p id=" + "\"" + i + "\"" +
                         "onClick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].formatted_address + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles &nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</p></div>";
                }

                if (pagination.hasNextPage) {
                    var moreButton = document.getElementById('more');
                    moreButton.disabled = false;

                    google.maps.event.addDomListenerOnce(moreButton, 'click',
                              function () {
                                  moreButton.disabled = true;
                                  pagination.nextPage();
                              });
                }

            } //if end

        } //function end

        

        function createMarker(place) {

            var placeLoc = place.geometry.location;
            var newmarker = new google.maps.Marker({
                map: map,
                position: place.geometry.location,
                reference: place.reference
            });
            marker_pos = place.geometry.location;
            var rating = place.rating;
            if (rating == undefined)
                rating = "N/A";
            else
                rating = rating;

            var openNowText = "N/A";

            if (place.opening_hours) {
                var openNow = place.opening_hours.open_now;
                if (openNow)
                    openNowText = "Open :D";
                else
                    openNowText = "Closed :(";                
            }
            //alert(place.geometry);
            // markerLoc;
            //var markerlat = place.geometry.location.lat + "";
            //var markerlng = place.geometry.location.lng + "";
            //alert(markerlat);
            //alert(markerlng);

            calcMapZoom();
            placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);
            newmarker['infowindow'] = new google.maps.InfoWindow({
                content: '<strong>' + place.name + '</strong>' + "</br>" + place.formatted_address + "</br> Ratings: " + rating + "&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText +
                "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles</br>" +
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

                createPhoto();
                getReviews();

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
            document.getElementById('li_tab2').setAttribute("class", "active");
            document.getElementById('tab2').setAttribute("class", "active");
        }

        function alertMarker(mark) {
           
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

            createPhoto();
            getReviews();

        }



        function SearchNearBy(maploc) {
            var search_types = null;
          
            var place_inp = document.getElementById("searchTextField").value;
            if (!place_inp) {

                switch (selbox_index) {
                    case 1:
                        {
                            search_types = document.getElementById("foodStuff").options[document.getElementById("foodStuff").selectedIndex].value;
                        }
                        break;
                    case 2:
                        {

                            search_types = document.getElementById("liesure").options[document.getElementById("liesure").selectedIndex].value;
                        }
                        break;
                    case 3:
                        {
                            search_types = document.getElementById("other").options[document.getElementById("other").selectedIndex].value;
                        }
                        break;
                }
            }
            var typ = search_types + "";
        
            var rad_inp = document.getElementById("radii").options[document.getElementById("radii").selectedIndex].value;

            if (place_inp) {
                var request = {
                    location: maploc,
                    name: place_inp,
                    keyword: place_inp,
                    //radius: rad_inp, //'500'
                    //types: [typ]
                    rankBy: google.maps.places.RankBy.DISTANCE
                };
            }
            else {
                var request = {
                    location: maploc,
                    //keyword:place_inp,
                    //name: place_inp,
                    //radius: rad_inp, //'500'
                    types: [typ],
                    rankBy: google.maps.places.RankBy.DISTANCE
                };

            }

            infowindow = new google.maps.InfoWindow();

            var service = new google.maps.places.PlacesService(map);
            service.nearbySearch(request, nearbyCallback);

            document.getElementById("tab1").innerHTML = "";
        }

        function nearbyCallback(results, status, pagination) {
            var openNow;
            var openNowText = "N/A"
            if (status == google.maps.places.PlacesServiceStatus.OK) {
                for (var i = 0; i < results.length; i++) {
                    nearbyCreateMarker(results[i]);
                    //document.getElementById("text").innerHTML += "<div><strong><p>" + results[i].name + "<strong></br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p></div>";

                    placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

                   // document.getElementById("tab1").innerHTML += "<p id=" + "\"" + i + "\"" +
                   //      "onClick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\"><strong>" +
                   //      results[i].name + "</strong></br>" + results[i].formatted_address + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles</p>";

                    if (results[i].opening_hours) {
                        openNow = results[i].opening_hours.open_now;
                        if (openNow)
                            openNowText = "Open :D";
                        else
                            openNowText = "Closed :(";
                    }

                    document.getElementById("tab1").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onclick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].vicinity + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + "miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</p>";
                }

                if (pagination.hasNextPage) {
                    var moreButton = document.getElementById('more');

                    moreButton.disabled = false;

                    google.maps.event.addDomListenerOnce(moreButton, 'click',
                function () {
                    moreButton.disabled = true;
                    pagination.nextPage();
                });
                }

            }
        }


        function nearbyCreateMarker(place) {

            var placeLoc = place.geometry.location;
            var newmarker = new google.maps.Marker({
                map: map,
                position: place.geometry.location,
                reference: place.reference
            });
            marker_pos = place.geometry.location;
            var rating = place.rating;
            if (rating == undefined)
                rating = "N/A";
            else
                rating = rating;

            calcMapZoom();
            placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

            var openNowText = "N/A";
            var openNow;

            if (place.opening_hours) {
                openNow = place.opening_hours.open_now;
                if (openNow)
                    openNowText = "Open :D";
                else
                    openNowText = "Closed :(";
            }

            newmarker['infowindow'] = new google.maps.InfoWindow({
                content: '<strong>' + place.name + '</strong>' + "</br>" + place.vicinity + "</br> Ratings: " + rating +
                "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles &nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText +
                "</br>Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'> "
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
            document.getElementById('li_' + 'tab2').setAttribute("class", "active");
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
                    map.setZoom(15);  // 15 -> it looks good.
                }

                var openNowText = "N/A";

                if (place.opening_hours) {
                    var openNow = place.opening_hours.open_now;
                    if (openNow)
                        openNowText = "Open :D";
                    else
                        openNowText = "Closed :(";
                }


                marker_reference = place.reference;
                marker.setPosition(place.geometry.location);
                marker.setVisible(true);
                marker.setAnimation(google.maps.Animation.DROP);
                marker.setClickable(true);
                marker_pos = place.geometry.location;
                dest = place.geometry.location;
                map_marker.push(marker);

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

                var rating = place.rating;
                if (rating == undefined)
                    rating = "N/A";
                else
                    rating = rating;

                marker_pos = place.geometry.location;

                calcMapZoom();
                placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

                activeInfoWindow = infowindow;

                infowindow.setContent("<div><strong>" + place.name + "</strong><br>" + place.formatted_address + "<br>Ratings: " + rating +
                 "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>"+ openNowText +"</br>" +
                "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>");

                infowindow.open(map, marker);
                

                document.getElementById("tab1").innerHTML += "<div><strong><p onClick='' onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\">" + place.name +
                                                              "</strong></br>" + place.formatted_address + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>"+ openNowText +"</p></div>";
            });

            if (marker_reference) {
                createPhoto();
                getReviews();
            }

            google.maps.event.addListener(marker, 'click', function () {
                infowindow.open(map, marker);
            });

//            google.maps.event.addListener(marker, 'click', function () {

//                dest = this.position;
//                marker_reference = this.reference;


//                if (activeInfoWindow == this['infowindow'])
//                    return;

//                if (activeInfoWindow) {
//                    activeInfoWindow.close();
//                }

//                this['infowindow'].open(map, this);
//                activeInfoWindow = this['infowindow'];

//                this['infowindow'].open(map, this);

//                createPhoto();
//                getReviews();

//            });





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
                    document.getElementById('clientAddress').value = "Directions cannot be computed at this time.";
                }
            });

            tab('tab2');
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
                    document.getElementById("reviewsPlaceName").innerHTML = "<p style='padding:20px;'><i><strong>" + place.name + "'s</strong> Reviews</i></p>";
                    for (var i = 0; i < place.reviews.length; i++) {
                        if (place.reviews[i].text)
                            //document.getElementById("reviews").innerHTML += "<p><strong><i>" + place.reviews[i].author_name + "</strong> says:</i></p><p>" + place.reviews[i].text + "</br></p>";

                            document.getElementById("reviews").innerHTML += "<div class='comment'><p><strong><i>" + place.reviews[i].author_name + "</strong> says:</i></p><p>" + place.reviews[i].text + "</br></p></div>";
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
                    document.getElementById("photosName").innerHTML = "<p style='padding:20px;'><i><strong>" + place.name + "'s</strong> Photos</i></p>";


                    var textToInsert = '';
                    fancy.each(place.photos, function (i, item) {
                        if (i == 5) return false;
                        var imgThumb = photos[i].getUrl({ 'maxWidth': 150, 'maxHeight': 150 });
                        var imgLarge = photos[i].getUrl({ 'maxWidth': 500, 'maxHeight': 500 });
                        textToInsert += '<a class="fancybox" rel="place_images" href="' + imgLarge + '.jpg' + '"><img src="' + imgThumb +  '.jpg' +'" style="padding:5px"/></a>';

                    });

                    fancy('#photos').empty();
                    fancy('#photos').append(textToInsert);


                    fancy("a[rel=place_images]").fancybox({
                        'transitionIn': 'none',
                        'transitionOut': 'none',
                        'titlePosition': 'over',
                        'titleFormat': function (title, currentArray, currentIndex, currentOpts) {
                            return '<span id="fancybox-title-over">Image ' + (currentIndex + 1) + ' / ' + currentArray.length + (title.length ? ' &nbsp; ' + title : '') + '</span>';
                        }

                    });

                }
            });
        }

        function calcMapZoom() {
            var dist = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);
            var bounds = new google.maps.LatLngBounds();
            if (dist > d1) {
                bounds.extend(map_center);
                bounds.extend(marker_pos);
                map.fitBounds(bounds);
                d1 = dist;
              }
        }
        //alert(d1);

        //if(dist > 1000)
        // map.setZoom(1);

        google.maps.event.addDomListener(window, 'load', initialize);   

    </script>

    <script src="JS/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="JS/jQuery.fitmaps.js" type="text/javascript"></script>
    <script type="text/javascript">
        var mapfit = jQuery.noConflict();

        mapfit(document).ready(function () {
            // Target your .container, .wrapper, .post, etc.
            mapfit("#map-canvas").fitMaps({ w: '75%', h: '50%' });
        });
    
    
    </script>


</head>
<body style="background-color: rgb(190, 190, 255);">
    <form id="form1" runat="server" onsubmit="initialize();">
    <header class="pageStyleHeader pageHeader pos_rel">
      <h1><a href="#" title="home"></a></h1>
      <nav id="pageNav">
    <ul>
    <li><a href="#">Home</a></li>
    <li><a href="#">Documentation</a></li>
      <li>
         <select  class = "select_typ" name="foodStuff" id="foodStuff" tabindex="1" onchange="foodstuff_fn()">
			<option value="" >Hungry?</option>
			<optgroup label="Food">
				<option value="food">Food</option>
                <option value="bakery">Bakery</option>
				<option value="cafe">Cafe</option>
                <option value="restaurants">Restaurants</option>
            </optgroup>
			<optgroup label="Drinks">
				<option value="bar">Bar</option>
				<option value="liquor">Liquor Store</option>
			</optgroup>
			<option value="establishment">Other Store</option>
			<option value="store">Other</option>
		</select>
           </li>

           <li>
         <select class = "select_typ" name="liesure" id="liesure" tabindex="2" onchange="liesure_fn()">
			<option value="">Bored?</option>
			<optgroup label="Wanna Party?">
				<option value="casino">Casino</option>
                <option value="night_club">Go Clubbing</option>
		    </optgroup>
			<optgroup label="Visit Places">
                <option value="aquarium">Aquarium</option>
                <option value="art_gallery">Artistic</option>
                <option value="movie_theater">Cinemas</option>
                <option value="museum">Museums</option>
				<option value="amusement_park">Picnic</option>
				<option value="park">Park</option> 
                <option value="shopping_mall">Go Shopping</option>                
			</optgroup>
            <optgroup label="Religious">
			<option value="church">Church</option>
			<option value="hindu_temple">Temple</option>
            </optgroup>
		</select>
           </li>
           
         <%-- <li>
         <select class = "select_typ" name="foodStuff" id="liesure" tabindex="1" onchange="liesure()">
			<option value="">Bored?</option>
			<optgroup label="Wanna Party?">
				<option value="casino">Casino</option>
                <option value="night_club">Go Clubbing</option>
		    </optgroup>
			<optgroup label="Visit Places">
                <option value="aquarium">Aquarium</option>
                <option value="art_gallery">Artistic</option>
                <option value="movie_theater">Cinemas</option>
                <option value="museum">Museums</option>
				<option value="amusement_park">Picnic</option>
				<option value="park">Park</option> 
                <option value="shopping_mall">Go Shopping</option>                
			</optgroup>
            <optgroup label="Religious">
			<option value="church">Church</option>
			<option value="hindu_temple">Temple</option>
            </optgroup>
		</select>
           </li>--%>

          <li >
         <select class = "select_typ" name="other" id="other" tabindex="1" onchange="other_fn()">
			<option value="">More places</option>
			    <option value="airport">Airport</option>
                <option value="atm">Need Money?</option>
		        <option value="bank">bank</option>
                <option value="gas_station">Gas Station</option>
                <option value="health">Health care</option>
                <option value="library">Library</option>
                <option value="subway_station">Subway Station</option>          
		</select>
           </li>
           <li>
           
            <asp:LoginView
    ID="LoginView0"
    Runat="server">

    <LoggedInTemplate>

    
        <asp:LoginName
            ID="Login1"
            Runat="server"
            FormatString="Welcome <span class='red'>{0}</span>" />
    

    </LoggedInTemplate>

</asp:LoginView>
           
<asp:LoginStatus ID="LoginStatus2" runat="server" LoginText="Sign In">     
           
           </asp:LoginStatus>
           </li>



      
        </ul>
  </nav>
</header>
    <div class="pageContent">
        <div>
            <input id="searchTextField" type="text" size="50">
            <asp:Button ID="find" runat="server" Text="Search Places" OnClientClick="initialize()"/>
            <asp:CheckBox ID="AutoFillCheck" runat="server" OnClick="initialize()" Text="Use AutoComplete" />
          


            <span style="float: right; padding-right: 7px">
                <asp:CheckBox ID="nearbySearch" runat="server" Text="Search for Specific place Nearby"
                    OnClick="initialize()" /></span><br />
            <span style="float: right">Travel Distance(in meters)&nbsp;
                <select id="radii" onchange="initialize()" disabled>
                    <option>10</option>
                    <option>100</option>
                    <option>500</option>
                    <option>1000</option>
                    <option>5000</option>
                    <option>10000</option>
                </select>
            </span>
        </div>
        <asp:ScriptManager ID="SM1" runat="server">
        </asp:ScriptManager>
        <asp:UpdatePanel ID="map_update" runat="server">
            <ContentTemplate>
                <p>
                    <span class="style2"><em><strong>Find your favourite spot near you.</strong></em></span><br />
                    <span class="style3">Simply, find the places which suits your mood right now.</span>
                </p>
                <div style="width:70%;">
                <div id="map-canvas">
                </div>
                </div>
            </ContentTemplate>
            <Triggers>
                <asp:AsyncPostBackTrigger ControlID="nearbySearch" EventName="CheckedChanged" />
                <asp:AsyncPostBackTrigger ControlID="AutoFillCheck" EventName="CheckedChanged" />
                <asp:AsyncPostBackTrigger ControlID="find" EventName="Click" />
            </Triggers>
        </asp:UpdatePanel>
        <aside>
       <div id="tabs">
                   <p class="style1"><strong><em>List of Places found for you!!</em></strong></p>
                    <ul>
                        <li id="li_tab1" onclick="tab('tab1')"><a>Place List</a></li>
                        <li id="li_tab2" onclick="tab('tab2')"><a>Directions</a></li>
                    </ul>
                    <div id="Content_Area">
                        <div id="tab1"> <p>Find Places using the Options given above</p>
                        </div>
                        <div id="tab2" style="display: none;"> 
                        <p>No Directions to Display</p>
                        </div>
                        
                    </div> 
                    <button id="more"> More Similar Places </button> 
                    <a href="comments.aspx">Find other user views</a>             
               </div>
</aside>
<div style="clear:both";></div>
</div>

<div id="photosName" style=" margin-top:10px;"> </div>
<div class="container wrapper" style=" margin-top:10px; padding-top:10px">
<div id="photos">
<a class="fancybox" rel="gallery1" href='../experiments/fancybox/images/img1.jpg'>
            <img src="../experiments/fancybox/images/img1.jpg" width="150px" height="150px"/></a>
            <a class="fancybox" rel="gallery1" href='../experiments/fancybox/images/img1.jpg' >
            <img src="../experiments/fancybox/images/img1.jpg" width="150px" height="150px" /></a>
</div>
</div>

<div id="reviewsPlaceName"></div>
                <div id="reviews" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: transparent; margin-top: 20px; width:90%; height: 30%; max-height: 300px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding: 10 10 10 10; margin-left:auto; margin-right:auto;">
</div>


<%--<div id="photosName">
                </div>
                <div id="photos" style="border-bottom: silver 1px solid; text-align: left; border-left: silver 1px solid;
                    background-color: #f4f4f4; margin: 20px 0px 10px; max-width: 500px; max-height: 250px;
                    overflow: auto; border-top: silver 1px solid; border-right: silver 1px solid;
                    padding-left: 10px; padding-right: 10px;">
                </div>--%>


        <%--             
        <table>    
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
                    padding-left=10px; padding-right=10px;">
                </div>
            </td>
            </td>
        </tr>
    </table>
        <%--<div id="contentWrapper">
     <article id="mainContent">
      <h1>Search Location</h1>
      <article class="post">
    <p>Find your favourite spot near you. Just hit some buttons and it'll get a good choice for you. Simply, find the places which suits your mood right now.</p> 
</div>--%>
        <%--<asp:ScriptManager ID="ScriptManager1" runat="server"> </asp:ScriptManager>--%>
        <%-- --------------------------------------------------------------------------------------------------------------------------------------- --%>
        <%-- 
    <asp:UpdatePanel ID="commentUpdate" runat="server">
        <ContentTemplate>
            <div id="commentSpace" runat="server" style="border-bottom: silver 1px solid; text-align: left;
                border-left: silver 1px solid; background-color: #f4f4f4; margin: 20px 0px 10px;
                width: 58.7%; max-height: 200px; overflow: auto; border-top: silver 1px solid;
                border-right: silver 1px solid; padding-top: 5px; padding-bottom: 5px; padding-left: 5px;
                padding-right: 5px">
            </div>
        </ContentTemplate>
        <Triggers>
            <asp:AsyncPostBackTrigger ControlID="post" EventName="Click" />
        </Triggers>
    </asp:UpdatePanel>
    <script type="text/javascript">
        function loginViewfn() {
            alert('logged in...!!!');
        }
    </script>
    <table class="style1">
        <tr>
            <td class="style3">
                Name:
            </td>
            <td>
                <asp:TextBox ID="name" runat="server" Width="200px" Enabled="false"></asp:TextBox>
                <asp:RequiredFieldValidator ID="RequiredFieldValidator1" runat="server" ControlToValidate="name"
                    ErrorMessage="Please Enter Your Name" Font-Italic="True" Font-Names="Aparajita"
                    ForeColor="Red"></asp:RequiredFieldValidator>
            </td>
        </tr>
        <tr>
            <td class="style3">
                Comment:
            </td>
            <td>
                <asp:TextBox ID="comment" runat="server" EnableTheming="False" Height="80px" TextMode="MultiLine"
                    Width="700px" Enabled="false"></asp:TextBox>
            </td>
        </tr>
        <tr>
            <td class="style2">
                &nbsp;
            </td>
            <td>
                <asp:Button ID="post" runat="server" OnClick="post_Click" Text="Post Comment" Width="97px"
                    Enabled="false" />
            </td>
        </tr>
    </table>
    <asp:Label ID="loggedin" runat="server" Text="abc"></asp:Label>--%>
    </form>
    <footer id="pageFooter" class="cf">
      <nav class="footerNav">
    </h2>
          <p>&copy;Copyright  Find Places.  All rights reserved.</p> 
    
</footer>
</body>
</html>

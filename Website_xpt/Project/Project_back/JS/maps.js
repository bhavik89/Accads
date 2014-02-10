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

function foodstuff() {
    selbox_index = 1;
    initialize();
}


function liesure() {
    selbox_index = 2;
    initialize();
}

function other() {
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

    if (status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
            // document.getElementById("tab1").innerHTML += "<div><strong><p>" + results[i].name + "</strong></br>" + results[i].geometry.location + "</p></div>";
            placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

            document.getElementById("tab1").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onClick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer'\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].formatted_address + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles</p>";
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
        position: place.geometry.location,
        reference: place.reference
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

    //calcMapZoom();
    placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);
    newmarker['infowindow'] = new google.maps.InfoWindow({
        content: '<strong>' + place.name + '</strong>' + "</br>" + place.formatted_address + "</br> Ratings: " + rating +
                "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles</br>" +
                "Your address: <input id='clientAddress' type='text'> <input type='button' onclick='getDirections()' value='Go!'>"
    });


    google.maps.event.addListener(newmarker, 'click', function () {
        // alert('Click Function');
        dest = this.position;
        marker_reference = this.reference;
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
        // getReviews();
        // createPhoto();

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
    var search_types = null;
    // alert('SearchNearBy');
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
    //alert(typ);

    var rad_inp = document.getElementById("radii").options[document.getElementById("radii").selectedIndex].value;

    if (place_inp) {
        var request = {
            location: maploc,
            name: place_inp,
            keyword: place_inp,
            radius: rad_inp, //'500'
            //types: [typ]
            rankBy: google.maps.places.RankBy.PROMINENCE
        };
    }
    else {
        var request = {
            location: maploc,
            //keyword:place_inp,
            //name: place_inp,
            radius: rad_inp, //'500'
            types: [typ],
            rankBy: google.maps.places.RankBy.PROMINENCE
        };

    }

    infowindow = new google.maps.InfoWindow();

    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, nearbyCallback);

    document.getElementById("text").innerHTML = "";
}

function nearbyCallback(results, status, pagination) {
    // alert('InCallBack');
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            nearbyCreateMarker(results[i]);
            //document.getElementById("text").innerHTML += "<div><strong><p>" + results[i].name + "<strong></br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p></div>";

            document.getElementById("text").innerHTML += "<p id=" + "\"" + i + "\"" +
                         "onclick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer'\"" + "onmouseover=\"this.style.background-color='blue'\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].vicinity + "</br>" + results[i].geometry.location + "</p>";
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
    document.getElementById("directionsPanel").innerHTML = "";
    directionsDisplay.setPanel(document.getElementById("directionsPanel"));
}


function AutoComplete(maploc) {
    //var map = new google.maps.Map(document.getElementById('map-canvas'), mapOptions);

    var input = document.getElementById('searchTextField');
    var autocomplete = new google.maps.places.Autocomplete(input);

    autocomplete.bindTo('bounds', map);

    //deleteOverlays();

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

        document.getElementById("text").innerHTML = "";
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
        document.getElementById("text").innerHTML += "<div><strong><p>" + place.name + "</strong><br>" + place.formatted_address + "</br>" + place.geometry.location + "</p></div>";
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
            }
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
        alert(d1);
    }
}
//alert(d1);

//if(dist > 1000)
// map.setZoom(1);

google.maps.event.addDomListener(window, 'load', initialize);
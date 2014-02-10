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
var d1 = 0; // required for distance calculation
var placeDetailLoc;

function foodstuff_fn() {

    selbox_index = 1;
    initialize();
}


function liesure_fn() {

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
                   setTimeout(function () { AutoCheck(maploc) }, 500);
                   //AutoCheck(maploc);
               });
    }



function AutoCheck(maploc) {

 
    if (document.getElementById("AutoFillCheck").checked && document.getElementById("nearbySearch").checked)
        alert('Please Check only one check box')

    if (document.getElementById("AutoFillCheck").checked)
        AutoComplete(maploc);

    else
        placesSearch(maploc);
}


function placesSearch(maploc) {
    //setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);

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

    if (document.getElementById("AutoFillCheck").checked && document.getElementById("nearbySearch").checked)
        alert('Please Check only one check box')

    if (document.getElementById("nearbySearch").checked) {
        
        SearchNearBy(maploc);
    }
    else {
        
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

    //setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);

    var request = {
        query: [place_inp],
        location: maploc,
        radius: 10,
        rankby: google.maps.places.RankBy.DISTANCE
    };

    infowindow = new google.maps.InfoWindow();

    var service = new google.maps.places.PlacesService(map);
    //setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
    service.textSearch(request, callback);
    document.getElementById("tab1").innerHTML = "";
}

function callback(results, status, pagination) {
    //setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
    var openNow;
    var openNowText = "N/A";
    document.getElementById("tab1").innerHTML = "";
    if (status == google.maps.places.PlacesServiceStatus.OK) {

        for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
            // document.getElementById("tab1").innerHTML += "<div><strong><p>" + results[i].name + "</strong></br>" + results[i].geometry.location + "</p></div>";
            placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

            if (results[i].opening_hours) {
                openNow = results[i].opening_hours.open_now;
                if (openNow)
                    openNowText = "Yes";
                else
                    openNowText = "No";
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
    //setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
    var placeLoc = place.geometry.location;
    var newmarker = new google.maps.Marker({
        map: map,
        position: place.geometry.location,
        reference: place.reference,
        animation: google.maps.Animation.DROP
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
            openNowText = "Yes";
        else
            openNowText = "No";
    }
   // setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
    calcMapZoom();
    placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);
    newmarker['infowindow'] = new google.maps.InfoWindow({
        content: '<strong>' + place.name + '</strong>' + "</br>" + place.formatted_address + "</br> Ratings: " + rating + "&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText +
                "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles</br>" +
                "Get Here: <input id='clientAddress' type='text' placeholder='Enter your address'> <input type='button' onclick='getDirections()' value='Go!'>"
    });

    

    google.maps.event.addListener(newmarker, 'click', function () {
      //  setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
        dest = this.position;
        marker_reference = this.reference;
        placeDetailLoc = this.position;

        var morinfo = document.getElementById("moreInfo");
        morinfo.style.display = 'block';

        if (activeInfoWindow == this['infowindow'])
            return;

        if (activeInfoWindow) {
            activeInfoWindow.close();
        }

        this['infowindow'].open(map, this);
        activeInfoWindow = this['infowindow'];

        this['infowindow'].open(map, this);

        setTimeout(function () { createPhoto(); }, 100);
        setTimeout(function () { getReviews(); }, 100);        
        pullPage();

    });

    map_marker.push(newmarker);
  //  setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
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



    if ((prev != 0 && mark != 0) || (prev == 0 && mark != 0) || (prev != 0 && mark == 0))
        map_marker[prev]['infowindow'].close();

    if (activeInfoWindow) {
        activeInfoWindow.close();
    }

    marker_reference = map_marker[mark].reference;
    placeDetailLoc = map_marker[mark].position;
    var morinfo = document.getElementById("moreInfo");
    morinfo.style.display = 'block';
    map_marker[mark]['infowindow'].open(map, map_marker[mark]);

    activeInfoWindow = map_marker[mark]['infowindow'];
    prev = mark;
    setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
    createPhoto();
    setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
    getReviews();
    setTimeout(function () { var x = 1; var y = 2; x + y; }, 100);
    pullPage();

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

    // var rad_inp = document.getElementById("radii").options[document.getElementById("radii").selectedIndex].value;

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


            if (results[i].opening_hours) {
                openNow = results[i].opening_hours.open_now;
                if (openNow)
                    openNowText = "Yes";
                else
                    openNowText = "No";
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
        reference: place.reference,
        animation: google.maps.Animation.DROP
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
            openNowText = "Yes";
        else
            openNowText = "No";
    }

    newmarker['infowindow'] = new google.maps.InfoWindow({
        content: '<strong>' + place.name + '</strong>' + "</br>" + place.vicinity + "</br> Ratings: " + rating +
                "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles &nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText +
                "</br>Get Here: <input id='clientAddress' type='text' placeholder='Enter your address'> <input type='button' onclick='getDirections()' value='Go!'> "
    });


    google.maps.event.addListener(newmarker, 'click', function () {
        dest = this.position;
        marker_reference = this.reference;
        placeDetailLoc = this.position;
        var morinfo = document.getElementById("moreInfo");
        morinfo.style.display = 'block';
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
                openNowText = "Yes";
            else
                openNowText = "No";
        }


        marker_reference = place.reference;
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);
        marker.setAnimation(google.maps.Animation.DROP);
        marker.setClickable(true);
        marker_pos = place.geometry.location;
        dest = place.geometry.location;
        placeDetailLoc = place.geometry.location;
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
                 "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</br>" +
                "Get Here: <input id='clientAddress' type='text' placeholder='Enter your address'> <input type='button' onclick='getDirections()' value='Go!'>");

        infowindow.open(map, marker);


        document.getElementById("tab1").innerHTML += "<div><strong><p onClick='' onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\">" + place.name +
                                                              "</strong></br>" + place.formatted_address + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</p></div>";
    });
    var morinfo = document.getElementById("moreInfo");
    morinfo.style.display = 'block';
    if (marker_reference) {
        createPhoto();
        getReviews();
        pullPage();
    }

    google.maps.event.addListener(marker, 'click', function () {
        infowindow.open(map, marker);
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

    geocoder.geocode({ 'address': document.getElementById('clientAddress').value },
            function (results, status) {
                if (status == google.maps.GeocoderStatus.OK) {
                    var origin = results[0].geometry.location;

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

            if (!place.reviews) {
                document.getElementById("reviewsPlaceName").innerHTML = "<i><strong>" + place.name + "'s</strong> Reviews</i>";
                document.getElementById("reviews").innerHTML += "<p>Sorry! No reviews found for this place.<p>";
                return;
            }
            placeDetailLoc = place.geometry.location;
            var d1 = new Date(0);
            var displayDate;
            document.getElementById("reviewsPlaceName").innerHTML = "<i><strong>" + place.name + "'s</strong> Reviews</i>";
            for (var i = 0; i < place.reviews.length; i++) {
                if (place.reviews[i].text)

                    document.getElementById("reviews").innerHTML += "<div class='comment-block'><div class='comment-header'><i>" + place.reviews[i].author_name + "</div><div class='comment-content'>" + place.reviews[i].text + "</div></div>";
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

                textToInsert = '<a class="fancybox imageRound" rel="place_images" href="../Project/_images/nophoto.jpg"><img class="imageRound"src="../Project/_images/nophoto.jpg" style="margin:0.5%;  width:12%; height:100px;"/></a>';

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

                return;

            }
            placeDetailLoc = place.geometry.location;
            document.getElementById("photosName").innerHTML = "<div style='padding:1%;'><i><strong>" + place.name + "'s</strong> Photos</i></div>";


            var textToInsert = '';
            fancy.each(place.photos, function (i, item) {
                if (i == 7) return false;
                var imgThumb = photos[i].getUrl({ 'maxWidth': 140, 'maxHeight': 140 });
                var imgLarge = photos[i].getUrl({ 'maxWidth': 500, 'maxHeight': 500 });
                textToInsert += '<a class="fancybox imageRound" rel="place_images" href="' + imgLarge + '.jpg' + '"><img class="imageRound"src="' + imgThumb + '.jpg' + '" style="margin:0.5%;  width:12%; height:100px;"/></a>';

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

function pullPage() {
    location.hash = 'PAGETOP';
}

function sendData() {
    var url = "moreInfo.aspx?" + marker_reference + "," + placeDetailLoc;
    window.open(url, '_blank')

}

google.maps.event.addDomListener(window, 'load', initialize);   

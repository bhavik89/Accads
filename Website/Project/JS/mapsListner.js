var locs = [];
var map;                // Stores the map on page
var infowindow;         // Stores infowindow on the map
var prev = 0;           // to preserve the ID of marker clicked
var activeInfoWindow;   // Stores the current activated infowindow on map
var map_marker = [];    // the list of markers on map
var geocoder;           // geocoder variable used for onbatining the current position
var directionsService;  // the google maps directionservice variable
var directionsDisplay;  // used to display the directions  
var dest;               // the destination for the directions 
var marker_reference;   // reference ID of the place place selected by clicking marker or selecting it from the list 
var marker_pos;         // reference to the position of given marker (used to compute the distance between user and given place)
var map_center;         // the center of the map
var selbox_index;       // index for one of the three select boxes on the home page
var d1 = 0;             // required for distance calculation
var placeDetailLoc;     // used to get the details of the place in autocomplete 

//Enter this function when any of the place type is selected from the first select menu on the home page
function foodstuff_fn() {
    selbox_index = 1;  // indicating that the first select box is selected
     document.getElementById("searchTextField").value = ""; //clear the text in text box to seach based on the selection
    initialize();
}


//Enter this function when any of the place type is selected from the second select menu on the home page
function liesure_fn() {
    selbox_index = 2; // indicating that the second select box is selected
    document.getElementById("searchTextField").value = ""; //clear the text in text box to seach based on the selection
    initialize();
}

//Enter this function when any of the place type is selected from the third select menu on the home page
function other_fn() {
    selbox_index = 3; // indicating that the third select box is selected
    document.getElementById("searchTextField").value = "";  //clear the text in text box to seach based on the selection
    initialize();
}

// The Start function on the page load    
function initialize() {
    d1 = 0;
    deleteOverlays();
    // This get the user's current location by using geolocation service of the map
    navigator.geolocation.getCurrentPosition(
               function (position) {
                   maploc = new google.maps.LatLng(position.coords.latitude, position.coords.longitude);
                   map_center = maploc;
                   setTimeout(function () { AutoCheck(maploc) }, 500); // introduce some delay to avoid the simultaneous calling of services
               }); // End of initialize()
}


// check for the check boxes checked on the paeg and accordingly go into the corresponding function
function AutoCheck(maploc) {

    if (document.getElementById("AutoFillCheck").checked && document.getElementById("nearbySearch").checked)
        alert('Please Check only one check box')

    if (document.getElementById("AutoFillCheck").checked)
        AutoComplete(maploc);

    else
        placesSearch(maploc);
}

//The place search function-(initializing the textsearch feature)
function placesSearch(maploc) {

//plot the map on the map canvas with the unique marker for the user's current location
    map = new google.maps.Map(document.getElementById('map-canvas'), {
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: maploc,
        zoom: 13
    });

    deleteOverlays();   //delete the map overlays from the map

    //Create a marker on the map indicating the user's current position
    var newmarker = new google.maps.Marker({
        map: map,
        title: "You are Here",
        icon: "../Images/CurrentLoc.png",
        position: maploc,
        Animation:google.maps.Animation.DROP
    });

    //check for the both the checkboxes on the maps being checked, if yes throw an error
    if (document.getElementById("AutoFillCheck").checked && document.getElementById("nearbySearch").checked)
        alert('Please Check only one check box')
    
    // check to initialize nearby search
    if (document.getElementById("nearbySearch").checked) {
         SearchNearBy(maploc);
    }
    else {
         searchByText(maploc); //the default text search is initialized
    }
}

//The text search function
function searchByText(maploc) {

//take the text entered in the textbox
    var place_inp = document.getElementById("searchTextField").value;
    if (!place_inp) {

    //check for the selectbox index and accordingly assign the value to the text
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

    //create a request query for the text search
    var request = {
        query: [place_inp],
        location: maploc,
        radius: 10,
    };

    //initialize the marker infowindow
    infowindow = new google.maps.InfoWindow();

    //send request to the text search service of the google places library
    var service = new google.maps.places.PlacesService(map);
    service.textSearch(request, callback);
    document.getElementById("tab1").innerHTML = "";
}

//the callback function after obtaining the result from the text search query
function callback(results, status, pagination) {
    var openNow;
    var openNowText = "N/A";
    document.getElementById("tab1").innerHTML = "";
    if (status == google.maps.places.PlacesServiceStatus.OK) {  //proceed if the value of status in the resulting JSON data is OK

        for (var i = 0; i < results.length; i++) {
            createMarker(results[i]);
            placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2); // Distance computation 

            //See if the place is open or not
            if (results[i].opening_hours) {
                openNow = results[i].opening_hours.open_now;
                if (openNow)
                    openNowText = "Yes";
                else
                    openNowText = "No";
            }

            //populate the result on the panel
            document.getElementById("tab1").innerHTML += "<div class='hoverColor'><p id=" + "\"" + i + "\"" +
                         "onClick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].formatted_address + "</br>Distance: " + 
                         ((placeDistance * 0.621).toFixed(2)) + " miles &nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</p></div>";
        }

        //the pagination function, used to obtain more results
        if (pagination.hasNextPage) {
            var moreButton = document.getElementById('more');
            moreButton.disabled = false;

            google.maps.event.addDomListenerOnce(moreButton, 'click',
                              function () {
                                  moreButton.disabled = true;
                                  pagination.nextPage(); // get the next page of 20 results
                              });
        }

    } //if end

} //function end

//function to display the markers on map - it takes the place information consisting of chunk of data
function createMarker(place) {

//extract the location of the place for the place JSON data
    var placeLoc = place.geometry.location;

//create a new marker for each place
    var newmarker = new google.maps.Marker({
        map: map,
        position: place.geometry.location,
        reference: place.reference
    });

//store the marker position 
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

//calculate the map zoom level
    calcMapZoom();
    placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2);

//populate the contents on infowindow
    newmarker['infowindow'] = new google.maps.InfoWindow({
        content: '<strong>' + place.name + '</strong>' + "</br>" + place.formatted_address + "</br> Ratings: " + 
                rating + "&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText +
                "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles</br>" +
                "Get Here: <input id='clientAddress' type='text' placeholder='Enter your address'> <input type='button' onclick='getDirections()' value='Go!'>"
    });

//function to open the corresponding infowindow when the marker is clicked
    google.maps.event.addListener(newmarker, 'click', function () {

 //store the destination, place reference requred for get directions and more info
        dest = this.position;
        marker_reference = this.reference;
        placeDetailLoc = this.position;

//display the more info link when the marker is clicked
        var morinfo = document.getElementById("moreInfo");
        morinfo.style.display = 'block';

//this closes the other infowindow of any marker if open and opens the info window of the current marker that is clicked
        if (activeInfoWindow == this['infowindow'])
            return;

        if (activeInfoWindow) {
            activeInfoWindow.close();
        }

        this['infowindow'].open(map, this);
        activeInfoWindow = this['infowindow'];

        this['infowindow'].open(map, this);

//call the functions to to get the reviews and photos of the place being selected 
//Note that a delay of 50ms is introduced before calling any function to avoid asynchronous callbacks
        setTimeout(function () { createPhoto(); }, 50);
        setTimeout(function () { getReviews(); }, 50);
        pullPage();

    });

//push the marker into the marker array to keep the track of all the markers 
    map_marker.push(newmarker);

//initiate the direction service to get directions

    geocoder = new google.maps.Geocoder;
    directionsService = new google.maps.DirectionsService();
    directionsDisplay = new google.maps.DirectionsRenderer({
        suppressMarkers: false
    });

//display the directions on map in a polyline manner
    directionsDisplay.setMap(map);

//display the directions on the side panel (directions panel)
    document.getElementById("tab2").innerHTML = "";
    directionsDisplay.setPanel(document.getElementById("tab2"));
    document.getElementById('li_tab2').setAttribute("class", "active");
    document.getElementById('tab2').setAttribute("class", "active");
}

//function to the display window when a place from the list is selected by clicking on it
function alertMarker(mark) {


//check for the previously open infowindows and for wchich marker ID it is open for
    if ((prev != 0 && mark != 0) || (prev == 0 && mark != 0) || (prev != 0 && mark == 0))
        map_marker[prev]['infowindow'].close();

//if any info window is open, close it
    if (activeInfoWindow) {
        activeInfoWindow.close();
    }

//set the refernce to the new place that is selected usinf the reference field of marker
    marker_reference = map_marker[mark].reference;

//extract the location of the place from the marker data
    placeDetailLoc = map_marker[mark].position;

//Display the more info link 
    var morinfo = document.getElementById("moreInfo");
    morinfo.style.display = 'block';

//open the infowindow of the given marker
    map_marker[mark]['infowindow'].open(map, map_marker[mark]);

//set the reference of the current info window
    activeInfoWindow = map_marker[mark]['infowindow'];
    prev = mark;

//call the functions to get the reviews and photos of the place being selected
    setTimeout(function () { createPhoto(); }, 50);
    setTimeout(function () { getReviews(); }, 50);
    pullPage();

}


//Function for nearbysearch feature
function SearchNearBy(maploc) {
    var search_types = null;

//take the text query from the text field
    var place_inp = document.getElementById("searchTextField").value;
    if (!place_inp) {

//set the types based on the selectedtype of place from themenu bar
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

 //typecasting the typ parameter to string as it need a string
    var typ = search_types + "";
        
//building a request for the nearby search depending upon whether user have entered the value into text box 
//or selected from the menu  note: the first preference is given to the string in the textbox
    if (place_inp) {
        var request = {
            location: maploc,
            name: place_inp,
            keyword: place_inp,
            rankBy: google.maps.places.RankBy.DISTANCE //sort the places by distance
        };
    }
    else {
        var request = {
            location: maploc,
            types: [typ],
            rankBy: google.maps.places.RankBy.DISTANCE //sort the places by distance
        };

    }

    infowindow = new google.maps.InfoWindow();

    //send request to the nearby search service of the google places library
    var service = new google.maps.places.PlacesService(map);
    service.nearbySearch(request, nearbyCallback);

    //empty the contents of tab1 to display the list of places
    document.getElementById("tab1").innerHTML = "";
}

//the function is same as the textsearch for populating the list of all the places found
function nearbyCallback(results, status, pagination) {
    var openNow;
    var openNowText = "N/A"
    if (status == google.maps.places.PlacesServiceStatus.OK) {
        for (var i = 0; i < results.length; i++) {
            nearbyCreateMarker(results[i]);
            placeDistance = (google.maps.geometry.spherical.computeDistanceBetween(marker_pos, map_center) / 1000).toFixed(2); //calculate the distance of user to the place

            //check if the place is open
            if (results[i].opening_hours) {
                openNow = results[i].opening_hours.open_now;
                if (openNow)
                    openNowText = "Yes";
                else
                    openNowText = "No";
            }

            //populate the contents on the place list (tab #1)
            document.getElementById("tab1").innerHTML += "<div class='hoverColor'><p id=" + "\"" + i + "\"" +
                         "onclick=" + "\"alertMarker(" + i + ");\"" + "onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\"><strong>" +
                         results[i].name + "</strong></br>" + results[i].vicinity + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + "miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</p></div>";
        }

        //check for pagination, if more places are to be displayed
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


//create the marker on map for the nearbysearch
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

//calculate the zoom level to possibly display all the places on the map
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

//populate the data on the marker's infowindow
    newmarker['infowindow'] = new google.maps.InfoWindow({
        content: '<strong>' + place.name + '</strong>' + "</br>" + place.vicinity + "</br> Ratings: " + rating +
                "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles &nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText +
                "</br>Get Here: <input id='clientAddress' type='text' placeholder='Enter your address'> <input type='button' onclick='getDirections()' value='Go!'> "
    });


//this function runs when a marker on the map is clicked
    google.maps.event.addListener(newmarker, 'click', function () {
        
//extract the position and the place reference number from the marker        
        dest = this.position;
        marker_reference = this.reference;
        placeDetailLoc = this.position;
        var morinfo = document.getElementById("moreInfo");
        morinfo.style.display = 'block';
        if (activeInfoWindow == this['infowindow'])
            return;
//check for the currently active infowindow 
        if (activeInfoWindow) {
            activeInfoWindow.close();
        }

//open the marker's infowindow and store its reference
        this['infowindow'].open(map, this);
        activeInfoWindow = this['infowindow'];

        this['infowindow'].open(map, this);

//call the functions for getting the reviews and photos of place after a specified delay 
        setTimeout(function () { createPhoto(); }, 50);
        setTimeout(function () { getReviews(); }, 50);
    });

// push the marker on the aray to keep track of all the markers on the map
    map_marker.push(newmarker);

//Google direction service is called for here
    geocoder = new google.maps.Geocoder;
    directionsService = new google.maps.DirectionsService();
    directionsDisplay = new google.maps.DirectionsRenderer({
        suppressMarkers: false
    });

//Directions are displayed on the directions panel
    directionsDisplay.setMap(map);
    document.getElementById("tab2").innerHTML = "";
    directionsDisplay.setPanel(document.getElementById("tab2"));
    document.getElementById('li_' + 'tab2').setAttribute("class", "active");
}


//Function when the autocomplete feature is initialted
function AutoComplete(maploc) {
    
//take the input from the textfield and initiate the autocomplete feature og Google
    var input = document.getElementById('searchTextField');
    var autocomplete = new google.maps.places.Autocomplete(input);

//This binds the autocomplete object to the current map on apge
    autocomplete.bindTo('bounds', map);

//Clear all the other overlays on map
    deleteOverlays();

//The infowindow and marker objects are created
    var infowindow = new google.maps.InfoWindow();
    var marker = new google.maps.Marker({
        map: map
    });

//Monitor for the change in textfield and start suggesting the places
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

//extract the details from the marker object and push it in the array
        marker_reference = place.reference;
        marker.setPosition(place.geometry.location);
        marker.setVisible(true);
        marker.setAnimation(google.maps.Animation.DROP);
        marker.setClickable(true);
        marker_pos = place.geometry.location;
        dest = place.geometry.location;
        placeDetailLoc = place.geometry.location;
        map_marker.push(marker);

//set the infowindow of the marker
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

        setTimeout(function () { createPhoto(); }, 50);
        setTimeout(function () { getReviews(); }, 50);

        activeInfoWindow = infowindow;

        infowindow.setContent("<div><strong>" + place.name + "</strong><br>" + place.formatted_address + "<br>Ratings: " + rating +
                 "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</br>" +
                "Get Here: <input id='clientAddress' type='text' placeholder='Enter your address'> <input type='button' onclick='getDirections()' value='Go!'>");

        infowindow.open(map, marker);


        document.getElementById("tab1").innerHTML += "<div class='hoverColor'><strong><p onmouseover=\"this.style.cursor='pointer';\"this.style.color = 'red';\">" + place.name +
                                                              "</strong></br>" + place.formatted_address + "</br>Distance: " + ((placeDistance * 0.621).toFixed(2)) + " miles&nbsp&nbsp&nbsp<strong>Open Now: </strong>" + openNowText + "</p></div>";
    });

//enable the more info. link on the page
    var morinfo = document.getElementById("moreInfo");
    morinfo.style.display = 'block';
    if (marker_reference) {
        setTimeout(function () { createPhoto(); }, 50);
        setTimeout(function () { getReviews(); }, 50);
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
            map_marker[i].setMap(null);   // clears all the markers from the map
        }
        map_marker.length = 0;
    }
}


//this function requests the google direction service for the directions to the place and an array of place is returned and the best route is displayed.
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

//This functions gets the reviews from different Google users about the place and populates in the review's box. 
//If no reviews of the place are available a message telling "No reviews are available is displayed"
//For getting the reviews of a particular place, it needs the reference of that place. The reference is stored in 
//marker_reference variable whenever a place is selected
//the place reference is given to the getDetails service and the data is extracted from the obtained JSON data.
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


//This functions gets the photos posted by Google users about the place and populates on the page. 
//For getting the photos of a particular place, it needs the reference of that place. The reference is stored in 
//marker_reference variable whenever a place is selected
//the place reference is given to the getDetails service and the data is extracted from the obtained JSON data.

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

//This function calculates the required zoom level to fir all the markers on the page
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

//This function sends the reference ID and the location of the place to get more information about it on another page
function sendData() {
    var url = "moreInfo.aspx?" + marker_reference + "," + placeDetailLoc;
    window.open(url, '_blank')

}

google.maps.event.addDomListener(window, 'load', initialize);   

var query = window.location.search;
// Skip the leading ?, which should always be there, 
// but be careful anyway

if (query.substring(0, 1) == '?') {
    query = query.substring(1);
}
var data = query.split(',');
for (i = 0; (i < data.length); i++) {
    data[i] = unescape(data[i]);

}
query = data[0];

var lat = (data[1].substr(1));
var lng = (data[2].substr(0, data[2].length - 1));

var map;
var locs = [];
var map;
var infowindow;
var map_marker = [];
var marker_reference;
var marker_pos;
var map_center;
var selbox_index;
var d1 = 0;
var maploc;

//This functions gets the details of the place and populates in the review's box. 
//For getting the details of a particular place, it needs the reference of that place. 
//The reference is obtained from the url (when this page page is requested, the reference is sent in the url)
//the place reference is given to the getDetails service and the data is extracted from the obtained JSON data and displayed on page. 

function initialize() {

    maploc = new google.maps.LatLng(lat, lng);
    map = new google.maps.Map(document.getElementById('map-canvas'), {
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        center: maploc,
        zoom: 15
    });

    var newmarker = new google.maps.Marker({
        map: map,
        title: "You are Here",
        position: maploc,
        animation: google.maps.Animation.DROP

    });

    getDetails();
}



function getDetails() {
    document.getElementById("reviews").innerHTML = "";
    document.getElementById("reviewsPlaceName").innerHTML = "";
    var request = {
        reference: query
    };

    var infowindow = new google.maps.InfoWindow();
    var service = new google.maps.places.PlacesService(map);

    service.getDetails(request, function (place, status) {
        if (status == google.maps.places.PlacesServiceStatus.OK) {

            document.getElementById("name").innerHTML = place.name;
            document.getElementById("address").innerHTML = place.formatted_address;
            if (place.formatted_phone_number)
                document.getElementById("tel").innerHTML = "Tel. No. " + place.formatted_phone_number;

            if (place.international_phone_number)
                document.getElementById("in_tel").innerHTML = "International Number: " + place.international_phone_number;

            var openNowText;
            if (place.opening_hours) {
                var openNow = place.opening_hours.open_now;
                if (openNow)
                    openNowText = "Yes";
                else
                    openNowText = "No";

                document.getElementById("open_now").innerHTML = "Open Now: " + openNowText;

            }


            var rating = place.rating;
            if (rating) {
                rating = rating;

                document.getElementById("rating").innerHTML = "Ratings: " + rating;
            }


            var price_level = place.price_level;
            var price;

            if (place.price_level) {
                if (price_level == 1)
                    price = "$";

                else if (price_level == 2)
                    price = "$$";

                else if (price_level == 3)
                    price = "$$$";

                else if (price_level == 4)
                    price = "$$$$";

                else if (price_level == 5)
                    price = "$$$$$";

                else if (price_level > 5)
                    price = "$$$$$";

                else
                    price = "";


                document.getElementById("price_level").innerHTML = "Price: <i><font color='green'>" + price + "</i></font>";
            }



            if (place.website) {
                document.getElementById("website").innerHTML = "Homepage: <a href='" + place.website + "'> Click Here <a>";
            }


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

            }


            placeDetailLoc = place.geometry.location;
            document.getElementById("photosName").innerHTML = "<div style='padding:1%;'><i><strong>" + place.name + "'s</strong> Photos</i></div>";

            if (photos) {
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

            if (!place.reviews) {
                document.getElementById("reviewsPlaceName").innerHTML = "<i><strong>" + place.name + "'s</strong> Reviews</i>";
                document.getElementById("reviews").innerHTML += "<p>Sorry! No reviews found for this place.<p>";
                return;
            }
            document.getElementById("reviewsPlaceName").innerHTML = "<i><strong>" + place.name + "'s</strong> Reviews</i>";
            for (var i = 0; i < place.reviews.length; i++) {
                if (place.reviews[i].text)
                   document.getElementById("reviews").innerHTML += "<div class='comment-block'><div class='comment-header'><i>" + place.reviews[i].author_name + "</div><div class='comment-content'>" + place.reviews[i].text + "</div></div>";
            }


        }
    });
}

google.maps.event.addDomListener(window, 'load', initialize); 
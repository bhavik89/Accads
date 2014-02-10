google.maps.event.addDomListener(window, 'load', function () {
    var map = new google.maps.Map(document.getElementById('map-canvas'), {
        center: new google.maps.LatLng(39, -97),
        zoom: 3,
        mapTypeId: google.maps.MapTypeId.ROADMAP
    });

    var panelDiv = document.getElementById('panel');

    var data = new DunkinDataSource;

    var view = new storeLocator.View(map, data);

    new storeLocator.Panel(panelDiv, {
        view: view,
        locationSearchLabel: 'Specify a location for directions (optional):'
    });
});

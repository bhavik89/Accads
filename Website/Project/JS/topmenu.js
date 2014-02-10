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
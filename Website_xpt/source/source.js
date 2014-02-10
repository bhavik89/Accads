$(function() {
    $(".downloadbutton").live("click", function() {
        var nextdata = $(this).next()[0].innerHTML;
        document.location = "download.aspx?" + nextdata;
    });
});

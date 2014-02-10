
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
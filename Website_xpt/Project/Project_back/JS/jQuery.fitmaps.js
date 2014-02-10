

(function ($) {

    $.fn.fitMaps = function (options) {
        var settings = {
            customSelector: null,
            h: '100%',
            w: '100%'
        }

        if (options) {
            $.extend(settings, options);
        }

        var div = document.createElement('div'),
		ref = document.getElementsByTagName('base')[0] || document.getElementsByTagName('script')[0];

        div.className = 'fit-maps-style';
        div.innerHTML = '&shy;<style>         \
			.fluid-width-map-wrapper {        \
				position: relative;           \
				width: ' + settings.w + ';    \
				height: ' + settings.h + ';   \
				padding: 0;                   \
    			margin: 0px auto;           \
			}                                 \
			\
			.fluid-width-map-wrapper iframe {  \
				width: 100%;                  \
				height: 100%;                  \
				top: 0;                       \
				left: 0;                      \
			}                                 \
			</style>';

        ref.parentNode.insertBefore(div, ref);

        return this.each(function () {
            var selectors = [
				"iframe[src*='maps.google']"
			];

            if (settings.customSelector) {
                selectors.push(settings.customSelector);
            }

            var $allMaps = $(this).find(selectors.join(','));

            $allMaps.each(function () {
                var $this = $(this);
                if ($this.parent('.fluid-width-map-wrapper').length) return;

                if (!$this.attr('id')) {
                    var mapID = 'fitmap' + Math.floor(Math.random() * 999999);
                    $this.attr('id', mapID);
                }

                $this.wrap('<div class="fluid-width-map-wrapper"></div>');
                $this.removeAttr('height').removeAttr('width');
            });
        });

    }
})(jQuery);
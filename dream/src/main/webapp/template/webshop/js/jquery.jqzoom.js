(function ($) {
	$.fn.jqueryzoom = function (options) {
		var settings = {
			xzoom: 400, 	//zoomed width default width
			yzoom: 400, 	//zoomed div default width
			offset: 10, 	//zoomed div default offset
			position: "right", //zoomed div default position,offset position is to the right of the image
			lens: 1, //zooming lens over the image,by default is 1;
			top: 50,
			preload: 1
		};
		if (options) {
			$.extend(settings, options);
		}
		var noalt = '';
		$(this).hover(function () {
			var imageLeft = $(this).offset().left;
			var imageTop = $(this).offset().top;
			var imageWidth = $(this).children('img').get(0).offsetWidth;
			var imageHeight = $(this).children('img').get(0).offsetHeight;
			noalt = $(this).children("img").attr("alt");
			var bigimage = $(this).children("img").attr("jqimg");

			$(this).children("img").attr("alt", '');
			if ($("div.MyZoomdiv").get().length == 0) {
				$(this).parent().after("<div class='MyZoomdiv'><img class='bigimg' src='" + bigimage + "'/></div>");
				$(this).append("<div class='MyJqZoomPup'>&nbsp;</div>");
			}
			if (settings.position == "right") {
				if (imageLeft + imageWidth + settings.offset + settings.xzoom > screen.width) {
					leftpos = imageLeft - settings.offset - settings.xzoom;
				} else {
					leftpos = imageLeft + imageWidth + settings.offset;
				}
			} else {
				leftpos = imageLeft - settings.xzoom - settings.offset;
				if (leftpos < 0) {
					leftpos = imageLeft + imageWidth + settings.offset;
				}
			}
			
			$("div.MyZoomdiv").css({ top: imageTop, left: leftpos });
			$("div.MyZoomdiv").width(settings.xzoom);
			$("div.MyZoomdiv").height(settings.yzoom);
			$("div.MyZoomdiv").show();

			if (!settings.lens) {
				$(this).css('cursor', 'crosshair');
			}
			$(document.body).mousemove(function (e) {
				mouse = new MouseEvent(e);
				/*$("div.MyJqZoomPup").hide();*/
				var bigwidth = $(".bigimg").get(0).offsetWidth;
				var bigheight = $(".bigimg").get(0).offsetHeight;
				var scaley = 'x';
				var scalex = 'y';
				if (isNaN(scalex) | isNaN(scaley)) {
					var scalex = (bigwidth / imageWidth);
					var scaley = (bigheight / imageHeight);
					$("div.MyJqZoomPup").width((settings.xzoom) / scalex);
					$("div.MyJqZoomPup").height((settings.yzoom) / scaley);
					if (settings.lens) {
						$("div.MyJqZoomPup").css('visibility', 'visible');
					}
				}
				xpos = mouse.x - $("div.MyJqZoomPup").width() / 2 - imageLeft;
				ypos = mouse.y - $("div.MyJqZoomPup").height() / 2 - imageTop;
				if (settings.lens) {
					xpos = (mouse.x - $("div.MyJqZoomPup").width() / 2 < imageLeft) ? 0 : (mouse.x + $("div.MyJqZoomPup").width() / 2 > imageWidth + imageLeft) ? (imageWidth - $("div.MyJqZoomPup").width() - 2) : xpos;
					ypos = (mouse.y - $("div.MyJqZoomPup").height() / 2 < imageTop) ? 0 : (mouse.y + $("div.MyJqZoomPup").height() / 2 > imageHeight + imageTop) ? (imageHeight - $("div.MyJqZoomPup").height() - 2) : ypos;
				}
				if (settings.lens) {
					$("div.MyJqZoomPup").css({ top: ypos, left: xpos });
				}
				scrolly = ypos;
				$("div.MyZoomdiv").get(0).scrollTop = scrolly * scaley;
				scrollx = xpos;
				$("div.MyZoomdiv").get(0).scrollLeft = (scrollx) * scalex;
			});
		}, function () {
			$(this).children("img").attr("alt", noalt);
			$(document.body).unbind("mousemove");
			if (settings.lens) {
				$("div.MyJqZoomPup").remove();
			}
			$("div.MyZoomdiv").remove();
		});
		count = 0;
		if (settings.preload) {
			$('body').append("<div style='display:none;' class='MyJqPreload" + count + "'>sdsdssdsd</div>");
			$(this).each(function () {
				var imagetopreload = $(this).children("img").attr("jqimg");
				var content = jQuery('div.MyJqPreload' + count + '').html();
				jQuery('div.MyJqPreload' + count + '').html(content + '<img src=\"' + imagetopreload + '\">');
			});
		}
	}
})(jQuery);

function MouseEvent(e) {
	this.x = e.pageX;
	this.y = e.pageY;
}



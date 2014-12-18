
$(document).ready(function() {
	var map;

	// Function for resizing the map to fill the available space on the screen
	var resize = function () {
		var $map = $('#map');
		
		//$map.height($(window).height() - $('div.navbar').outerHeight() - 150);
		
		$map.height($(window).height() );
		if (map) {
			map.invalidateSize();
		}
	};
	
	// Resize the map element on window resize
	$(window).on('resize', function () {
		resize();
	});
	
	// Resize the map element
	resize();
	
	// Initialize the map
	map = L.map('map').setView([41.510056,-95.0924184], 4);
	
	// Add Mapquest tile layer
	var baseLayer = L.tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
	    attribution: 'Tiles Courtesy of OpenStreetMap.',
	    detectRetina: true
	});
	
	baseLayer.addTo(map);
	
	var layerControl = new L.Control.Layers({
		'Map Quest': baseLayer
	});
	
	var timeline = new links.Timeline($('#timeline').get(0));
	
	var options = {
		'width':  '99.99%',
		'height': '15px',
		'editable': false,   // enable dragging and editing events
		'style': 'box'
	};
	
	// Draw our timeline with no data and the provided options
	timeline.draw([], options);
	
	var layers = [];
	var lastLayer = null;
	
	// Function that will be called when a user clicks on a timeline item
	var onSelect = function () {
		var selected = timeline.getSelection();
		
		if (selected.length > 0) {
			var row = selected[0].row;
			var item = timeline.getItem(row);
			var id = $(item.content).attr('data-id');
			var layer = layers[id];
			
			if (lastLayer && lastLayer.viewedImage) {
				lastLayer.viewedImage.fire('click');
			}
			
			layer._map.panTo(layer.getLatLng());
			layer.fire('click');
			
			lastLayer = layer;
		}
		
		
	};
	
	// Add a listener to the select event
	links.events.addListener(timeline, 'select', onSelect);

	var index = 0;
	
	// Create a new L.PanoramioLayer instance
	var panoramioLayer = new L.PanoramioLayer({
		photoSet: 'full',
		onEachPhoto: function (layer, photo) {
			var uploaded = photo['upload_date'];
			var url = photo['photo_file_url'];
			var startMoment = moment(uploaded, 'DD MMM YYYY h:mm:ss');
			var start = startMoment.toDate();
			var caption = "";
			if (photo['photo_title'].length > 1){
				caption = photo['photo_title'];
			}
			else {
				caption= "User: " + photo['owner_name'];
			}
			var photoItem = {
				start: start,
                content: '<img class="photo" title="' + caption + '"onload="this.style.opacity=1" src="marker_icon/tweetbutton.jpg"  data-id="'+index +'"/>'+ caption.substring(0,20),
                //content: '"<img class="' + photo['photo_title'].substring(0,20) + '">', 
				//content: '<img class="photo" title="' + photo['photo_title'] + '" onload="this.style.opacity=1" src="' + url + '" height="64" data-id="' + index + '"/>',
				className: 'photo-item'
			};
			
			layers.push(layer);
			
			var clickFunction = function (timeline, index, start) {
				return function(e) {
					var startDate = moment(start).subtract('days', 10).toDate();
					var endDate = moment(start).add('days', 10).toDate();
					
					timeline.setVisibleChartRange(startDate, endDate);
					timeline.setSelection([{row: index}]);
				};
			};
			
			layer.on('click', clickFunction(timeline, index, start));
			
			index++;
			
			timeline.addItem(photoItem, true);
			//timeline.redraw();
			//timeline.setVisibleChartRangeAuto();
		},
		refreshEvents: 'viewreset'
	});
     

	panoramioLayer.on('photosAvailable', function (data) {
		timeline.deleteAllItems();
		
		layoutPhotos('#gallery', JSON.parse(JSON.stringify(data.photos)), function (photo) {
			return photo.width / photo.height;
		}, function (photo, width, height) {
			photo.width = width;
			photo.height = height;
			return photo;
		});


	});
	
	map.addLayer(panoramioLayer);
	
    map.on('enterFullscreen', function(){
        if(window.console) window.console.log('enterFullscreen');
    });
    map.on('exitFullscreen', function(){
        if(window.console) window.console.log('exitFullscreen');
    });






	// Use OSM Nominatim Gazetteer for place name lookup
	$('.navbar-search').on('submit', function (e) {
		e.preventDefault();
		
		$.ajax({
			url: 'http://nominatim.openstreetmap.org/search',
			dataType: 'jsonp',
			jsonp: 'json_callback',
			data: {
				q: $('.search-query').val(),
				format: 'json'
			},
			success: function (data) {
				if (data && data.length > 0) {
					var firstResult = data[0];
					
					map.setView(new L.LatLng(firstResult.lat, firstResult.lon), 10);
				}
			}
		});
		
	});

});

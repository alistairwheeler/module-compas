var ComHomeZoneInfo = (function () {
	var app, responsive = typeof $ui !== "undefined",
		debug = true;

	function init(infos) {
		
		var template = $('#home-template').html();
    	var count = 0;
    	if (!template || !infos) {
    		if (count>100) return;
			setTimeout(function() {
				init(infos);
				count++;
			}, 500);
			return;
    	}
		
		var parsedInfos = JSON.parse(infos);
		console.log(parsedInfos)
		$('#main').html(Mustache.render(template, parsedInfos));
		
		
	}
	return {
		init: init
	};

})();
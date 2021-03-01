if (typeof ComHomeUserInfo === 'undefined') ComHomeUserInfo = (function ($) {

	function init(infos) {
		
		var parsedInfos = JSON.parse(infos);
		console.log(parsedInfos)
		var p = $('#main-infos').html(Mustache.render($('#home-template-infos').html(), parsedInfos));
	}

	return {
		init: init
	};
})(jQuery);
if(typeof ComACPGMExt === 'undefined') ComACPGMExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"PGM"},
			});
}


return { render: render };
})(jQuery);
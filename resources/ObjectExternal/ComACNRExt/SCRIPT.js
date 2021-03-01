if(typeof ComACNRExt === 'undefined') ComACNRExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"PNR"},
			});
}


return { render: render };
})(jQuery);
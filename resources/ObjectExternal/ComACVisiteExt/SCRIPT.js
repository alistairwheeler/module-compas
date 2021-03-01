if(typeof ComACVisite === 'undefined') ComACVisite = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"VIS"},
			});
}


return { render: render };
})(jQuery);
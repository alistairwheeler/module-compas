if(typeof ComACRDVExt === 'undefined') ComACRDVExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"VIS"},
			});
}


return { render: render };
})(jQuery);
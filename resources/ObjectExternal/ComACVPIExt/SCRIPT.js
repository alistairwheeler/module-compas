if(typeof ComACVPIExt === 'undefined') ComACVPIExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"VPR"},
			});
}


return { render: render };
})(jQuery);
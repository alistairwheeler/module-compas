if(typeof ComACVAUExt === 'undefined') ComACVAUExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"VAU"},
			});
}


return { render: render };
})(jQuery);
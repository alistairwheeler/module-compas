if(typeof ComACAppelExt=== 'undefined') ComACAppelExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"APP"},
			});
}


return { render: render };
})(jQuery);
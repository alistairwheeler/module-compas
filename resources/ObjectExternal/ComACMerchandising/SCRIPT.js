if(typeof ComACMerchandising === 'undefined') ComACMerchandising = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"MER"},
			});
}
return { render: render };
})(jQuery);
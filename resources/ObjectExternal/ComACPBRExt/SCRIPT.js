if(typeof ComACPBRExt === 'undefined') ComACPBRExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"PBR"},
			});
}


return { render: render };
})(jQuery);
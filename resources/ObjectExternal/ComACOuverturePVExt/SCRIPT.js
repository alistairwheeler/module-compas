if(typeof ComACOuverturePVExt === 'undefined') ComACOuverturePVExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComActionCommerciale", {
			fixedFilters:{comACtype:"OPV"},
			});
}
return { render: render };
})(jQuery);
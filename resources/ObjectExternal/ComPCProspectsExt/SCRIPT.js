if(typeof ComPCProspectsExt === 'undefined') ComPCProspectsExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComClient", {
			fixedFilters:{comCliNature:"PRO"},
			});
}
return { render: render };
})(jQuery);
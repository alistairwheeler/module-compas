if(typeof ComPCClientExt === 'undefined') ComPCClientExt = (function($) {
	
function render() {
	$ui.displayList(null, "ComClient", {
			fixedFilters:{comCliNature:"CLI"},
			});
}
return { render: render };
})(jQuery);
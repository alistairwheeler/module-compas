if (typeof ComBCPbr === 'undefined') ComBCPbr = (function ($) {
	function render(root) {
		//app = responsive ? $ui.getAjax() : Simplicite.Application;
		app = (parent && parent.Simplicite ? parent.Simplicite.Application : null) || new Simplicite.Ajax(root, 'uipublic');
		
		console.log(data);
		var m = $('#bdc-div').html(Mustache.render($('#bdc-template').html(), data));
		console.log(m);
		
				
		$('#print-btn').click(function(){
			document.title = " ";
			window.print();
		});
		
	}

	return {
		render: render
	};
})(jQuery);
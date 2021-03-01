if (typeof ComConventionDiffExt === 'undefined') ComConventionDiffExt = (function ($) {
	function render(root) {
		//app = responsive ? $ui.getAjax() : Simplicite.Application;
		app = (parent && parent.Simplicite ? parent.Simplicite.Application : null) || new Simplicite.Ajax(root, 'uipublic');
		
		//var jsonData = JSON.parse(data);
		
		console.log(data);
		var m = $('#convention-div').html(Mustache.render($('#convention-template').html(), data));
		
		$('#print-btn').click(function(){
			document.title = " ";
			window.print();
		});	
		
	}

	return {
		render: render
	};
})(jQuery);
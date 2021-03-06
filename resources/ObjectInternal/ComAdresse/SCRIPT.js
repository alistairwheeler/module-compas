var ComAdresse = typeof ComAdresse !== "undefined" ? ComAdresse : (function(ui, $) {
	if (!ui) return; // Do nothing on legacy UI
	Simplicite.UI.hooks.ComAdresse = function(o, cbk) {
		try {
			
		/*	o.locals.ui.list.onload = function(ctn, obj){
				$("head").append('<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>');
			};*/
			
			o.locals.ui.form.onload = function(ctn, obj) {
				
				//setup before functions
				var typingTimer;                //timer identifier
				var doneTypingInterval = 3000;  //time in ms, 5 second for example
				var $input = $("#field_comAdrLigne1");
				
				//on keyup, start the countdown
				$input.on('input', function () {
					clearTimeout(typingTimer);
					$ui.view.widget.completion($input, 20,
					function(cbk){
						return autocompleteCb($input.val(), function(r){
							r && cbk(r);
						});
						
					},
					function(resp){
						//select
						console.log(resp);
						$("#field_comAdrLigne1").val(resp.value);
						$("#field_comAdrCP").val(resp.cp);
						$("#field_comAdrVille").val(resp.city);
						$("#field_comAdrCoords").val(resp.coords);
						$("#field_comAdrCodeINSEE").val(resp.insee);
						$("#field_comAdrNumVoie").val(resp.housenumber);
						$("#field_comAdrLabelVoie").val(resp.street);
						$('.completion.dropdown').hide();
					},
					function(resp){
						//display
						return $('<div/>')
							.append($('<span/>').text(resp.label))
	 				});	
						
					  
					typingTimer = setTimeout(function(){}, doneTypingInterval);
				});
				
				//on keydown, clear the countdown 
				$input.on('keydown', function () {
					console.log("clearing timer");
				  clearTimeout(typingTimer);
				});
				
			};
			
			} catch (e) {
			console.error(e.message);
		} finally {
			cbk && cbk();
		}
	};
	
	function autocompleteCb(request, cbk){
			console.log("autocomplete request : "+request);
			var q = request;
			const req = new XMLHttpRequest();

			req.onreadystatechange = function(event) {
			    // XMLHttpRequest.DONE === 4
			    var list=[];
			    if (this.readyState === XMLHttpRequest.DONE) {
			        if (this.status === 200) {
			            obj = JSON.parse(this.responseText);
			            console.log(obj);
			            for(i=0; i<obj.features.length; i++){
			            	var item = {
				            	label:obj.features[i].properties.label,
				            	value:obj.features[i].properties.name,
				            	cp:obj.features[i].properties.postcode,
				            	street:obj.features[i].properties.street,
				            	housenumber:obj.features[i].properties.housenumber,
				            	coords:obj.features[i].geometry.coordinates["1"]+","+obj.features[i].geometry.coordinates["0"],
				            	city:obj.features[i].properties.city,
				            	insee:obj.features[i].properties.citycode,
			            	};
			            	list.splice(i, 0, item);
			            }
			            console.log(list);
			            cbk(list);
			        } else {
			            console.log("Status de la réponse: %d (%s)", this.status, this.statusText);
			        }
			    }
			};
			var replaced = q.split(' ').join('+');
			var requestUrl = 'https://api-adresse.data.gouv.fr/search/?q='+replaced;
			req.open('GET', requestUrl, true);
			req.send(null);
			
		}
	
	
})(window.$ui, jQuery);
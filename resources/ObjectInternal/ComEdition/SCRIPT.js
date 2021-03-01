(function(ui) {
    if (!ui) return;
    var app = ui.getAjax();
    Simplicite.UI.hooks.ComEdition = function(o, cbk) {
        try {
                var p = o.locals.ui;
                
                
            	p.list.minified = true;
            	p.list.layout = 'inline';
            	
        }
        catch(e) {
            // Thank you to isolate your scripts
            app.error("Error in Simplicite.UI.hooks.McoLivrePayant: "+e.message);
        }
        finally {
            // Required callback when hooks are loaded
            cbk && cbk();
        }
    };
})(window.$ui);
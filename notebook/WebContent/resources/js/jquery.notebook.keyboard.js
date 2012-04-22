(function($)
{
    if(!$.notebook)
    {
        $.notebook = new Object();
    };
    
    $.notebook.Keyboard = function(el, options)
    {
        // To avoid scope issues, use 'base' instead of 'this'
        // to reference this class from internal events and functions.
        var base = this;
        
        // Access to jQuery and DOM versions of element
        base.$el = $(el);
        base.el = el;
        
        // Add a reverse reference to the DOM object
        base.$el.data("notebook.Keyboard", base);
        
        base.init = function()
        {
        	base.autocompleteText = "";
        	
        	base.active=false;
        	
        	base.dragging=false;
        	
        	base.inKey = false;
        	
        	// Extend the options with the defaults if necessary.
            base.options = $.extend({},$.notebook.Keyboard.defaultOptions, options);
            
            // Build the keyboard and make it invisible.
            base.keyboard = $('[id='+base.options.keyboardElement+']');
           
            // Uncomment this to use Primefaces autocomplete
            base.autocomplete = $('[id="'+base.options.clientId+':'+base.options.autocompleteValueElement+'"]');

            // Uncomment this to build fresh autocomplete input field
            //base.autocomplete = $(document.createElement('input'));
           //base.keyboard.append(base.autocomplete);
           // base.autocomplete.autocomplete({
           // 	source : ["cat"]
           // });
            
            
            base.autocomplete.css({
            	'position' : 'absolute',
            	'top' : base.options.diameter/2,
            	'left' : (base.options.diameter/2)-base.options.diameter/4 + 10,
            	'z-index' : '51',
            	'size' : '15'
            });
            
            base.keyboard.css( {
            	'width' : base.options.diameter,
            	'height' : base.options.diameter,
            	'position' : 'absolute',
            	'background-color' : '#eee',
            	'border-radius' : base.options.diameter/2,
            	'z-index' : '50'
            });
            
            var canvas = Raphael(base.keyboard.attr('id'), base.options.diameter, base.options.diameter);
            angle = 0;
            var color = Raphael.color(base.options.color);
            while (angle <= 360) 
            {
		            (function (t, c) {
		            	charRadius = ((Math.PI*base.options.diameter)/base.options.numOptions)/2.5;
		            	// Draw the character for this circle.
		            	px = (0.5*base.options.diameter) + ( (0.45*base.options.diameter) * Math.cos( angle * (Math.PI/180) ) );
		            	py = (0.5*base.options.diameter) + ( (0.45*base.options.diameter) * Math.sin( angle * (Math.PI/180) ) );
		            	
		            	character = base.options.characterSet[Math.ceil((angle/(360/base.options.numOptions)))];
		            	
		            	x = 0.18*base.options.diameter;
		            	y = 0.18*base.options.diameter;
		            	text = canvas.text(px, py, character);
		            	text.transform("s1.5");
		            	
	            	
		            	// Draw the circle and attach event handlers to it.
		                canvas.circle(x, y, charRadius).attr({stroke: c, fill: c, transform: t, "fill-opacity": .4})
		                .mouseover(function () {
		                    this.animate({"fill-opacity": .75}, 200);
		                    base.inKey = true;
		                }).mouseout(function () {
		                    this.animate({"fill-opacity": .4}, 200);
		                    base.inKey = false;
		                }).click(function(){
		                	console.log( this.character );
		                	// Append the character to the autocomplete word and hidden value.
		                	//base.autocomplete.val(base.autocomplete.val()+this.character);
		                	base.autocompleteText = base.autocompleteText + this.character;
		                	//autocompleteWidget.setText( base.autocompleteText );
		                	autocompleteWidget.search( base.autocompleteText );
		                }).character = character;
	            })("r" + angle + " " + 0.5*base.options.diameter +", "+0.5*base.options.diameter, color);
	            angle += (360/base.options.numOptions);
	        }
            
            // Hide the keyboard
            base.keyboard.hide();
            
            // Bind the context menu event.
            base.$el.contextmenu( function(e) 
            { 
            	e.preventDefault();
            	// Make keyboard visible, etc.
            	if(!base.active)
            	{
            		base.activateKeyboard(e);
            		base.active = true;
            	}
            	else
            	{
            		base.keyboard.hide();
            		base.active = false;
            	}
            });
            
            base.keyboard.mousedown(function(e){
            	if(!base.inKey) {base.dragging=true;}
            });
            
            base.keyboard.mouseup(function(e){
            	base.dragging=false;
            });
            
            base.keyboard.mousemove(function(e){
            	if(base.dragging)
            	{
            		base.keyboard.css('top', e.pageY-base.options.diameter/2);
            		base.keyboard.css('left', e.pageX-base.options.diameter/2);
            	}
            });
        };
        
        base.activateKeyboard = function(e) 
        {
        	// Position the keyboard appropriately and make it visible.
    		base.keyboard.css('top', e.pageY-base.options.diameter/2);
    		base.keyboard.css('left', e.pageX-base.options.diameter/2);
        	
    		//base.autocomplete.css('top',500); base.autocomplete.css('left',1000);
    		
        	base.keyboard.show();
        };
        
        // Run initializer
        base.init();
    };
    
    $.notebook.Keyboard.defaultOptions = 
    {
    	clientId : '',
    	keyboardElement : 'keyboard',
    	autocompleteValueElement : 'keyboardAutocomplete',
        diameter : '400',
        color : 'cornflowerblue',
        numOptions : '0',
        characterSet : [ ]
    };
    
    $.fn.notebook_Keyboard = function(options)
    {
        return this.each(function()
        {
            (new $.notebook.Keyboard(this, options));
        });
    };
    
})(jQuery);

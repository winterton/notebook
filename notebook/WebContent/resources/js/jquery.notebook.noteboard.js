(function($){
    if(!$.notebook){
        $.notebook = new Object();
    };
    
    $.notebook.Noteboard = function(el, options){
        // To avoid scope issues, use 'base' instead of 'this'
        // to reference this class from internal events and functions.
        var base = this;
        
        // Access to jQuery and DOM versions of element
        base.$el = $(el);
        base.el = el;
        
        // Add a reverse reference to the DOM object
        base.$el.data("notebook.Noteboard", base);
        
        base.init = function(){
        	
        	// Extend the options object
            base.options = $.extend({},$.notebook.Noteboard.defaultOptions, options);
            
            // Draw the notes
            for( var i = 0; i < base.options.notes.length; i++ )
            {
            	var note = base.options.notes[i];
            	
            	for( var j = 0; j < note.categories.length; j++ )
            	{
            		//var category = note.categories[j];
            	}
            	
            	// Draw each note
            	var noteDiv = $(document.createElement('div'));
            	noteDiv.addClass('note');
            	noteDiv.css('background-color',note.backgroundColor);
            	noteDiv.css('color',note.textColor);
            	
            	var noteHeader = $(document.createElement('div'));
            	noteHeader.addClass('noteHeader');
            	//noteHeader.text(note.title);
            	
            	var noteHeaderText = $(document.createElement('div'));
            	noteHeaderText.text(note.title);
            	noteHeaderText.css('width','80%');
            	noteHeaderText.css('height','100%');
            	noteHeaderText.css('text-overflow','ellipsis');
            	noteHeaderText.css('float','left');
            	
            	noteHeader.append(noteHeaderText);
            	
            	var deleteButton = $(document.createElement('button'));
            	deleteButton.text('X');
            	deleteButton.css('float','right');
            	
            	deleteButton.live('mouseenter', function(e){
            		$(e.target).css('color','#ddd');
            	});
            	
            	deleteButton.live('mouseleave', function(e){
            		$(e.target).css('color','#000');
            	});
            	
            	noteHeader.append(deleteButton);
            	
            	
            	var noteBody = $(document.createElement('div'));
            	noteBody.addClass('noteBody');
            	noteBody.text(note.comments);
            	
            	noteDiv.append(noteHeader);
            	noteDiv.append(noteBody);
            	
            	base.$el.append( noteDiv );
           	}
            
            var p = $(document.createElement('p'));
            p.css('clear','both');
            base.$el.append(p);
            
            base.$el.sortable(
            		{
            			containment : base.$el
            		});
            
        };
        
        // Sample Function, Uncomment to use
        // base.functionName = function(paramaters){
        // 
        // };
        
        // Run initializer
        base.init();
    };
    
    $.notebook.Noteboard.defaultOptions = {
        notes: null
    };
    
    $.fn.notebook_Noteboard = function(options){
        return this.each(function(){
            (new $.notebook.Noteboard(this, options));
        });
    };
    
})(jQuery);

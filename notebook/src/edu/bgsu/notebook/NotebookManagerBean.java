package edu.bgsu.notebook;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;

@SessionScoped
@Named
public class NotebookManagerBean implements Serializable
{
	private static final long serialVersionUID = 4105775284798910753L;
	
	// The name of the application.  Declared here so it can be "plugged in" in other places throughout the GUI.
	final static String applicationName = "Research Notebook Manager";

	// The body text of the currently-loaded note.
	private String noteText = "";
	
	private String autocompleteText = "";

	private String keyboardColor = NotebookColors.GREEN.getHexString();

	private Note currentNote;
	
	private Notebook notebook;

	/**
	 * Default constructor.
	 */
	public NotebookManagerBean()
	{
		// Important:  Put initialization stuff into the init() method.  DO NOT put initializations here.
	}
	
	/**
	 * Initialization method.
	 */
	@PostConstruct
	void init()
	{
		
	}
	
	
	/**
	 * Dictionary autocomplete bean method.
	 */
	public List<String> autocomplete( String query )
	{
		return Autocomplete.search( query );
	}
	
	/**
	 * Note/category search autocomplete bean method.
	 */
	public List<Note> search( String query )
	{
		List<Note> results = new ArrayList<Note>();
		
		this.getDemoNotes();
		
		Note searchModel = new Note(query, null, null, null);
		results.addAll( notebook.searchNotes(searchModel, new TitleComparator()) );
		//for( Note result : notebook.searchNotes(searchModel, new CommentsComparator()) )
		//	if( ! results.contains( result ) ) results.add( result );
		
		return results;
	}
	
	/**
	 * File upload listener for the 'open notebook' welcome option.  Opens and parses the supplied notebook
	 * file, and redirects to the main application page.
	 * @param e The file upload event
	 */
	public void openNotebook( FileUploadEvent e )
	{
		try 
		{
			// TODO Read in the notebook file.
			
			
			// Redirect to the main application page.
			FacesContext.getCurrentInstance().getExternalContext().redirect("board.xhtml");
		} 
		catch (IOException e1) { e1.printStackTrace(); }
	}
	
	/**
	 * Simple utility function to add a message to the FacesContext.
	 * @param summary The message summary.
	 * @param detail The message detail.
	 * @param severity The message severity.
	 */
	private static void message(String summary, String detail, FacesMessage.Severity severity)
	{
		FacesMessage message = new FacesMessage();
		message.setSeverity(severity);
		message.setSummary(summary);
		message.setDetail(detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public List<Note> getDemoNotes()
	{
		List<Category> categories = Arrays.asList( new Category[]{ new Category("Demo Notes", "These are for demo/testing purposes only!") } );
		List<Note> notes = new ArrayList<Note>();
		for( int i = 0; i < 10; i++ )
			notes.add( new Note("Note " + i + " Title", 
								"Note " + i + " Comments",
								NotebookColors.BLUE,
								categories) );
		
		// TODO remove this
		notebook = new Notebook("Demo");
		notebook.add( categories.get(0) );
		for( Note n : notes ) notebook.add( n );
		
		return notes;
	}
	
	public void autocompleteValueChanged(ValueChangeEvent e)
	{
		System.out.println("Autocomplete text changed: " + (String)e.getNewValue());
		// TODO
	}
	
	// ---------- Generic getters and setters below here ----------
	
	public Note getCurrentNote() 
	{
		return this.currentNote;
	}

	public void setCurrentNote(Note currentNote) 
	{
		System.out.println("Setting current note: " + currentNote.getTitle());
		this.currentNote = currentNote;
	}
	
	public String getKeyboardColor() 
	{
		return keyboardColor;
	}
	
	public String getAutocompleteText() 
	{
		return autocompleteText;
	}

	public void setAutocompleteText(String autocompleteText) 
	{
		System.out.println("Changing autocomplete text: " + autocompleteText);
		this.autocompleteText = autocompleteText;
	}
	
	/**
	 * Returns the name of the application.
	 * @return The application name.
	 */
	public String getApplicationName()
	{
		System.out.println(applicationName);
		return applicationName;
	}
	
	/**
	 * Gets the body text of the current note.
	 * @return The current note body text.
	 */
	public String getNoteText() 
	{
		return noteText;
	}

	/**
	 * Sets the body text of the current note.
	 * @param noteText
	 */
	public void setNoteText(String noteText) 
	{
		this.noteText = noteText;
	}
}
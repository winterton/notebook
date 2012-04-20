package edu.bgsu.notebook;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Named;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DualListModel;

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

	private String newNoteTitle;
	private DualListModel<Category> newNoteCategories;
	private String newNoteComments;
	private String newCategoryTitle;
	private String newCategoryComments;


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
		
		Note searchModel = new Note(query, null, null, null);
		results.addAll( notebook.searchNotes(searchModel, new TitleComparator()) );
		
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
	
	public String startNewNotebook()
	{
		System.out.println("Constructing notebook!");
		
		// TODO Have the user enter the notebook name somewhere.
		notebook = new Notebook("Notebook");
		
		notebook.add( new Category("General", "General/miscellaneous notes.") );
		
		newNoteCategories = new DualListModel<Category>(notebook.getCategories(), new ArrayList<Category>());
		
		return "noteboard";
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
	
	public String createNewCategory()
	{
		notebook.add( new Category( newCategoryTitle, newCategoryComments ) );
		newCategoryTitle = ""; newCategoryComments = "";
		return "noteboard";
	}
	
	public DualListModel<Category> getNewNoteCategories()
	{
		return this.newNoteCategories;
	}
	
	public void setNewNoteCategories(DualListModel<Category> categories)
	{
		this.newNoteCategories = categories;
	}
	
	public String createNewNote()
	{
		if( newNoteTitle == null )
		{
			message("Note must have a title!", "Note must have a title.", FacesMessage.SEVERITY_ERROR);
			return "";
		}
		if( newNoteTitle.length() == 0 )
		{
			message("Note must have a title!", "Note must have a title.", FacesMessage.SEVERITY_ERROR);
			return "";
		}
		if( newNoteComments == null )
		{
			message("Note must have comments!", "Note must have comments.", FacesMessage.SEVERITY_ERROR);
			return "";
		}
		if( newNoteComments.length() == 0)
		{
			message("Note must have comments!", "Note must have comments.", FacesMessage.SEVERITY_ERROR);
			return "";
		}
		
		message("Added new note!","Added " + newNoteTitle, FacesMessage.SEVERITY_INFO);
		
		// TODO Add category selection to new note page.
		notebook.add( new Note( newNoteTitle, newNoteComments, NotebookColors.YELLOW, new ArrayList<Category>()) );
		
		// Clear the new note title and comments fields.
		newNoteTitle = ""; newNoteComments = "";
		
		newNoteCategories = new DualListModel<Category>(notebook.getCategories(), new ArrayList<Category>());
		
		// Navigate application to the note browser.
		return "noteboard";
	}
	
	// ---------- Generic getters and setters below here ----------
	
	public Notebook getNotebook() 
	{
		return notebook;
	}
	
	public String getNewNoteComments() 
	{
		return newNoteComments;
	}

	public void setNewNoteComments(String newNoteComments) 
	{
		this.newNoteComments = newNoteComments;
	}

	public String getNewCategoryTitle() 
	{
		return newCategoryTitle;
	}

	public void setNewCategoryTitle(String newCategoryTitle)
	{
		this.newCategoryTitle = newCategoryTitle;
	}

	public String getNewCategoryComments() 
	{
		return newCategoryComments;
	}

	public void setNewCategoryComments(String newCategoryComments) 
	{
		this.newCategoryComments = newCategoryComments;
	}
	
	public String getNewNoteTitle()
	{
		return newNoteTitle;
	}

	public void setNewNoteTitle(String newNoteTitle) 
	{
		this.newNoteTitle = newNoteTitle;
	}
	
	public Note getCurrentNote() 
	{
		return this.currentNote;
	}

	public void setCurrentNote(Note currentNote) 
	{
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
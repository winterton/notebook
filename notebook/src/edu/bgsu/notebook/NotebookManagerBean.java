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

import com.google.gson.Gson;

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

	private String keyboardColor = NotebookColors.GREEN;

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
	 * Returns the current notebook's list of notes as a JSON-serialized string.
	 * @return JSON-serialized List<Note>
	 */
	public String getNotesJson()
	{
		return new Gson().toJson( notebook.getNotes() );
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
		
		Note searchModel = new Note(query, null, null, null, null);
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
		// TODO Have the user enter the notebook name somewhere.
		notebook = new Notebook("Notebook");
		
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
		notebook.add( new Note( newNoteTitle, newNoteComments, NotebookColors.YELLOW, NotebookColors.BLACK, new ArrayList<Category>()) );
		
		// Clear the new note title and comments fields.
		newNoteTitle = ""; newNoteComments = "";
		
		newNoteCategories = new DualListModel<Category>(notebook.getCategories(), new ArrayList<Category>());
		
		// Navigate application to the note browser.
		return "noteboard";
	}
	
	public String loadDemoNotebook()
	{
		notebook = new Notebook("Demo");
		
		Category cat0 = new Category("General", "This is a category for miscellaneous notes.");
		Category cat1 = new Category("Sea Otters", "This is a category for notes regarding sea otters.");
		Category cat2 = new Category("Cat Facts", "This is a category for fun facts about cats.");
		Category cat3 = new Category("Poets", "I love poetry!");
		
		List<Category> note0Cats = new ArrayList<Category>(); note0Cats.add(cat0); note0Cats.add(cat3);
		List<Category> note1Cats = new ArrayList<Category>(); note1Cats.add(cat1);
		List<Category> note2Cats = new ArrayList<Category>(); note2Cats.add(cat2);
		List<Category> note4Cats = new ArrayList<Category>(); note4Cats.add(cat0);
		
		Note note0 = new Note("Shakespeare Quotes", 
				"What's in a name? That which we call a rose/ By any other name would smell as sweet.", 
				NotebookColors.YELLOW,
				NotebookColors.BLACK, 
				note0Cats );
		Note note1 = new Note("Sea Otters are the Best", 
				"The sea otter (Enhydra lutris) is a marine mammal native to the coasts of the northern and eastern North Pacific Ocean. Adult sea otters typically weigh between 14 and 45 kg (30 to 100 lb), making them the heaviest members of the weasel family, but among the smallest marine mammals. Unlike most marine mammals, the sea otter's primary form of insulation is an exceptionally thick coat of fur, the densest in the animal kingdom. Although it can walk on land, the sea otter lives mostly in the ocean.",
				NotebookColors.BLUE, 
				NotebookColors.BLACK, 
				note1Cats);
		Note note2 = new Note("Cat Facts!",
				"Both humans and cats have identical regions in the brain responsible for emotion.",
				NotebookColors.GREEN,
				NotebookColors.BLACK,
				note2Cats);
		Note note3 = new Note("Cat Facts 2",
				"Unlike humans, cats do not need to blink their eyes on a regular basis to keep their eyes lubricated.",
				NotebookColors.GREEN,
				NotebookColors.BLACK,
				note2Cats);
		Note note4 = new Note("Usability Engineering",
				"This is the best class ever.  For real.",
				NotebookColors.RED,
				NotebookColors.GREEN,
				note4Cats);
		
		notebook.add(cat0); notebook.add(cat1); notebook.add(cat2);
		notebook.add(note2); notebook.add(note3); notebook.add(note0); notebook.add(note1); notebook.add(note4);
		
		newNoteCategories = new DualListModel<Category>(notebook.getCategories(), new ArrayList<Category>());
		
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
package edu.bgsu.notebook.test;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import edu.bgsu.notebook.*;

/**
 * To run these, right click on ExampleTests.java and choose Run As -> JUnit Test.
 * @author Josh LaRoe <jklaroe@gmail.com>
 */
public class SearchingTests
{
	public Notebook myNotebook = new Notebook("My notebook");
	
	public Note noteOne = new Note("Milk", "Milk comes from cows.", NotebookColors.BLACK, null);
	public Note noteTwo = new Note("Eggs", "Eggs come from chickens.", NotebookColors.BLACK, null);
	public Note noteThr = new Note("Sugar", "Sugar comes from sugarcane.", NotebookColors.BLACK, null);
	
	public Note noteSrch;
	
	public List<Note> resultsSrch;
	
	public TitleComparator titleSrch = new TitleComparator();
	public DateComparator dateSrch = new DateComparator();
	public CommentsComparator commentsSrch = new CommentsComparator();
	public CategoryComparator categorySrch = new CategoryComparator();
	
	public Category categoryOne = new Category("Animals", "These notes are about animals.");
	public Category categoryTwo = new Category("Plants", "These notes are about plants.");
	
	@Test
	public void testSearch()
	{
		noteOne.addCategory(categoryOne);
		noteTwo.addCategory(categoryOne);
		noteThr.addCategory(categoryTwo);
		
		noteOne.setTimeStamp(new Date(1325419200));
		noteTwo.setTimeStamp(new Date(1328097600));
		noteThr.setTimeStamp(new Date(1328097600));
		
		myNotebook.add(categoryOne);
		myNotebook.add(categoryTwo);
		
		myNotebook.add(noteOne);
		myNotebook.add(noteTwo);
		myNotebook.add(noteThr);
		
		System.out.println("Contents of myNotebook:");
		
		for(Note note : myNotebook.getNotes())
			System.out.println(note.getTitle() + " - " + note.getComments());
		
		noteSrch = new Note("Eggs", null, null, null);
		resultsSrch = myNotebook.searchNotes(noteSrch, titleSrch);
		
		System.out.println("Title Search results:");
		
		for(Note note : resultsSrch)
			System.out.println(note.getTitle() + " - " + note.getComments());
		
		noteSrch = new Note(null, "cows", null, null);
		resultsSrch = myNotebook.searchNotes(noteSrch, commentsSrch);
		
		System.out.println("Comments Search results:");
		
		for(Note note : resultsSrch)
			System.out.println(note.getTitle() + " - " + note.getComments());
		
		noteSrch = new Note(null, null, null, null);
		noteSrch.addCategory(categoryTwo);
		
		resultsSrch = myNotebook.searchNotes(noteSrch, categorySrch);
		
		System.out.println("Category Search results:");
		
		for(Note note : resultsSrch)
			System.out.println(note.getTitle() + " - " + note.getComments());
		
		noteSrch = new Note(null, null, null, null);
		noteSrch.setTimeStamp(new Date(1328097600));
		
		resultsSrch = myNotebook.searchNotes(noteSrch, dateSrch);
		
		System.out.println("Date Search results:");
		
		for(Note note : resultsSrch)
			System.out.println(note.getTitle() + " - " + note.getComments());
	}
}
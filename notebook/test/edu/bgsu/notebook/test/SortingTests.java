package edu.bgsu.notebook.test;

import org.junit.Assert;
import org.junit.Test;

import edu.bgsu.notebook.*;

/**
 * To run these, right click on ExampleTests.java and choose Run As -> JUnit Test.
 * @author Josh LaRoe <jklaroe@gmail.com>
 */
public class SortingTests
{

	@Test
	public void testSortByTitle()
	{
		Notebook myNotebook = new Notebook("My notebook");
		
		myNotebook.add(new Note("Lactose","This is my first note", NotebookColors.BLACK, null));
		myNotebook.add(new Note("Anger","This is my third note", NotebookColors.BLACK, null));
		myNotebook.add(new Note("Celebrate","This is my second note", NotebookColors.BLACK, null));
		
		System.out.println("Before sort:");
		
		for(Note note : myNotebook.getNotes())
		{
			System.out.println(note.getTitle());
		}
		
		myNotebook.sortNotes(new TitleComparator());
		
		System.out.println("After sort:");
		
		for(Note note : myNotebook.getNotes())
		{
			System.out.println(note.getTitle());
		}
		
		if(myNotebook.getNotes().get(0).getTitle() != "Anger")
			Assert.fail("Anger should come first");
		
	}
	
	@Test
	public void testTwoPlusTwoEqualsFour()
	{
		// To test something, we use cases where we know for sure what the result should be.
		int known_correct_answer = 4;
		
		// Then we just check that everything is as we expect.  If not, call the fail() method.
		if ( 2 + 2 != known_correct_answer )
			Assert.fail("2 + 2 not equal to 4!  Call George Orwell!");
	}
}
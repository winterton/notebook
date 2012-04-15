package edu.bgsu.notebook.test;

import java.util.Date;

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
		
		long date = 132537200;
		long day = 86400;
		
		myNotebook.add(new Note("Lactose","This is my first note", NotebookColors.BLACK, null));
		myNotebook.add(new Note("Anger","This is my second note", NotebookColors.BLACK, null));
		myNotebook.add(new Note("Celebrate","This is my third note", NotebookColors.BLACK, null));
		
		System.out.println("Before sort:");
		
		for(Note note : myNotebook.getNotes())
		{
			System.out.println(note.getTitle() + " " + note.getComments());
			note.setTimeStamp(new Date(date));
			date += day;
		}
		
		myNotebook.sortNotes(new TitleComparator());
		
		System.out.println("After sort:");
		
		for(Note note : myNotebook.getNotes())
		{
			System.out.println(note.getTitle() + " " + note.getComments());
		}
		
		myNotebook.sortNotes(new DateComparator());
		
		System.out.println("After sort:");
		
		for(Note note : myNotebook.getNotes())
		{
			System.out.println(note.getTitle() + " " + note.getComments());
		}
		
	}
}
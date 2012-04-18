package edu.bgsu.notebook;

import java.util.*;

/**
 * Notebook class.
 * @author Josh LaRoe
 * @since 04/03/2012
 */

public class DateComparator implements Comparator<Note>
{
	public int compare(Note leftNote, Note rightNote)
	{
		return leftNote.getTimeStamp().compareTo(rightNote.getTimeStamp());
	}
}
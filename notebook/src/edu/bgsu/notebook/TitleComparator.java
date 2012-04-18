package edu.bgsu.notebook;

import java.util.*;

/**
 * Title Comparator
 * @author Josh LaRoe
 * @since 04/03/2012
 */

public class TitleComparator implements Comparator<Note>
{
	public int compare(Note leftNote, Note rightNote)
	{
		return leftNote.getTitle().compareTo(rightNote.getTitle());
	}
}
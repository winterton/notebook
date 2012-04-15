package edu.bgsu.notebook;

import java.util.*;

/**
 * Category Comparator
 * @author Josh LaRoe
 * @since 04/03/2012
 */

public class CategoryComparator implements Comparator<Note>
{
	public int compare(Note leftNote, Note rightNote)
	{
		return rightNote.getCategories().containsAll(leftNote.getCategories()) ? 0 : 1;
	}
}
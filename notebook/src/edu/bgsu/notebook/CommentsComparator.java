package edu.bgsu.notebook;

import java.util.*;

/**
 * Comments Comparator
 * @author Josh LaRoe
 * @since 04/03/2012
 */

public class CommentsComparator implements Comparator<Note>
{
	public int compare(Note leftNote, Note rightNote)
	{
		return rightNote.getComments().matches("(?i).*" + leftNote.getComments() + ".*") ? 0 : 1;
	}
}
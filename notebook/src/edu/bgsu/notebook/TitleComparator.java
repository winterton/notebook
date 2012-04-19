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
		if ( leftNote.getTitle().toLowerCase().contains( rightNote.getTitle().toLowerCase() ) ||
				rightNote.getTitle().toLowerCase().contains( leftNote.getTitle().toLowerCase() ) ) return 0;
		
		else return leftNote.getTitle().toLowerCase().compareTo( rightNote.getTitle().toLowerCase() );
	}
}
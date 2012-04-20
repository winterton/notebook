package edu.bgsu.notebook;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;

public class NoteConverter implements Converter
{
	//@Inject
	//NotebookManagerBean bean;

	/**
	 * Default constructor.
	 */
	public NoteConverter()
	{
		
	}
	
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) 
	{
		//if(arg2 == null) return null;
		//for( Note n : bean.getNotebook().getNotes() )
		//	if( n.getTitle().equals(arg2) )
		//		return n;
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) 
	{
		if( arg2 == null ) return null;
		return ( (Note) arg2 ).getTitle();
	}
	
}
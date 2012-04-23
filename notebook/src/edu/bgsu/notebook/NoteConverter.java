package edu.bgsu.notebook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class NoteConverter implements Converter
{
	static final private BASE64Encoder encoder = new BASE64Encoder();
	static final private BASE64Decoder decoder = new BASE64Decoder();

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) 
	{
		try
		{
			ByteArrayInputStream in = new ByteArrayInputStream( decoder.decodeBuffer( arg2 ) );
			ObjectInputStream obj = new ObjectInputStream( in );
			return obj.readObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) 
	{
		try 
		{	
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream obj = new ObjectOutputStream( out );
			obj.writeObject( arg2 );
			return encoder.encode( out.toByteArray() );
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
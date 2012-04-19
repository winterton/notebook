package edu.bgsu.properties;

import java.io.IOException;
import java.util.Properties;

public class NotebookProperties
{
	private static final String properties_file_location = "edu/bgsu/properties/notebook.properties";
	
	private static Properties properties;
	
	public static String DICTIONARY_FILE_CLASSPATH_LOCATION_KEY = "dictionary_file_classpath_location";
	
	public static String getPropertyValue( String key )
	{
		try
		{
			if( properties == null ) 
			{ 
				properties = new Properties(); 
				properties.load( NotebookProperties.class.getClassLoader().getResourceAsStream( properties_file_location ) );
			}
		
			return properties.getProperty( key );
		}
		catch( IOException e ) 
		{ 
			e.printStackTrace(); 
		}
		return ""; 
	}
}
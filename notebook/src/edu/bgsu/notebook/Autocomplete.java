package edu.bgsu.notebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import edu.bgsu.properties.NotebookProperties;

/**
 * Autocomplete.java
 * 
 * @author Matthew Baer
 * 
 * Purpose: A wrapper class for the autoComplete search function. The UI will call this function with the current text
 * 			that the user has entered as an argument. The job of this function is to return the five shortest words from a dictionary
 * 			file that have the substring at the beginning of them.
 * 
 * Supported Tasks: None. Purely UI function.
 */
public class Autocomplete
{
	private static HashMap<Character, LinkedList<String>> dictionary;
	
	public static List<String> search( String query )
	{
		// Initialize the results list.
		LinkedList<String> results = new LinkedList<String>();
		
		// If a null or empty query was passed in, return an empty list.
		if( query == null ) return results;
		if( query.length() == 0 ) return results;
		
		// Load the dictionary, if necessary.
		try { if( dictionary == null ) loadDictionary(); }
		catch (IOException e) { e.printStackTrace(); return results; }
		
		// Iterate through the possible choices.
		for( String option : dictionary.get( query.toLowerCase().charAt(0) ) )
		{
			if( option.toLowerCase().startsWith( query.toLowerCase() ) ) results.add( option );
			if( results.size() > 5 ) break;
		}
		
		// Return the results.
		return results;
	}
	
    /**
	 * Loads the dictionary file into the HashMap for searching.
	 * @throws IOException
	 */
	public static void loadDictionary() throws IOException
	{
		System.out.println("INFO - Dictionary loading!");
		
		// Initialize the dictionary as an empty HashMap.
		dictionary = new HashMap<Character, LinkedList<String>>();
		
		String line;
		
		//Create the input stream to prep the dictionary file for reading
		InputStream in = Autocomplete.class.getClassLoader().getResourceAsStream( NotebookProperties.getPropertyValue( NotebookProperties.DICTIONARY_FILE_CLASSPATH_LOCATION_KEY ) );
		
		//Create a buffered reader to read in the dictionary file
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//Loop until we reach the end of the file
		while( (line = br.readLine()) != null ) 
		{
			// Create a key for this letter, if one does not already exist
			if(!dictionary.containsKey(line.charAt(0)))
				dictionary.put(line.charAt(0), new LinkedList<String>());
			
			//Add the line to the dictionary linked list
			dictionary.get(line.charAt(0)).add(line);
		}
	}
}
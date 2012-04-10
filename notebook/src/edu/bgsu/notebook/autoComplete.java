/*
 * autoComplete.java
 * 
 * Author: Matthew Baer
 * 
 * Purpose: A wrapper class for the autoComplete search function. The UI will call this function with the current text
 * 			that the user has entered as an argument. The job of this function is to return the five shortest words from a dictionary
 * 			file that have the substring at the beginning of them.
 * 
 * Supported Tasks: None. Purely UI function.
 */

package edu.bgsu.notebook;

import java.util.*;
import java.io.*;

public class autoComplete {

	/**
	 * autoComplete.search()
	 * The UI will call this function with the current text that the user has entered as an argument. 
	 * The job of this function is to return the five shortest words from a dictionary file that have the substring at the beginning of them.
	 * @author Matthew Baer
	 * @since 4/10/12
	 * @param substring
	 * @return results
	 * @throws IOException
	 */
	public LinkedList<String> search(String substring) throws IOException {
		String temp;
		int i = 0, x;
		boolean foundall = false;
		boolean firstUpper = false, allUpper = false;
		LinkedList<String> dictionary = new LinkedList<String>(); //Linked list of Strings to store the words from the dictionary file
		LinkedList<String> results = new LinkedList<String>(); //Stores the top n matches
		
		/*
		 * Check the case of the substring. There are two possible cases we will replicate in the results:
		 * 1. All uppercase
		 * 2. First letter capitalized
		 * 
		 * RaNdOm cApitAlIZATION will be ignored. It's much more helpful to provide a result with correct capitalization, i.e. a word that should be capitalized.
		 */
		if(substring.compareTo(substring.toUpperCase()) == 0)
			allUpper = true;
		else if(Character.isUpperCase(substring.charAt(0)))
			firstUpper = true;
		
		//Create the input stream to prep the dictionary file for reading
		InputStream in = this.getClass().getResourceAsStream("edu.bgsu.notebook.dictionary/74550com.mon");
		
		//Create a buffered reader to read in the dictionary file
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//Read the first line from the file
		temp = br.readLine();
		
		//Loop until we reach the end of the file
		while(temp != null) {
			//Add the line to the dictionary linked list
			dictionary.add(temp);
			
			//Read the next line
			temp = br.readLine();
		}
		
		//Search the dictionary file
		//for(int i = 0; i < dictionary.size(); i++) 
		while(i < dictionary.size() && !foundall){
			temp = dictionary.get(i);
			//If we find a word that contains the substring at the beginning, add it to the results list
			if(temp.matches(substring.toLowerCase() + ".*") || temp.equalsIgnoreCase(substring)) {
				
				//Adjust the case of the matching word before adding to the list of found words
				if(allUpper)
					temp = temp.toUpperCase();
				else if(firstUpper)
					//This is really ugly. I don't know if there is a better way, though.
					temp = temp.replaceFirst("" + temp.charAt(0), "" + Character.toUpperCase(temp.charAt(0)));
				
				results.add(temp);
				
				//If this is not the last word, search for more matches
				if(i != dictionary.size() - 1){
					/*
					 * Search for other matches. Since the list is alphabetized, these should appear ONLY directly below the first match and nowhere else.
					 */
					
					//Set the counter to the next position
					x = i + 1;
					
					//Get the next word
					temp = dictionary.get(x);
					
					//Loop until we get five results, hit a result that doesn't match, or reach the end of the dictionary
					while(x < dictionary.size() && x < i + 5 && (temp.matches(substring.toLowerCase() + ".*") || temp.equalsIgnoreCase(substring))) {
						//Adjust the case of the matching word before adding to the list of found words
						if(allUpper)
							temp = temp.toUpperCase();
						else if(firstUpper)
							//This is really ugly. I don't know if there is a better way, though.
							temp = temp.replaceFirst("" + temp.charAt(0), "" + Character.toUpperCase(temp.charAt(0)));
						
						
						//Add to the results list
						results.add(temp);
						
						//Increment the counter
						x++;
						
						//Get the next word
						temp = dictionary.get(x);
					}
					
					//Found all matches
					foundall = true;
				}
				
				//Found all matches
				foundall = true;
			}
			
			//Increment the counter
			i++;
		}
		
		
		//Return matches. This will be empty if nothing is found.
		return results;
	}
}

/**
 * autoComplete.java
 * 
 * @author Matthew Baer
 * 
 * Purpose: A wrapper class for the autoComplete search function. The UI will call this function with the current text
 * 			that the user has entered as an argument. The job of this function is to return the five shortest words from a dictionary
 * 			file that have the substring at the beginning of them.
 * 
 * Supported Tasks: None. Purely UI function.
 */

package edu.bgsu.notebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class autoComplete {

	//HashMap that stores LinkedLists for every letter of the alphabet. After loading, all the words beginning with that letter will belong to the corresponding LinkedList
	HashMap<String, LinkedList<String>> dictionary = new HashMap<String, LinkedList<String>>();
	
	//Dictionary file location + name
	String dict_loc = "dictionary/74550com.mon";
	//String dict_loc = "dictionary/354984si.ngl";
	
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
		String temp, substring_l, key;
		int i = 0, x;
		boolean foundall = false;
		boolean firstUpper = false, allUpper = false;
		int maxMatches = 5;
		LinkedList<String> results = new LinkedList<String>(); //Stores the top maxMatches # of matches
		
		//Substring is empty, has numbers, or only 1 character, so return immediately with a blank result list
		if(substring.isEmpty() || substring.length() == 1 || hasDigits(substring))
			return results;
		
		
		/*
		 * Check the case of the substring. There are two possible cases we will replicate in the results:
		 * 1. All uppercase
		 * 2. First letter capitalized
		 * 
		 * RaNdOm cApitAlIZATIoN will be ignored. It's much more helpful to provide a result with correct capitalization, i.e. a word that should be capitalized.
		 */
		if(substring.compareTo(substring.toUpperCase()) == 0)
			allUpper = true;
		else if(Character.isUpperCase(substring.charAt(0)))
			firstUpper = true;
		
		//Make the substring lower case for comparing
		substring_l = substring.toLowerCase();
		
		//Pull off the first character to get HashMap key
		key = substring.substring(0, 1);
		
		//Load the dictionary into memory
		loadDictionary();
		
		//Search the dictionary file
		while(i < dictionary.get(key).size() && !foundall){
			temp = dictionary.get(key).get(i);
			//If we find a word that contains the substring at the beginning, add it to the results list
			if(temp.matches(substring_l + ".*")) {
				
				//Adjust the case of the matching word before adding to the list of found words
				if(allUpper)
					temp = temp.toUpperCase();
				else if(firstUpper)
					//This is really ugly. I don't know if there is a better way, though.
					temp = temp.replaceFirst("" + temp.charAt(0), "" + Character.toUpperCase(temp.charAt(0)));
				
				results.add(temp);
				
				//If this is not the last word, search for more matches
				if(i != dictionary.get(key).size() - 1){
					/*
					 * Search for other matches. Since the list is alphabetized, these should appear ONLY directly below the first match and nowhere else.
					 */
					
					//Set the counter to the next position
					x = i + 1;
					
					//Get the next word
					temp = dictionary.get(key).get(x);
					
					//Loop until we get maxMatches # of results, hit a result that doesn't match, or reach the end of the dictionary
					while(x < dictionary.get(key).size() && x < i + maxMatches && (temp.matches(substring_l + ".*"))) {
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
						temp = dictionary.get(key).get(x);
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
	
	
	/**
	 * autoComplete.loadDictionary
	 * Loads the dictionary file into the HashMap for searching.
	 * @throws IOException
	 */
	private void loadDictionary() throws IOException {
		String temp;
		
		//Create the input stream to prep the dictionary file for reading
		InputStream in = this.getClass().getResourceAsStream(dict_loc);
		
		//Create a buffered reader to read in the dictionary file
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		
		//Read the first line from the file
		temp = br.readLine();
		
		//Loop until we reach the end of the file
		while(temp != null) {
			
			//Create a key for this letter if it does not already exist
			if(!dictionary.containsKey(temp.substring(0, 1)))
				dictionary.put(temp.substring(0, 1), new LinkedList<String>());
			
			//Add the line to the dictionary linked list
			dictionary.get(temp.substring(0, 1)).add(temp);
			
			//Read the next line
			temp = br.readLine();
		}
	}
	
	private boolean hasDigits(String substring) {
		for(int i = 0; i < substring.length(); i++) {
			if((Character.isDigit(substring.charAt(i))))
					return true;
		}
		
		return false;
	}
}

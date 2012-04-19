package edu.bgsu.notebook.dictionary;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.bgsu.notebook.Autocomplete;

public class AutocompleteContextListener implements ServletContextListener
{
	@Override
	public void contextInitialized(ServletContextEvent arg0) 
	{
		System.out.println("Application server initializing.  Loading dictionary...");
		try 
		{
			Autocomplete.loadDictionary();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) 
	{
		// No need to do anything here.
	}
}
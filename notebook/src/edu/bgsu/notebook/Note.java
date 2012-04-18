package edu.bgsu.notebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Note class.
 * @author Josh LaRoe
 * @since 04/03/2012
 */

public class Note
{
	private Date timeStamp;
	private String title;
	private String comments;
	private NotebookColors color;
	private List<Category> categories;

	/**
	 *  Constructor for new note with categories.
	 */
	public Note(String title, String comments, NotebookColors color, List<Category> categories) 
	{
		this.timeStamp = new Date();
		this.title = title != null ? title : "";
		this.comments = comments != null ? comments : "";
		this.categories = categories != null ? categories : new ArrayList<Category>();
		this.color = color != null ? color : NotebookColors.BLACK;
	}

	public void removeCategory(Category category) 
	{
		categories.remove(category);   
	}

	public void addCategory(Category category) 
	{     
		categories.add(category);
	}

	public void addCategories(List<Category> categories) 
	{
		this.categories.addAll(categories);
	}

	public List<Category> getCategories() 
	{
		return categories;
	}

	public void setTitle(String _title) 
	{
		title = _title;
	}

	public void setComments(String _comments) 
	{
		comments = _comments;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getComments() 
	{
		return comments;
	}

	public void setColor(NotebookColors color) 
	{
		this.color = color;
	}

	public NotebookColors getColor() 
	{
		return color;
	}
	
	public Date getTimeStamp()
	{
		return timeStamp;
	}
	
	public void setTimeStamp(Date timeStamp)
	{
		this.timeStamp = timeStamp;
	}
}
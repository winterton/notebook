package edu.bgsu.notebook;

import java.util.Date;

/**
 * Category class.
 * @author Josh LaRoe
 * @since 04/03/2012
 */

public class Category 
{	
	private Date timeStamp;
	private String title;
	private String comments;

	/**
	 *  Constructor for new category.
	 */
	public Category(String _title, String _comments) {
		this.timeStamp = new Date();
		this.title = title != null ? title : "";
		this.comments = comments != null ? comments : "";
	}

	public void setComments(String _comments) 
	{   
		comments = _comments;  
	}

	public String getComments() 
	{
		return comments;
	}

	public void setTitle(String _title) 
	{
		title = _title;
	}

	public String getTitle() 
	{
		return title;
	}
	
	public Date getTimeStamp()
	{
		return timeStamp;
	}
}
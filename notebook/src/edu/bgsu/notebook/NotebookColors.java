package edu.bgsu.notebook;

import java.awt.Color;

/**
 * Colors enumeration.
 */
public enum NotebookColors 
{
    BLUE, RED, YELLOW, BLACK, MAGENTA, GREEN;
    
    public Color getColor()
    {
    	switch(this)
    	{
    	case BLUE : return new Color(0x7375d8);
    	case RED : return new Color(0xfe3f44);
    	case YELLOW : return new Color(0xffff80);
    	case BLACK : return new Color(0x000000);
    	case MAGENTA : return new Color(0xdd37b4);
    	case GREEN : return new Color(0x92ed6b);
    	default : return new Color(0xffffff);
    	}
    }
    
    public String getHexString()
    {
    	switch(this)
    	{
    	case BLUE : return "#7375D8";
    	case RED : return "#FE3F44";
    	case YELLOW : return "#FFFF80";
    	case BLACK : return "#000000";
    	case MAGENTA : return "#DD37B4";
    	case GREEN : return "#92ED6B";
    	default : return "#FFFFFF";
    	}
    }
}
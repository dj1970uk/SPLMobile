package com.djs.spl;
import java.util.*;

public class LeagueDBEntry
{
	Integer lid;
	String lname;

	public LeagueDBEntry(Integer lid, String lname)
	{
		this.lid = lid;
		this.lname = lname;
	}

	public void setLid(Integer lid)
	{
		this.lid = lid;
	}

	public Integer getLid()
	{
		return lid;
	}

	public void setLname(String lname)
	{
		this.lname = lname;
	}

	public String getLname()
	{
		return lname;
	}
}

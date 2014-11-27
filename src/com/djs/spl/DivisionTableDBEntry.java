package com.djs.spl;

public class DivisionTableDBEntry
{
	String divname,divid;
	public DivisionTableDBEntry(){
		this.divid = null;
		this.divname = null;
	}
	public DivisionTableDBEntry(String divname, String divid)
	{
		this.divname = divname;
		this.divid = divid;
	}

	public void setDivname(String divname)
	{
		this.divname = divname;
	}

	public String getDivname()
	{
		return divname;
	}

	public void setDivid(String divid)
	{
		this.divid = divid;
	}

	public String getDivid()
	{
		return divid;
	}
}

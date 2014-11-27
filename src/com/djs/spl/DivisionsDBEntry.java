package com.djs.spl;

public class DivisionsDBEntry
{
	String divid,divname;
	String sid;

	public DivisionsDBEntry(String divid, String divname, String sid)
	{
		this.divid = divid;
		this.divname = divname;
		this.sid = sid;
	}

	public DivisionsDBEntry(String sid)
	{
		this.sid = sid;
	}

	public void setDivid(String divid)
	{
		this.divid = divid;
	}

	public String getDivid()
	{
		return divid;
	}

	public void setDivname(String divname)
	{
		this.divname = divname;
	}

	public String getDivname()
	{
		return divname;
	}
}

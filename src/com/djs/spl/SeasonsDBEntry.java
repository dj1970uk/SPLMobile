package com.djs.spl;

public class SeasonsDBEntry
{
	Integer lid;
	String sid;
	String sname;

	public SeasonsDBEntry(Integer lid, String sid, String sname)
	{
		this.lid = lid;
		this.sid = sid;
		this.sname = sname;
	}

	public void setLid(Integer lid)
	{
		this.lid = lid;
	}

	public Integer getLid()
	{
		return lid;
	}

	public void setSid(String sid)
	{
		this.sid = sid;
	}

	public String getSid()
	{
		return sid;
	}

	public void setSname(String sname)
	{
		this.sname = sname;
	}

	public String getSname()
	{
		return sname;
	}
}

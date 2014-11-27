package com.djs.spl;

public class LeagueTableDBEntry
{
	String tablename_id;
	String season_id;
	String divisiondata;

	public LeagueTableDBEntry(String table_id, String year_id, String divisiondata)
	{
		this.tablename_id = table_id;
		this.season_id = year_id;
		this.divisiondata = divisiondata;
	}

	public void setTable_id(String table_id)
	{
		this.tablename_id = table_id;
	}

	public String getTable_id()
	{
		return tablename_id;
	}

	public void setYear_id(String year_id)
	{
		this.season_id = year_id;
	}

	public String getYear_id()
	{
		return season_id;
	}

	public void setDivisiondata(String divisiondata)
	{
		this.divisiondata = divisiondata;
	}

	public String getDivisiondata()
	{
		return divisiondata;
	}
	
	}

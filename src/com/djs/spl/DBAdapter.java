package com.djs.spl;

import android.content.*;
import android.database.sqlite.*;
import com.djs.spl.*;
import java.util.*;
import android.util.*;
import android.database.*;
import android.inputmethodservice.*;

class DBAdapter
{
	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "SouthportPoolLeagueDB";

    // Table names
	
	private static final String TABLE_LEAGUES = "leagues";
	private static final String TABLE_SEASONS = "seasons";
    private static final String TABLE_LEAGUETABLES = "leaguetables";
	private static final String TABLE_DIVISIONS = "divisions";
	private static final String TABLE_TEAMS = "teams";

    // League Table Columns names
	private static final String KEY_LEAGUEID = "leagueid";
    private static final String KEY_DIVISIONID = "divisionid";
    private static final String KEY_SEASONID = "seasonid";
	private static final String KEY_TEAMID = "teamid";
    private static final String KEY_TEAMNAMEID = "teamnameid";
	private static final String DIVISION_NAME = "divisionname";
	private static final String DIVISION_DATA = "divisiondata";
	private static final String TEAM_NAME = "teamname";
	private static final String LEAGUE_NAME = "leaguename";
	private static final String SEASON_NAME = "seasonname";
	private static final String TEAM_DATA = "teamdata";
	private static final String NEWS_DATA = "newsdata";
	
	private Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

    public DBAdapter (Context ctx){
		this.context = ctx;
		DBHelper = new DatabaseHelper(context);
	}

	public List getSeasons(Integer lid)
	{
		List<SeasonsDBEntry> seasons = new ArrayList<SeasonsDBEntry>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_SEASONS
			+ " WHERE leagueid = "+ lid +";";
		//	SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				SeasonsDBEntry season = new SeasonsDBEntry(Integer.parseInt( cursor.getString(1)),cursor.getString(0),cursor.getString((2)));
				Log.d("djs",cursor.getString(1)+" "+cursor.getString(0)+ " " +cursor.getString(2));
				seasons.add(season);
			} while (cursor.moveToNext());
		}
		return seasons;
	}

	public List getDivisions(String sid)
	{
		List<DivisionsDBEntry> divisions = new ArrayList<DivisionsDBEntry>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_DIVISIONS
			+ " WHERE seasonid = "+ sid +";";
		//	SQLiteDatabase db = this.getWritableDatabase();
		Log.d("djs",sid);
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DivisionsDBEntry div = new DivisionsDBEntry(cursor.getString(0),cursor.getString(3),cursor.getString(1));
				Log.d("djs",cursor.getString(0)+" "+cursor.getString(1)+ " " +cursor.getString(2)+ " "+ cursor.getString(3));
				divisions.add(div);
			} while (cursor.moveToNext());
		}
		return divisions;
	}
	
	
	public List<LeagueDBEntry> getLeagues()
	{
		List<LeagueDBEntry> leagues = new ArrayList<LeagueDBEntry>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_LEAGUES
			+ ";";
		//	SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				LeagueDBEntry league = new LeagueDBEntry(Integer.parseInt( cursor.getString(0)),cursor.getString(1));
				Log.d("djs",cursor.getString(1)+" "+cursor.getString(0));
				leagues.add(league);
			} while (cursor.moveToNext());
		}
		return leagues;
	}

	public DBAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	private class DatabaseHelper extends SQLiteOpenHelper{
		DatabaseHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
          db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
	
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO: Implement this method
		String CREATE_TABLE_LEAGUES = "CREATE TABLE " 
			+ TABLE_LEAGUES + "("
			+ KEY_LEAGUEID + " INTEGER PRIMARY KEY NOT NULL," 
			+ LEAGUE_NAME + " TEXT" + ");";
		String CREATE_TABLE_LEAGUETABLES = "CREATE TABLE " 
		+ TABLE_LEAGUETABLES + "("
			+ KEY_DIVISIONID + " TEXT PRIMARY KEY," 
			+ KEY_SEASONID + " TEXT,"
			+ DIVISION_DATA + " TEXT" + ");";
		String CREATE_TABLE_DIVISIONS = "CREATE TABLE " 
			+ TABLE_DIVISIONS + "("
			+ KEY_DIVISIONID + " TEXT PRIMARY KEY," 
			+ KEY_SEASONID + " TEXT,"
			+ KEY_LEAGUEID + " INTEGER,"
			+ DIVISION_NAME + " TEXT," 
			+ "FOREIGN KEY(" + KEY_LEAGUEID + " ) REFERENCES " 
			+ TABLE_LEAGUES +  " (" + KEY_LEAGUEID + "),"
			+ "FOREIGN KEY(" + KEY_SEASONID + " ) REFERENCES " 
			+ TABLE_SEASONS +  " (" + KEY_SEASONID + "));";
		String CREATE_TABLE_TEAMS = "CREATE TABLE " 
			+ TABLE_TEAMS + "("
			+ KEY_TEAMID + " INTEGER PRIMARY KEY NOT NULL,"
			+ KEY_TEAMNAMEID + " TEXT," 
			+ KEY_DIVISIONID + " TEXT," 
			+ KEY_SEASONID + " TEXT,"
			+ KEY_LEAGUEID + " INTEGER,"
			
			+ TEAM_NAME + " TEXT,"
			+ "FOREIGN KEY(" + KEY_DIVISIONID + " ) REFERENCES " 
			+ TABLE_DIVISIONS +  " (" + KEY_DIVISIONID + "),"
			+ "FOREIGN KEY(" + KEY_LEAGUEID + " ) REFERENCES " 
			+ TABLE_LEAGUES +  " (" + KEY_LEAGUEID + "),"
			+ "FOREIGN KEY(" + KEY_SEASONID + " ) REFERENCES " 
			+ TABLE_SEASONS +  " (" + KEY_SEASONID + "));";
			
		String CREATE_TABLE_SEASONS = "CREATE TABLE " 
			+ TABLE_SEASONS + "("
			+ KEY_SEASONID + " TEXT PRIMARY KEY," 
			+ KEY_LEAGUEID + " INTEGER,"
			+ SEASON_NAME + " TEXT," 
			+"FOREIGN KEY(" + KEY_LEAGUEID + " ) REFERENCES " 
			+ TABLE_LEAGUES +  " (" + KEY_LEAGUEID + "));";
		Log.i("djs",CREATE_TABLE_LEAGUES);
		Log.i("djs",CREATE_TABLE_SEASONS);
		Log.i("djs",CREATE_TABLE_DIVISIONS);
		Log.i("djs",CREATE_TABLE_TEAMS);
        db.execSQL(CREATE_TABLE_DIVISIONS);
		db.execSQL(CREATE_TABLE_LEAGUETABLES);
		db.execSQL(CREATE_TABLE_TEAMS);
		db.execSQL(CREATE_TABLE_LEAGUES);
		db.execSQL((CREATE_TABLE_SEASONS));
		
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int OLDVERSION, int NEWVERSION)
	{
		// TODO: Implement this method
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAGUES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEASONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIVISIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS);
        // Create tables again
        onCreate(db);

	}
}
	public List<DivisionTableDBEntry> getDivisionsForSeason(String  sid){
		List<DivisionTableDBEntry> divisions = new ArrayList<DivisionTableDBEntry>();
		// Select All Query
		String selectQuery = "SELECT  divisionname,divisionid FROM " + TABLE_DIVISIONS 
							+ " WHERE seasonid = "+ sid +";";
	//	SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				DivisionTableDBEntry div = new DivisionTableDBEntry(cursor.getString(0),cursor.getString(1));
				//div.setDivid(cursor.getString(1));
			//	div.setDivname(cursor.getString(0));
				Log.d("djs",cursor.getString(1)+" "+cursor.getString(0));
				divisions.add(div);
			} while (cursor.moveToNext());
		}
		return divisions;
	}
	
	public long insertLeague(String name)
	{
		ContentValues values = new ContentValues();
		values.put(LEAGUE_NAME,name);
		return db.insert(TABLE_LEAGUES,null,values);
	}
	
	public long insertSeason(String name,String id,String lid)
	{
		ContentValues values = new ContentValues();
		values.put(SEASON_NAME,name);
		values.put(KEY_SEASONID,id);
		values.put(KEY_LEAGUEID,lid);
		return db.insert(TABLE_SEASONS,null,values);
	}
	
	public long insertDivision(String name,String divid,String seasonid,String lid)
	{
		ContentValues values = new ContentValues();
		values.put(DIVISION_NAME,name);
		values.put(KEY_DIVISIONID,divid);
		values.put(KEY_SEASONID,seasonid);
		values.put(KEY_LEAGUEID,lid);
		return db.insert(TABLE_DIVISIONS,null,values);
	}
	public long insertTeam(String name,String teamid,String divid,String seasonid,String lid)
	{
		ContentValues values = new ContentValues();
		values.put(TEAM_NAME,name);
		values.put(KEY_TEAMNAMEID,teamid);
		values.put(KEY_DIVISIONID,divid);
		values.put(KEY_SEASONID,seasonid);
		values.put(KEY_LEAGUEID,lid);
		Log.d("djs",values.toString());
		return db.insert(TABLE_TEAMS,null,values);
	}
}

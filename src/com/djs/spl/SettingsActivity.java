package com.djs.spl;

import android.content.*;
import android.content.SharedPreferences.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import com.djs.spl.*;
import java.util.*;


public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener

{
	
	@Override
	public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState)
	{
		// TODO: Implement this method
		super.onPostCreate(savedInstanceState, persistentState);
		followlinkpref = (CheckBoxPreference) getPreferenceScreen(). findPreference("pref_followlink");
	}
	
	
	CheckBoxPreference followlinkpref;
	ListPreference prefLeague,prefSeason,prefDivision,prefTeam;
	CharSequence[] entries,entryvalues;
	SharedPreferences settings;
	//PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
	
	
	@Override
	public void onCreate ( Bundle savedInstanceState) {
		super . onCreate( savedInstanceState);
		//prefLeague = new ListPreference(this);
		//prefSeason = new ListPreference(this);
		prefDivision = new ListPreference(this);
		prefTeam = new ListPreference(this);
	//	setPreferenceScreen(createprefscreen());
		addPreferencesFromResource ( R.layout.settings);
		settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		PopulateLeagueList();
		PopulateSeasonsList(1);
		}
		
	public void PopulateSeasonsList(Integer lid){
		DBAdapter dba = new DBAdapter(this);
		dba.open();
		List<SeasonsDBEntry> seasons = new ArrayList<SeasonsDBEntry>();
		prefSeason = (ListPreference) findPreference("SeasonList");
		seasons = dba.getSeasons(lid);
		
		entries = new CharSequence[seasons.size()];
		entryvalues = new CharSequence[seasons.size()];
		int i;
		i = 0;
		for(SeasonsDBEntry season:seasons){
			entries[i] = season.getSname();
			entryvalues[i] = season.getSid();
			Log.d("djs",Integer.toString( season.getLid()));
			Log.d("djs",entries[i].toString()+ " " + entryvalues[i].toString());
			i++;
		}
		prefSeason.setEntries(entries);
		prefSeason.setEntryValues(entryvalues);
		prefSeason.setValueIndex(i-1);
		prefSeason.setSummary(prefSeason.getEntry());
	}	
		
	public ListPreference PopulateLeagueList(){
		DBAdapter dba = new DBAdapter(this);
		dba.open();
		List<LeagueDBEntry> leagues = new ArrayList<LeagueDBEntry>();
		int i;
		prefLeague = (ListPreference) findPreference("LeagueList");
		leagues = dba.getLeagues();

		entries = new CharSequence[leagues.size()];
		entryvalues = new CharSequence[leagues.size()];
		i = 0;
		for(LeagueDBEntry league:leagues){
			entries[i] = league.getLname();
			entryvalues[i] = Integer.toString( league.getLid());
			i++;
		}
		prefLeague.setEntries(entries);
		prefLeague.setEntryValues(entryvalues);
		prefLeague.setValueIndex(0);
		prefLeague.setSummary(prefLeague.getEntry());
		return prefLeague;
	}

	
	@Override
	public void onSharedPreferenceChanged (SharedPreferences sp , String key ) {
		Log.d("djs","here");
		if (key.equals("LeagueList")){
			prefLeague = (ListPreference) findPreference("LeagueList");
			prefLeague.setSummary(prefLeague.getEntry());
			PopulateSeasonsList(Integer.parseInt( prefLeague.getValue()));
		}
		if (key.equals("SeasonList")){
			prefSeason = (ListPreference) findPreference("SeasonList");
			prefSeason.setSummary(prefSeason.getEntry());
		//	PopulateSeasonsList(Integer.parseInt( prefLeague.getValue()));
		}
		
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		getPreferenceScreen(). getSharedPreferences(). unregisterOnSharedPreferenceChangeListener( this);
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		getPreferenceScreen(). getSharedPreferences(). registerOnSharedPreferenceChangeListener( this);
	}
	/*private PreferenceScreen createprefscreen()
	 {
	 PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);
	 DBAdapter dba = new DBAdapter(this);
	 dba.open();
	 List<LeagueDBEntry> leagues = new ArrayList<LeagueDBEntry>();
	 List<SeasonsDBEntry> seasons = new ArrayList<SeasonsDBEntry>();
	 List<DivisionTableDBEntry> divs = new ArrayList<DivisionTableDBEntry>();
	 int i;
	 CharSequence[] entries,entryvalues;
	 leagues = dba.getLeagues();

	 entries = new CharSequence[leagues.size()];
	 entryvalues = new CharSequence[leagues.size()];
	 i = 0;
	 for(LeagueDBEntry league:leagues){
	 entries[i] = league.getLname();
	 entryvalues[i] = Integer.toString( league.getLid());
	 Log.d("djs",entries[i].toString()+ " " + entryvalues[i].toString());
	 i++;
	 }
	 Log.d("djs",entries.toString());
	 Log.d("djs",entries.toString());
	 prefLeague.setEntries(entries);
	 prefLeague.setEntryValues(entryvalues);
	 prefLeague.setTitle("League");

	 root.addPreference(prefLeague);

	 seasons = dba.getSeasons(3);
	 entries = new CharSequence[seasons.size()];
	 entryvalues = new CharSequence[seasons.size()];

	 i = 0;
	 for(SeasonsDBEntry season:seasons){
	 entries[i] = season.getSname();
	 entryvalues[i] = season.getSid();
	 i++;
	 }
	 Log.d("djs",entries.toString());
	 Log.d("djs",entries.toString());
	 prefSeason.setEntries(entries);
	 prefSeason.setEntryValues(entryvalues);
	 prefSeason.setTitle("Season");
	 root.addPreference(prefSeason);

	 divs = dba.getDivisionsForSeason("5117964");
	 entries = new CharSequence[divs.size()];
	 entryvalues = new CharSequence[divs.size()];

	 i = 0;
	 for(DivisionTableDBEntry div:divs){
	 entries[i] = div.divname;
	 entryvalues[i] = div.divid;
	 i++;
	 }
	 Log.d("djs",entries.toString());
	 Log.d("djs",entries.toString());
	 prefDivision.setEntries(entries);
	 prefDivision.setEntryValues(entryvalues);
	 prefDivision.setTitle("Division");
	 root.addPreference(prefDivision);



	 return root;
	 }*/

}

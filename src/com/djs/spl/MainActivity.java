package com.djs.spl;

import android.app.*;
import android.content.*;
import android.graphics.*;
import android.os.*;
import android.preference.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.webkit.*;
import android.widget.*;
import com.djs.spl.*;
import java.io.*;
import java.util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;


public class MainActivity extends Activity
{

	   private OnClickListener buttonClickListener = new OnClickListener() {

	 @Override
	 public void onClick(View p1)
	 {
	 switch(p1.getId()){
	 case R.id.mainmenuButton2: // League Tables
			showWebView();
			List<DivisionsDBEntry> divslist = new ArrayList<DivisionsDBEntry>();
			 divslist = dba.getDivisions(settings.getString("SeasonList",""));
			String[] divs = new String[divslist.size()];
			//webview.setWebChromeClient(new WebChromeClient() {
		//	new getLeagueTableTask(_activity).execute("356201535", "468055784", "916149631");
			int i=0;
			 for(DivisionsDBEntry div:divslist){
				 String divname = div.getDivname();
				 if(divname.toLowerCase().contains("division")){
				 divs[i] = div.getDivid();
				 divs[i] = divs[i].substring(2,divs[i].length());
				 i++;}
				 if(Integer.parseInt(settings.getString("LeagueList",""))==3 && !divname.toLowerCase().contains("ko")){
					 divs[i] = div.getDivid();
					 divs[i] = divs[i].substring(2,divs[i].length());
					 i++;
				 }
			 }
			 new getLeagueTableTask(_activity).execute(divs);
			 
	 break;
	 case R.id.mainmenuButton1: // Stopwatch
			 Intent intent = new Intent(_activity, Stopwatch. class );
			 startActivity(intent);
	 // To do
	 break;
	 case R.id.mainmenuButton3: // My Team
	 		showWebView();
			new getTeamResultsTask(_activity).execute("995436718","1_356201535","1174183");
			break;
	 
		 
	 case R.id.mainmenuButton4:// News
			showWebView();
			new getNewsTask(_activity).execute();
			break;
	 }
	 // TODO: Implement this method
	 }
	 };
	 
	public Activity _activity = this;
	private Button LeagueTablesBtn,MyTeamBtn,RulesBtn,NewsBtn;
	//private TextView ;
	WebView webview;
	DBAdapter dba;
	
	SharedPreferences settings = null;
	String webdata = "";
	Document doc;
	boolean followlink;
	
	Handler updateBarHandler;
	String customfont = "@font-face{font-family: 'verdana';src:url('file:///android_asset/MountainsofChristmas.ttf');} body{font-family:verdana;}";
	String CSSStylesheet = "<head><style> .odd-row {background-color:rgb(239,239,239); } .centre { text-align:center;}table {outline-top:5px; border-spacing:0px; } h3{text-align:center; width:100%; background-color:yellow;} h4 {text-align:center; color:blue;} h5 {text-align:center; color:red;} th {font-size:12px; background-color:DimGray; color: gold;}td {font-size:12px;} td {outline-width:0px; margin-width:0px;}</style></head>";
    public class mywvc extends WebViewClient
	{
		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon)
		{
			// TODO: Implement this method

			super.onPageStarted(view, url, favicon);


		}

		@Override
		public void onLoadResource(WebView view, String url)
		{
			// TODO: Implement this method
			super.onLoadResource(view, url);

		}

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url)
		{
			// TODO: Implement this method
		//view.loadUrl(url);
			return true;
		}

		@Override
		public void onPageFinished(WebView view, String url)
		{
			// TODO: Implement this method
			super.onPageFinished(view, url);

		}

	}

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent keyevent)
	{
		switch (keyCode)
		{
			case KeyEvent.KEYCODE_BACK:
				if (webview.canGoBack())
				{
					webview.goBack();
					return true;
				}
		}
		return super.onKeyDown(keyCode, keyevent);
	}
*/
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		if (webview!=null){
			showMainMenu();
		} else {
			super.onBackPressed();}
	}

	public void showMainMenu(){
		setContentView(R.layout.mainmenu); 
		Typeface typeface = Typeface.createFromAsset(getAssets(),"MountainsofChristmas.ttf");
		((Button) findViewById(R.id.mainmenuButton1)).setTypeface(typeface);
		((Button) findViewById(R.id.mainmenuButton2)).setTypeface(typeface);
		((Button) findViewById(R.id.mainmenuButton3)).setTypeface(typeface);
		((Button) findViewById(R.id.mainmenuButton4)).setTypeface(typeface);
		LeagueTablesBtn = (Button) findViewById(R.id.mainmenuButton1);
		LeagueTablesBtn.setOnClickListener(buttonClickListener);
		RulesBtn = (Button) findViewById(R.id.mainmenuButton2);
		RulesBtn.setOnClickListener(buttonClickListener);
		MyTeamBtn = (Button) findViewById(R.id.mainmenuButton3);
		MyTeamBtn.setOnClickListener(buttonClickListener);
		NewsBtn = (Button) findViewById(R.id.mainmenuButton4);
		NewsBtn.setOnClickListener(buttonClickListener);
		webview=null;
	}
	
	public void showAbout(){
		
		
		final Dialog aboutDialog = new Dialog(_activity) ;
		aboutDialog.setContentView( R.layout.about) ;
		
	/*	Button AboutBtn = (Button) findViewById(R.id.aboutButton1);
		AboutBtn.setOnClickListener(new View.OnClickListener () {
				@Override
				public void onClick ( View v ) {
					aboutDialog.dismiss() ;
				}
			}) ;*/
		aboutDialog.setTitle( "About" ) ;
		aboutDialog.setCanceledOnTouchOutside(true);
		aboutDialog.show();
	}
	
	public void showWebView(){
		setContentView(R.layout.main);
		webview = (WebView) findViewById(R.id.webview);
		webview.getSettings().setJavaScriptEnabled(true);	
		webview.getSettings().setLoadWithOverviewMode(true);
		
		webview.addJavascriptInterface(new JSInterface(), "interface");
		webview.setWebViewClient(new mywvc());
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.layout.menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_settings:
				showsettings();
				return true;
			case R.id.menu_about:
				showAbout();
				
			/*	*/
// Red item was selected
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
	{
        super.onCreate(savedInstanceState);
		settings = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
		if (settings.getBoolean("firstrun", true)) {
			showsettings();
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
            settings.edit().putBoolean("firstrun", false).apply();
        }
		followlink = settings.getBoolean("pref_followlink",false);
		dba = new DBAdapter(_activity);
		dba.open();
		//new populateDBTask(_activity).execute();
		Log.d("djs",dba.getDivisionsForSeason("5117964").toString());
		showMainMenu();
    }

	private void showsettings()
	{
		// TODO: Implement this method
		//setContentView();
		Intent intent = new Intent(_activity, SettingsActivity. class );
		startActivity(intent);
	}
	
	private class JSInterface
	{
		@JavascriptInterface
		public void teamresults(String yearid, String divisionid,String teamid)
		{
				new getTeamResultsTask(_activity).execute(yearid,divisionid,teamid);
		}
		@JavascriptInterface
		public void getpdf(String url){
			Log.i("djs","in getpdf()");
			webview.loadUrl(url);
		}
	}

	public void populateDB(String baseurl) throws IOException
	{
		Elements temp,temp2,temp3;
		List<Element> leagues;
		String division,divisionid,seasonid,seasonname,yearid,yearname;

		dba.open();
		doc = Jsoup.connect(baseurl).get();
		int count = 0;
		leagues = doc.select("div#main-navigation li a").subList(2, 5);
		for (Element league:leagues)
		{
			dba.insertLeague(league.text().substring(3)); //, league.attr("href").toString().split("/")[3].split("\\.")[0]);
			count++;
			doc = Jsoup.connect(baseurl + league.attr("href").toString()).get();
			temp  = doc.select("select#adjustSeasonID option");

			for (Element season:temp)
			{
				seasonname = season.text();
				seasonid = season.attr("value").toString();
				dba.insertSeason(seasonname, seasonid, Integer.toString(count));
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{}
				doc = Jsoup.connect(baseurl + "/l/league/" + seasonid + ".html").get();
				temp2 = doc.select("select#adjustFixtureGroupKey option");
				for (Element div:temp2)
				{
					division = div.text();
					divisionid = div.attr("value").toString();
					if (division != "")
					{
						dba.insertDivision(division, divisionid, seasonid, Integer.toString(count));
						doc = Jsoup.connect(baseurl + "/l/fg/" + divisionid + ".html").get();
						temp3 = doc.select("div#selector-container a:nth-child(even)");
						for (Element team:temp3)
						{
							dba.insertTeam(team.text(), team.attr("href").toString().split("/")[4].split("\\.")[0], divisionid, seasonid, Integer.toString(count));
						}
					}
				}
			}
		}
				
	}

	
	public String getNewsList() throws IOException
	{
		Elements newsitems,newshrefs,newsbrief,links;
		String hrefs,news= "";
		doc = Jsoup.connect("http://southportpool.leaguerepublic.com/l/newsFor/6/5646793.html").get();
		newsitems = doc.select("div.content-item-text h2");
		newsbrief = doc.select("div.content-item-text p");
		newshrefs = doc.select("a.read-more-link");
		int count = 0;
		for(Element item:newsitems){
			String newstext = "";
			news+="<h3>"+item.text()+"</h3>";
		//	news+="<p>"+newsbrief.get(count).toString()+"</p>";
			doc = Jsoup.connect("http://southportpool.leaguerepublic.com"+newshrefs.get(count).attr("href").split(";")[0]).get();
			news += "<p>"+doc.select("div.news-article-page-body-container h2").get(1).text()+"</p>";
			links = doc.select("div.news-article-page-body-container a");
			int count2 = 0;
			for(Element link:links){
				link.attr("onclick","interface.getpdf(\"http://drive.google.com/viewerng/viewer?embedded=true&url="+link.attr("href")+"\")");
				link.attr("href","javascript:void(0)");
				
				news += "<p align=\"center\">"+link.toString()+"</p>";
				Log.w("djs",link.attr("onclick"));
				count2++;
			}
			count++;
		}
	//	Log.i("djs",teamname+" "+divisionid);
		;
		return "<h4>Latest News</h4>"+news;
	}
	public String getResultsTable(String yearid, String divisionid,String teamid) throws IOException
	{
		Elements table,hrefs;
		String teamname;
		doc = Jsoup.connect("http://southportpool.leaguerepublic.com/l/team/" + yearid + "/" + teamid + ".html").get();
		teamname = doc.select("div#team-name-display-container h1").text();
	//	Log.i("djs",teamname+" "+divisionid);
		table = doc.select("table.results-table").attr("width", "100%").attr("border", "1px solid black").attr("border-collapse", "collapse");
		table.select("th:eq(1)").attr("align","center");
		table.select("th:eq(4)").attr("align","center");
		table.select("th:gt(6)").remove();
		table.select("th:eq(5)").remove();
		table.select("th:eq(2)").remove();
		table.select("th:eq(0)").remove();
		for (Element tr : table){
			tr.select("td:eq(1)").attr("align","center");
			tr.select("td:eq(4)").attr("align","center");
			tr.select("td:gt(6)").remove();
			tr.select("td:eq(5)").remove();
			tr.select("td:eq(2)").remove();
			tr.select("td:eq(0)").remove();
		}
		hrefs = table.select("td a");
		for (Element href: hrefs){
			href.attr("href");
			String[] hrefsplit = href.attr("href").split("/");
			String[] hrefsplit2 = hrefsplit[4].split("\\.");
			href.attr("onclick", "interface.teamresults(\"" + hrefsplit[3] + "\",\""  + divisionid + "\",\"" + hrefsplit2[0]  + "\")");
			href.attr("href", "javascript:void(0)");
			if (!settings.getBoolean("pref_followlink",false)){href.removeAttr("href");href.removeAttr("onclick");}
			Log.w("djs",href.attr("onclick"));
		}
		return "<h4>" + teamname + "</h4><h5>Results</h5>" + table.toString();
	}
	
	public String getTeamFixtures(String yearid, String divisionid, String teamid) throws IOException
	{
		Elements table,hrefs;
		doc = Jsoup.connect("http://southportpool.leaguerepublic.com/l/matches/" + yearid + "/" + divisionid + "/" + teamid + "/-1/-1.html").get();
		Log.d("djs","http://southportpool.leaguerepublic.com/l/matches/" + yearid + "/" + divisionid + "/" + teamid + "/-1/-1.html");
		table = doc.select("table.fixtures-table").attr("width", "100%").attr("border", "1px solid black").attr("border-collapse", "collapse");
		table.select("th:eq(1)").attr("align","center");
		table.select("th:gt(4)").remove();
		table.select("th:eq(2)").remove();
		table.select("th:eq(0)").remove();
		for (Element tr : table){
			tr.select("td:eq(1)").attr("align","center");
			tr.select("td:gt(4)").remove();
			tr.select("td:eq(2)").remove();
			tr.select("td:eq(0)").remove();
		}
		hrefs = table.select("td a");
		for (Element href: hrefs)
		{
			href.attr("href");
			String[] hrefsplit = href.attr("href").split("/");
			String[] hrefsplit2 = hrefsplit[4].split("\\.");
			href.attr("onclick", "interface.teamresults(\"" + hrefsplit[3] + "\",\""  + divisionid + "\",\"" + hrefsplit2[0] + "\")");
			href.attr("href", "javascript:void(0)");
			Log.d("djs",Boolean.toString( settings.getBoolean("pref_followlink",false)));
			if (!settings.getBoolean("pref_followlink",false)){href.removeAttr("href");href.removeAttr("onclick");}
		}
		return "<h5>Remaining Fixtures</h5>" + table.toString();
		
	}
	
	class getLeagueTableTask extends AsyncTask<String,Integer,String>
	{
		Context mContext;
		ProgressDialog barProgressDialog;
		List<String> divs = new ArrayList<String>(Arrays.asList("Premier", "2nd", "3rd", "4th","5th","6th"));
		public getLeagueTableTask(Context context)
		{
			mContext = context;
		}


		@Override
		protected void onProgressUpdate(Integer[] values)
		{
			// TODO: Implement this method
			super.onProgressUpdate(values);
			barProgressDialog.setMessage("Receiving " + (String)divs.toArray()[values[0]] + " Division data...");

		}

		@Override
		protected String doInBackground(String... p1)
		{
			// TODO: Implement this method
			int count = 0;
			if (!settings.getBoolean("pref_fastmode",true)){
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e)
			{}}
			for (String str:p1){	
				publishProgress(count);
				try{ 
					webdata += getLeagueTable(str);
		if (!settings.getBoolean("pref_fastmode",true)){
			try
			{
				Thread.sleep(2000);
			}
			catch (InterruptedException e)
			{}}
			}
					
	catch (IOException e)
{}
				count++;
			}
			return webdata;
		}


		@Override
		protected void onPreExecute()
		{
			// TODO: Implement this method
			super.onPreExecute();
			webdata = "";
			barProgressDialog = new ProgressDialog(mContext);
			barProgressDialog.setTitle(R.string.progress_title);
			barProgressDialog.setMessage("Connecting to server...");
			barProgressDialog.setCancelable(false);
			barProgressDialog.show();
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO: Implement this method
			super.onPostExecute(result);
			barProgressDialog.cancel();
			barProgressDialog = null;
		//	Log.i("djs",result);
			webview.loadDataWithBaseURL(null, CSSStylesheet + result, "text/html", "UTF-8", "");

		}

	}
	
	class populateDBTask extends AsyncTask<String,Void,Void>
	{
		Context mContext;
		ProgressDialog barProgressDialog;
		public populateDBTask(Context context)
		{
			mContext = context;
		}


		@Override
		protected Void doInBackground(String ... p1)
		{
			try
			{
				populateDB("http://southportpool.leaguerepublic.com");
			}
			catch (IOException e)
			{}
			return null;
		}


		@Override
		protected void onPreExecute()
		{
			// TODO: Implement this method
			super.onPreExecute();
			webdata = "";
			barProgressDialog = new ProgressDialog(mContext);
			barProgressDialog.setTitle(R.string.progress_title);
			barProgressDialog.setMessage("Connecting to server...");
			barProgressDialog.setCancelable(false);
			barProgressDialog.show();
		}

		@Override
		protected void onPostExecute(Void result)
		{
			// TODO: Implement this method
			super.onPostExecute(result);
			barProgressDialog.cancel();
			barProgressDialog = null;
			//	Log.i("djs",result);
			

		}

	}
	
	class getNewsTask extends AsyncTask<Void,Void,String>
	{
		Context mContext;
		ProgressDialog barProgressDialog;
		List<String> myteamprogress = new ArrayList<String>(Arrays.asList("Result", "Fixture"));
		public getNewsTask(Context context)
		{
			mContext = context;
		}


		@Override
		protected void onProgressUpdate(Void... p1)
		{
			// TODO: Implement this method
			super.onProgressUpdate();
		//	barProgressDialog.setMessage("Receiving " + (String)divs.toArray()[values[0]] + " Division data...");

		}

		@Override
		protected String doInBackground(Void... p1)
		{
			// TODO: Implement this method
			
				try{ 
					webdata = getNewsList();} 
				catch (IOException e){}
				
			return webdata;
			
		}


		@Override
		protected void onPreExecute()
		{
			// TODO: Implement this method
			super.onPreExecute();
			
			barProgressDialog = new ProgressDialog(mContext);
			barProgressDialog.setTitle(R.string.progress_title);
			barProgressDialog.setMessage("Receiving News Data...");
			barProgressDialog.setIndeterminate(true);
			barProgressDialog.setCancelable(false);
			barProgressDialog.show();
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO: Implement this method
			super.onPostExecute(result);
			barProgressDialog.cancel();
			barProgressDialog = null;
			//	Log.i("djs",result);
			webview.loadDataWithBaseURL(null, CSSStylesheet + result, "text/html", "UTF-8", "");

		}

	}
	
	class getTeamResultsTask extends AsyncTask<String,Integer,String>
	{
		Context mContext;
		ProgressDialog barProgressDialog;
	//	String divisionid;
		List<String> myteamprogress = new ArrayList<String>(Arrays.asList("Result", "Fixture"));
		List<String> msgtext = new ArrayList<String>(Arrays.asList("Premier", "2nd", "3rd", "4th"));
		public getTeamResultsTask(Context context)
		{
			mContext = context;
		}


		@Override
		protected void onProgressUpdate(Integer[] values)
		{
			// TODO: Implement this method
			super.onProgressUpdate(values);
		//	Log.d("djs",values[0]);
		//	barProgressDialog.setMessage("Receiving " + values[0] + " data...");
			barProgressDialog.setMessage("Receiving " + (String)myteamprogress.toArray()[values[0]] + " data...");
			

		}

		@Override
		protected String doInBackground(String... p1)
		{
			// TODO: Implement this method
			//	publishProgress(0);
				try
				{
					webdata += getResultsTable(p1[0],p1[1],p1[2]);
				}
				catch (IOException e)
				{}
			//	publishProgress(1);
				try
				{
					webdata += getTeamFixtures(p1[0],p1[1],p1[2]);
				}
				catch (IOException e){}
	
			
Log.d("djs",p1[0]+" "+p1[1]+" "+p1[2]);

			return webdata;
		}


		@Override
		protected void onPreExecute()
		{
			// TODO: Implement this method
			super.onPreExecute();
			webdata = "";
			barProgressDialog = new ProgressDialog(mContext);
			barProgressDialog.setTitle(R.string.progress_title);
			barProgressDialog.setMessage("Receiving Team Data...");
			barProgressDialog.show();
		}

		@Override
		protected void onPostExecute(String result)
		{
			// TODO: Implement this method
			super.onPostExecute(result);
			barProgressDialog.cancel();
			barProgressDialog = null;
			webview.loadDataWithBaseURL(null, CSSStylesheet + result, "text/html", "UTF-8", "");

		}

	}
	
	public String getLeagueTable(String tableid) throws IOException
	{
		Elements table,hrefs;
		String division,divisionid;
		doc = Jsoup.connect("http://southportpool.leaguerepublic.com/l/standingsForDate/" + tableid + "/2/-1/-1.html#&panel1-1").get();
		table = doc.select("table.league-table").attr("width", "100%").attr("border", "1px solid black").attr("border-collapse", "collapse");
		division = doc.select("select#adjustFixtureGroupKey option[selected]").text();
		/*if (!settings.getBoolean("pref_prem",true) && division.equalsIgnoreCase("Premier Division")){return "";}
		if (!settings.getBoolean("pref_2nd",true) && division.equalsIgnoreCase("2nd Division")){return "";}
		if (!settings.getBoolean("pref_3rd",true) && division.equalsIgnoreCase("3rd Division")){return "";}*/
		divisionid = doc.select("select#adjustFixtureGroupKey option[selected]").attr("value");
		Log.w("djs",division+" "+divisionid);
		table.select("tr th:eq(1)").attr("width", "40%").first().text("TEAM");
		table.select("tr th:gt(1)").attr("width", "10%").attr("align", "center");
		table.select("tr th:eq(8)").remove();
		table.select("tr th:lt(1)").remove();
		for (Element tr : table)
		{
			tr.select("td:eq(1)").attr("width", "40%");
			tr.select("td:gt(1)").attr("width", "10%").attr("align", "center");
			tr.select("td:eq(8)").remove();
			tr.select("td:lt(1)").remove();
		}
		hrefs = table.select("td a");
		for (Element href: hrefs)
		{
			href.attr("href");
			String[] hrefsplit = href.attr("href").split("/");
			String[] hrefsplit2 = hrefsplit[4].split("\\.");
			href.attr("onclick", "interface.teamresults(\"" + hrefsplit[3] +  "\",\"" + divisionid + "\",\"" + hrefsplit2[0]  + "\")");
			href.attr("href", "javascript:void(0)");
			if (!settings.getBoolean("pref_followlink",false)){href.removeAttr("href");href.removeAttr("onclick");}
			Log.w("djs",href.attr("onclick"));
		}
		return "<h5>" + division + "</h5>" + table.first().toString();
	}
}

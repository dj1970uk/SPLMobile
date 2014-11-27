package com.djs.spl;
import android.webkit.*;
import android.content.*;
import android.util.*;

public class splWebClient extends WebViewClient
{
	private Context context;
	public splWebClient(Context context) {
		this.context = context;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url)
	{
		// TODO: Implement this method
		//Log.i("url",url);
		view.loadUrl(url);
		return true;
		//return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onLoadResource(WebView view, String url)
	{
		// TODO: Implement this method
		
		super.onLoadResource(view, url);
		Log.i("url",url);
	}
	
}

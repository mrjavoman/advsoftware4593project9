package com.cybertrons.orfa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;

public class StudentStatsGraphActivity extends Activity {
	
	private int student = 0;
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unimplemented_msg);
        
        // Get the message from the intent
 		Intent intent = getIntent();
 		this.student = intent.getIntExtra(NUMBER, 0);
 		 		
 		/*
 		WebView googleChartView = new WebView(this);
 		setContentView(googleChartView);
 		String mUrl = "http://chart.apis.google.com/chart?cht=p3&chd=t:30,60,10&chs=250x100&chl=cars|bikes|trucks";
 		googleChartView.loadUrl(mUrl);
 		*/
    }
}

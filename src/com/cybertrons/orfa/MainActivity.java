package com.cybertrons.orfa;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onClickStartSession(View view){
    	Intent myIntent = new Intent(MainActivity.this, StartStoryAvtivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    
    public void onClickEditStudent(View view){
    	Intent myIntent = new Intent(MainActivity.this, EditStudentActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    public void onClickImportStory(View view){
    	Intent myIntent = new Intent(MainActivity.this, ImportStoryActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    public void onClickAssignSounds(View view){
    	Intent myIntent = new Intent(MainActivity.this, AssignSoundsActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    public void onClickStats(View view){
    	Intent myIntent = new Intent(MainActivity.this, StatsActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    public void onClickDbTester(View view){
    	Intent myIntent = new Intent(MainActivity.this, DbTesterActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    
}

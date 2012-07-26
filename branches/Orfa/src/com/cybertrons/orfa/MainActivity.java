package com.cybertrons.orfa;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.util.Log;

public class MainActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	public static final String PREFS_NAME = "orfa_settings";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // If a Preference called "skipMessage" isn't created in the app, create it
        // and set it to "NOT checked".  This means that the "Do not show this message"
        // check box is "NOT checked".
        final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        if(!settings.contains("skipMessage")){
        	Log.i("debugger", "Initializing the skipMessage SharedPreference");
        	SharedPreferences.Editor editor = settings.edit();
        	// Initializing "skipMessage" to "NOT checked"
            editor.putString("skipMessage", "NOT checked");
            // Committing initialization.
            editor.commit();
        }
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
    public void onClickSettings(View view){
    	Intent myIntent = new Intent(MainActivity.this, SettingsActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
}

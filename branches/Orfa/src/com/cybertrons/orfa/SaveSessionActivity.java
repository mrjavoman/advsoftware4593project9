package com.cybertrons.orfa;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SaveSessionActivity extends Activity {
	
	private int finalScore;
	private int finalErrors;
	private String finalNotes;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_session);
		Intent intent = getIntent();
		finalScore = intent.getIntExtra(SessionStatsActivity.SCORE, 0);
		finalErrors = intent.getIntExtra(SessionStatsActivity.ERRORS, 0);

        EditText fScore = (EditText) findViewById(R.id.save_score);
        EditText fErrors = (EditText) findViewById(R.id.save_errors_text);

        fScore.setText( new Integer(finalScore).toString());
        fErrors.setText( new Integer(finalErrors).toString()); 
    }
    public void onCommittSession(View view){
	    DataBaseHelper dbHelper = new DataBaseHelper(this);
	    
	    
	    try {
	    	dbHelper.openDataBaseRW();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
			dbHelper.writeSession(finalErrors, finalScore, finalNotes);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	dbHelper.close();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
        
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }
 
}
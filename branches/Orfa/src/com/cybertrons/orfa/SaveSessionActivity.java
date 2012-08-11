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
	private String finalNotes  = "";
	private EditText score_field;
	private EditText errors_field;
	private EditText notes_field;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_session);
		Intent intent = getIntent();
		this.finalScore = intent.getIntExtra(SessionStatsActivity.SCORE, 0);
		this.finalErrors = intent.getIntExtra(SessionStatsActivity.ERRORS, 0);

        this.score_field = (EditText) findViewById(R.id.save_score);
        this.errors_field = (EditText) findViewById(R.id.save_errors_text);
        this.notes_field = (EditText) findViewById(R.id.save_notes);

        score_field.setText( new Integer(finalScore).toString());
        errors_field.setText( new Integer(finalErrors).toString()); 
    }
    public void onCommittSession(View view){
	    DataBaseHelper dbHelper = new DataBaseHelper(this);
	    
	    this.finalScore = Integer.parseInt(this.score_field.getText().toString());
	    this.finalErrors = Integer.parseInt(this.errors_field.getText().toString());
	    
	    if( this.notes_field.getText().toString().equals("") ){
	    	this.finalNotes = "N/A";
	    }
	    else{
		    this.finalNotes = this.notes_field.getText().toString();
		}
	    		
	    try {
	    	dbHelper.openDataBaseRW();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    
	    this.finalNotes = this.finalNotes.replace("'", "''");
	    
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
package com.cybertrons.orfa;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SaveSessionActivity extends Activity {
	
	private int lastWord;
	private int finalScore;
	private int finalErrors;
	private String finalNotes;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_session);
		Intent intent = getIntent();
		lastWord = intent.getIntExtra(MarkWordActivity.UID_WORD, 0);

        EditText score = (EditText) findViewById(R.id.save_score);
        EditText errors = (EditText) findViewById(R.id.save_errors_text);
	    DataBaseHelper dbHelper = new DataBaseHelper(this);

	    
	    try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    try {
	    	dbHelper.openDataBaseRW();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	int rightCount = dbHelper.getWordsCorrectCount(lastWord);
			score.setText( new Integer(rightCount).toString());
			this.finalScore = rightCount;
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
    
	    try {
	    	int wrongCount = dbHelper.getWordsIncorrectCount(lastWord);
	    	errors.setText( new Integer(wrongCount).toString());
	    	this.finalErrors = wrongCount;
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	dbHelper.close();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
        
        
    }
    public void onCommittSession(View view){
	    DataBaseHelper dbHelper = new DataBaseHelper(this);
	    
	    try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
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
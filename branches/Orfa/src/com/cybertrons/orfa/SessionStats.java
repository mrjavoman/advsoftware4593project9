package com.cybertrons.orfa;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;

public class SessionStats extends Activity {
	
	private int lastWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_stats);
    
	    DataBaseHelper dbHelper = new DataBaseHelper(this);
		// Get the message from the intent
		Intent intent = getIntent();
		lastWord = intent.getIntExtra(MarkWordActivity.UID_WORD, 0);
	    setContentView(R.layout.word_mark);

	   TextView correctBlank = (TextView) findViewById(R.id.corr_words);
	   TextView incorrectBlank = (TextView) findViewById(R.id.corr_words);
	    
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
	    	correctBlank.setText(dbHelper.getWordsCorrectCount(lastWord));
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    
	    try {
	    	incorrectBlank.setText(dbHelper.getWordsIncorrectCount(lastWord));
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    dbHelper.close();
    }
}

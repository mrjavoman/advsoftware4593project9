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
	    
	    
	   TextView correctBlank = (TextView) findViewById(R.id.corr_words);
	   TextView incorrectBlank = (TextView) findViewById(R.id.incorr_words);
	    
 
	    try {
	    	dbHelper.openDataBaseRW();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    	    
	    try {
	    	int rightCount = dbHelper.getWordsCorrectCount(lastWord);
	    	correctBlank.setText( new Integer(rightCount).toString());
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
    
	    try {
	    	int wrongCount = dbHelper.getWordsIncorrectCount(lastWord);
	    	incorrectBlank.setText( new Integer(wrongCount).toString());
//	    	incorrectBlank.setText( Integer.valueOf(wrongCount));
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    dbHelper.close();

    }
}

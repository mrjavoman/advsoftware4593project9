package com.cybertrons.orfa;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

public class MarkWordActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	public final static String SESSION = "cs4953.advsoft.orfa.SESSION";
	public static final String UID_WORD = "cs4953.advsoft.orfa.UID_WORD";
	
	private int uidWord;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		// Get the message from the intent
		Intent intent = getIntent();
		uidWord = intent.getIntExtra(DisplayButtonsActivity.WORD, 0);
        setContentView(R.layout.word_mark);
        
    }
	
	public void onClickMarkCorrect(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 0);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }
	
	public void onClickLastRead(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 1);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
    	Intent intent = new Intent(this, SessionStatsActivity.class);
    	intent.putExtra(UID_WORD, uidWord);
    	startActivity(intent);
    }
	
	public void onClickErrorSelfCorrect(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 2);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }
	
	public void onClickErrorOmit(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 3);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
	}
	
	public void onClickErrorWordOrder(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 4);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }
	
	public void onClickErrorMispronounce(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 5);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }
	
	public void onClickErrorHesitate(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 6);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }

}

package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MarkWordActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	private static final int REQUEST_CODE = 99;
	
	public int uidWord;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		// Get the message from the intent
		Intent intent = getIntent();
		uidWord = intent.getIntExtra(DisplayButtonsActivity.WORD, 0);
        setContentView(R.layout.word_mark);
        
        
    }
	
	public void onClickErrorOmit(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 1);
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
	}
	
	public void onClickErrorWordOrder(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 2);
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }
	
	public void onClickErrorMispronounce(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 3);
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }
	
	public void onClickErrorHesitate(View view){		
		DataBaseHelper dbHelper = new DataBaseHelper(this);	
		try {
			dbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}
		try {
			dbHelper.setCurrentSessionError(uidWord, 4);
		} catch (SQLException sqle) {
			throw sqle;
		}
		finish();
    }
	
	public void onClickErrorSelfCorrect(View view){
		finish();
    }
	
	public void onClickMarkCorrect(View view){
		finish();
    }
	
	public void onClickLastRead(View view){
		finish();
    }

}

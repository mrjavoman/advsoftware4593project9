package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DbTesterActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.db_tester);
        
        //create an instance of a database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        ArrayList<String> arrList;
        try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        try {
        	dbHelper.openDataBase();
       	}catch(SQLException sqle){
       		throw sqle;
       	}
                
        try {
        	arrList = dbHelper.getWords();
       	}catch(SQLException sqle){
       		throw sqle;
       	}
        
        CharSequence txt ="hello";

        try {
        	String str = arrList.remove(1);
        	((TextView)findViewById (R.id.text1)).setText(arrList.remove(0));
        	((TextView)findViewById (R.id.word1)).setText(arrList.remove(0));
        	//((TextView)findViewById (R.id.toggleButton1)).setText(arrList.remove(0));
        	
        	((ToggleButton)findViewById (R.id.toggleButton1)).setText(str);
        	((ToggleButton)findViewById (R.id.toggleButton1)).setTextOn(str);
        	((ToggleButton)findViewById (R.id.toggleButton1)).setTextOff(str);
             
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
    }
}
package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SessionStorySelectActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	public final static String STUDENT = "cs4953.advsoft.orfa.STUDENT";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_story);
    
	    DataBaseHelper dbHelper = new DataBaseHelper(this);
	    
	    ArrayList<RadioButton> storiesList;
	    
	    RadioGroup storyOptions = (RadioGroup) findViewById(R.id.story_group);
	    
/*	    
	    try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/	    
	    try {
	    	dbHelper.openDataBase();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	storiesList = dbHelper.getStories(this);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	dbHelper.close();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
       
        Iterator<RadioButton> itr = storiesList.iterator();
        while (itr.hasNext())
        {
        	storyOptions.addView(itr.next());
        }

	}
    
    public void onSelectButton(View view){
     	int radioButtonID = ((RadioGroup) findViewById(R.id.story_group)).getCheckedRadioButtonId();
    	Intent intent = new Intent(this, SessionStudentSelectActivity.class);
    	intent.putExtra(NUMBER, radioButtonID);
    	startActivity(intent);
    }
}
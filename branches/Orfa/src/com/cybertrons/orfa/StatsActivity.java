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

public class StatsActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_select_student);
    
	    DataBaseHelper dbHelper = new DataBaseHelper(this);
	    
	    ArrayList<RadioButton> studentList;
	    
	    RadioGroup studentOptions = (RadioGroup) findViewById(R.id.student_group);
	    
	    
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
	    	studentList = dbHelper.getStudentForStats(this);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	dbHelper.close();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
        
        Iterator<RadioButton> itr = studentList.iterator();
        while (itr.hasNext())
        {
        	studentOptions.addView(itr.next());
        }

	}
    
    public void onSelectButton(View view){
     	int radioButtonID = ((RadioGroup) findViewById(R.id.student_group)).getCheckedRadioButtonId();
    	Intent intent = new Intent(this, StudentStatsGraphActivity.class);
    	intent.putExtra(NUMBER, radioButtonID);
    	startActivity(intent);
    }
}

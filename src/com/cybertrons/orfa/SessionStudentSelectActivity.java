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

public class SessionStudentSelectActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	public final static String STUDENT = "cs4953.advsoft.orfa.STUDENT";
	private int storyName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_student);
        

		// Get the message from the intent
		Intent intent = getIntent();
		this.storyName = intent.getIntExtra(SessionStorySelectActivity.NUMBER, 0);
    
	    DataBaseHelper dbHelper = new DataBaseHelper(this);

	    ArrayList<RadioButton> studentsList;
	    RadioGroup studentOptions = (RadioGroup) findViewById(R.id.student_group);
	    
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
	    	studentsList = dbHelper.getStudentsList(this);
	    	if(studentsList.size() >= 1)
	    		studentsList.get(0).setChecked(true);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	dbHelper.close();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
       
        Iterator<RadioButton> itr = studentsList.iterator();
        while (itr.hasNext())
        {
        	studentOptions.addView(itr.next());
        }

	}
    
    public void onSelectButton(View view){
     	int radioButtonID = ((RadioGroup) findViewById(R.id.student_group)).getCheckedRadioButtonId();
    	Intent intent = new Intent(this, DisplayButtonsActivity.class);
    	intent.putExtra(STUDENT, radioButtonID);
    	intent.putExtra(NUMBER, this.storyName);
    	startActivity(intent);
    }
}
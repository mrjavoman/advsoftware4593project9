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

//This class is used to generate a list of of students and launch their
//sessions graph.  The class is called when a user clicks on "view statistics"
//from the main menu
public class StatsActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	public static ArrayList<Integer> correctList;
	public static ArrayList<Integer> incorrectList;
	private DataBaseHelper dbHelper;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_select_student);
    
	    dbHelper = new DataBaseHelper(this);
	    
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
	    	studentList = dbHelper.getStudentsList(this);
	    	if(studentList.size() >= 1)
	    		studentList.get(0).setChecked(true);

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
     	int studentID = ((RadioGroup) findViewById(R.id.student_group)).getCheckedRadioButtonId();
     	
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
	    	StatsActivity.correctList = dbHelper.getCorrect(studentID);
	    	StatsActivity.incorrectList = dbHelper.getIncorrect(studentID);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    try {
	    	dbHelper.close();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
     	
     	LineGraph line = new LineGraph();
    	Intent lineIntent = line.getIntent(this);
    	startActivity(lineIntent); 
    }
}
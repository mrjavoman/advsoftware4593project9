package com.cybertrons.orfa;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class EditTheStudentActivity extends Activity{

	private EditText firstName;
	private EditText lastName;
	
	private DataBaseHelper dbHelper = new DataBaseHelper(this);
	private String fname;
	private String lname; 
	private int student_id;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_student);
        
        firstName = (EditText) findViewById(R.id.editStFNtxt);
        lastName = (EditText)findViewById(R.id.editStLNtxt);
        
        Intent intent = getIntent();
        student_id = intent.getIntExtra(StudentCursorAdapter.getStudentId(), 0);
        
        ArrayList<String> aStudent = dbHelper.getStudentInfo(student_id);
        
        firstName.setText(aStudent.get(0));
        lastName.setText(aStudent.get(1));
        
	}
	
	public void onSubmitClick(View view){
						
		fname = firstName.getText().toString();
		lname = lastName.getText().toString();
		
		dbHelper.updateStudent(fname, lname, student_id);
		Intent intent = new Intent(this, EditStudentActivity.class);
    	startActivity(intent);
				
	}
}


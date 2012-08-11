package com.cybertrons.orfa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;


//This class is called when you select "View Sessions" and the click on "Add Student"
//button.  The class that calls in is EditStudentActivity
public class AddStudentActivity extends Activity{
	
	private EditText firstName;
	private EditText lastName;
	private View button;
	private DataBaseHelper dbHelper = new DataBaseHelper(this);
	private String fname;
	private String lname; 
	private Context mContext;

	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.add_student);

        button = (View) findViewById(R.id.newStSubmit);
        firstName = (EditText)findViewById(R.id.newStFNtxt);
		lastName = (EditText)findViewById(R.id.newStLNtxt);
        
        TextWatcher watcher = new LocalTextWatcher();
        firstName.addTextChangedListener(watcher);
        lastName.addTextChangedListener(watcher);
        updateButtonState();
    }

	public void onSubmitClick(View view){

		final AlertDialog.Builder addStudentBldr = new AlertDialog.Builder(this);
		LayoutInflater inflater = LayoutInflater.from(this);
		final View layoutInflator = inflater.inflate(R.layout.settings, null);
		
		fname = firstName.getText().toString();
		lname = lastName.getText().toString();
		mContext = this;
		
		addStudentBldr.setView(layoutInflator);
		addStudentBldr.setMessage("Student " + fname + " " + lname + " will be added, are you sure?");

		addStudentBldr.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
						
				dbHelper.insertStudent(fname, lname);
				Intent intent = new Intent(mContext, EditStudentActivity.class);
				
				//this forces android to use the already existing instance of EditStudentActivity
				//so there isn't an unnecessary instance of EditStudentActivity in the back stack
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				
            	startActivity(intent);
				
			}
		});
		
		addStudentBldr.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int id) {
						// Go back to choosing the story and student.
						//finish();
						dialog.dismiss();
					}
				});
		
		AlertDialog alert2 = addStudentBldr.create(); // Creating the alert.
		alert2.show(); // Showing the Alert.
	}
	
	void updateButtonState() {
	    boolean enabled = checkEditText(firstName)
	        && checkEditText(lastName);
	       
	    button.setEnabled(enabled);
	}

	private boolean checkEditText(EditText edit) {
	    return ((edit.getText().toString()).length() >0 );
	}

	private class LocalTextWatcher implements TextWatcher {
		@Override
	    public void afterTextChanged(Editable s) {
	        updateButtonState();
	    }

	    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	    }

	    public void onTextChanged(CharSequence s, int start, int before, int count) {
	    }
		
	}

	
}

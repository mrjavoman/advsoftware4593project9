package com.cybertrons.orfa;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

public class DisplaySessionActivity extends Activity{
	
	private int stu_id;
	private int sess_id;
	private DataBaseHelper dbHelper = new DataBaseHelper(this);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_session);

        ArrayList<String> studentSess;
        Intent intent = getIntent();
        stu_id = intent.getIntExtra(SessionCursorAdapter.getStudentId(), 0);
        sess_id = intent.getIntExtra(SessionCursorAdapter.getSessionId(), 0);
        
        studentSess = dbHelper.getStudentSession(stu_id, sess_id);
        
        TextView first_name = (TextView) findViewById(R.id.dispSess_name);
		if (first_name != null) {
			first_name.setText(studentSess.get(0) + " " + studentSess.get(1));
		}
		
		TextView incorrect = (TextView) findViewById(R.id.txt_incorr_words);
		if (incorrect != null) {
			incorrect.setText(studentSess.get(2));
		}
		
		TextView score = (TextView) findViewById(R.id.txtScore);
		if (score != null) {
			score.setText(studentSess.get(3));
		}
		
		TextView notes = (TextView) findViewById(R.id.txtSessNotes);
		if (notes != null) {
			notes.setText(studentSess.get(4));
		}
		
	}

}

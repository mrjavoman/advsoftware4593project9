package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({ "ParserError", "ParserError" })
public class DisplayButtonsActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		final int storyName = intent.getIntExtra(MainActivity.NUMBER, 0);

		// LinearLayout buttonList = (LinearLayout) View.inflate(this,
		// R.layout.reader, null);
		PredicateLayout layout = new PredicateLayout(this);

		DataBaseHelper dbHelper = new DataBaseHelper(this);
		ArrayList<Button> storyWordsList;
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			dbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}

		/*
		 * TODO there is a one hard-coded in here, it needs to be replace with
		 * the new session number that is unique to this session
		 */
		try {
			storyWordsList = dbHelper.getStoryWords(storyName, 1, this);
		} catch (SQLException sqle) {
			throw sqle;
		}

		try {
			dbHelper.openDataBase();
		} catch (SQLException sqle) {
			throw sqle;
		}

		Iterator<Button> itr = storyWordsList.iterator();
		while (itr.hasNext()) {
			layout.addView(itr.next(), new PredicateLayout.LayoutParams(2, 0));			
			setContentView(layout);			
		}
		
		
		 

		// Beginning Arthur's clock
		
		final Handler handler = new Handler();
		final Handler h = new Handler();
		final long time = 15000; // time in milliseconds to delay the timer. 1000 milliseconds = 1 second.

		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final Runnable runnable = new Runnable() {
			public void run() {
				// Building the Alert.
				builder.setMessage("Time is up! Please select the last word read.")
						
						.setPositiveButton("OK", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {
										
									}
								});
				
				AlertDialog alert = builder.create(); // Creating the Alert.
				alert.show(); // Showing the Alert.
			}
		}; // The above AlertDialog alerts the user that time is up.
		
		
		final AlertDialog.Builder startBuilder = new AlertDialog.Builder(this);
		final Runnable runStartTimerPrompt = new Runnable(){
			public void run(){
				// Building the Alert
				startBuilder.setMessage("Press start to begin the session. (Timer = " + time / 1000 + " seconds)")
				
				.setPositiveButton("Start", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							// Delay the "time is up" message for so many milliseconds after "start" is pressed.
							h.postDelayed(runnable, time); 
						}
					})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							finish();
						}
				});
				AlertDialog alert2 = startBuilder.create(); // Creating the Alert.
				alert2.show(); // Showing the Alert.
			}
		};
		h.post(runStartTimerPrompt); // This executes the first prompt to start the timer (above).
		
		// end Arthur's clock
	}
	
	public static View.OnClickListener markWord(final Button button){ // -LJ
		return new View.OnClickListener() {
	        public void onClick(View v) {
	        	Intent myIntent = new Intent(v.getContext(), MarkWordActivity.class);
	        	v.getContext().startActivity(myIntent);
	        	//MainActivity.this.startActivity(myIntent);
	        }
	    };
	}

	/*
	 * TODO create and call the dialog that allows the user to select an error
	 * and call the method in the DataBaseHelper class that writes the error to
	 * the db
	 */

	/*
	 * TODO figure out how to add onClick handler that can get the button's ID
	 * and pass it to the dialog to set an error
	 */

	/*
	 * TODO wrap the buttons to fit the screen... see comment below
	 */
}

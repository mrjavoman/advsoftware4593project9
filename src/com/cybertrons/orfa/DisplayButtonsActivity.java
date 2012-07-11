package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class DisplayButtonsActivity extends Activity {

	
	public final static String WORD = "cs4953.advsoft.orfa.WORD";
	public final static String SESSION = "cs4953.advsoft.orfa.SESSION";
	private static final int REQUEST_CODE = 0;
	private ArrayList<Button> storyWordsList;


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
		try {
			dbHelper.createDataBase();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
	    	dbHelper.openDataBaseRW();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
		
	    try {
	    	dbHelper.populateCurrentSessionData(storyName, 1);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}

		try {
			storyWordsList = dbHelper.getStoryWords(storyName, this);
			dbHelper.close();
		}catch (SQLException sqle) {
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
						startBuilder.setMessage("Please read this (point) out loud.  If you get stuck," +
                                " I will tell you the word so you can keep reading.  When I say, \"stop\"" +
                                " I may ask you to tell me about what you read, so do your best reading.  " +
                                "Start here (point to the first word of the passage).  Begin.  " +
                                "\n\nPress start to begin the session. (Timer = " + time / 1000 + " seconds)\n")
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
	        	int buttonID = v.getId();
	        	Intent myIntent = new Intent(v.getContext(), MarkWordActivity.class);
	        	myIntent.putExtra(WORD, buttonID);
	        	myIntent.putExtra(SESSION, 1);
	        	v.getContext().startActivity(myIntent);
	        }
	    };
	}
	
	public void onResume(){
		super.onResume();

		DataBaseHelper dbHelper = new DataBaseHelper(this);
		try {
			dbHelper.openDataBaseRW();
		} catch (SQLException sqle) {
			throw sqle;
		}

		try {
			dbHelper.updateWordButtons(storyWordsList);
			dbHelper.close();
		} catch (SQLException sqle) {
			throw sqle;
		}
		

	}
	
}

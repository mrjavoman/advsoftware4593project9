package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.SQLException;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class DisplayButtonsActivity extends Activity {

	
	public final static String WORD = "cs4953.advsoft.orfa.WORD";
	public final static String SESSION = "cs4953.advsoft.orfa.SESSION";
	public static final String PREFS_NAME = "orfa_settings";
	private ArrayList<Button> storyWordsList;
	private SoundPool sounds;
	private int sAlert;
	private int storyName = 0;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.main);
		
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		sAlert = sounds.load(getBaseContext(), R.raw.alarmpositive, 1);


		// Get the message from the intent
		Intent intent = getIntent();
		this.storyName = intent.getIntExtra(MainActivity.NUMBER, 0);

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
	    	dbHelper.clearCurrentSessionData();
	    	dbHelper.populateCurrentSessionData(this.storyName, 1);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    

		try {
			storyWordsList = dbHelper.getStoryWords(this.storyName, this);
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
		
				// SharedPreferences
				final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
				//LayoutInflater is helping me insert a checkbox layout inside the AlertBuilder.
				LayoutInflater inflater = LayoutInflater.from(this);
		        final View layoutInflator = inflater.inflate(R.layout.settings, null);
		        final CheckBox dontShowAgain = (CheckBox)layoutInflator.findViewById(R.id.checkBox1);
				
				
				final Handler h = new Handler();
				final long time = 5000; // time in milliseconds to delay the timer. 1000 milliseconds = 1 second.

				final AlertDialog.Builder endBuilder = new AlertDialog.Builder(this);
				final Runnable runnable = new Runnable() {
					public void run() {
						// Building the Alert.
						endBuilder.setView(layoutInflator);
						endBuilder.setMessage("Time is up! Please select the last word read.")
								
								.setPositiveButton("OK", new DialogInterface.OnClickListener() {
											public void onClick(DialogInterface dialog, int which) {
												String checkBoxResult = "NOT checked";
								                  if (dontShowAgain.isChecked())  checkBoxResult = "checked";
									                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
									                    SharedPreferences.Editor editor = settings.edit();
									                    editor.putString("skipMessage", checkBoxResult);   
									                    // Commit the edits!
									                    editor.commit();
												
											}
										});
						
						AlertDialog alert = endBuilder.create(); // Creating the Alert.
					String skipMessage = settings.getString("skipMessage", "checked");
					if (!skipMessage.equalsIgnoreCase("checked") )
						alert.show(); // Showing the Alert.
						sounds.play(sAlert, 1.0f, 1.0f, 0, 0, 1.5f);
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
                                "\n\nPress start to begin the session.")
						.setPositiveButton("Start", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
															// Delay the "time is up" message for so many milliseconds after "start" is pressed.
									h.postDelayed(runnable, time); 
								}
							})
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int id) {
									/*String checkBoxResult = "NOT checked";
					                  if (dontShowAgain.isChecked())  checkBoxResult = "checked";
						                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
						                    SharedPreferences.Editor editor = settings.edit();
						                    editor.putString("skipMessage", checkBoxResult);   
						                    // Commit the edits!
						                    editor.commit();*/
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
		super.onResume();// Get the message from the intent

		DataBaseHelper dbHelper = new DataBaseHelper(this);

		try {
			dbHelper.openDataBase();
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

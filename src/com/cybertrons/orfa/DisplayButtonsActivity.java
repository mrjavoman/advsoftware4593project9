package com.cybertrons.orfa;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class DisplayButtonsActivity extends Activity {

	
	public final static String WORD = "cs4953.advsoft.orfa.WORD";
	public final static String SESSION = "cs4953.advsoft.orfa.SESSION";
	public static final String PREFS_NAME = "orfa_settings";
	private ArrayList<Button> storyWordsList;
	private SoundPool sounds;
	private int sAlert;
	private int storyName;
	private int student;
	private Runnable stopTimer;
	private Runnable startTimer;
	private Handler h;

	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.predicatelayout);	
		
		PredicateLayout layout = new PredicateLayout(this);

		// These two lines are the bread and butter that gets the page scrolling!
		LinearLayout myLayout = (LinearLayout) findViewById(R.id.mlayout);
		myLayout.addView(layout);
		
		// Sounds
		sounds = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
		sAlert = sounds.load(getBaseContext(), R.raw.alarmpositive, 1);

		// Get the message from the intent
		Intent intent = getIntent();
		this.storyName = intent.getIntExtra(SessionStorySelectActivity.NUMBER, 0);
		this.student = intent.getIntExtra(SessionStudentSelectActivity.STUDENT, 0);

		DataBaseHelper dbHelper = new DataBaseHelper(this);
		/*
		try {
			dbHelper.createDataBase();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
*/
		try {
	    	dbHelper.openDataBaseRW();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
		
	    try {
	    	dbHelper.clearCurrentSessionData();
	    	dbHelper.populateCurrentSessionData(this.storyName, student);
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    

		try {
			storyWordsList = dbHelper.getStoryWords(this.storyName, this);
			setTitle("Session In Progress with: "+ dbHelper.getStudent(this, student));
			dbHelper.close();
		}catch (SQLException sqle) {
			throw sqle;
		}

		Iterator<Button> itr = storyWordsList.iterator();
		while (itr.hasNext()) {		
			layout.addView(itr.next(), new PredicateLayout.LayoutParams(2, 0));			
		}

		// Beginning Arthur's clock

		// Getting SharedPreferences
		final SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		// LayoutInflater is helping me insert a checkbox layout inside the stop message.
		LayoutInflater inflater = LayoutInflater.from(this);
		final View layoutInflator = inflater.inflate(R.layout.settings, null);
		final CheckBox dontShowAgain = (CheckBox) layoutInflator.findViewById(R.id.checkBox1);
		
		// The handler is used to delay the second message, which is the basis of the timer.  It does not create a new thread.
		this.h = new Handler();
		final long time = 5000; // time in milliseconds to delay the timer. 1000 milliseconds = 1 second.
		
		// The alert to display that time is up.
		final AlertDialog.Builder endBuilder = new AlertDialog.Builder(this); // Builder for the stop alert.
		this.stopTimer = new Runnable() {
			public void run() {
				// Building the Alert.
				endBuilder.setView(layoutInflator);
				endBuilder.setMessage("Time is up! Please select the last word read.")

				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String checkBoxResult = "NOT checked";
						
						if (dontShowAgain.isChecked())
							checkBoxResult = "checked";
						
						SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("skipMessage", checkBoxResult);
						// Commit the edits!
						editor.commit();
					}
				});

				AlertDialog alert = endBuilder.create(); // Creating the Alert.
				String skipMessage = settings.getString("skipMessage","checked");
				
				// If the checkbox is not checked, show the message.
				if (!skipMessage.equalsIgnoreCase("checked"))
					alert.show(); // Showing the Alert.
				sounds.play(sAlert, 1.0f, 1.0f, 0, 0, 1.5f);
			}
		};

		final AlertDialog.Builder startBuilder = new AlertDialog.Builder(this); // Builder for the start alert.
		this.startTimer = new Runnable() {
			public void run() {

				// Building the Alert
				startBuilder
						.setMessage(
								"Please read this (point) out loud.  If you get stuck,"
										+ " I will tell you the word so you can keep reading.  When I say, \"stop\""
										+ " I may ask you to tell me about what you read, so do your best reading.  "
										+ "Start here (point to the first word of the passage).  Begin.  "
										+ "\n\nPress start to begin the session.")
										
						.setPositiveButton("Start",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										// "Start" the "timer". This really just delays the stop alert.
										try {
											h.postDelayed(stopTimer, time);
									    } catch (WindowManager.BadTokenException e) {
									        // TODO: handle exception
									    } catch (Exception e) {
									        // TODO: handle exception
									    }
										
									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										// Go back to choosing the story and student.
										finish();
									}
								});
				AlertDialog alert2 = startBuilder.create(); // Creating the alert.
				alert2.show(); // Showing the Alert.
			}
		};
		h.post(startTimer); // This starts the timer.
		
		// end Arthur's clock
		
		
		
	}

	/*
	public static View.OnClickListener markWord(final Button button){
		return new View.OnClickListener() {
	        public void onClick(final View v) {
	        	int buttonID = v.getId();
		
				final CharSequence[] items = {"Omit", "Word Order", "Mispronounce" ,"Hesitate", "Self Correct", "No Error", "Last Word"};
		
				AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
				builder.setTitle("Select an Error");
				builder.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						Toast.makeText(v.getContext(), items[item], Toast.LENGTH_SHORT).show();
						}
				});
				AlertDialog alert = builder.create();
				alert.show();
	        }
	    };
	}
*/
	
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
	
	public void onDestroy(){
		super.onDestroy();
		this.h.removeCallbacks(this.startTimer);
		this.h.removeCallbacks(this.stopTimer);
	}
	
}


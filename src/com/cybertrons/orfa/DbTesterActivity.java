package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.SQLException;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.ToggleButton;

public class DbTesterActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_tester);

		
		//Beginning Arthur's clock
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);

		Runnable runnable = new Runnable() {

			public void run() {

				builder.setMessage(
						"Time is up! Please select the last word read!")
						.setCancelable(true)
						.setPositiveButton("Yes",
								new DialogInterface.OnClickListener() {
									public void onClick(int id) {

									}

									public void onClick(DialogInterface dialog,
											int which) {
										// TODO Auto-generated method stub

									}
								})
						.setNegativeButton("No",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		};

		final Handler handler = new Handler();
		final Handler h = new Handler();
		long time = 10000; //This is the amount of time in milliseconds to delay before saying "stop"
		h.postDelayed(runnable, time);
		//end arthur's clock
		
		
	
	}
}
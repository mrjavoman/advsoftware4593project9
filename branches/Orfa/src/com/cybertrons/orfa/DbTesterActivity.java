package com.cybertrons.orfa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

public class DbTesterActivity extends Activity {
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_tester);
	}
	
	public void onClickExportCSV(View view)  {

		DataBaseHelper dbHelper = new DataBaseHelper(this);
		try  {
			dbHelper.createDataBase();
			dbHelper.openDataBase();
//			dbHelper.writeCSV();
			dbHelper.close();
		}
		catch(Exception ex)  {
			Log.e("Error in DbTesterActivity",ex.toString());
		}

	}
	
	
}
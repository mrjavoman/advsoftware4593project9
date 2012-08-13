package com.cybertrons.orfa;

import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

	

public class SettingsActivity extends Activity {
	
	public static final String PREFS_NAME = "orfa_settings";	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	private final Context context = this;
	private DataBaseHelper dbHelper = new DataBaseHelper(context); 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_settings);
        SharedPreferences s = getSharedPreferences(PREFS_NAME,0);
       
		
        
        CheckBox cb = (CheckBox) findViewById(R.id.checkBox2); 
        
        String setCheckBox = s.getString("skipMessage", "NOT checked");
        if( setCheckBox.equalsIgnoreCase("checked") ){
        	cb.setChecked(false);
        }
        else{
        	cb.setChecked(true);
        }
        
        cb.setOnCheckedChangeListener(new OnCheckedChangeListener() { 

        @Override 
        public void onCheckedChanged(CompoundButton buttonView, 
        boolean isChecked) { 
        // TODO Auto-generated method stub 
        if (buttonView.isChecked()) { 
        	 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
     		SharedPreferences.Editor edit = settings.edit();
     		edit.putString("skipMessage", "NOT checked");
     		edit.commit();
        } 
        else 
        { 
        	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
     		SharedPreferences.Editor edit = settings.edit();
     		edit.putString("skipMessage", "checked");
     		edit.commit();
        } 

        } 
        }); 
    }
    
  //export a csv file to the sd card or through e-mail
    public void onExportCSV(View view){
    	
    	final CharSequence[] items = {"Email", "SD Card"};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final AlertDialog.Builder exportBldr = new AlertDialog.Builder(this);
		builder.setTitle("Export Options:");
		builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int item) {
		        //Toast.makeText(mContext, items[item] , Toast.LENGTH_SHORT).show();
		    	
		        if(items[item].equals("Email")){
		           	dbHelper.openDataBase();
		           	boolean fileWritten = dbHelper.writeCSV("local");
		        	dbHelper.close();
		        	
		        	if(fileWritten){
		        		Intent sendIntent = new Intent(android.content.Intent.ACTION_SEND);
		        		// sendIntent.setType("text/html");
		        		sendIntent.setType("application/csv");
		        		sendIntent.putExtra(android.content.Intent.EXTRA_EMAIL, "");
		        		sendIntent.putExtra(Intent.EXTRA_SUBJECT, "");
		        		sendIntent.putExtra(Intent.EXTRA_TEXT, "");
		        		String temp_path = "/data/data/com.cybertrons.orfa/databases/app_files/" + "ORFA_DB.csv";
		        		File F = new File(temp_path);
		        		Uri U = Uri.fromFile(F);
		        		sendIntent.putExtra(Intent.EXTRA_STREAM, U);
		        		startActivity(Intent.createChooser(sendIntent, "Send Mail"));
		        	}else{
		        		exportBldr.setMessage("File could not be sent, \n " +
		        				"an SD card is needed to hold the file temporarily, \n" +
	            				"please verify an SD Card is present");
	            	exportBldr.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog,int id) {
	    					// if this button is clicked, close dialog
	    					dialog.dismiss();
	    				}
	    			  }); 

	    			// create alert dialog
	    			AlertDialog alertDialog = exportBldr.create();
	    			// show it
	    			alertDialog.show();
		        	}
		        	
	            	dialog.dismiss();
		        }
		        else if(items[item].equals("SD Card")){
		        	
		        	dbHelper.openDataBase();
		        	boolean fileWritten = dbHelper.writeCSV("sd");
		        	dbHelper.close();
	            	dialog.dismiss();
	            	
	            	//display message box saying that file has been exported
	            	if(fileWritten)
	            		exportBldr.setMessage("ORFA_DB.csv file has been created on SD card");
	            	else
	            		exportBldr.setMessage("File could not be written to SD card, \n" +
	            				"please verify if SD Card is present");
	            	exportBldr.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
	    				public void onClick(DialogInterface dialog,int id) {
	    					// if this button is clicked, close dialog
	    					dialog.dismiss();
	    				}
	    			  }); 
	    			// create alert dialog
	    			AlertDialog alertDialog = exportBldr.create();
	    			// show it
	    			alertDialog.show();
		        }
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
    }
    
   
}

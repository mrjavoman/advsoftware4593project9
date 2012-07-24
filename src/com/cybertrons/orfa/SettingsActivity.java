package com.cybertrons.orfa;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

	

public class SettingsActivity extends Activity {
	
	public static final String PREFS_NAME = "orfa_settings";	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	
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
        	Log.d("Orfa","Checked");
        	 SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
     		SharedPreferences.Editor edit = settings.edit();
     		edit.putString("skipMessage", "NOT checked");
     		edit.commit();
        } 
        else 
        { 
        	Log.d("Orfa","NOT checked");
        	SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
     		SharedPreferences.Editor edit = settings.edit();
     		edit.putString("skipMessage", "checked");
     		edit.commit();
        } 

        } 
        }); 
 

		
       
        
    }
    
   
}

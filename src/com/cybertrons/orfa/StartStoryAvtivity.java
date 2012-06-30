package com.cybertrons.orfa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class StartStoryAvtivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_prompt);
    }
    
    public void onGenerateButton(View view){
    	Intent intent = new Intent(this, DisplayButtonsActivity.class);
    	EditText editText = (EditText) findViewById(R.id.editCount);
    	String message = editText.getText().toString();
    	intent.putExtra(NUMBER, message);
        startActivity(intent);
    }
}
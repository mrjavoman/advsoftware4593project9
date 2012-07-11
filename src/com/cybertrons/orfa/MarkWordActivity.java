package com.cybertrons.orfa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MarkWordActivity extends Activity {
public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_mark);
        
        
    }
	
	public void onClickErrorOmit(View view){
		finish();
    }
	
	public void onClickErrorWordOrder(View view){
		finish();
    }
	
	public void onClickErrorMispronounce(View view){
		finish();
    }
	
	public void onClickErrorHesitate(View view){
		finish();
    }
	
	public void onClickErrorSelfCorrect(View view){
		finish();
    }
	
	public void onClickMarkCorrect(View view){
		finish();
    }
	
	public void onClickLastRead(View view){
		finish();
    }

}

package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayButtonsActivity extends Activity{
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        int storyName = intent.getIntExtra(MainActivity.NUMBER, 0);
        
        LinearLayout buttonList = (LinearLayout) View.inflate(this, R.layout.reader, null);

        
        
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
       	}catch(SQLException sqle){
       		throw sqle;
       	}
        
        /*
         * TODO there is a one hard-coded in here, it needs to be replace with the 
         * new session number that is unique to this session
         */
        try {
        	storyWordsList = dbHelper.getStoryWords(storyName, 1, this);
       	}catch(SQLException sqle){
       		throw sqle;
       	}
        
        try {
		  	dbHelper.openDataBase();
        }catch(SQLException sqle){
		 		throw sqle;
		}
        
        Iterator<Button> itr = storyWordsList.iterator();
        while (itr.hasNext())
        {
        	buttonList.addView(itr.next());       
            setContentView(buttonList); 
        }
    }
    
    /*
     * TODO create and call the dialog or activity that allows the user to select an error and 
     * call the method in the DataBaseHelper class that writes the error to the db
     */
    
    
    /*
     * TODO wrap the buttons to fit the screen... see comment below
     */

	/*
	 * this is a method I got from stack overflow that explains how to wrap the buttons so they fit on the screen'
	 * The link is  http://stackoverflow.com/questions/2961777/android-linearlayout-horizontal-with-wrapping-children
	 * obviously we will need to modify it to work in our application and avoid plagiarism
	 * 
	 */
    
    /*

    private void populateLinks(LinearLayout ll, ArrayList collection, String header) {

        Display display = getWindowManager().getDefaultDisplay();
        int maxWidth = display.getWidth() - 10;

        if (collection.size() > 0) {
            LinearLayout llAlso = new LinearLayout(this);
            llAlso.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            llAlso.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSample = new TextView(this);
            txtSample.setText(header);

            llAlso.addView(txtSample);
            txtSample.measure(0, 0);

            int widthSoFar = txtSample.getMeasuredWidth();
            for (Sample samItem : collection) {
                TextView txtSamItem = new TextView(this, null,
                        android.R.attr.textColorLink);
                txtSamItem.setText(samItem.Sample);
                txtSamItem.setPadding(10, 0, 0, 0);
                txtSamItem.setTag(samItem);
                txtSamItem.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        TextView self = (TextView) v;
                        Sample ds = (Sample) self.getTag();

                        Intent myIntent = new Intent();
                        myIntent.putExtra("link_info", ds.Sample);
                        setResult("link_clicked", myIntent);
                        finish();
                    }
                });

                txtSamItem.measure(0, 0);
                widthSoFar += txtSamItem.getMeasuredWidth();

                if (widthSoFar >= maxWidth) {
                    ll.addView(llAlso);

                    llAlso = new LinearLayout(this);
                    llAlso.setLayoutParams(new LayoutParams(
                            LayoutParams.FILL_PARENT,
                            LayoutParams.WRAP_CONTENT));
                    llAlso.setOrientation(LinearLayout.HORIZONTAL);

                    llAlso.addView(txtSamItem);
                    widthSoFar = txtSamItem.getMeasuredWidth();
                } else {
                    llAlso.addView(txtSamItem);
                }
            }

            ll.addView(llAlso);
        }
    }
    */

}


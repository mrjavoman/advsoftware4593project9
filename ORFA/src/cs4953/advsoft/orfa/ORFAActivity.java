package cs4953.advsoft.orfa;


import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;

public class ORFAActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //crete an instance of a database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        ArrayList<String> arrList;
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
                
        try {
        	arrList = dbHelper.getWords();
       	}catch(SQLException sqle){
       		throw sqle;
       	}
        
        CharSequence txt ="hello";

        try {
        	((TextView)findViewById (R.id.text1)).setText(arrList.remove(1));
        	((TextView)findViewById (R.id.word1)).setText(arrList.remove(1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          
    }
}
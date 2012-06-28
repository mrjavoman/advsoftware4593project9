package cs4953.advsoft.orfa;


import java.io.IOException;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;

public class ORFAActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //crete an instance of a database
        DataBaseHelper dbHelper = new DataBaseHelper(this);
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
                
        String wrd;
        try {
        	dbHelper.getWords();
       	}catch(SQLException sqle){
       		throw sqle;
       	}
        
    
        
    }
}
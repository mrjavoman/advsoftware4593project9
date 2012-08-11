package com.cybertrons.orfa;


import java.io.IOException;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class EditStudentActivity extends ListActivity {
	
	private static String[] data = new String[] { "0", "1", "2", "3", "4", "5" };
	
	private StudentCursorAdapter dataSource;
	
	private DataBaseHelper dbHelper = new DataBaseHelper(this);
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_student);
     	
     	try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
               
        Cursor cursorData = dbHelper.getStudents();
        dataSource = new StudentCursorAdapter(this, cursorData);
        
        setListAdapter(dataSource);
       
        
    }
	public void onResume(){
		super.onResume();
		
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Cursor cursorData = dbHelper.getStudents();
		dataSource = new StudentCursorAdapter(this, cursorData);

		setListAdapter(dataSource);
		
	}
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
   	  // TODO Auto-generated method stub
      super.onListItemClick(l, v, position, id);
      Toast.makeText(this, "Click-" + String.valueOf(position), Toast.LENGTH_SHORT).show();
    }
    
    public void onAddStudentClick(View view){
    	Intent intent = new Intent(this, AddStudentActivity.class);
    	startActivity(intent);
    }

}

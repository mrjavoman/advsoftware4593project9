package com.cybertrons.orfa;


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

public class ViewSessionActivity extends ListActivity {
	
	private static String[] data = new String[] { "0", "1", "2", "3", "4", "5" };
	
	private SessionCursorAdapter dataSource;
	
	private DataBaseHelper dbHelper = new DataBaseHelper(this);
	
	private int student_id;
	
		
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_session);
        
        Bundle bundle;
        
        Intent intent = getIntent();
        bundle = intent.getExtras();
        student_id = bundle.getInt("cs4953.advsoft.orfa.STUDENT_ID");
        //student_id = intent.getIntExtra(StudentCursorAdapter.getStudentId(), 0);
               
        Cursor cursorData = dbHelper.getSession(student_id);
        dataSource = new SessionCursorAdapter(this, cursorData);
        
        setListAdapter(dataSource);
       
        
    }
    
    public void onResume(){
		super.onResume();
		
        Cursor cursorData = dbHelper.getSession(student_id);
        dataSource = new SessionCursorAdapter(this, cursorData);
        
        setListAdapter(dataSource);
		
		
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
      // TODO Auto-generated method stub
      super.onListItemClick(l, v, position, id);
      Toast.makeText(this, "Click-" + String.valueOf(position), Toast.LENGTH_SHORT).show();
    }

}

package com.cybertrons.orfa;

import android.R.layout;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

public class StudentCursorAdapter extends CursorAdapter implements OnClickListener,SectionIndexer,Filterable, android.widget.AdapterView.OnItemClickListener{
	
	private LayoutInflater mLayoutInflater;
	private Context mContext;
    private AlphabetIndexer alphaIndexer;
    private boolean aLongClick = false;
    private static final String STUDENT_ID = "cs4953.advsoft.orfa.STUDENT_ID";
	private int std_id;
	private DataBaseHelper dbHelper;
	private AlertDialog alert;
	private Bundle bundle;

	@SuppressWarnings("deprecation")
	public StudentCursorAdapter(Context context, Cursor c) {
		super(context, c);
		mContext = context;
		mLayoutInflater = LayoutInflater.from(context); 
		dbHelper = new DataBaseHelper(context);
	}
	
	public void initIndexer(Cursor c){
        alphaIndexer=new AlphabetIndexer(c, c.getColumnIndexOrThrow("lastName"), " ABCDEFGHIJKLMNOPQRSTUVWXYZ");
   }

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View v = mLayoutInflater.inflate(R.layout.adaptor_content, parent, false);
		return v;
	}

	/**
	 * @author will
	 * 
	 * @param   v
	 *          The view in which the elements we set up here will be displayed.
	 * 
	 * @param   context
	 *          The running context where this ListView adapter will be active.
	 * 
	 * @param   c
	 *          The Cursor containing the query results we will display.
	 */

	@Override
	public void bindView(View v, Context context, Cursor c) {
		int uid_student = c.getInt(c.getColumnIndexOrThrow("_id"));
		String firstName = c.getString(c.getColumnIndexOrThrow("firstName"));
		String lastName = c.getString(c.getColumnIndexOrThrow("lastName"));
		

		/**
		 * Next set the title of the entry.
		 */

		TextView first_name = (TextView) v.findViewById(R.id.textLine);
		if (first_name != null) {
			first_name.setText(firstName);
		}

		/**
		 * Set Date
		 */

		TextView last_name = (TextView) v.findViewById(R.id.textView1);
		if (last_name != null) {
			last_name.setText(lastName);
		}
		
		RelativeLayout layoutBtn = (RelativeLayout)v.findViewById(R.id.lineItem);
		layoutBtn.setTag(uid_student);
		layoutBtn.setOnClickListener(rowClicked);
		layoutBtn.setOnLongClickListener(longRowClick);

//		Button btnButtonEdit = (Button)v.findViewById(R.id.buttonLine);
//		btnButtonEdit.setTag(uid_student);
//		btnButtonEdit.setOnClickListener(btnButtonEditClicked);

		/**
		 * Decide if we should display the paper clip icon denoting image attachment
		 */
		//
		//        ImageView item_image = (ImageView) v.findViewById(R.id.item_attachment);
		//        item_image.setVisibility(ImageView.INVISIBLE);
		//        if (imagePath != null && imagePath.length() != 0 && item_image != null) {
		//            item_image.setVisibility(ImageView.VISIBLE);
		//        }

		/**
		 * Decide if we should display the deletion indicator
		 */
		//Button del_student = (Button) v.findViewById(R.id.buttonLine);
		//del_image.setVisibility(ImageView.INVISIBLE);
		//        if (deletion == 1) {
		//            del_image.setVisibility(ImageView.VISIBLE);
		//        }
	}
	
		
	 private OnClickListener rowClicked = new OnClickListener() {
	        @Override
	        public void onClick(View view) {
	        	if(!aLongClick){
	        		//Toast.makeText(mContext, "student id -" + view.getTag(), Toast.LENGTH_SHORT).show();
	        		std_id = (Integer) view.getTag();
	        		bundle = new Bundle();
	        		bundle.putInt(STUDENT_ID, std_id);
	        		Intent intent = new Intent(mContext, ViewSessionActivity.class);
	            	intent.putExtras(bundle);
	            	mContext.startActivity(intent);
	        	}
	        	aLongClick = false;
	        }
	    };
	    
	    private OnLongClickListener longRowClick = new OnLongClickListener() {
	      
			@Override
			public boolean onLongClick(View arg0) {
				// TODO Auto-generated method stub
				aLongClick = true;
				final CharSequence[] items = {"Edit", "Delete"};
				final View view = arg0;
				
				AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
				builder.setTitle("Students Menu");
				builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog, int item) {
				        //Toast.makeText(mContext, items[item] , Toast.LENGTH_SHORT).show();
				    	std_id = (Integer) view.getTag();
				        if(items[item].equals("Edit")){
				        	Intent intent = new Intent(mContext, EditTheStudentActivity.class);
			            	intent.putExtra(getStudentId(), std_id);
			            	mContext.startActivity(intent);
			            	dialog.dismiss();
				        }
				        else if(items[item].equals("Delete")){
				        	dbHelper.deleteStudent(std_id);
				        	Intent intent = new Intent(mContext, EditStudentActivity.class);
			            	intent.putExtra(getStudentId(), std_id);
			            	mContext.startActivity(intent);
			            	dialog.dismiss();
				        }
				       
				    }
				});
				alert = builder.create();
				alert.show();
				//aLongClick = false; //reset the boolean for normal click
				return false;
			}
	    };
	
//	 private OnClickListener btnButtonEditClicked = new OnClickListener() {
//	        @Override
//	        public void onClick(View view) {
//	        	Toast.makeText(mContext, "Delete-" + view.getTag(), Toast.LENGTH_SHORT).show();
//	        	//std_id = (Integer) view.getTag();
//	        	//Intent intent = new Intent(mContext, EditTheStudentActivity.class);
//            	//intent.putExtra(getStudentId(), std_id);
//            	//mContext.startActivity(intent);
//	        }
//	    };

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "Delete-" + arg2, Toast.LENGTH_SHORT).show();
	}

	@Override
	public int getPositionForSection(int arg0) {
		// TODO Auto-generated method stub
		return alphaIndexer.getPositionForSection(arg0);
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return alphaIndexer.getSectionForPosition(position);
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return  alphaIndexer.getSections();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(mContext, "Delete-" + arg0, Toast.LENGTH_SHORT).show();
	}

	public static String getStudentId() {
		return STUDENT_ID;
	}


}
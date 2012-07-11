package cs4953.advsoft.orfa;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ORFAActivity extends Activity {
	
	public final static String NUMBER = "cs4953.advsoft.orfa.MESSAGE";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onClickStartSession(View view){
    	Intent myIntent = new Intent(ORFAActivity.this, StartStoryAvtivity.class);
    	ORFAActivity.this.startActivity(myIntent);
    }
    
    public void onClickEditStudent(View view){
    	Intent myIntent = new Intent(ORFAActivity.this, EditStudentActivity.class);
    	ORFAActivity.this.startActivity(myIntent);
    }
    public void onClickImportStory(View view){
    	Intent myIntent = new Intent(ORFAActivity.this, ImportStoryActivity.class);
    	ORFAActivity.this.startActivity(myIntent);
    }
    public void onClickAssignSounds(View view){
    	Intent myIntent = new Intent(ORFAActivity.this, AssignSoundsActivity.class);
    	ORFAActivity.this.startActivity(myIntent);
    }
    public void onClickStats(View view){
    	Intent myIntent = new Intent(ORFAActivity.this, StatsActivity.class);
    	ORFAActivity.this.startActivity(myIntent);
    }
    public void onClickDbTester(View view){
    	Intent myIntent = new Intent(ORFAActivity.this, DbTesterActivity.class);
    	ORFAActivity.this.startActivity(myIntent);
    }
    
}
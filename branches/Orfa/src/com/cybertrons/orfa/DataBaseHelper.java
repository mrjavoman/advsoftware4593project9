package com.cybertrons.orfa;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.widget.Button;
import android.widget.RadioButton;



public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.cybertrons.orfa/databases/";
 
    private static String DB_NAME = "test";
 
    private SQLiteDatabase myDataBase = null; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
	
    	if(dbExist){
    		//do nothing - database already exist
    		//this.myDataBase = getReadableDatabase();
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
    		//this.myDataBase = getReadableDatabase();
    		SQLiteDatabase tmpDB = this.getReadableDatabase();
    		tmpDB.close();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
 
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}
	
	//this method returns all the words that have a given sound, at this point the sound is hardcoded on the query but it can easily passed as a parameter if needed
	public ArrayList<String> getWords(){
        //store the words in an ArrayList
        ArrayList<String> theWords = new ArrayList<String>();
        Cursor aCursor;
        
        //query selects all words that have sound "eh", it uses the mapping table, the words are hello, fetch, set
        String aSql = "SELECT word FROM words WHERE idx IN (SELECT uid_word FROM soundwordmap WHERE uid_sound = 1)";
        
        //a Cursor object stores the results rawQuery
        aCursor = myDataBase.rawQuery(aSql, null);
        // moveToFirst moves the Cursor to the first row of the results
        if(aCursor.moveToFirst()){
            do{
                theWords.add(aCursor.getString(0)); // getString() returns the value of the given column index
            }while(aCursor.moveToNext()); // moveToNext() moves the cursor to the next row
        }
        
        //return the words hello, fetch, set
        return theWords; 
    }
	
	public ArrayList<RadioButton> getStories(Context tContext){

		ArrayList<RadioButton> storiesList = new ArrayList<RadioButton>();
		
		String aSql = "SELECT uid_story, name FROM story";//a Cursor object stores the results rawQuery
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
        // moveToFirst moves the Cursor to the first row of the results
        if(aCursor.moveToFirst()){
            do{
            	RadioButton btn = new RadioButton(tContext);
                btn.setId(aCursor.getInt(0));
                String label = aCursor.getString(1);
                btn.setText(label);                
            	storiesList.add(btn);
            }while(aCursor.moveToNext()); // moveToNext() moves the cursor to the next row
        }
		
		return storiesList;
	}
	
	
	public ArrayList<Button> getStoryWords(int name, int session, Context tContext){

		ArrayList<Button> storyWordsList = new ArrayList<Button>();
		Cursor aCursor;

		String aSql = "SELECT " +
				" uid_story_content AS _id " +
				", word " +
				", COALESCE(punctuation, 0) AS punctuation " +
				", name " +
				", COALESCE(errors.type, 0) AS errType " +
				"FROM story_content " +
					"LEFT JOIN story " + 
						"ON story_content.id_story = story.uid_story " +
					"LEFT JOIN words  "+
						"ON story_content.id_word = words.idx " +
					"LEFT JOIN (SELECT  " +
							"type " +
							", id_story_content " +
							", id_word " +
						  "FROM errors_made " +
						  "WHERE id_session = " + session +")errors  " +
						"ON story_content.id_word = errors.id_word " +
						"AND story_content.uid_story_content = errors.id_story_content " +

				"WHERE uid_story = "+ name;
		


        //a Cursor object stores the results rawQuery
        aCursor = myDataBase.rawQuery(aSql, null);
        // moveToFirst moves the Cursor to the first row of the results
        if(aCursor.moveToFirst()){
            do{
            	Button btn = new Button(tContext);
                btn.setId(aCursor.getInt(0));
                btn.setText(aCursor.getString(1));
                
                if(aCursor.getInt(2) > 0){
                	btn.setBackgroundColor(Color.WHITE);
                }else{
                	btn.setOnClickListener(DisplayButtonsActivity.markWord(btn));
                }/*
                }
                else if(aCursor.getInt(4) > 0){
                	btn.setBackgroundColor(Color.RED);
                }
                else{
                	//leave button default color
                }*/
            	storyWordsList.add(btn);
            }while(aCursor.moveToNext()); // moveToNext() moves the cursor to the next row
        }
        
        //return the words hello, fetch, set
        return storyWordsList; 
		
	}
	public void setCurrentSessionError(int uid, int error, Context tContext){

		Cursor aCursor;

		String aSql = "UPDATE current_session " +
						"SET errorType = " + error +
						"WHERE uid_current_session = " + uid;
		

		
	}
	
	public void populateCurrentSessionData(int story, int student ){
		String aSql = "INSERT INTO current_session (id_word, word, "
						+"punctuation, id_story, name, errType) "
						+"SELECT id_word, word, COALESCE(punctuation, 0) "
						+"AS punctuation,“+story+”, “+student+”,0";

		myDataBase.rawQuery(aSql, null);
	}
	
	public void clearCurrentSessionData(){
		String aSql = "DELETE FROM current_session";
		myDataBase.rawQuery(aSql, null);
	}
}
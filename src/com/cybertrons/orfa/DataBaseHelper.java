package com.cybertrons.orfa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
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
    		copyDataBase();
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
    		//this.myDataBase = getReadableDatabase();
    		SQLiteDatabase tmpDB = this.getWritableDatabase();
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
 
    public void openDataBaseRW() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
 
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
        aCursor.close();
		
		return storiesList;
	}
	
	public ArrayList<RadioButton> getStudentForStats(Context tContext){

		ArrayList<RadioButton> storiesList = new ArrayList<RadioButton>();
		
		String aSql = "SELECT uid_student, last_name, first_name FROM student";//a Cursor object stores the results rawQuery
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
        // moveToFirst moves the Cursor to the first row of the results
        if(aCursor.moveToFirst()){
            do{
            	RadioButton btn = new RadioButton(tContext);
                btn.setId(aCursor.getInt(0));
                String label = aCursor.getString(2) + " " + aCursor.getString(1);
                btn.setText(label);                
            	storiesList.add(btn);
            }while(aCursor.moveToNext()); // moveToNext() moves the cursor to the next row
        }
        aCursor.close();
		
		return storiesList;
	}
	
	public ArrayList<Integer> getCorrect(int studentID){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		String aSql = " SELECT score FROM session WHERE id_student = " + studentID;
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
		
		if(aCursor.moveToFirst()){
			do{
				list.add(Integer.parseInt(aCursor.getString(0)));
			}while(aCursor.moveToNext());
		}
		
		aCursor.close();
		return list; 
	}
	
	public ArrayList<Integer> getIncorrect(int studentID){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		String aSql = " SELECT incorrect_count FROM session WHERE id_student = " + studentID;
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
		
		if(aCursor.moveToFirst()){
			do{
				list.add(Integer.parseInt(aCursor.getString(0)));
			}while(aCursor.moveToNext());
		}
		
		aCursor.close();
		return list; 
	}
	
	
	public ArrayList<Button> getStoryWords(int story, Context tContext){

		ArrayList<Button> storyWordsList = new ArrayList<Button>();
		Cursor aCursor;

		String aSql = "SELECT "
				+"uid_current_session AS _id  "
				+", word  "
				+", punctuation  "
				+", name  "
				+", errType  "
				+"FROM current_session "
				+"WHERE id_story = "+ story;
 

		
		//a Cursor object stores the results rawQuery
        aCursor = myDataBase.rawQuery(aSql, null);
        // moveToFirst moves the Cursor to the first row of the results
        
        int previousButton = -1;
        if(aCursor.moveToFirst()){
            do{
                
                if(aCursor.getInt(2) == 1 && previousButton >= 0){
                	Button prevBtn = storyWordsList.get(previousButton);
                	CharSequence prevButtonLabel = prevBtn.getText();
                	storyWordsList.get(previousButton).setText(prevButtonLabel + aCursor.getString(1));
                	
                	Button btn = new Button(tContext);
                    btn.setId(aCursor.getInt(0));
                	btn.setBackgroundColor(Color.WHITE);
                    previousButton++;
                	storyWordsList.add(btn);
                }else{
                	Button btn = new Button(tContext);
                    btn.setId(aCursor.getInt(0));
                    btn.setText(aCursor.getString(1)); //+ " " + new Integer(aCursor.getInt(0)).toString() +" " + btn.getId());
                    btn.setTextColor(Color.BLACK);
                	btn.setOnClickListener(DisplayButtonsActivity.markWord(btn));
                	btn.setBackgroundColor(Color.WHITE);
                    previousButton++;
                	storyWordsList.add(btn);
                }
            }while(aCursor.moveToNext()); // moveToNext() moves the cursor to the next row
        }
        aCursor.close();
        //return the words hello, fetch, set
        return storyWordsList; 
		
	}
	public void setCurrentSessionError(int uid, int error){
		
		myDataBase.execSQL(" UPDATE current_session " +
						" SET errType = " + error +
						" WHERE uid_current_session = " + uid);
	}
	
	public void populateCurrentSessionData(int story, int student ){
		
		
		myDataBase.execSQL("INSERT INTO current_session (id_word, word, "
						+"punctuation, id_story, name, errType) "
						+"SELECT id_word, word, COALESCE(punctuation, 0) "
						+"AS punctuation,"+story+", "+student+",0 "
						+"FROM story_content "
						+"LEFT JOIN words on story_content.id_word = words.uid_word "
						+"WHERE id_story = "+story);
	}
	
	public void clearCurrentSessionData(){
		myDataBase.execSQL("DELETE FROM current_session");
	}

	public void updateWordButtons(ArrayList<Button> storyWordsList){
		String aSql = "SELECT "
				+"uid_current_session AS _id  "
				+", word  "
				+", punctuation  "
				+", name  "
				+", errType  "
				+"FROM current_session ";
 
		//a Cursor object stores the results rawQuery
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
        // moveToFirst moves the Cursor to the first row of the results
        aCursor.moveToFirst();
         
		Iterator<Button> itr = storyWordsList.iterator();
		while (itr.hasNext()) {
			int errorCode = aCursor.getInt(4);
			switch(errorCode){
            case 0:  itr.next().setTextColor(Color.BLACK);
            break;
            case 1:  itr.next().setTextColor(Color.GREEN);
            break;
            case 2:  itr.next().setTextColor(Color.YELLOW);
            break;
            case 3:  itr.next().setTextColor(Color.RED);
            break;
            case 4:  itr.next().setTextColor(Color.RED);
            break;
            case 5:  itr.next().setTextColor(Color.RED);
            break;
            case 6:  itr.next().setTextColor(Color.RED);
			}
			aCursor.moveToNext();
		}

        aCursor.close();
        return; 
		
	}
	
	//This method gets the five largest words that were missed in the 
	//passage arranged in descending length order
	public ArrayList<String> getWordsIncorrect(ArrayList<String> list){
		String aSql = " SELECT word"
				+" 		FROM current_session"
				+" 		WHERE errType >= 3"
				+" 		ORDER BY LENGTH(word) DESC"
				+" 		LIMIT 5";
			
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
		if(aCursor.moveToFirst()){
            do{
            	list.add(aCursor.getString(0));
            }while(aCursor.moveToNext());
		}
        aCursor.close();
        return list; 
	}

	//This method returns the five most frequently missed rules in a session sorted by
	//descending string length
	public ArrayList<String> getRulesIncorrect(ArrayList<String> list){
		String aSql =" SELECT sounds.sound, COUNT(sounds.sound) AS cnt"
				+"     FROM current_session"
				+"     LEFT JOIN soundwordmap on current_session.id_word = soundwordmap.id_word"
				+"     LEFT JOIN sounds on soundwordmap.id_sound = sounds.uid_sound"
				+"     WHERE errType >=3"
				+"     GROUP BY sounds.sound"
				+"     ORDER BY cnt DESC, LENGTH(sound) DESC"
				+"     LIMIT 5";
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
		if(aCursor.moveToFirst()){
            do{
            	list.add(aCursor.getString(0));
            }while(aCursor.moveToNext());
		}
        aCursor.close();
        return list; 
	}

	//This method returns the number of Correct words from a session
	public int getWordsCorrectCount(int end){
		int correctCount = 0;
		
		String aSql = "SELECT *"
				+" FROM current_session"
				+" WHERE uid_current_session <= "+end
				+" AND  punctuation = 0"
				+" AND errType < 3";
			
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
        correctCount = aCursor.getCount();
        aCursor.close();
        return correctCount; 
	}

	public int getWordsIncorrectCount(int end){
		int incorrectCount = 0;
		
		String aSql = "SELECT *"
				+" FROM current_session"
				+" WHERE uid_current_session <= "+end
				+" AND  punctuation = 0"
				+" AND errType >= 3";
 
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
        aCursor.moveToFirst();
        incorrectCount = aCursor.getCount();
        aCursor.close();

        return incorrectCount; 
	}
	public int getAverageLenghtIncorrect(){
		int average = 0;
		
		String aSql = " SELECT AVG(LENGTH(WORD)) AS average"
				+"      FROM current_session"
				+"      WHERE errType >= 3";
		Cursor aCursor = myDataBase.rawQuery(aSql, null);
		if(aCursor.moveToFirst()){
			average = aCursor.getInt(0);
		}
        aCursor.close();
		return average;
	}

	//this method returns the error type and count of that error in current session
	public ArrayList<String> getErrorStats(){
		ArrayList<String> list = new ArrayList<String>();
		String aSql = " SELECT error_name, COUNT(errType)"
					+"  FROM current_session"
					+"  LEFT JOIN errors ON errType = errors._uid_error"
					+"  WHERE errType >= 2"	
					+"  GROUP BY errType";

		Cursor aCursor = myDataBase.rawQuery(aSql, null);
		if(aCursor.moveToFirst()){
            do{
            	list.add(aCursor.getString(0));
            	list.add(Integer.toString(aCursor.getInt(1)));
            }while(aCursor.moveToNext());
		}
        aCursor.close();
        return list;
	}
	
	//Export a csv file with table sqlite table contents
	public void writeCSV() {

		File exportDir = new File(Environment.getExternalStorageDirectory(), "");        
		if (!exportDir.exists()) {
			exportDir.mkdirs();
		}
		File file = new File(exportDir, "dibblesDB.csv");
	
		try {
			file.createNewFile();                
			CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
			Cursor curCSV = myDataBase.rawQuery("SELECT * FROM student",null);
			csvWrite.writeNext(curCSV.getColumnNames());
			while(curCSV.moveToNext())	{
				String arrStr[] ={curCSV.getString(0),curCSV.getString(1),
						curCSV.getString(2)};
				csvWrite.writeNext(arrStr);
			}
			String arrStr[] = {""};
			csvWrite.writeNext(arrStr);
			curCSV = myDataBase.rawQuery("SELECT * FROM session",null);
			csvWrite.writeNext(curCSV.getColumnNames());
			while(curCSV.moveToNext())	{
				String arrStr2[] ={curCSV.getString(0),curCSV.getString(1),
						curCSV.getString(2),curCSV.getString(3),curCSV.getString(4),curCSV.getString(5)};
				csvWrite.writeNext(arrStr2);
			}
					
			csvWrite.close();
			curCSV.close();
		}
		catch(SQLException sqlEx) {
			Log.e("Database Helper Class", sqlEx.getMessage(), sqlEx);
		}
		catch (IOException e) {
			Log.e("Database Helper Class", e.getMessage(), e);
		}

	}
	
	//return a curosr object with the results of the students table
		public Cursor getStudents(){
			
			Cursor std = null;
			try {
				openDataBase();
				String sql = "select uid_student as _id, first_name as firstName, last_name as LastName from student";
				std = myDataBase.rawQuery(sql,null);
				close();
			}catch(SQLException sqlEx){
				Log.e("Database Helper Class", sqlEx.getMessage(), sqlEx);
			}
					
			return std;
		}

		public void writeSession(int finalErrors, int finalScore, String finalNotes) {
			int student = 0;
			
			myDataBase.execSQL("INSERT INTO session (notes, id_student, incorrect_count, score)"
	        	+" VALUES ( " + finalNotes + ", "+ student + ", "+ finalErrors  + ", "+ finalScore+ ")");
			
		}

}
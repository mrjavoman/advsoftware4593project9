package com.cybertrons.orfa;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SessionStats extends Activity {
	
	static final String ERRORS = "cs4953.advsoft.orfa.ERRORS";
	static final String SCORE = "cs4953.advsoft.orfa.SCORE";
	private int lastWord;
	private int score;
	private int errors;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_stats);
    
	    DataBaseHelper dbHelper = new DataBaseHelper(this);
		// Get the message from the intent
		Intent intent = getIntent();
		lastWord = intent.getIntExtra(MarkWordActivity.UID_WORD, 0);
	    
	    
	   TextView correctBlank = (TextView) findViewById(R.id.corr_words);
	   TextView incorrectBlank = (TextView) findViewById(R.id.incorr_words);
	   TextView avgIncorrBlank = (TextView) findViewById(R.id.avg_length);
	   TextView word1 = (TextView) findViewById(R.id.incorr_word_one);
	   TextView word2 = (TextView) findViewById(R.id.incorr_word_two);
	   TextView word3 = (TextView) findViewById(R.id.incorr_word_three);
	   TextView word4 = (TextView) findViewById(R.id.incorr_word_four);
	   TextView word5 = (TextView) findViewById(R.id.incorr_word_five);
	   TextView rule1 = (TextView) findViewById(R.id.incorr_rule_one);
	   TextView rule2 = (TextView) findViewById(R.id.incorr_rule_two);
	   TextView rule3 = (TextView) findViewById(R.id.incorr_rule_three);
	   TextView rule4 = (TextView) findViewById(R.id.incorr_rule_four);
	   TextView rule5 = (TextView) findViewById(R.id.incorr_rule_five);
	   
	   TextView errTypeOne = (TextView) findViewById(R.id.errType_one);
	   TextView errTypeTwo = (TextView) findViewById(R.id.errType_two);
	   TextView errTypeThree = (TextView) findViewById(R.id.errType_three);
	   TextView errTypeFour = (TextView) findViewById(R.id.errType_four);
	   TextView errTypeFive = (TextView) findViewById(R.id.errType_five);	   
	   TextView errCountOne = (TextView) findViewById(R.id.errType_one_count);
	   TextView errCountTwo = (TextView) findViewById(R.id.errType_two_count);
	   TextView errCountThree = (TextView) findViewById(R.id.errType_three_count);
	   TextView errCountFour = (TextView) findViewById(R.id.errType_four_count);
	   TextView errCountFive = (TextView) findViewById(R.id.errType_five_count);
	   
	   ArrayList<String> wordsList = new ArrayList<String>();	   
	   ArrayList<String> rulesList = new ArrayList<String>();
	   ArrayList<String> errorList = new ArrayList<String>();
	    
	    
 
	    try {
	    	dbHelper.openDataBase();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    
		try {
			score = dbHelper.getWordsCorrectCount(lastWord);
			correctBlank.setText( new Integer(score).toString());
		}catch(SQLException sqle){
			throw sqle;
		}
	    
		try {
			int rightCount = dbHelper.getAverageLenghtIncorrect();
			avgIncorrBlank.setText( new Integer(rightCount).toString());
		}catch(SQLException sqle){
			throw sqle;
		}

	    
		try {
			errorList = dbHelper.getErrorStats();
			int numwords = 0;
	    	if(!errorList.isEmpty()){
	    		numwords = errorList.size() / 2;
	    		errTypeOne.setText(errorList.get(0));
	    		errCountOne.setText("   "+errorList.get(1));
	    	}else{

	    		errTypeOne.setText("");
	    		errCountOne.setText("");
	    	}
	    	if(numwords > 1){
	    		errTypeTwo.setText(errorList.get(2));
	    		errCountTwo.setText("   "+errorList.get(3));
	    	}else{
	    		errTypeTwo.setText("");
	    		errCountTwo.setText("");
	    	}
	    	if(numwords > 2){
	    		errTypeThree.setText(errorList.get(4));
	    		errCountThree.setText("   "+errorList.get(5));
			}else{
	    		errTypeThree.setText("");
	    		errCountThree.setText("");
	    	}
	    	if(numwords > 3){
	    		errTypeFour.setText(errorList.get(6));
	    		errCountFour.setText("   "+errorList.get(7));
			}else{
	    		errTypeFour.setText("");
	    		errCountFour.setText("");
	    	}
	    	if(numwords > 4){
	    		errTypeFive.setText(errorList.get(8));
	    		errCountFive.setText("   "+errorList.get(9));
			}else{
	    		errTypeFive.setText("");
	    		errCountFive.setText("");
	    	}
			
		}catch(SQLException sqle){
			throw sqle;
		}
	    	    
	    try {
	    	wordsList = dbHelper.getWordsIncorrect(wordsList);
	    	if(!wordsList.isEmpty()){
	    		errors = wordsList.size();
	    		word1.setText(wordsList.get(0));
	    	}else{
	    		word1.setText("-");
	    	}
	    	if(errors > 1){
	    		word2.setText(wordsList.get(1));
	    	}else{
	    		word2.setText("-");
	    	}
	    	if(errors > 2){
	    		word3.setText(wordsList.get(2));
			}else{
	    		word3.setText("-");
	    	}
	    	if(errors > 3){
	    		word4.setText(wordsList.get(3));
			}else{
	    		word4.setText("-");
	    	}
	    	if(errors > 4){
	    		word5.setText(wordsList.get(4));
			}else{
	    		word5.setText("-");
	    	}
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}

	    
	    try {
	    	rulesList = dbHelper.getRulesIncorrect(rulesList);
	    	int numRules = 0;
	    	if(!rulesList.isEmpty()){
	    		numRules = rulesList.size();
	    		rule1.setText(rulesList.get(0));
	    	}else{
	    		rule1.setText("-");
	    	}
	    	if(numRules > 1){
	    		rule2.setText(rulesList.get(1));
	    	}else{
	    		rule2.setText("-");
	    	}
	    	if(numRules > 2){
	    		rule3.setText(rulesList.get(2));
			}else{
	    		rule3.setText("-");
	    	}
	    	if(numRules > 3){
	    		rule4.setText(rulesList.get(3));
			}else{
	    		rule4.setText("-");
	    	}
	    	if(numRules > 4){
	    		rule5.setText(rulesList.get(4));
			}else{
	    		rule5.setText("-");
	    	}
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
    
	    try {
	    	int wrongCount = dbHelper.getWordsIncorrectCount(lastWord);
	    	incorrectBlank.setText( new Integer(wrongCount).toString());
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    dbHelper.close();
    }
    
    public void onSaveSession(View view){
    	Intent intent = new Intent(this, SaveSessionActivity.class);
    	intent.putExtra(SCORE, score);
    	intent.putExtra(ERRORS, errors);
    	startActivity(intent);
    }
}

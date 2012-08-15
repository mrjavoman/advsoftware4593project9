package com.cybertrons.orfa;

import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SessionStatsActivity extends Activity {
	
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
	   TextView word6 = (TextView) findViewById(R.id.incorr_word_six);
	   TextView word7 = (TextView) findViewById(R.id.incorr_word_seven);
	   TextView word8 = (TextView) findViewById(R.id.incorr_word_eight);
	   TextView word9 = (TextView) findViewById(R.id.incorr_word_nine);
	   TextView word10 = (TextView) findViewById(R.id.incorr_word_ten);
	   
	   
	   TextView rule1 = (TextView) findViewById(R.id.incorr_rule_one);
	   TextView rule2 = (TextView) findViewById(R.id.incorr_rule_two);
	   TextView rule3 = (TextView) findViewById(R.id.incorr_rule_three);
	   TextView rule4 = (TextView) findViewById(R.id.incorr_rule_four);
	   TextView rule5 = (TextView) findViewById(R.id.incorr_rule_five);
	   TextView rule6 = (TextView) findViewById(R.id.incorr_rule_six);
	   TextView rule7 = (TextView) findViewById(R.id.incorr_rule_seven);
	   TextView rule8 = (TextView) findViewById(R.id.incorr_rule_eight);
	   TextView rule9 = (TextView) findViewById(R.id.incorr_rule_nine);
	   TextView rule10 = (TextView) findViewById(R.id.incorr_rule_ten);	
	   
	   TextView ruleCountOne = (TextView) findViewById(R.id.rule_one_count);
	   TextView ruleCountTwo = (TextView) findViewById(R.id.rule_two_count);
	   TextView ruleCountThree = (TextView) findViewById(R.id.rule_three_count);
	   TextView ruleCountFour = (TextView) findViewById(R.id.rule_four_count);
	   TextView ruleCountFive = (TextView) findViewById(R.id.rule_five_count);	   
	   TextView ruleCountSix = (TextView) findViewById(R.id.rule_six_count);
	   TextView ruleCountSeven = (TextView) findViewById(R.id.rule_seven_count);
	   TextView ruleCountEight = (TextView) findViewById(R.id.rule_eight_count);
	   TextView ruleCountNine = (TextView) findViewById(R.id.rule_nine_count);
	   TextView ruleCountTen = (TextView) findViewById(R.id.rule_ten_count);
	   
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
	    		word1.setText("");
	    	}
	    	if(errors > 1){
	    		word2.setText(wordsList.get(1));
	    	}else{
	    		word2.setText("");
	    	}
	    	if(errors > 2){
	    		word3.setText(wordsList.get(2));
			}else{
	    		word3.setText("");
	    	}
	    	if(errors > 3){
	    		word4.setText(wordsList.get(3));
			}else{
	    		word4.setText("");
	    	}
	    	if(errors > 4){
	    		word5.setText(wordsList.get(4));
			}else{
	    		word5.setText("");
	    	}
	    	if(errors > 5){
	    		word6.setText(wordsList.get(1));
	    	}else{
	    		word6.setText("");
	    	}
	    	if(errors > 6){
	    		word7.setText(wordsList.get(2));
			}else{
	    		word7.setText("");
	    	}
	    	if(errors > 7){
	    		word8.setText(wordsList.get(3));
			}else{
	    		word8.setText("");
	    	}
	    	if(errors > 8){
	    		word9.setText(wordsList.get(4));
			}else{
	    		word9.setText("");
	    	}
	    	if(errors > 9){
	    		word10.setText(wordsList.get(4));
			}else{
	    		word10.setText("");
	    	}
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}

	    
	    try {
	    	rulesList = dbHelper.getRulesIncorrect(rulesList);
	    	int numRules = 0;
	    	
	    	if(!rulesList.isEmpty()){
	    		numRules = rulesList.size() / 2;
	    		rule1.setText(rulesList.get(0));
	    		ruleCountOne.setText("   "+rulesList.get(1));
	    	}else{
	    		rule1.setText("");
	    		ruleCountOne.setText("");
	    	}
	    	if(numRules > 1){
	    		rule2.setText(rulesList.get(2));
	    		ruleCountTwo.setText("   "+rulesList.get(3));
	    	}else{
	    		rule2.setText("");
	    		ruleCountTwo.setText("");
	    	}
	    	if(numRules > 2){
	    		rule3.setText(rulesList.get(4));
	    		ruleCountThree.setText("   "+rulesList.get(5));
			}else{
	    		rule3.setText("");
	    		ruleCountThree.setText("");
	    	}
	    	if(numRules > 3){
	    		rule4.setText(rulesList.get(6));
	    		ruleCountFour.setText("   "+rulesList.get(7));
			}else{
	    		rule4.setText("");
	    		ruleCountFour.setText("");
	    	}
	    	if(numRules > 4){
	    		rule5.setText(rulesList.get(8));
	    		ruleCountFive.setText("   "+rulesList.get(9));
			}else{
	    		rule5.setText("");
	    		ruleCountFive.setText("");
	    	}
	    	if(numRules > 5){
	    		rule6.setText(rulesList.get(10));
	    		ruleCountSix.setText("   "+rulesList.get(11));
	    	}else{
	    		rule6.setText("");
	    		ruleCountSix.setText("");
	    	}
	    	if(numRules > 6){
	    		rule7.setText(rulesList.get(12));
	    		ruleCountSeven.setText("   "+rulesList.get(13));
			}else{
	    		rule7.setText("");
	    		ruleCountSeven.setText("");
	    	}
	    	if(numRules > 7){
	    		rule8.setText(rulesList.get(14));
	    		ruleCountEight.setText("   "+rulesList.get(15));
			}else{
	    		rule8.setText("");
	    		ruleCountEight.setText("");
	    	}
	    	if(numRules > 8){
	    		rule9.setText(rulesList.get(16));
	    		ruleCountNine.setText("   "+rulesList.get(17));
			}else{
	    		rule9.setText("");
	    		ruleCountNine.setText("");
	    	}
	    	if(numRules > 9){
	    		rule10.setText(rulesList.get(18));
	    		ruleCountTen.setText("   "+rulesList.get(19));
			}else{
	    		rule10.setText("");
	    		ruleCountTen.setText("");
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

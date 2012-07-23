package com.cybertrons.orfa;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.TextView;

public class SessionStats extends Activity {
	
	private int lastWord;

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
	   
	   ArrayList<String> wordsList = new ArrayList<String>();	   
	   ArrayList<String> rulesList = new ArrayList<String>();
	    
	    
 
	    try {
	    	dbHelper.openDataBaseRW();
	   	}catch(SQLException sqle){
	   		throw sqle;
	   	}
	    
		try {
			int rightCount = dbHelper.getWordsCorrectCount(lastWord);
			correctBlank.setText( new Integer(rightCount).toString());
		}catch(SQLException sqle){
			throw sqle;
		}
	    	    
	    try {
	    	wordsList = dbHelper.getWordsIncorrect(wordsList);
	    	int numWords = 0;
	    	if(!wordsList.isEmpty()){
	    		numWords = wordsList.size();
	    		word1.setText(wordsList.get(0));
	    	}else{
	    		word1.setText("-");
	    	}
	    	if(numWords > 1){
	    		word2.setText(wordsList.get(1));
	    	}else{
	    		word2.setText("-");
	    	}
	    	if(numWords > 2){
	    		word3.setText(wordsList.get(2));
			}else{
	    		word3.setText("-");
	    	}
	    	if(numWords > 3){
	    		word4.setText(wordsList.get(3));
			}else{
	    		word4.setText("-");
	    	}
	    	if(numWords > 4){
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
}

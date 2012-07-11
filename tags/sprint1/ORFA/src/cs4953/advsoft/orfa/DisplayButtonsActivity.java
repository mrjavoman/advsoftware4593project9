package cs4953.advsoft.orfa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class DisplayButtonsActivity extends Activity{
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(ORFAActivity.NUMBER);
 
        int numButtons = Integer.parseInt(message);

     //   LinearLayout buttonList = new LinearLayout(this); 
        
        LinearLayout buttonList = (LinearLayout) View.inflate(this, R.layout.reader, null);

        for (int i = 0; i < numButtons; i++){
            Button btn = new Button(this);
            btn.setId(2000+i);
            btn.setText(Integer.toString(i+1));
            btn.setWidth(60);
            buttonList.addView(btn);       
            setContentView(buttonList); 
        }


 
    }
}


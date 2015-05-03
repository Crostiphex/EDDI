package com.visionarries.www.eddi;


import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Steps to using the DB:
 * 1. [DONE] Instantiate the DB Adapter
 * 2. [DONE] Open the DB
 * 3. [DONE] use get, insert, delete, .. to change data.
 * 4. [DONE]Close the DB
 */

/**
 * Demo application to show how to use the 
 * built-in SQL lite database.
 */
public class Database_main extends WelcomeScreen {
	
	DBAdapter myDb;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.data_base);
		
		openDB();
        Cursor cursor = myDb.getAllRows();
        displayRecordSet(cursor);
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();	
		closeDB();
	}


	private void openDB() {
		myDb = new DBAdapter(this);
		myDb.open();
	}
	private void closeDB() {
		myDb.close();
	}

	
	
	private void displayText(String message) {

        TextView textView = (TextView) findViewById(R.id.textDisplay);
        textView.setText(message);
	}

	



	public void onClick_ClearAll(View v) {
		displayText("Clicked clear all!");
		myDb.deleteAll();
	}

	public void onClick_GoHome(View v) {
       WelcomeScreen.doRestart(this);
	}

	// Display an entire recorded set to the screen.
	private void displayRecordSet(Cursor cursor) {
		String message = "";
        // populate the message from the cursor
		
		// Reset cursor to start, checking to see if there's data:
		if (cursor.moveToFirst()) {
			do {
				// Process the data:
				String name = cursor.getString(DBAdapter.COL_NAME);
				double dom_index = cursor.getDouble(DBAdapter.COL_DOMINANCE_INDEX);
				double id = cursor.getDouble(DBAdapter.COL_ROW_ID);
				String right_time = cursor.getString(DBAdapter.COL_RIGHT_EYE_TIME);

				
				// Append data to the message:
                message += id+".\n"+
                        "Name: "+name+"\n"+
                        "Dominance Index: "+dom_index+"\n"+
                        "Values: "+right_time+"\n\n";

			} while(cursor.moveToNext());
		}
		
		// Close the cursor to avoid a resource leak.
		cursor.close();
		//person can scroll through long list of test. They can click and seemingly edit but that's
		// just the field it is displayed in there is  no way to edit the text, but rather just
		// clear it or add more by retaking the test.
		displayText(message);
	}
}











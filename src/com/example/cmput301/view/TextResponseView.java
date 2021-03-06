/*******************************************************************************
 * Copyright (c) 2012 Jason Reddekopp, Andrew McCann, Daniel Sopel, David Yu.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Jason Reddekopp, Andrew McCann, Daniel Sopel, David Yu - initial API and                              
 *     implementation
 ******************************************************************************/
package com.example.cmput301.view;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.cmput301.model.*;
import com.example.cmput301.model.response.TextResponse;
import com.example.cmput301.model.response.factory.TextResponseFactory;
import com.example.cmput301.controller.*;
import com.example.cmput301.R;


/**
 * This class is a type of response view, it is responsible for getting
 * input from the user in the form of text and adds it as a response
 * to the selected task. A view to show the already existing responses
 * is also available.
 *
 */
@TargetApi(15)
public class TextResponseView extends ResponseView {

	
	private IndividualTaskController itController;
	Task aTask;
	String responseString;
	Activity currentActivity;

	/**
	 * Overrided method that sets the view of the activity.
	 * The action bar has a back button enabled, and the title
	 * of the activity is set to the task title. A list of
	 * responses for that task is also displayed, with a 
	 * prompt at the bottom for user text response input to
	 * that task.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		respFactory = new TextResponseFactory();
		
		currentActivity = this;

		super.onCreate(savedInstanceState);
		setContentView(R.layout.text_response_view);

		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		itController = new IndividualTaskController(this.getApplicationContext(), this);
		String taskTile;

		//Getting information from bundle passed from MainActivity
		Bundle bundle = getIntent().getExtras();
		aTask = (Task) bundle.getSerializable("task");
		taskTile = aTask.getName();

		//setting the title of the task to be the activity title
		setTitle(taskTile);

		final EditText responseInput;
		responseInput = (EditText) findViewById(R.id.textResponseInput);


		Button postBut;
		postBut = (Button) findViewById(R.id.postResponseButton);


		postBut.setOnClickListener(new View.OnClickListener() {
			//Post has been clicked.
			public void onClick(View v) {

				//Can now add response to the task using this string
				responseString = responseInput.getText().toString();
				TextResponse resp = (TextResponse) respFactory.createResponse(responseString, responseString);
				
				if(responseString.equals(""))
				{
					Toast.makeText(getApplicationContext(), 
							"Text input is required!", Toast.LENGTH_SHORT).show();
				}
				else
				{
					itController.addResponse(aTask, resp);
					responseInput.setText("");
				}
				//setting list of responses for task
				ListView responses = (ListView) findViewById(R.id.text_response_list);
				responses.setAdapter(new TextAdapter(currentActivity, aTask));
			}
		});

		//setting list of responses for task
		ListView responses = (ListView) findViewById(R.id.text_response_list);
		responses.setAdapter(new TextAdapter(currentActivity, aTask));
	}


	/**
     * Overrided method that just enables the back button to kill the activity
     */
	public boolean onOptionsItemSelected(MenuItem item) {

		//go back, kills activity
		if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}
}

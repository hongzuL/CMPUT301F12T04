package com.example.cmput301;


import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

@TargetApi(11)
public class IndividualTaskView extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.individual_task_view);
		
		String taskTile;
		String taskDesc;
		int taskPos; 
		
		//enable back button on actionbar
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//Getting information from bundle passed from MainActivity
		Bundle bundle = getIntent().getExtras();
		taskTile = bundle.getString("title");
		taskDesc = bundle.getString("description");
		taskPos = bundle.getInt("id");
		
		//setting the description of the task
		TextView title = (TextView) findViewById(R.id.indvidual_des_view);
		title.setText(taskDesc);
		
		//setting the title of the task
		setTitle(taskTile);
		
		



	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.individual_task_view, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) {

        //do upload, currently set to just kill activity
		if(item.getItemId() == R.id.menu_upload) {
			finish();
		}
		if(item.getItemId() == R.id.menu_respond )
		{
			//Should launch the respond to a task activity here. 
			finish();
			
		}
		
		//go back, kills activity
		if(item.getItemId() == android.R.id.home) {
			finish();
		}
		return true;
	}
}

package net.ddns.richardkellnberger.campuscompanion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import net.ddns.richardkellnberger.campuscompanion.helper.SQLHandler;

public class LaunchActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_launch);

		((ImageButton) findViewById(R.id.food)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LaunchActivity.this, FoodActivity.class));
			}
		});

		((ImageButton) findViewById(R.id.config)).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LaunchActivity.this, ConfigActivity.class));
			}
		});
		
		String start = new SQLHandler(this).getConfig("start");
		if(start!=null) {
			if(start.equals("Speißeplan")) {
				startActivity(new Intent(LaunchActivity.this, FoodActivity.class));
			}else if(start.equals("Einstellungen")) {
				startActivity(new Intent(LaunchActivity.this, ConfigActivity.class));
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.launch, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}
}

package net.ddns.richardkellnberger.campuscompanion;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import net.ddns.richardkellnberger.campuscompanion.helper.SQLHandler;

public class ConfigActivity extends Activity {
	
	private SQLHandler sql;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_config);
		
		sql = new SQLHandler(this);
		
		ArrayList<String> mensen = new ArrayList<String>();
		mensen.add("Hauptmensa");//TODO
		mensen.add("Frischraum");
		mensen.add("Cafetaria");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mensen);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		final Spinner spinner = (Spinner) findViewById(R.id.spinnerMensa);
		spinner.setAdapter(adapter);
		String m = sql.getConfig("mensa");
		if(m!=null) {
			spinner.setSelection(mensen.indexOf(m));
		}
		
		ArrayList<String> start = new ArrayList<String>();
		start.add("Startseite");//TODO
		start.add("Speiﬂeplan");
		start.add("Einstellungen");
		ArrayAdapter<String> adapterS = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, start);
		adapterS.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		final Spinner spinnerS = (Spinner) findViewById(R.id.spinnerStart);
		spinnerS.setAdapter(adapterS);
		String s = sql.getConfig("start");
		if(s!=null) {
			spinnerS.setSelection(start.indexOf(s));
		}

		spinnerS.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				sql.setConfig("start", spinnerS.getSelectedItem().toString());
			}
		});
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				sql.setConfig("mensa", spinner.getSelectedItem().toString());
			}
		});
		
		((Button) findViewById(R.id.save)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sql.setConfig("start", spinnerS.getSelectedItem().toString());
				sql.setConfig("mensa", spinner.getSelectedItem().toString());
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.config, menu);
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

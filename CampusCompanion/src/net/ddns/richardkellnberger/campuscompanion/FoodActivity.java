package net.ddns.richardkellnberger.campuscompanion;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import net.ddns.richardkellnberger.campuscompanion.helper.SQLHandler;
import net.ddns.richardkellnberger.campuscompanion.views.FoodFragment;
import thilokru.mensa.IMensa;
import thilokru.mensa.MensaDataRetriever;

public class FoodActivity extends FragmentActivity {

	private Date day;
	private TextView dayT;
	private SimpleDateFormat sdf;
	private ViewPager pager;
	private ScreenSlidePagerAdapter pagerAdapter;
	private String[] mensen = { "Hauptmensa", "Frischraum", "Cafetaria" }; // TODO
	private Button m1, m2, m3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food);
		
		cleanDB(new SQLHandler(this));

		sdf = new SimpleDateFormat("E dd.MM.yyyy");
		day = new Date(System.currentTimeMillis());

		dayT = (TextView) findViewById(R.id.day);

		dayT.setText(sdf.format(day));

		findViewById(R.id.last).setEnabled(false);

		findViewById(R.id.last).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				day.setTime(day.getTime() - 24 * 60 * 60 * 1000);
				dayT.setText(sdf.format(day));
				if (sdf.format(day).equals(sdf.format(new Date(System.currentTimeMillis())))) {
					findViewById(R.id.last).setEnabled(false);
				}
			}
		});

		findViewById(R.id.next).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				day.setTime(day.getTime() + 24 * 60 * 60 * 1000);
				dayT.setText(sdf.format(day));
				findViewById(R.id.last).setEnabled(true);
			}
		});

		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);

		m1 = ((Button) findViewById(R.id.m1));
		m2 = ((Button) findViewById(R.id.m2));
		m3 = ((Button) findViewById(R.id.m3));

		pager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int page) {
				update(page);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
		});

		m1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(0);
			}
		});
		m2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(1);
			}
		});
		m3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				pager.setCurrentItem(2);
			}
		});

		int index = 0;
		String mensaDB = new SQLHandler(this).getConfig("mensa");
		if(mensaDB!=null) {
			for(int i = 0; i<3; i++) {
				if(mensen[i].equals(mensaDB)) {
					index = i;
					break;
				}
			}
		}
		pager.setCurrentItem(index);
		update(index);
	}
	
	private void update(int page) {
		setTitle(mensen[page]);
		if (page == 0) {
			m1.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_blue_light, null));
			m2.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_green_dark, null));
			m3.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_orange_dark, null));
		} else if (page == 1) {
			m1.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_blue_dark, null));
			m2.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_green_light, null));
			m3.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_orange_dark, null));
		} else if (page == 2) {
			m1.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_blue_dark, null));
			m2.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_green_dark, null));
			m3.setBackgroundColor(new ResourcesCompat().getColor(getResources(), android.R.color.holo_orange_light, null));
		}
	}
	
	private void cleanDB(SQLHandler sql) {
		long time = System.currentTimeMillis()-24*60*60*1000;
		
		SimpleDateFormat sdf = new SimpleDateFormat("E dd.MM.yyyy");
		Date day = new Date(time);
		String fday = sdf.format(day);
		updateDB(sql, fday);
		while(sql.dropFood(fday)!=0) {
			time -= 24*60*60*1000;
			day.setTime(time);
			fday = sdf.format(day);
		}
	}
	
	private void updateDB(final SQLHandler sql, final String day) {
		if(!day.equals(sql.getConfig("lastPull"))) {
			//TODO update DB

			new Timer(true).schedule(new TimerTask() {
				
				@Override
				public void run() {
					try {
						MensaDataRetriever data = new MensaDataRetriever(new URL("http://ub.campusapp.creatives-at-work.de/data_1_1_0.json"));
						Map<String, Map<String, Double>> food = data.getFoodPriceList(new Date(System.currentTimeMillis()), IMensa.FoodSource.MENSA, IMensa.Role.STUDENT);
						for(String key: food.keySet()) {
							System.out.println(key);
						}
						
						
						
						sql.setConfig("lastPull", day);
					} catch (IOException e) {
						Toast.makeText(FoodActivity.this, "Keine Internetverbindung", Toast.LENGTH_LONG).show();
					}
				}
			}, 0);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.food, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.settings) {
			startActivity(new Intent(this, ConfigActivity.class));
			return true;
		}
		if (id == R.id.menue) {
			Intent intent = new Intent(this, LaunchActivity.class);
			intent.putExtra("launch", "no");
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		private FoodFragment[] fragments = new FoodFragment[3];

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public FoodFragment getItem(int position) {
			if (fragments[position] == null) {
				for(int i = 0; i<3; i++) {
					fragments[i] = new FoodFragment(i);
				}
			}
			return fragments[position];
		}

		@Override
		public int getCount() {
			return 3;
		}
	}
}

package net.ddns.richardkellnberger.campuscompanion;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import android.widget.ListView;
import android.widget.TextView;
import net.ddns.richardkellnberger.campuscompanion.helper.ListAdapter;
import net.ddns.richardkellnberger.campuscompanion.views.FoodFragment;

public class FoodActivity extends FragmentActivity {

	private Date day;
	private TextView dayT;
	private SimpleDateFormat sdf;
	private ViewPager pager;
	private ScreenSlidePagerAdapter pagerAdapter;
	private ListView listView;
	private ListAdapter adapter;
	private String[] mensen = { "Hauptmensa", "Frischraum", "Cafetaria" }; // TODO

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_food);

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
		adapter = new ListAdapter();

		final Button m1 = ((Button) findViewById(R.id.m1));
		final Button m2 = ((Button) findViewById(R.id.m2));
		final Button m3 = ((Button) findViewById(R.id.m3));

		pager.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int page) {
				listView = (ListView) pagerAdapter.getItem(0).getRootView();
				if (listView != null) {
					listView.setAdapter(adapter);
				}
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

		pager.setCurrentItem(1, true);
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
		if (id == R.id.action_settings) {
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
				fragments[0] = new FoodFragment();
				fragments[1] = new FoodFragment();
				fragments[2] = new FoodFragment();
			}
			return fragments[position];
		}

		@Override
		public int getCount() {
			return 3;
		}
	}
}

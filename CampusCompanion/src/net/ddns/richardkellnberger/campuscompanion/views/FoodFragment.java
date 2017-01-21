package net.ddns.richardkellnberger.campuscompanion.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import net.ddns.richardkellnberger.campuscompanion.R;
import net.ddns.richardkellnberger.campuscompanion.helper.ListAdapter;

public class FoodFragment extends Fragment {
	private ListView listView;
	private ListAdapter adapter;
	private int id;
	
	public FoodFragment(int id) {
		this.id = id;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_food, container, false);

		adapter = new ListAdapter(id);
		listView = (ListView) rootView;
		listView.setAdapter(adapter);
		
		return rootView;
	}
}

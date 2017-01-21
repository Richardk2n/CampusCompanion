package net.ddns.richardkellnberger.campuscompanion.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import net.ddns.richardkellnberger.campuscompanion.R;

public class FoodFragment extends Fragment {

	private View rootView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_food, container, false);
		return rootView;
	}
	
	public View getRootView() {
		return rootView;
	}
}

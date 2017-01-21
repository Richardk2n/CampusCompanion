package net.ddns.richardkellnberger.campuscompanion.helper;

import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import net.ddns.richardkellnberger.campuscompanion.R;

public class ListAdapter extends BaseAdapter implements android.widget.ListAdapter{

	@Override
	public int getCount() {
		//TODO
		return 5;
	}

	@Override
	public Object getItem(int position) {
		// TODO
		return String.valueOf(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = View.inflate(parent.getContext(), R.layout.list_item, null);
		((TextView) convertView.findViewById(R.id.item)).setText(String.valueOf(position));
		((TextView) convertView.findViewById(R.id.price)).setText(String.valueOf(Math.random()*5)+"€");
		((RatingBar) convertView.findViewById(R.id.rating)).setEnabled(false);
		((RatingBar) convertView.findViewById(R.id.rating)).setRating(Float.parseFloat(String.valueOf(Math.random()*5)));;
		return convertView;
	}

}

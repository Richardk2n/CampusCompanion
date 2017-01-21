package net.ddns.richardkellnberger.campuscompanion.helper;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import net.ddns.richardkellnberger.campuscompanion.DishActivity;
import net.ddns.richardkellnberger.campuscompanion.R;

public class ListAdapter extends BaseAdapter implements android.widget.ListAdapter{

	private int id;
	
	public ListAdapter(int id) {
		this.id = id;
	}
	
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
	public View getView(int position, View convertView, final ViewGroup parent) {
		final String name = String.valueOf(position)+"efduijolks wuiofejwdas yiuojlhkefuiefi uefjfeudjiklhedu ifjlked iujk";
		final String price = String.valueOf(Math.random()*5).substring(0, 4)+"€";
		final float rating = Float.parseFloat(String.valueOf(Math.random()*5));
		
		convertView = View.inflate(parent.getContext(), R.layout.list_item, null);
		((TextView) convertView.findViewById(R.id.item)).setText(name);
		((TextView) convertView.findViewById(R.id.price)).setText(price);
		((RatingBar) convertView.findViewById(R.id.rating)).setEnabled(false);
		((RatingBar) convertView.findViewById(R.id.rating)).setRating(rating);
		
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(parent.getContext(), DishActivity.class);
				intent.putExtra("name", name);
				intent.putExtra("price", price);
				intent.putExtra("rating", rating);
				parent.getContext().startActivity(intent);
			}
		});
		
		return convertView;
	}

}

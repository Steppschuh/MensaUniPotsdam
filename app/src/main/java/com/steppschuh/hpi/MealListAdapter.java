package com.steppschuh.hpi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MealListAdapter extends BaseAdapter {

	private MensaApp app;
	private Activity activity;
	private Menu menu = new Menu();
	private LayoutInflater inflater;

	public MealListAdapter(Activity activity) {
		this.activity = activity;
		app = (MensaApp) activity.getApplicationContext();
		inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public int getCount() {
		return menu.getMeals().size();
	}

	@Override
	public Object getItem(int position) {
		return menu.getMeals().get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (convertView == null) {
			view = inflater.inflate(R.layout.meal_item, null);
		}

		Meal meal = menu.getMeals().get(position);

		TextView title = (TextView) view.findViewById(R.id.meal_name);
		title.setText(meal.getName());
		//title.setText(items.get(0).getMeals().get(position).getReadableTitle());

		TextView category = (TextView) view.findViewById(R.id.meal_category);
		category.setText(meal.getCategory());
		//category.setText(items.get(0).getMeals().get(position).getCategory());

		TextView price = (TextView) view.findViewById(R.id.meal_price);
		price.setText(String.valueOf(meal.getPrice()));
		//price.setText(items.get(0).getMeals().get(position).getPrice());

		LinearLayout iconContainer = (LinearLayout) view.findViewById(R.id.meal_icon_container);

		return view;
	}

}

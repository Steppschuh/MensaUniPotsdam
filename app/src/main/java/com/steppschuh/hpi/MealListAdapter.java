package com.steppschuh.hpi;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.steppschuh.hpi.utils.DataHelper;
import com.steppschuh.hpi.utils.UiHelper;

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
		Meal meal = menu.getMeals().get(position);

		// Inflate item layout
		View view = convertView;
		if (convertView == null) {
			view = inflater.inflate(R.layout.meal_item, parent, false);
		}

		// Set item height to fill parent layout
		int itemHeight = parent.getHeight() / menu.getMeals().size();
		if (itemHeight < UiHelper.dpToPx(100)) {
			itemHeight = UiHelper.dpToPx(100);
		}

		view.getLayoutParams().height = itemHeight;
		view.requestLayout();

		// Fill item layout with menu data
		TextView title = (TextView) view.findViewById(R.id.meal_name);
		title.setText(meal.getReadableName());

		TextView category = (TextView) view.findViewById(R.id.meal_category);
		category.setText(meal.getCategory());

		TextView price = (TextView) view.findViewById(R.id.meal_price);
		String readablePrice = meal.getReadablePrice();
		if (readablePrice != null) {
			price.setText(String.valueOf(meal.getPrice()));
		} else {
			price.setVisibility(View.GONE);
		}

		// Add indicator icons to container
		LinearLayout iconContainer = (LinearLayout) view.findViewById(R.id.meal_icon_container);
		if(iconContainer.getChildCount() > 0) {
			iconContainer.removeAllViews();
		}
		for (Drawable icon: meal.getIcons(activity)) {
			iconContainer.addView(getIndicatorView(icon, activity));
		}

		return view;
	}

	private ImageView getIndicatorView(Drawable icon, Context context) {
		ImageView iconView = new ImageView(context);
		iconView.setImageDrawable(icon);

		int width = UiHelper.dpToPx(30);
		int margin = UiHelper.dpToPx(5);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
		params.setMargins(margin, margin, margin, margin);
		iconView.setLayoutParams(params);

		iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		return iconView;
	}

}

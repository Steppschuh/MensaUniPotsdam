package com.steppschuh.hpi;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steppschuh.hpi.utils.ImageHelper;
import com.steppschuh.hpi.utils.UiHelper;

import java.util.ArrayList;

public class MenuFragment extends Fragment {

	public static final int FRAGMENT_MENSA_GRIEBNITZSEE = 0;
	public static final int FRAGMENT_ULF = 1;

	private MensaApp app;
	private View fragment;
	private int mensaIndex;
	private int menuIndex;

	ListView listViewMenu;
	MealListAdapter adapter;

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragment = inflater.inflate(R.layout.fragment_menu, container, false);
		app = (MensaApp) getActivity().getApplicationContext();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mensaIndex = bundle.getInt("index", FRAGMENT_MENSA_GRIEBNITZSEE);
		} else {
			mensaIndex = FRAGMENT_MENSA_GRIEBNITZSEE;
		}

		menuIndex = ((MensaActivity) getActivity()).getCurrentMenuIndex();
		Log.d(MensaApp.TAG, "Creating menu fragment for mensa " + mensaIndex + " menu " + menuIndex);

		Handler callbackHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateMenuList();
			}
		};

		switch (mensaIndex) {
			case FRAGMENT_ULF:
				app.setCallbackHandlerUlf(callbackHandler);
				break;
			case FRAGMENT_MENSA_GRIEBNITZSEE:
				app.setCallbackHandlerGriebnitzsee(callbackHandler);
				break;
		}

		listViewMenu = (ListView) fragment.findViewById(R.id.listview_menu);
		adapter = new MealListAdapter(getActivity());
		listViewMenu.setAdapter(adapter);
		listViewMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				try {
					// Show detail view for selected meal
					View dialoglayout = generateDetailView(position);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setView(dialoglayout);
					builder.show();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		updateMenuList();

        return fragment;
    }

	private void updateMenuList() {
		try {
			// Set current meals in the menu adapter
			adapter.setMenu(app.getMensas().get(mensaIndex).getMenus().get(menuIndex));
			Log.i(MensaApp.TAG, "Menu list updated, " + String.valueOf(app.getMensas().get(mensaIndex).getMenus().get(menuIndex)).length() + " meals found");
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "Unable to update menu list, no data available yet");
			// Fill view with info meal
			Menu infoMenu = new Menu();
			Meal infoMeal = new Meal();
			infoMeal.setCategory(getString(R.string.info_meal_category));
			infoMeal.setName(getString(R.string.info_meal_name));

			ArrayList<Meal> meals = new ArrayList<Meal>();
			meals.add(infoMeal);
			infoMenu.setMeals(meals);
			adapter.setMenu(infoMenu);
		}
		adapter.notifyDataSetChanged();
	}

	private View generateDetailView(int position) {
		final Meal meal = app.getMensas().get(mensaIndex).getMenus().get(menuIndex).getMeals().get(position);

		LayoutInflater inflater = getActivity().getLayoutInflater();
		final View view = inflater.inflate(R.layout.meal_item_detail, null);

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
		for (Indicator indicator: meal.getIndicators(getActivity())) {
			iconContainer.addView(getIndicatorView(indicator, getActivity()));
		}

		// Add meal images to container
		LinearLayout foodContainer = (LinearLayout) view.findViewById(R.id.meal_image_container);
		if(foodContainer.getChildCount() > 0) {
			foodContainer.removeAllViews();
		}
		for (Drawable drawable: ImageHelper.getImages(meal.getReadableName().toLowerCase(), getActivity())) {
			foodContainer.addView(getFoodImageView(drawable, getActivity()));
		}

		RelativeLayout layoutShare = (RelativeLayout) view.findViewById(R.id.meal_share);
		layoutShare.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RelativeLayout shareLayout = (RelativeLayout) view.findViewById(R.id.meal_share);
				if (shareLayout != null) {
					shareLayout.setVisibility(View.INVISIBLE);
				}



				Meal.shareMeal(view, getActivity());
			}
		});

		return view;
	}

	private LinearLayout getIndicatorView(Indicator indicator, Context context) {
		// Icon
		ImageView iconView = new ImageView(context);
		iconView.setImageDrawable(indicator.getIcon());

		int width = UiHelper.dpToPx(30);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
		iconView.setLayoutParams(params);

		iconView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		// Text
		TextView textView = new TextView(context);
		textView.setText(indicator.getDescription());

		int margin = UiHelper.dpToPx(10);
		params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		params.setMargins(margin, 0, 0, 0);
		textView.setLayoutParams(params);
		textView.setGravity(Gravity.CENTER);
		textView.setTextColor(Color.WHITE);

		// Layout
		margin = UiHelper.dpToPx(5);
		LinearLayout layout = new LinearLayout(context);
		params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		params.setMargins(0, 0, 0, margin);
		layout.setLayoutParams(params);
		layout.setOrientation(LinearLayout.HORIZONTAL);

		layout.addView(iconView);
		layout.addView(textView);

		return layout;
	}

	private ImageView getFoodImageView(Drawable drawable, Context context) {
		ImageView iconView = new ImageView(context);
		iconView.setImageDrawable(drawable);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		params.weight = 1f;
		iconView.setLayoutParams(params);

		iconView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		return iconView;
	}
}

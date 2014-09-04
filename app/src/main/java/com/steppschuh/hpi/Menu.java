package com.steppschuh.hpi;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Menu {

	private Date date;
	private boolean closed;
	private ArrayList<Meal> meals = new ArrayList<Meal>();

	public void parseFromJson(JSONObject menuJson) {
		try {
			meals = new ArrayList<Meal>();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			date = formatter.parse(menuJson.getString("date"));

			closed = menuJson.getBoolean("closed");

			JSONArray mealsJson = menuJson.getJSONArray("meals");
			for (int i = 0; i < mealsJson.length(); i++) {
				try {
					JSONObject mealJson = (JSONObject) mealsJson.get(i);
					Meal meal = new Meal();
					meal.parseFromJson(mealJson);
					meals.add(meal);
				} catch (Exception ex) {
					Log.e(MensaApp.TAG, "Unable to parse meal JSON object");
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "Unable to parse menu JSON object");
			ex.printStackTrace();
		}
	}

	public String getReadableDate(Activity activity) {
		if (date == null) {
			return activity.getString(R.string.unkown);
		}

		// Get difference between menu date and today
		int dayToday;
		int dayMenu;
		int dayDif;
		Calendar today = Calendar.getInstance();
		dayToday = today.get(Calendar.DAY_OF_YEAR);

		Calendar meal = new GregorianCalendar();
		meal.setTime(date);
		dayMenu = meal.get(Calendar.DAY_OF_YEAR);

		// Check for simple strings
		dayDif = dayMenu - dayToday;
		switch (dayDif) {
			case 0: {
				return activity.getString(R.string.date_today);
			}
			case 1: {
				return activity.getString(R.string.date_tomorrow);
			}
			case 2: {
				return activity.getString(R.string.date_aftertomorrow);
			}
		}

		// Format date to readable string
		return meal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMANY);
	}

	/**
	 * Getter and setter
	 */
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public ArrayList<Meal> getMeals() {
		return meals;
	}

	public void setMeals(ArrayList<Meal> meals) {
		this.meals = meals;
	}
}

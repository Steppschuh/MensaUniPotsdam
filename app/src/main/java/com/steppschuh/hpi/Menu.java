package com.steppschuh.hpi;

import android.os.Handler;
import android.util.Log;

import com.steppschuh.hpi.utils.DataHelper;
import com.steppschuh.hpi.utils.NetworkHelper;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

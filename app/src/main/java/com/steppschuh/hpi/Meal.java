package com.steppschuh.hpi;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Meal {

	public static final String INDICATOR_POULTRY = "geflügel";
	public static final String INDICATOR_PORK = "schwein";
	public static final String INDICATOR_BEEF = "rind";
	public static final String INDICATOR_FISH = "fisch";
	public static final String INDICATOR_VEGAN = "vegan";
	public static final String INDICATOR_VITAL = "mensavital";
	public static final String INDICATOR_VEGETABIL = "ovo-lacto-vegetabil";


	private int id;
	private String name;
	private String category;
	private Double price;
	private ArrayList<String> notes = new ArrayList<String>();

	public void parseFromJson(JSONObject mealJson) {
		try {
			notes = new ArrayList<String>();

			id = mealJson.getInt("id");
			name = mealJson.getString("name");
			category = mealJson.getString("category");

			try {
				JSONObject pricesJson = mealJson.getJSONObject("prices");
				price = pricesJson.getDouble("students");
			} catch (Exception ex) {
				price = null;
			}

			try {
				JSONArray notesJson = mealJson.getJSONArray("notes");
				for (int i = 0; i < notesJson.length(); i++) {
					String note = notesJson.getString(i);
					notes.add(note);
				}
			} catch (Exception ex) {
				notes = new ArrayList<String>();
			}
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "Unable to parse menu JSON object");
			ex.printStackTrace();
		}
	}

	public static String removeIndicators(String oldValue) {
		String newValue = oldValue;
		newValue = newValue.replaceAll("\\([0-9]*\\)", "");
		return newValue;
	}

	/**
	 * Returns a list of icons based on the meal name & notes
	 * (indicating meat, fish, etc...)
	 */
	public ArrayList<Drawable> getIcons(Activity activity) {
		ArrayList<Drawable> images = new ArrayList<Drawable>();
		if (notes != null) {
			for (int i = 0; i < notes.size(); i++) {
				String note = notes.get(i).toLowerCase();
				if (note.contains(INDICATOR_POULTRY)) {
					images.add(activity.getResources().getDrawable(R.drawable.ic_chicken));
				} else if (note.contains(INDICATOR_PORK)) {
					images.add(activity.getResources().getDrawable(R.drawable.ic_pig));
				} else if (note.contains(INDICATOR_BEEF)) {
					images.add(activity.getResources().getDrawable(R.drawable.ic_cow));
				} else if (note.contains(INDICATOR_FISH)) {
					images.add(activity.getResources().getDrawable(R.drawable.ic_fish));
				} else if (note.contains(INDICATOR_VEGAN)) {
					images.add(activity.getResources().getDrawable(R.drawable.ic_vegetarian));
				} else if (note.contains(INDICATOR_VITAL)) {
					images.add(activity.getResources().getDrawable(R.drawable.ic_vital));
				} else if (note.contains(INDICATOR_VEGETABIL)) {
					images.add(activity.getResources().getDrawable(R.drawable.ic_vegetabil));
				}
			}
		}
		return images;
	}

	/**
	 * Getter and setter
	 */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getReadableName() {
		return removeIndicators(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Double getPrice() {
		return price;
	}

	public String getReadablePrice() {
		if (price != null) {
			String readablePrice = String.valueOf(price);
			readablePrice += "€";
			return readablePrice;
		} else {
			return null;
		}
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public ArrayList<String> getNotes() {
		return notes;
	}

	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

}

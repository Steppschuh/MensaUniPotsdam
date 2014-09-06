package com.steppschuh.hpi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;

import com.steppschuh.hpi.utils.ImageHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
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
		newValue = newValue.replaceAll("(\\([0-9]*\\))*(\\(.*\\))*", "");
		return newValue;
	}

	/**
	 * Returns a list of icons based on the meal name & notes
	 * (indicating meat, fish, etc...)
	 */
	public ArrayList<Indicator> getIndicators(Activity activity) {
		ArrayList<Indicator> indocators = new ArrayList<Indicator>();
		if (notes != null) {
			for (int i = 0; i < notes.size(); i++) {
				String note = notes.get(i).toLowerCase();
				if (note.contains(INDICATOR_POULTRY)) {
					indocators.add(new Indicator(activity.getResources().getDrawable(R.drawable.ic_chicken),
							activity.getString(R.string.indicator_poultry)));
				} else if (note.contains(INDICATOR_PORK)) {
					indocators.add(new Indicator(activity.getResources().getDrawable(R.drawable.ic_pig),
							activity.getString(R.string.indicator_pork)));
				} else if (note.contains(INDICATOR_BEEF)) {
					indocators.add(new Indicator(activity.getResources().getDrawable(R.drawable.ic_cow),
							activity.getString(R.string.indicator_beef)));
				} else if (note.contains(INDICATOR_FISH)) {
					indocators.add(new Indicator(activity.getResources().getDrawable(R.drawable.ic_fish),
							activity.getString(R.string.indicator_fish)));
				} else if (note.contains(INDICATOR_VEGAN)) {
					indocators.add(new Indicator(activity.getResources().getDrawable(R.drawable.ic_vegetarian),
							activity.getString(R.string.indicator_vegan)));
				} else if (note.contains(INDICATOR_VITAL)) {
					indocators.add(new Indicator(activity.getResources().getDrawable(R.drawable.ic_vital),
							activity.getString(R.string.indicator_vital)));
				} else if (note.contains(INDICATOR_VEGETABIL)) {
					indocators.add(new Indicator(activity.getResources().getDrawable(R.drawable.ic_vegetabil),
							activity.getString(R.string.indicator_vegetabil)));
				}
			}
		}
		//TODO: parse title if notes are null
		return indocators;
	}

	public static void shareMeal(View view, Activity activity) {
		try {
			Bitmap viewBitmap = ImageHelper.getBitmapFromView(view);
			File viewFile = ImageHelper.saveBitmapToStorage(viewBitmap);
			ImageHelper.shareFile(viewFile, activity);
		} catch (Exception ex) {
			ex.toString();
		}
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

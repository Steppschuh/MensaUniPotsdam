package com.steppschuh.hpi;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Meal {

	private int id;
	private String name;
	private String category;
	private Double price;
	private ArrayList<String> notes;

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

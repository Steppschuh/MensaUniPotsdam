package com.steppschuh.hpi;

import android.os.Handler;
import android.util.Log;

import com.steppschuh.hpi.utils.DataHelper;
import com.steppschuh.hpi.utils.NetworkHelper;

import org.apache.http.HttpResponse;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Menu {

	private Date date;
	private boolean closed;
	private ArrayList<Meal> meals;

	/**
	 * Asynchronusly requests and parses menu data from
	 * the OpenMensa API
	 */
	public void getMenu(final int id, final Handler callbackHandler) {
		if (id == -1) {
			Log.e(MensaApp.TAG, "Unable to get menu info, id is not set");
			callbackHandler.sendEmptyMessage(0);
			return;
		}

		(new Thread() {
			@Override
			public void run() {
				// Request info from API
				String url = API.getMensaMenuUrl(id);
				HttpResponse response = NetworkHelper.getHttpResponse(url);

				// Parse info from JSON response
				parseJsonResponse(DataHelper.getJsonString(response));

				// Send callback message
				callbackHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void parseJsonResponse(String response) {
		JSONObject jsonRoot = DataHelper.getJsonRoot(response);
		//TODO: parse json
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

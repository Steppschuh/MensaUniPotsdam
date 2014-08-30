package com.steppschuh.hpi;

import android.os.Handler;
import android.util.Log;

import com.steppschuh.hpi.utils.DataHelper;
import com.steppschuh.hpi.utils.NetworkHelper;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Mensa {

	private int id = -1;
	private String name;
	private String address;
	private String city;
	private String coordinates;
	private ArrayList<Menu> menus;

	/**
	 * Asynchronusly requests and parses info about itself from
	 * the OpenMensa API
	 */
	public void getInfo(final Handler callbackHandler) {
		if (id == -1) {
			Log.e(MensaApp.TAG, "Unable to get mensa info, id is not set");
			callbackHandler.sendEmptyMessage(0);
			return;
		}

		(new Thread() {
			@Override
			public void run() {
				// Request info from API
				String url = API.getMensaUrl(id);
				HttpResponse response = NetworkHelper.getHttpResponse(url);

				// Parse info from JSON response
				parseInfoResponse(DataHelper.getJsonString(response));

				// Send callback message
				callbackHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void parseInfoResponse(String response) {
		JSONObject jsonRoot = DataHelper.getJsonRoot(response);

	}

	/**
	 * Asynchronusly requests and parses menu data from
	 * the OpenMensa API
	 */
	public void getMenu(final Handler callbackHandler) {
		if (id == -1) {
			Log.e(MensaApp.TAG, "Unable to get mensa menu, id is not set");
			callbackHandler.sendEmptyMessage(0);
			return;
		}

		(new Thread() {
			@Override
			public void run() {
				// Request info from API
				String url = API.getMensaMenuUrl(id);
				HttpResponse response = NetworkHelper.getHttpResponse(url);

				// Parse menu from JSON response
				parseMenuResponse(DataHelper.getJsonString(response));

				// Send callback message
				callbackHandler.sendEmptyMessage(0);
			}
		}).start();
	}

	public void parseMenuResponse(String response) {
		try {
			menus = new ArrayList<Menu>();

			JSONArray rootJson = new JSONArray(response);
			for (int i = 0; i < rootJson.length(); i++) {
				try {
					JSONObject menuJson = (JSONObject) rootJson.get(i);
					Log.d(MensaApp.TAG, menuJson.toString());

					Menu menu = new Menu();
					menu.parseFromJson(menuJson);
					menus.add(menu);
				} catch (Exception ex) {
					Log.e(MensaApp.TAG, "Unable to parse menu JSON object");
					ex.printStackTrace();
				}
			}
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "Unable to parse menus JSON");
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public ArrayList<Menu> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<Menu> menus) {
		this.menus = menus;
	}
}

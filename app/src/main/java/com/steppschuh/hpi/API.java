package com.steppschuh.hpi;

public class API {

	public static final String URL_API = "http://openmensa.org/api/v2/";
	public static final String URL_MENSA = URL_API + "canteens/";
	public static final String ENDPOINT_MENU = "meals/";

	public static final int ID_GRIEBNITZSEE = 62;
	public static final int ID_ULF = 112;

	public static String getMensaUrl(int id) {
		String url = URL_MENSA;
		url += String.valueOf(id) + "/";
		return url;
	}

	public static String getMensaMenuUrl(int id) {
		String url = getMensaUrl(id);
		url += ENDPOINT_MENU;
		return url;
	}

}

package com.steppschuh.hpi;

import android.app.Application;

import java.util.ArrayList;

public class MensaApp extends Application {

	public static final String TAG = "mensa";

	private ArrayList<Mensa> mensas;

	public void initialize() {
		mensas = new ArrayList<Mensa>();
	}

	public void initializeAsync() {

	}

	public void restoreSettings() {

	}

	public void saveSettings() {

	}

	public ArrayList<Mensa> getMensas() {
		return mensas;
	}

	public void setMensas(ArrayList<Mensa> mensas) {
		this.mensas = mensas;
	}
}

package com.steppschuh.hpi;

import android.app.Application;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

public class MensaApp extends Application {

	public static final String TAG = "mensa";

	private ArrayList<Mensa> mensas;

	public void initialize() {
		Log.d(TAG, "Initializing app");

		mensas = new ArrayList<Mensa>();
		loadDefaultMensas();

		// invoke async initialization
		(new Thread() {
			@Override
			public void run() {
				initializeAsync();
			}
		}).start();
	}

	public void initializeAsync() {
		Log.d(TAG, "Initializing app async");

	}

	public void restoreSettings() {

	}

	public void saveSettings() {

	}

	public void loadDefaultMensas() {
		Handler callbackHandlerGriebnitzsee = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.d(TAG, "callbackHandlerGriebnitzsee");
			}
		};
		Mensa mensaGriebnitzsee = new Mensa();
		mensaGriebnitzsee.setId(API.ID_GRIEBNITZSEE);
		mensaGriebnitzsee.getInfo(callbackHandlerGriebnitzsee);
		mensaGriebnitzsee.getMenu(callbackHandlerGriebnitzsee);


		Handler callbackHandlerUlf = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.d(TAG, "callbackHandlerUlf");
			}
		};
		Mensa mensaUlf = new Mensa();
		mensaUlf.setId(API.ID_ULF);
		//mensaUlf.getInfo(callbackHandlerUlf);
		//mensaUlf.getMenu(callbackHandlerUlf);
	}

	public ArrayList<Mensa> getMensas() {
		return mensas;
	}

	public void setMensas(ArrayList<Mensa> mensas) {
		this.mensas = mensas;
	}
}

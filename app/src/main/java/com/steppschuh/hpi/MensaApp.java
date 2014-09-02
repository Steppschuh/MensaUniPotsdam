package com.steppschuh.hpi;

import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.steppschuh.hpi.utils.DataHelper;

import java.util.ArrayList;

public class MensaApp extends Application {

	public static final String TAG = "mensa";

	private ArrayList<Mensa> mensas;
	private Handler callbackHandlerSplash;
	private Handler callbackHandlerGriebnitzsee;
	private Handler callbackHandlerUlf;

	private Boolean initialized = false;

	public void initialize(final Handler callbackHandler) {
		Log.d(TAG, "Initializing app");
		initialized = false;

		callbackHandlerSplash = callbackHandler;
		DataHelper.sendMessage(callbackHandlerSplash, SplashActivity.KEY_MESSAGE, getString(R.string.initializing));

		Handler defaultCallBackHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.d(TAG, "defaultCallBackHandler received a message");

				Bundle data = msg.getData();
				if (data != null) {
					Message msgForward = new Message();
					msgForward.setData(data);
					callbackHandlerSplash.sendMessage(msgForward);
				}

				switch (msg.what) {
					case API.ID_ULF:
						callbackHandlerUlf.sendEmptyMessage(0);
						break;
					case API.ID_GRIEBNITZSEE:
						DataHelper.sendMessage(callbackHandlerSplash, SplashActivity.KEY_MESSAGE, SplashActivity.MESSAGE_INITIALIZED);
						callbackHandlerGriebnitzsee.sendEmptyMessage(0);
						break;
				}
			}
		};

		callbackHandlerGriebnitzsee = defaultCallBackHandler;
		callbackHandlerUlf = defaultCallBackHandler;

		mensas = new ArrayList<Mensa>();

		// invoke async initialization
		(new Thread() {
			@Override
			public void run() {
				initializeAsync();
			}
		}).start();

		initialized = true;
	}

	public void initializeAsync() {
		Log.d(TAG, "Initializing app async");

	}

	public void restoreSettings() {

	}

	public void saveSettings() {

	}

	public void loadDefaultMensas() {
		Log.d(TAG, "Loading default mensas");
		DataHelper.sendMessage(callbackHandlerSplash, SplashActivity.KEY_MESSAGE, getString(R.string.loading_menu));

		Mensa mensaGriebnitzsee = new Mensa();
		mensaGriebnitzsee.setId(API.ID_GRIEBNITZSEE);
		//mensaGriebnitzsee.getInfo(callbackHandlerGriebnitzsee);
		mensaGriebnitzsee.getMenu(callbackHandlerGriebnitzsee);
		mensas.add(mensaGriebnitzsee);


		Mensa mensaUlf = new Mensa();
		mensaUlf.setId(API.ID_ULF);
		//mensaUlf.getInfo(callbackHandlerUlf);
		mensaUlf.getMenu(callbackHandlerUlf);
		mensas.add(mensaUlf);
	}

	public ArrayList<Mensa> getMensas() {
		return mensas;
	}

	public void setMensas(ArrayList<Mensa> mensas) {
		this.mensas = mensas;
	}

	public Handler getCallbackHandlerGriebnitzsee() {
		return callbackHandlerGriebnitzsee;
	}

	public void setCallbackHandlerGriebnitzsee(Handler callbackHandlerGriebnitzsee) {
		this.callbackHandlerGriebnitzsee = callbackHandlerGriebnitzsee;
	}

	public Handler getCallbackHandlerUlf() {
		return callbackHandlerUlf;
	}

	public void setCallbackHandlerUlf(Handler callbackHandlerUlf) {
		this.callbackHandlerUlf = callbackHandlerUlf;
	}

	public Boolean isInitialized() {
		return initialized;
	}

}

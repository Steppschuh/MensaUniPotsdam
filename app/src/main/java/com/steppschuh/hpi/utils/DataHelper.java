package com.steppschuh.hpi.utils;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.steppschuh.hpi.MensaApp;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

public class DataHelper {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
	public static final String PREF_FILE_NAME = "mensa_prefs";

	public static String getDeviceName() {
		return android.os.Build.MODEL;
	}

	public static String getDeviceOs() {
		return "Android " + android.os.Build.VERSION.RELEASE;
	}

	public static String getAppVersion(Application application) {
		try {
			return application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			return "n.a.";
		}
	}

	public static int getAppVersionCode(Application application) {
		try {
			return application.getPackageManager().getPackageInfo(application.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			return 0;
		}
	}

	public static String inputStreamToString(InputStream stream) throws IOException {
		BufferedReader r = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line);
		}
		return new String(total);
	}


	public static JSONObject getJsonRoot(String response) {
		JSONObject responseJson = null;
		try {
			responseJson = new JSONObject(response);
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "Unable to get Json Object from response");
			ex.printStackTrace();
		}
		return responseJson;
	}

	public static JSONObject getJsonRoot(HttpResponse response) {
		return getJsonRoot(getJsonString(response));
	}

	public static String getJsonString(HttpResponse response) {
		String responseString = null;
		try {
			responseString = EntityUtils.toString(response.getEntity());
			//Log.d(MensaApp.TAG, "Response: " + responseString);
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "Unable to get Json String from response");
			ex.printStackTrace();
		}
		return responseString;
	}

	public static String urlEncodeUTF8(String s) {
		try {
			return URLEncoder.encode(s, UTF8_CHARSET.name());
		} catch (UnsupportedEncodingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public static String urlEncodeUTF8(Map<?,?> map) {
		if (map.size() < 1) {
			return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append("?");
		for (Map.Entry<?,?> entry : map.entrySet()) {
			if (sb.length() > 0) {
				sb.append("&");
			}
			sb.append(String.format("%s=%s",
					urlEncodeUTF8(entry.getKey().toString()),
					urlEncodeUTF8(entry.getValue().toString())
			));
		}
		return sb.toString();
	}

	public static void sendMessage(Handler mHandler, String key, String value) {
		Log.d(MensaApp.TAG, "Sending message to handler: [" + key + "] " + value);
		Bundle data = new Bundle();
		data.putString(key, value);
		Message message = new Message();
		message.setData(data);
		mHandler.sendMessage(message);
	}

	public static void sendMessage(Handler mHandler, String key, int value) {
		Log.d(MensaApp.TAG, "Sending message to handler: [" + key + "] " + String.valueOf(value));
		Bundle data = new Bundle();
		data.putInt(key, value);
		Message message = new Message();
		message.setData(data);
		mHandler.sendMessage(message);
	}

}

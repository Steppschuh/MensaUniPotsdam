package com.steppschuh.hpi.utils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.steppschuh.hpi.API;
import com.steppschuh.hpi.Mensa;
import com.steppschuh.hpi.MensaApp;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class NetworkHelper {

	/**
	 * HTTP request types
	 */
	public static final int POST_TYPE   = 1;
	public static final int GET_TYPE    = 2;
	public static final int PUT_TYPE    = 3;
	public static final int DELETE_TYPE = 4;

	/**
	 * HTTP request header constants
	 */
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String CONTENT_ENCODING = "Content-Encoding";
	public static final String MIME_FORM_ENCODED = "application/x-www-form-urlencoded";
	public static final String MIME_TEXT_PLAIN = "text/plain";


	/**
	 * Methods for getting a HTTP response with custom headers and params
	 */
	public static void getHttpResponse(final String url, Handler handler) {
		final Handler mHandler = handler;
		(new Thread() {
			@Override
			public void run() {
				try {
					HttpResponse response = getHttpResponse(url);
					String result = DataHelper.getJsonString(response);
					DataHelper.sendMessage(mHandler, "result", result);
				} catch (Exception ex) {
					mHandler.sendEmptyMessage(0);
				}
			}
		}).start();
	}

	public static HttpResponse getHttpResponse(final String url) {
		Map<String, String> params = new HashMap<String, String>();
		return getHttpResponse(url, params);
	}

	public static HttpResponse getHttpResponse(final String url, final Map<String, String> params) {
		Map<String, String> headers = new HashMap<String, String>();
		return getHttpResponse(MIME_TEXT_PLAIN, url, headers, params, GET_TYPE);
	}

	public static HttpResponse getHttpResponse(final String url, final Map<String, String> headers, final Map<String, String> params) {
		return getHttpResponse(MIME_TEXT_PLAIN, url, headers, params, GET_TYPE);
	}

	public static HttpResponse getHttpResponse(final String contentType, final String url, final Map<String, String> headers, final Map<String, String> params, final int requestType) {
		HttpResponse response = null;
		try {
			DefaultHttpClient client = new DefaultHttpClient();

			final Map<String, String> sendHeaders = new HashMap<String, String>();
			if ((headers != null) && (headers.size() > 0)) {
				sendHeaders.putAll(headers);
			}

			if (requestType == POST_TYPE || requestType == PUT_TYPE ) {
				sendHeaders.put(CONTENT_TYPE, contentType);
			}

			if (sendHeaders.size() > 0) {
				client.addRequestInterceptor(new HttpRequestInterceptor() {
					public void process(final HttpRequest request, final HttpContext context) throws HttpException,
							IOException {
						for (String key : sendHeaders.keySet()) {
							if (!request.containsHeader(key)) {
								request.addHeader(key, sendHeaders.get(key));
							}
						}
					}
				});
			}

			String requestUrl = url + DataHelper.urlEncodeUTF8(params);

			HttpGet request = new HttpGet();
			request.setURI(URI.create(requestUrl));

			Log.d(MensaApp.TAG, "Requesting URL: " + requestUrl);
			response = client.execute(request);
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "HTTP request failed");
			ex.printStackTrace();
		}
		return response;
	}

}

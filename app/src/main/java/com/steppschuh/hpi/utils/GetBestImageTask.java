package com.steppschuh.hpi.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class GetBestImageTask extends AsyncTask<String, Void, Drawable> {

	private final WeakReference<ImageView> imageViewReference;
	private Activity activity;

	public GetBestImageTask(ImageView imageView, Activity activity) {
		// Use a WeakReference to ensure the ImageView can be garbage collected
		imageViewReference = new WeakReference<ImageView>(imageView);
		this.activity = activity;
	}

	// Decode image in background.
	@Override
	protected Drawable doInBackground(String... params) {
		return ImageHelper.getBestImage(params[0], activity);
	}

	// Once complete, see if ImageView is still around and set bitmap.
	@Override
	protected void onPostExecute(Drawable drawable) {
		if (imageViewReference != null && drawable != null) {
			final ImageView imageView = imageViewReference.get();
			if (imageView != null) {
				imageView.setAlpha(0f);
				imageView.setImageDrawable(drawable);
				UiHelper.fadeInView(imageView, 0.01f);
			}
		}
	}
}

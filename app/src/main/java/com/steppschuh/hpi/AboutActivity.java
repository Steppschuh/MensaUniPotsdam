package com.steppschuh.hpi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

		/*
		ImageView btnDevMail = (ImageView) findViewById(R.id.about_dev_mail);
		ImageView btnDevWeb = (ImageView) findViewById(R.id.about_dev_web);
		ImageView btnDataWeb = (ImageView) findViewById(R.id.about_data_web);
		ImageView btnIconsWeb = (ImageView) findViewById(R.id.about_icons_web);
		*/
	}

	public void onClick(View view) {
		if (view.getId() == R.id.about_dev_mail) {
			Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","contact@steppschuh.net", null));
			startActivity(Intent.createChooser(emailIntent, "E-Mail schreiben"));
		} else if (view.getId() == R.id.about_dev_web) {
			openWebsite(getString(R.string.about_dev_web));
		} else if (view.getId() == R.id.about_data_web) {
			openWebsite(getString(R.string.about_data_web));
		} else if (view.getId() == R.id.about_icons_web) {
			openWebsite(getString(R.string.about_icons_web));
		}
	}

	private void openWebsite(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

}

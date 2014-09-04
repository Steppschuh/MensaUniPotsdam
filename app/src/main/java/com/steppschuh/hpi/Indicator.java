package com.steppschuh.hpi;

import android.graphics.drawable.Drawable;

/**
 * Indicator is a pair of icon and description for a meal
 */
public class Indicator {

	private Drawable icon;
	private String description;

	public Indicator() {

	}

	public Indicator(Drawable icon, String description) {
		this.icon = icon;
		this.description = description;
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

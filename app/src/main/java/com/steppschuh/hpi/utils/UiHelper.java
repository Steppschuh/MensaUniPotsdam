package com.steppschuh.hpi.utils;

import android.content.res.Resources;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

public class UiHelper {

	public static int dpToPx(int dp) {
		return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
	}

	public static int pxToDp(int px) {
		return (int) (px / Resources.getSystem().getDisplayMetrics().density);
	}

	public static void expandView(final View v) {
		v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		final int targtetHeight = v.getMeasuredHeight();

		v.getLayoutParams().height = 0;
		v.setVisibility(View.VISIBLE);
		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				v.getLayoutParams().height = interpolatedTime == 1
						? ViewGroup.LayoutParams.WRAP_CONTENT
						: (int)(targtetHeight * interpolatedTime);
				v.requestLayout();
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int)(targtetHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public static void collapseView(final View v) {
		final int initialHeight = v.getMeasuredHeight();

		Animation a = new Animation()
		{
			@Override
			protected void applyTransformation(float interpolatedTime, Transformation t) {
				if(interpolatedTime == 1){
					v.setVisibility(View.GONE);
				}else{
					v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
					v.requestLayout();
				}
			}

			@Override
			public boolean willChangeBounds() {
				return true;
			}
		};

		// 1dp/ms
		a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
		v.startAnimation(a);
	}

	public static void turnViewUp(final View v) {
		Animation animation = new RotateAnimation(180.0f, 0.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(200);
		animation.setFillAfter(true);
		v.startAnimation(animation);
	}

	public static void turnViewDown(final View v) {
		Animation animation = new RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animation.setDuration(200);
		animation.setFillAfter(true);
		v.startAnimation(animation);
	}

	public static void fadeInView(final View v, final float step) {
		final Handler handler = new Handler();
		(new Thread() {
			@Override
			public void run() {
				for (int i = 0; i <= 1000; i++) {
					final float new_opacity = v.getAlpha() + step;
					if (new_opacity > 1) {
						return;
					}
					handler.post(new Runnable() {
						public void run() {
							try {
								v.setAlpha(new_opacity);
							} catch (Exception ex) {
							}
						}
					});
					try {
						sleep(5);
					} catch (Exception ex) {
						break;
					}
				}
			}
		}).start();
	}

	public static void fadeOutView(final View v, final float step) {
		final Handler handler = new Handler();
		(new Thread() {
			@Override
			public void run() {
				for (int i = 0; i <= 1000; i++) {
					final float new_opacity = v.getAlpha() - step;
					if (new_opacity < 0) {
						return;
					}
					handler.post(new Runnable() {
						public void run() {
							try {
								v.setAlpha(new_opacity);
							} catch (Exception ex) {
							}
						}
					});
					try {
						sleep(5);
					} catch (Exception ex) {
						break;
					}
				}
			}
		}).start();
	}
}

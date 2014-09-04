package com.steppschuh.hpi;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.steppschuh.hpi.utils.UiHelper;


public class MensaActivity extends FragmentActivity {

	private MensaApp app;
	private int currentMenuIndex = 0;
	private int currentMensaIndex = MenuFragment.FRAGMENT_MENSA_GRIEBNITZSEE;

	ViewPager mViewPager;
	RelativeLayout daySelector;
	RelativeLayout dayPrevious;
	RelativeLayout dayNext;
	TextView dayText;

	MenuCollectionPagerAdapter mMenuCollectionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensa);

		prepareUi();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	private void prepareUi() {
		// View pager and title strip
		mMenuCollectionPagerAdapter = new MenuCollectionPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.viewpager_mensas);
		mViewPager.setAdapter(mMenuCollectionPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int i, float v, int i2) {
				Log.d(MensaApp.TAG, "onPageScrollStateChanged " + i);
			}

			@Override
			public void onPageSelected(int i) {
				Log.d(MensaApp.TAG, "onPageSelected " + i);
			}

			@Override
			public void onPageScrollStateChanged(int i) {
				//Log.d(MensaApp.TAG, "onPageSelected " + i);
				if (i != currentMenuIndex) {
					currentMensaIndex = i;
					Log.d(MensaApp.TAG, "onPageSelected " + i);
				}
			}
		});

		PagerTitleStrip titleStrip = (PagerTitleStrip) findViewById(R.id.viewpager_mensa_title);
		titleStrip.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
		titleStrip.setBackgroundColor(getResources().getColor(R.color.green_light));
		titleStrip.setTextColor(Color.WHITE);
		int padding = UiHelper.dpToPx(3);
		titleStrip.setPadding(0, padding, 0, padding);

		// Day selector
		daySelector = (RelativeLayout) findViewById(R.id.menu_bar);
		dayNext = (RelativeLayout) findViewById(R.id.menu_bar_right);
		dayPrevious = (RelativeLayout) findViewById(R.id.menu_bar_left);
		dayText = (TextView) findViewById(R.id.menu_bar_text);

		dayNext.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showNextDayMenu(currentMensaIndex);
			}
		});
	}

	private boolean hasNextDayMenu(int mensaIndex) {
		int nextMenuIndex = currentMenuIndex + 1;
		try {
			if (app.getMensas().get(mensaIndex).getMenus().get(nextMenuIndex) != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	private boolean hasPreviousDayMenu(int mensaIndex) {
		int nextMenuIndex = currentMenuIndex - 1;
		try {
			if (app.getMensas().get(mensaIndex).getMenus().get(nextMenuIndex) != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
	}

	private void showNextDayMenu(int mensaIndex) {
		if (!hasNextDayMenu(mensaIndex)) {
			return;
		}

		mMenuCollectionPagerAdapter.notifyDataSetChanged();
	}

	/**
	 * Adapter for the viewpager
	 * holds the menu for Uni Potsdam and Ulf's Cafe
	 */
	public class MenuCollectionPagerAdapter extends FragmentStatePagerAdapter {

		public MenuCollectionPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public android.support.v4.app.Fragment getItem(int position) {
			android.support.v4.app.Fragment fragment= null;

			switch (position) {
				case MenuFragment.FRAGMENT_MENSA_GRIEBNITZSEE:
					fragment = new MenuFragment();
					break;
				case MenuFragment.FRAGMENT_ULF:
					fragment = new MenuFragment();
					break;
				default:
					fragment = null;
					break;
			}
			Bundle bundle = new Bundle();
			bundle.putInt("index", position);
			fragment.setArguments(bundle);
			return fragment;
		}

		@Override
		public int getCount() {
			return 2;
		}

		public int getItemPosition(Object object) {
			// Causes adapter to reload all Fragments when
			// notifyDataSetChanged is called
			return POSITION_NONE;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String title;
			switch (position) {
				case MenuFragment.FRAGMENT_ULF:
					title = getString(R.string.mensa_ulf);
					break;
				case MenuFragment.FRAGMENT_MENSA_GRIEBNITZSEE:
					title = getString(R.string.mensa_potsdam);
					break;
				default:
					title = getString(R.string.app_name);
			}
			return title;
		}
	}
}

package com.steppschuh.hpi;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class StartActivity extends FragmentActivity {

	private MensaApp app;

	ViewPager mViewPager;
	MenuCollectionPagerAdapter mMenuCollectionPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

		app = (MensaApp) getApplicationContext();
		app.initialize();

		mMenuCollectionPagerAdapter = new MenuCollectionPagerAdapter(getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.viewpager_mensas);
		mViewPager.setAdapter(mMenuCollectionPagerAdapter);
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

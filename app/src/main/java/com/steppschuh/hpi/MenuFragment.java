package com.steppschuh.hpi;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MenuFragment extends Fragment {

	public static final int FRAGMENT_MENSA_GRIEBNITZSEE = 0;
	public static final int FRAGMENT_ULF = 1;

	private MensaApp app;
	private View fragment;
	private int mensaIndex;
	private int mensaIndex;

	ListView listViewMenu;
	MealListAdapter adapter;

    public MenuFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		fragment = inflater.inflate(R.layout.fragment_menu, container, false);
		app = (MensaApp) getActivity().getApplicationContext();

		Bundle bundle = this.getArguments();
		if (bundle != null) {
			mensaIndex = bundle.getInt("index", FRAGMENT_MENSA_GRIEBNITZSEE);
		} else {
			mensaIndex = FRAGMENT_MENSA_GRIEBNITZSEE;
		}



		Handler callbackHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				updateMenuList();
			}
		};

		switch (mensaIndex) {
			case FRAGMENT_ULF:
				app.setCallbackHandlerUlf(callbackHandler);
				break;
			case FRAGMENT_MENSA_GRIEBNITZSEE:
				app.setCallbackHandlerGriebnitzsee(callbackHandler);
				break;
		}

		listViewMenu = (ListView) fragment.findViewById(R.id.listview_menu);
		adapter = new MealListAdapter(getActivity());
		listViewMenu.setAdapter(adapter);

		updateMenuList();

        return fragment;
    }

	private void updateMenuList() {
		try {
			adapter.setMenu(app.getMensas().get(mensaIndex).getMenus().get(0));
			adapter.notifyDataSetChanged();
			Log.i(MensaApp.TAG, "Menu list updated, " + String.valueOf(app.getMensas().get(mensaIndex).getMenus().get(0)).length() + " meals found");
		} catch (Exception ex) {
			Log.e(MensaApp.TAG, "Unable to update menu list, no data available yet");
		}
	}
}

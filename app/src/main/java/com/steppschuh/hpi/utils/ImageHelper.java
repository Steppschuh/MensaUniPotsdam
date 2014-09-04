package com.steppschuh.hpi.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.steppschuh.hpi.R;

import java.util.ArrayList;

public class ImageHelper {

	public static Drawable getBestImage(String title, Activity activity) {
		return getImages(title, activity).get(0);
	}

	public static ArrayList<Drawable> getImages(String title, Activity activity) {
		ArrayList<Drawable> mainImages = getMainImages(title, activity);
		ArrayList<Drawable> subImages = getSubImages(title, activity);

		ArrayList<Drawable> allImages = new ArrayList<Drawable>();
		allImages.addAll(mainImages);
		allImages.addAll(subImages);

		if (allImages.size() < 1) {
			//TODO: Image
			Drawable defualtImage =  activity.getResources().getDrawable(R.drawable.ic_logo_green);
			allImages.add(defualtImage);
		}

		return allImages;
	}

	public static ArrayList<Drawable> getMainImages(String title, Activity activity) {
		ArrayList<Drawable> images = new ArrayList<Drawable>();

		if (title.contains("fisch")) {
			if (title.contains("fischstäbchen")) {
				images.add(getDrawableById(R.drawable.food_fischstaebchen, activity));
			} else if (title.contains("fischfilet")) {
				images.add(getDrawableById(R.drawable.food_fischfilet, activity));
			} else {
				//TODO: Image
				images.add(getDrawableById(R.drawable.food_fischfilet, activity));
			}
		}

		if (title.contains("pute")) {
			//TODO: Images
			if (title.contains("putenbrustgeschnetzeltes")) {
				images.add(getDrawableById(R.drawable.food_geschnaetzeltes, activity));
			} else if (title.contains("putenbrust")) {
				images.add(getDrawableById(R.drawable.food_haehnchenbrustfilet, activity));
			} else if (title.contains("putenfilet")) {
				images.add(getDrawableById(R.drawable.food_haehnchenbrustfilet, activity));
			} else {
				images.add(getDrawableById(R.drawable.food_haehnchenbrustfilet, activity));
			}
		}

		if (title.contains("fleisch")) {
			if (title.contains("schweinefleich")) {
				images.add(getDrawableById(R.drawable.food_schweinefleich, activity));
			} else {
				//TODO: Image
				images.add(getDrawableById(R.drawable.food_schweinefleich, activity));
			}
		}

		if (title.contains("hähnchen")) {
			if (title.contains("hähnchenbrust")) {
				images.add(getDrawableById(R.drawable.food_haehnchenbrustfilet, activity));
			} else {
				//TODO: Image
				images.add(getDrawableById(R.drawable.food_haehnchenbrustfilet, activity));
			}
		}

		if (title.contains("seelachs"))
			images.add(getDrawableById(R.drawable.food_seelachs, activity));
		if (title.contains("frikadelle") || title.contains("boulette"))
			images.add(getDrawableById(R.drawable.food_frikadelle, activity));
		if (title.contains("frühlingsrolle"))
			images.add(getDrawableById(R.drawable.food_fruehlingsrollen, activity));
		if (title.contains("sülze"))
			images.add(getDrawableById(R.drawable.food_suelze, activity));
		if (title.contains("schnitzel"))
			images.add(getDrawableById(R.drawable.food_schnitzel, activity));
		if (title.contains("eierkuchen"))
			images.add(getDrawableById(R.drawable.food_eierkuchen, activity));
		if (title.contains("kartoffelpuffer"))
			images.add(getDrawableById(R.drawable.food_kartoffelpuffer, activity));
		if (title.contains("chili con carne"))
			images.add(getDrawableById(R.drawable.food_chiliconcarne, activity));
		if (title.contains("tofu"))
			images.add(getDrawableById(R.drawable.food_tofu, activity));
		if (title.contains("spaghetti"))
			images.add(getDrawableById(R.drawable.food_spaghetti, activity));
		if (title.contains("steak"))
			images.add(getDrawableById(R.drawable.food_steak, activity));
		if (title.contains("auflauf"))
			images.add(getDrawableById(R.drawable.food_auflauf, activity));

		//TODO: Image
		if (title.contains("penne rigate"))
			images.add(getDrawableById(R.drawable.food_rahmgeschnaetzeltes, activity));
		if (title.contains("hefeklöße"))
			images.add(getDrawableById(R.drawable.food_hefekloesse, activity));
		if (title.contains("ofengemüse"))
			images.add(getDrawableById(R.drawable.food_ofengemuese, activity));
		if (title.contains("jagdwurst"))
			images.add(getDrawableById(R.drawable.food_jagdwurst, activity));
		if (title.contains("gyros"))
			images.add(getDrawableById(R.drawable.food_gyros, activity));
		if (title.contains("tzatziki"))
			images.add(getDrawableById(R.drawable.food_tzatziki, activity));
		if (title.contains("lasagne"))
			images.add(getDrawableById(R.drawable.food_lasagne, activity));

		//TODO: Image
		if (title.contains("brathering"))
			images.add(getDrawableById(R.drawable.food_fischfilet, activity));
		if (title.contains("rahmgeschnetzeltes"))
			images.add(getDrawableById(R.drawable.food_rahmgeschnaetzeltes, activity));
		if (title.contains("milchreis"))
			images.add(getDrawableById(R.drawable.food_milchreis, activity));
		if (title.contains("quark"))
			images.add(getDrawableById(R.drawable.food_quark2, activity));
		if (title.contains("burger"))
			images.add(getDrawableById(R.drawable.food_burger, activity));
		if (title.contains("currywurst"))
			images.add(getDrawableById(R.drawable.food_currywurst, activity));
		if (title.contains("soljanka"))
			images.add(getDrawableById(R.drawable.food_soljanka, activity));
		if (title.contains("rindergeschnetzeltes"))
			images.add(getDrawableById(R.drawable.food_geschnaetzeltes, activity));
		if (title.contains("cordon bleu"))
			images.add(getDrawableById(R.drawable.food_cordonbleu, activity));
		if (title.contains("braten"))
			images.add(getDrawableById(R.drawable.food_braten, activity));
		if (title.contains("paprika"))
			images.add(getDrawableById(R.drawable.food_paprika, activity));
		if (title.contains("rührei"))
			images.add(getDrawableById(R.drawable.food_ruehrei, activity));
		if (title.contains("gnocchi"))
			images.add(getDrawableById(R.drawable.food_gnocchi, activity));

		return images;
	}

	public static ArrayList<Drawable> getSubImages(String title, Activity activity) {
		ArrayList<Drawable> images = new ArrayList<Drawable>();

		if (title.contains("kartoffel")) {
			//TODO: Images
			if (title.contains("bratkartoffeln")) {
				images.add(getDrawableById(R.drawable.food_bratkartoffeln, activity));
			} else if (title.contains("kartoffelpüree")) {
				images.add(getDrawableById(R.drawable.food_kartoffelbrei, activity));
			} else if (title.contains("salzkartoffeln")) {
				images.add(getDrawableById(R.drawable.food_salzkartoffeln, activity));
			} else if (title.contains("herzoginkartoffeln")) {
				images.add(getDrawableById(R.drawable.food_herzoginkartoffeln, activity));
			} else if (title.contains("kartoffelgratin")) {
				//TODO: Image
				images.add(getDrawableById(R.drawable.food_auflauf, activity));
			} else {
				images.add(getDrawableById(R.drawable.food_kartoffeln, activity));
			}
		}

		if (title.contains("nudel")) {
			if (title.contains("bandnudeln")) {
				images.add(getDrawableById(R.drawable.food_bandnudeln, activity));
			} else if (title.contains("schupfnudeln")) {
				images.add(getDrawableById(R.drawable.food_schupfnudeln, activity));
			} else {
				images.add(getDrawableById(R.drawable.food_spirelli, activity));
			}
		}

		if (title.contains("gemüse"))
			images.add(getDrawableById(R.drawable.food_buttergemuese, activity));
		if (title.contains("pommes"))
			images.add(getDrawableById(R.drawable.food_pommes, activity));
		if (title.contains("erbsen"))
			images.add(getDrawableById(R.drawable.food_erbsen, activity));
		if (title.contains("salat"))
			images.add(getDrawableById(R.drawable.food_salat, activity));
		if (title.contains("reis"))
			images.add(getDrawableById(R.drawable.food_reis, activity));
		if (title.contains("brot"))
			images.add(getDrawableById(R.drawable.food_brot, activity));
		if (title.contains("tomaten"))
			images.add(getDrawableById(R.drawable.food_tomaten, activity));
		if (title.contains("brokolli"))
			images.add(getDrawableById(R.drawable.food_brokkoli, activity));
		if (title.contains("blumenkohl"))
			images.add(getDrawableById(R.drawable.food_blumenkohl, activity));
		if (title.contains("kroketten"))
			images.add(getDrawableById(R.drawable.food_kroketten, activity));
		if (title.contains("spirelli"))
			images.add(getDrawableById(R.drawable.food_spirelli, activity));
		if (title.contains("suppe"))
			images.add(getDrawableById(R.drawable.food_suppe, activity));
		if (title.contains("käse"))
			images.add(getDrawableById(R.drawable.food_reibekaese, activity));
		if (title.contains("champignon"))
			images.add(getDrawableById(R.drawable.food_champignon, activity));
		if (title.contains("sauerkraut"))
			images.add(getDrawableById(R.drawable.food_sauerkraut, activity));
		if (title.contains("spätzle"))
			images.add(getDrawableById(R.drawable.food_spaetzle, activity));
		if (title.contains("tortellini"))
			images.add(getDrawableById(R.drawable.food_tortellini, activity));
		if (title.contains("wedges"))
			images.add(getDrawableById(R.drawable.food_wedges, activity));
		if (title.contains("zucchini"))
			images.add(getDrawableById(R.drawable.food_zucchini, activity));

		return images;
	}

	public static Drawable getDrawableById(int id, Activity activity) {
		return activity.getResources().getDrawable(id);
	}


}
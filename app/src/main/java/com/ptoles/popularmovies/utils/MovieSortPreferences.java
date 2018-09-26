package com.ptoles.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ptoles.popularmovies.R;

public class MovieSortPreferences {

    static SharedPreferences sharedPreferences;
    static String sortOrderKey;
    static String defaultSortOrder;
    static String currentSortOrder;

    Context context;

    public static String getPreferredSortOrder(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sortOrderKey = context.getString(R.string.sort_order_key);
        defaultSortOrder = CONSTANTS.DEFAULT_SORT_ORDER_KEY;
        return sharedPreferences.getString(sortOrderKey, defaultSortOrder);
    }

    public static void setCurrentSortOrder(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentSortOrder", currentSortOrder);
        editor.apply();
    }

    public static void setPreferredSortOrder(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentSortOrder", defaultSortOrder);
        editor.putString(sortOrderKey, defaultSortOrder);

        editor.apply();
    }

}

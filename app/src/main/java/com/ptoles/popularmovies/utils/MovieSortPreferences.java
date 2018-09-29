package com.ptoles.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ptoles.popularmovies.R;

public class MovieSortPreferences {

    static SharedPreferences sharedPreferences;
    static String sortOrderKey;
    static String orderByKey;
    static String defaultSortOrder;
    static String currentSortOrder;

    Context context;

    public static String getPreferredSortOrder(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        //TODO: Which to use: sortOrderKey or orderByKey?
        //sortOrderKey = context.getString(R.string.sort_order_key);
        orderByKey = CONSTANTS.DEFAULT_ORDER_BY_KEY;
        //TODO: This is where the failure happens. We always but alway pass defaultSortOrder
        // back to MainActivity
        //defaultSortOrder = CONSTANTS.DEFAULT_SORT_ORDER_KEY;
      //  defaultSortOrder = sharedPreferences.getString();
        defaultSortOrder = CONSTANTS.DEFAULT_ORDER_BY_KEY;

        return sharedPreferences.getString(orderByKey, defaultSortOrder);
    }

    public static void setCurrentSortOrder(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentSortOrder", currentSortOrder);
        editor.apply();
    }

 /*   public static void setPreferredSortOrder(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("CurrentSortOrder", defaultSortOrder);
        editor.putString(sortOrderKey, defaultSortOrder);

        editor.apply();
    }
*/
}

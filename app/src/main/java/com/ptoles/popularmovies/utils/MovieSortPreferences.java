package com.ptoles.popularmovies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.ptoles.popularmovies.R;

public class MovieSortPreferences {


    public static String getPreferredSortOrder(Context context) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String sortOrderKey = context.getString(R.string.sort_order_key);

        String defaultSortOrder = context.getString(R.string.pref_most_popular_key);

        return sharedPreferences.getString(sortOrderKey, defaultSortOrder);
    }
}

package com.ptoles.popularmovies;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ptoles.popularmovies.utils.MovieSortPreferences;

import static com.ptoles.popularmovies.utils.CONSTANTS.DEFAULT_ORDER_BY_KEY;
import static com.ptoles.popularmovies.utils.CONSTANTS.KEY_DEFAULT_SORT_ORDER_STATE;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);
    }



    public static class moviePreferenceFragments extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            //TODO: Store this info in  SHAREDPREFERENCES or in a  BUNDLE or SOMEWHERE!!!
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            setPreferencesOnChangeListener(orderBy);
        }


        @Override
        public boolean onPreferenceChange(Preference currentSettingsItem, Object currentSettingsObject) {

            String currentSettingsItemLabel = currentSettingsObject.toString();
            if (currentSettingsItem instanceof ListPreference) {
                ListPreference listOfSortPreferences = (ListPreference) currentSettingsItem;
                int chosenSettingsIndex = listOfSortPreferences.findIndexOfValue(currentSettingsItemLabel);
                if (chosenSettingsIndex >= 0) {
                    CharSequence[] currentSettingsItemLabels = listOfSortPreferences.getEntries();
                    //load the labels into an array
                    currentSettingsItem.setSummary(currentSettingsItemLabels[chosenSettingsIndex]);
                }
            } else {
                currentSettingsItem.setSummary(currentSettingsItemLabel);
            }
            return true;
        }
        private void setPreferencesOnChangeListener(Preference preference) {

            preference.setOnPreferenceChangeListener(this);

                    String preferredSortOrder = MovieSortPreferences.getPreferredSortOrder(getActivity());

                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
                    String sortKeyString = preferences.getString(preference.getKey(), "");

                    onPreferenceChange(preference, sortKeyString);
                    // Set these for either mode: Portrait or Landscape

                    //getActivity().setTitle(sortKeyString);


                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(KEY_DEFAULT_SORT_ORDER_STATE, preferredSortOrder);
                    editor.putString(DEFAULT_ORDER_BY_KEY, sortKeyString);
                    editor.apply();


            }//

        }
    }
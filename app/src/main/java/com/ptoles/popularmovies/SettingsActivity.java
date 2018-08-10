package com.ptoles.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SettingsActivity extends AppCompatActivity {

    @NonNull
    public static Intent makeIntent(Context context){
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sortOptionsRadioButtons();

    }

    public static class moviePreferenceFragments extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference orderBy = findPreference(getString(R.string.sort_method_key));
            bindPreferenceSummaryToValue(orderBy);
        }
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }




















    // https://www.youtube.com/watch?v=m_ZiP0U_zRA Youtube video to setup radio button sort options
    private void sortOptionsRadioButtons() {

        RadioGroup group = findViewById(R.id.radio_group_sort_options);

        String[] numSortOptions = getResources().getStringArray(R.array.moviePoster_sort_options);
        // Create the group
        for (int thisSortOption = 0; thisSortOption < numSortOptions.length; thisSortOption++) {

            final String sortOptionString = numSortOptions[thisSortOption];

            RadioButton radioButton = new RadioButton(this);

            radioButton.setText(sortOptionString);

            //TODO: set on-click callbacks
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(SettingsActivity.this, "You clicked " + sortOptionString, Toast.LENGTH_SHORT).show();

                }

            });
            //Add to radio group
            group.addView(radioButton);

            // Select default button
            if (sortOptionString.equals(SettingsActivity.this.getResources().getString(R.string.pref_unsorted))) {
                radioButton.setChecked(true);
            }


        }

    }
        @Override
        public boolean onOptionsItemSelected (MenuItem item){

            int id = item.getItemId();
            if (id == android.R.id.home) {
                onBackPressed();
            }

            return super.onOptionsItemSelected(item);
        }
    }



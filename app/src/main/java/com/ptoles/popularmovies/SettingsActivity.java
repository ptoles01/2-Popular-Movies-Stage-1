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
import android.support.v7.app.ActionBar;
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

      /*  ActionBar actionBar = getSupportActionBar();
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);

        }
        */

    }

    public static class moviePreferenceFragments extends PreferenceFragment implements
            Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
            Preference orderBy = findPreference(getString(R.string.sort_method_key));

            setPreferencesOnChangeListener(orderBy);

        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object chosenPreferenceObject) {
            String chosenPreferenceObjString = chosenPreferenceObject.toString();

            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                // cast the preference to a list preference

                int chosenPreferenceIndex = listPreference.findIndexOfValue(chosenPreferenceObjString);
                if (chosenPreferenceIndex >= 0) {

                    CharSequence[] chosenPreferenceLabels = listPreference.getEntries();
                    //load the labels into an array

                    preference.setSummary(chosenPreferenceLabels[chosenPreferenceIndex]);
                }
            } else {
                preference.setSummary(chosenPreferenceObjString);
            }
            return true;
        }

        private void setPreferencesOnChangeListener(Preference preference) {
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



/*
*
Skip to content

Search or jump to…

Pull requests
Issues
Marketplace
Explore
 @ptoles01 Sign out
1
0 0 cerodriguez46/PopularMovies
 Code  Issues 0  Pull requests 0  Projects 0  Wiki  Insights
PopularMovies/app/src/main/java/christopher/popularmovies/Settings.java
c39d20b  on Jun 16
@cerodriguez46 cerodriguez46 Fixed bug that would not allow favorites list to appear after selecti…

69 lines (53 sloc)  1.93 KB
package christopher.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;

public class Settings extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingsFragment()).commit();

        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);

        }
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.prefs);


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) < 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");
        Intent setIntent = new Intent(getApplicationContext(), MainActivity.class);
        setIntent.addCategory(Intent.CATEGORY_HOME);

        startActivity(setIntent);

        return;
    }

}
© 2018 GitHub, Inc.
Terms
Privacy
Security
Status
Help
Contact GitHub
API
Training
Shop
Blog
About
Press h to open a hovercard with more details.
*
* */
















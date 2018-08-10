package com.ptoles.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class PreferencesActivity extends AppCompatActivity {

    public static Intent makeIntent(Context context){
        return new Intent(context, PreferencesActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sortOptionsRadioButtons();

    }


    // https://www.youtube.com/watch?v=m_ZiP0U_zRA Youtube video to setup radio button sort options
    private void sortOptionsRadioButtons() {

        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group_sort_options);

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
                    Toast.makeText(PreferencesActivity.this, "You clicked " + sortOptionString, Toast.LENGTH_SHORT).show();

                }

            });
            //Add to radio group
            group.addView(radioButton);

            // Select default button
            if (sortOptionString.equals(PreferencesActivity.this.getResources().getString(R.string.pref_unsorted))) {
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



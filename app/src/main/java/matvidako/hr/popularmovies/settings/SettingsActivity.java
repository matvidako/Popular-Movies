package matvidako.hr.popularmovies.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import matvidako.hr.popularmovies.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

}

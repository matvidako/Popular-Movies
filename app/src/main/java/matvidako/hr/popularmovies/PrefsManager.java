package matvidako.hr.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PrefsManager {

    public static String getSortType(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultSort = context.getResources().getStringArray(R.array.pref_sort_by_values)[0];
        return prefs.getString(context.getString(R.string.pref_key_sort), defaultSort);
    }

}

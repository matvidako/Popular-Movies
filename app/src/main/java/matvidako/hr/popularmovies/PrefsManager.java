package matvidako.hr.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.HashSet;
import java.util.Set;

import matvidako.hr.popularmovies.model.Movie;

public class PrefsManager {

    public static String getSortType(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultSort = context.getResources().getStringArray(R.array.pref_sort_by_values)[0];
        return prefs.getString(context.getString(R.string.pref_key_sort), defaultSort);
    }

    public static void addMovieToFavorites(Context context, Movie movie) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String keyFavorites = context.getString(R.string.pref_key_favorites);
        Set<String> favorites = prefs.getStringSet(keyFavorites, new HashSet<String>());
        favorites.add(movie.id);
        prefs.edit().putStringSet(keyFavorites, favorites).commit();
    }

}

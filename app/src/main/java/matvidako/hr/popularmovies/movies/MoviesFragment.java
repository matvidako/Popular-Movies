package matvidako.hr.popularmovies.movies;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.PrefsManager;
import matvidako.hr.popularmovies.R;
import matvidako.hr.popularmovies.model.Movie;
import matvidako.hr.popularmovies.model.PopularMoviesResponse;
import matvidako.hr.popularmovies.moviedetails.MovieDetailsActivity;
import matvidako.hr.popularmovies.net.MovieDb;
import matvidako.hr.popularmovies.settings.SettingsActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MoviesFragment extends Fragment implements AdapterView.OnItemClickListener {

    private static final String STATE_GRID = "gridState";

    Parcelable gridState;
    MovieDb movieDb;

    PopularMoviesAdapter moviesAdapter;

    @InjectView(R.id.gridPopularMovies)
    GridView gridPopularMovies;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        gridPopularMovies.setOnItemClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        movieDb = new MovieDb();
        fetchMovies();
    }

    private void fetchMovies() {
        movieDb.getMovieDbService().getPopularMovies(PrefsManager.getSortType(getActivity()), getPopularMoviesCallback);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        gridState = gridPopularMovies.onSaveInstanceState();
        state.putParcelable(STATE_GRID, gridState);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            gridState = savedInstanceState.getParcelable(STATE_GRID);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(MovieDetailsActivity.buildIntent(getActivity(), moviesAdapter.getItem(position)));
    }

    private void loadMoviesIntoGridView(List<Movie> movies) {
        moviesAdapter = new PopularMoviesAdapter(getActivity(), movies, PrefsManager.isShowingFavoritesOnly(getActivity()));
        gridPopularMovies.setAdapter(moviesAdapter);
        if(gridState != null) {
            gridPopularMovies.onRestoreInstanceState(gridState);
        }
    }

    Callback<PopularMoviesResponse> getPopularMoviesCallback = new Callback<PopularMoviesResponse>() {
        @Override
        public void success(PopularMoviesResponse movies, Response response) {
            loadMoviesIntoGridView(movies.results);
        }

        @Override
        public void failure(RetrofitError error) {
        }
    };
}

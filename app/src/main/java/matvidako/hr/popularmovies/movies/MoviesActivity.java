package matvidako.hr.popularmovies.movies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.PrefsManager;
import matvidako.hr.popularmovies.model.Movie;
import matvidako.hr.popularmovies.moviedetails.MovieDetailsActivity;
import matvidako.hr.popularmovies.R;
import matvidako.hr.popularmovies.model.PopularMoviesResponse;
import matvidako.hr.popularmovies.net.MovieDb;
import matvidako.hr.popularmovies.settings.SettingsActivity;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MoviesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String STATE_GRID = "gridState";
    Parcelable gridState;

    MovieDb movieDb;
    PopularMoviesAdapter moviesAdapter;

    @InjectView(R.id.gridPopularMovies)
    GridView gridPopularMovies;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.inject(this);
        setSupportActionBar(toolbar);
        movieDb = new MovieDb();
        gridPopularMovies.setOnItemClickListener(this);
        movieDb.getMovieDbService().getPopularMovies(PrefsManager.getSortType(this), getPopularMoviesCallback);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        gridState = gridPopularMovies.onSaveInstanceState();
        state.putParcelable(STATE_GRID, gridState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        gridState = state.getParcelable(STATE_GRID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(MovieDetailsActivity.buildIntent(this, moviesAdapter.getItem(position)));
    }

    private void loadMoviesIntoGridView(List<Movie> movies) {
        moviesAdapter = new PopularMoviesAdapter(MoviesActivity.this, movies);
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

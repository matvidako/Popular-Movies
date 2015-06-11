package matvidako.hr.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.model.PopularMoviesResponse;
import matvidako.hr.popularmovies.net.MovieDb;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MoviesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    MovieDb movieDb;
    PopularMoviesAdapter moviesAdapter;

    @InjectView(R.id.gridPopularMovies)
    GridView gridPopularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        ButterKnife.inject(this);
        movieDb = new MovieDb();
        gridPopularMovies.setOnItemClickListener(this);
        movieDb.getMovieDbService().getPopularMovies(getPopularMoviesCallback);
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    Callback<PopularMoviesResponse> getPopularMoviesCallback = new Callback<PopularMoviesResponse>() {
        @Override
        public void success(PopularMoviesResponse movies, Response response) {
            moviesAdapter = new PopularMoviesAdapter(MoviesActivity.this, movies.results);
            gridPopularMovies.setAdapter(moviesAdapter);
        }

        @Override
        public void failure(RetrofitError error) {
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(MovieDetailsActivity.buildIntent(this, moviesAdapter.getItem(position)));
    }

}

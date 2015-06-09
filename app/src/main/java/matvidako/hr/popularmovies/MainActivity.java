package matvidako.hr.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.model.PopularMoviesResponse;
import matvidako.hr.popularmovies.net.MovieDb;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity {

    private MovieDb movieDb;

    @InjectView(R.id.gridPopularMovies)
    GridView gridPopularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        movieDb = new MovieDb();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            Log.d("DISI", movies.results.get(0).original_title);
            gridPopularMovies.setAdapter(new PopularMoviesAdapter(MainActivity.this, movies.results));
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d("DISI", error.toString());
        }
    };

}

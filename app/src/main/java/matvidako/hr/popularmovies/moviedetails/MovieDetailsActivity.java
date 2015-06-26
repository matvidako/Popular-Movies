package matvidako.hr.popularmovies.moviedetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.ImageUtils;
import matvidako.hr.popularmovies.PrefsManager;
import matvidako.hr.popularmovies.R;
import matvidako.hr.popularmovies.model.Movie;
import matvidako.hr.popularmovies.model.Review;
import matvidako.hr.popularmovies.model.ReviewsResponse;
import matvidako.hr.popularmovies.model.Trailer;
import matvidako.hr.popularmovies.model.TrailersResponse;
import matvidako.hr.popularmovies.net.MovieDb;
import matvidako.hr.popularmovies.view.FakeListView;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    static final String EXTRA_MOVIE = "MOVIE";
    Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.inject(this);
        loadFromIntent(getIntent());
        if(movie == null) {
            finish();
            return;
        }
        setupToolbar();
        setupFragment(savedInstanceState);
    }

    private void setupFragment(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return;
        }
        getFragmentManager().beginTransaction().add(R.id.fragment_container, MovieDetailsFragment.newInstance(movie)).commit();
    }

    private void setupToolbar() {
        toolbar.setTitle(movie.original_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movie_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if(menuItem.getItemId() == R.id.action_favorite) {
            PrefsManager.addMovieToFavorites(this, movie);
            Toast.makeText(this, getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public static Intent buildIntent(Activity activity, Movie movie) {
        Intent intent = new Intent(activity, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }

    public void loadFromIntent(Intent intent) {
        if(intent == null) {
            finish();
            return;
        }
        movie = (Movie) intent.getSerializableExtra(EXTRA_MOVIE);
    }

}

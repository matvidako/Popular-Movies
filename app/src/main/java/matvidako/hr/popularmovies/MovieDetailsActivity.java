package matvidako.hr.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.model.Movie;

public class MovieDetailsActivity extends AppCompatActivity {

    static final String EXTRA_MOVIE = "MOVIE";
    Movie movie;

    @InjectView(R.id.image)
    ImageView imagePoster;
    @InjectView(R.id.release_date)
    TextView tvReleaseDate;
    @InjectView(R.id.plot)
    TextView tvPlot;
    @InjectView(R.id.rating)
    TextView tvRating;
    @InjectView(R.id.root)
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        loadFromIntent(getIntent());
        updateUi();
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

    private void updateUi() {
        if(movie == null) {
            finish();
            return;
        }
        ButterKnife.inject(this);
        getSupportActionBar().setTitle(movie.original_title);
        Picasso.with(this).load(Movie.Tools.getFullPosterPath(movie)).into(imagePoster, new Callback() {
            @Override
            public void onSuccess() {
            }
            @Override
            public void onError() {
            }
        });
        tvPlot.setText(movie.overview);
        tvRating.setText("" + movie.vote_average);
        tvReleaseDate.setText(movie.release_date.toString());
    }

}

package matvidako.hr.popularmovies.moviedetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.ImageUtils;
import matvidako.hr.popularmovies.R;
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
    @InjectView(R.id.plot1)
    TextView tvPlot1;
    @InjectView(R.id.plot2)
    TextView tvPlot2;
    @InjectView(R.id.rating)
    TextView tvRating;
    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        loadFromIntent(getIntent());
        updateUi();
        setupToolbar();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        //getSupportActionBar().setTitle(movie.original_title);
        Picasso.with(this).load(Movie.Tools.getFullPosterPath(movie)).into(imagePoster, new Callback() {
            @Override
            public void onSuccess() {
                updateBackgroundColor();
            }

            @Override
            public void onError() {
            }
        });
        toolbar.setTitle(movie.original_title);
        tvPlot.setText(movie.overview);
        tvPlot1.setText(movie.overview);
        tvPlot2.setText(movie.overview);
        tvRating.setText(getString(R.string.rating_out_of_ten, String.format("%.1f", movie.vote_average)));
        tvReleaseDate.setText(movie.getReleaseYear());
    }

    private void updateBackgroundColor() {
        Bitmap bitmap = ImageUtils.getBitmap(imagePoster);
        Palette palette = Palette.from(bitmap).generate();
        int colorStart = palette.getDarkMutedColor(R.color.primary);
        int colorEnd = palette.getDarkVibrantColor(R.color.primaryLight);
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BR_TL, new int[]{colorStart, colorEnd});
        gradientDrawable.setDither(true);
        getWindow().setBackgroundDrawable(gradientDrawable);
        toolbar.setBackgroundColor(palette.getVibrantColor(R.color.primary));
    }

}

package matvidako.hr.popularmovies.moviedetails;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.Calendar;

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
    @InjectView(R.id.rating)
    TextView tvRating;
    @InjectView(R.id.title)
    TextView tvTitle;

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
        tvPlot.setText(movie.overview);
        tvRating.setText("" + movie.vote_average);
        tvReleaseDate.setText(movie.getReleaseYear());
        tvTitle.setText(movie.original_title);
    }

    private void updateBackgroundColor() {
        Bitmap bitmap = ImageUtils.getBitmap(imagePoster);
        Palette palette = Palette.from(bitmap).generate();
        int colorStart = palette.getDarkMutedColor(R.color.primary);
        int colorEnd = palette.getDarkVibrantColor(R.color.primaryLight);
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BR_TL, new int[]{colorStart, colorEnd});
        gradientDrawable.setDither(true);
        getWindow().setBackgroundDrawable(gradientDrawable);
    }

}

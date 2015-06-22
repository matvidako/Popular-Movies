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
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.ImageUtils;
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

    static final String EXTRA_MOVIE = "MOVIE";
    Movie movie;

    @InjectView(R.id.image) ImageView imagePoster;
    @InjectView(R.id.release_date) TextView tvReleaseDate;
    @InjectView(R.id.plot) TextView tvPlot;
    @InjectView(R.id.rating) TextView tvRating;
    @InjectView(R.id.toolbar) Toolbar toolbar;
    @InjectView(R.id.trailer_list) RecyclerView trailerList;
    @InjectView(R.id.review_list) FakeListView reviewList;

    MovieDb movieDb = new MovieDb();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        loadFromIntent(getIntent());
        updateUi();
        setupToolbar();
        loadTrailers();
        loadReviews();
    }

    private void loadReviews() {
        movieDb.getMovieDbService().getReviews(movie.id, new retrofit.Callback<ReviewsResponse>() {
            @Override
            public void success(ReviewsResponse reviewsResponse, Response response) {
                setupReviewUi(reviewsResponse.results);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void setupReviewUi(List<Review> results) {
        reviewList.setAdapter(new ReviewAdapter(this, results));
    }

    private void loadTrailers() {
        movieDb.getMovieDbService().getTrailers(movie.id, new retrofit.Callback<TrailersResponse>() {
            @Override
            public void success(TrailersResponse trailersResponse, Response response) {
                setupTrailerUi(trailersResponse.results);
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }

    private void setupTrailerUi(List<Trailer> trailers) {
        trailerList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailerList.setAdapter(new TrailerAdapter(this, trailers));
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

        Picasso.with(this).load(Movie.Tools.getFullPosterPath(movie, getString(R.string.param_poster_size))).into(imagePoster, new Callback() {
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

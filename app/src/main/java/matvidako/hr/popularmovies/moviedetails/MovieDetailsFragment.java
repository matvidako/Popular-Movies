package matvidako.hr.popularmovies.moviedetails;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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

public class MovieDetailsFragment extends Fragment {

    static final String EXTRA_MOVIE = "MOVIE";

    @InjectView(R.id.image)
    ImageView imagePoster;
    @InjectView(R.id.release_date)
    TextView tvReleaseDate;
    @InjectView(R.id.plot) TextView tvPlot;
    @InjectView(R.id.rating) TextView tvRating;
    @InjectView(R.id.trailer_list)
    RecyclerView trailerList;
    @InjectView(R.id.review_list)
    FakeListView reviewList;

    MovieDb movieDb = new MovieDb();
    Movie movie;

    public static Fragment newInstance(Movie movie) {
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putSerializable(EXTRA_MOVIE, movie);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movie_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.inject(this, view);
        updateUi();
        loadTrailers();
        loadReviews();
    }

    private Movie getMovie() {
        if(movie == null) {
            movie = (Movie) getArguments().getSerializable(EXTRA_MOVIE);
        }
        return movie;
    }

    private void loadReviews() {
        movieDb.getMovieDbService().getReviews(getMovie().id, new retrofit.Callback<ReviewsResponse>() {
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
        reviewList.setAdapter(new ReviewAdapter(getActivity(), results));
    }

    private void loadTrailers() {
        movieDb.getMovieDbService().getTrailers(getMovie().id, new retrofit.Callback<TrailersResponse>() {
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
        trailerList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        trailerList.setAdapter(new TrailerAdapter(getActivity(), trailers));
    }

    private void updateUi() {
        Picasso.with(getActivity()).load(Movie.Tools.getFullPosterPath(getMovie(), getString(R.string.param_poster_size))).into(imagePoster, new Callback() {
            @Override
            public void onSuccess() {
                updateBackgroundColor();
            }

            @Override
            public void onError() {
            }
        });
        tvPlot.setText(getMovie().overview);
        tvRating.setText(getString(R.string.rating_out_of_ten, String.format("%.1f", getMovie().vote_average)));
        tvReleaseDate.setText(getMovie().getReleaseYear());
    }

    private void updateBackgroundColor() {
        Bitmap bitmap = ImageUtils.getBitmap(imagePoster);
        Palette palette = Palette.from(bitmap).generate();
        int colorStart = palette.getDarkMutedColor(R.color.primary);
        int colorEnd = palette.getDarkVibrantColor(R.color.primaryLight);
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.BR_TL, new int[]{colorStart, colorEnd});
        gradientDrawable.setDither(true);
        getActivity().getWindow().setBackgroundDrawable(gradientDrawable);
    }

    @OnClick(R.id.favorite)
    public void addToFavorites(View v) {
        PrefsManager.addMovieToFavorites(getActivity(), movie);
        Toast.makeText(getActivity(), getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show();
    }

}

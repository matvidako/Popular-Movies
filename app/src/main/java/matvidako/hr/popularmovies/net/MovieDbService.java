package matvidako.hr.popularmovies.net;

import matvidako.hr.popularmovies.model.PopularMoviesResponse;
import matvidako.hr.popularmovies.model.ReviewsResponse;
import matvidako.hr.popularmovies.model.TrailersResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface MovieDbService {

    @GET("/discover/movie")
    void getPopularMovies(@Query("sort_by") String sortType, Callback<PopularMoviesResponse> callback);

    @GET("/movie/{id}/videos")
    void getTrailers(@Path("id") String movieId, Callback<TrailersResponse> callback);

    @GET("/movie/{id}/reviews")
    void getReviews(@Path("id") String movieId, Callback<ReviewsResponse> callback);
}

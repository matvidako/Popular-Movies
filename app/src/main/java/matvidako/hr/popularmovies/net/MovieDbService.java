package matvidako.hr.popularmovies.net;

import matvidako.hr.popularmovies.model.PopularMoviesResponse;
import retrofit.Callback;
import retrofit.http.GET;

public interface MovieDbService {

    @GET("/discover/movie")
    void getPopularMovies(Callback<PopularMoviesResponse> callback);

}

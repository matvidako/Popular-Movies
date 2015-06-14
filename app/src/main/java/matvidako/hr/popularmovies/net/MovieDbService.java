package matvidako.hr.popularmovies.net;

import matvidako.hr.popularmovies.model.PopularMoviesResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

public interface MovieDbService {

    @GET("/discover/movie")
    void getPopularMovies(@Query("sort_by") String sortType, Callback<PopularMoviesResponse> callback);

}

package matvidako.hr.popularmovies.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class MovieDb implements RequestInterceptor {

    private static final String API_KEY = "dc0a06fa11121ac96569c439f890c4fc";
    private MovieDbService movieDbService;

    public MovieDb() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        GsonConverter gsonConverter = new GsonConverter(gson);
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://api.themoviedb.org/3").setConverter(gsonConverter).setRequestInterceptor(this).build();
        movieDbService = restAdapter.create(MovieDbService.class);
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addEncodedQueryParam("api_key", API_KEY);
    }

    public MovieDbService getMovieDbService() {
        return movieDbService;
    }

}

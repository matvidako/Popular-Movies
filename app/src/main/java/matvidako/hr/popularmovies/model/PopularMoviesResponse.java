package matvidako.hr.popularmovies.model;

import java.util.List;

public class PopularMoviesResponse {
    public final int page;
    public final List<Movie> results;

    public PopularMoviesResponse(int page, List<Movie> results) {
        this.page = page;
        this.results = results;
    }
}

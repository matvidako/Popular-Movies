package matvidako.hr.popularmovies.model;

import java.util.List;

public class TrailersResponse {
    public final List<Trailer> results;

    public TrailersResponse(List<Trailer> results) {
        this.results = results;
    }
}

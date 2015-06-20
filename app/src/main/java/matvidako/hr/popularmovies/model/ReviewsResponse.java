package matvidako.hr.popularmovies.model;

import java.util.List;

public class ReviewsResponse {
    public final List<Review> results;

    public ReviewsResponse(List<Review> results) {
        this.results = results;
    }
}

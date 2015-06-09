package matvidako.hr.popularmovies.model;

import java.util.Date;

public class Movie {
    public final String original_title;
    public final String overview;
    public final String poster_path;
    public final float vote_average;
    public final Date release_date;

    public Movie(String original_title, String overview, String poster_path, float vote_average, Date release_date) {
        this.original_title = original_title;
        this.overview = overview;
        this.poster_path = poster_path;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }

    public static class Tools {
        public static String getFullPosterPath(Movie movie) {
            return "http://image.tmdb.org/t/p/w342/" + movie.poster_path;
        }
    }

}

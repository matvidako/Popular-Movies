package matvidako.hr.popularmovies.model;

public class Trailer {

    public final String name;
    public final String site;
    public final String key;

    public Trailer(String name, String type, String key) {
        this.name = name;
        this.site = type;
        this.key = key;
    }

}

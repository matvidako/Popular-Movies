package matvidako.hr.popularmovies.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import matvidako.hr.popularmovies.R;
import matvidako.hr.popularmovies.model.Movie;

public class PopularMoviesAdapter extends BaseAdapter {

    private List<Movie> movies;
    private LayoutInflater layoutInflater;
    private Context context;

    public PopularMoviesAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        convertView = layoutInflater.inflate(R.layout.list_item_movie, null, false);
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        Picasso.with(context).load(Movie.Tools.getFullPosterPath(movie, context.getString(R.string.param_poster_size))).placeholder(R.drawable.bg_placeholder).into(image);
        return convertView;
    }

}

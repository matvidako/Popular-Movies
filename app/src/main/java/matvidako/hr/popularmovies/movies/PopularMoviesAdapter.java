package matvidako.hr.popularmovies.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;
import matvidako.hr.popularmovies.PrefsManager;
import matvidako.hr.popularmovies.R;
import matvidako.hr.popularmovies.model.Movie;

public class PopularMoviesAdapter extends BaseAdapter {

    private List<Movie> movies;
    private LayoutInflater layoutInflater;
    private Context context;

    public PopularMoviesAdapter(Context context, List<Movie> movies, boolean isShowingFavoritesOnly) {
        if(isShowingFavoritesOnly) {
            this.movies = filterFavorites(context, movies);
        } else {
            this.movies = movies;
        }
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    private List<Movie> filterFavorites(Context context, List<Movie> movies) {
        Set<String> favoriteIds = PrefsManager.getFavoriteMovieIds(context);
        List<Movie> favoriteMovies = new ArrayList<>();
        for(Movie movie : movies) {
            if(favoriteIds.contains(movie.id)) {
                favoriteMovies.add(movie);
            }
        }
        return favoriteMovies;
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
        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item_movie, null, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        Picasso.with(context).load(Movie.Tools.getFullPosterPath(movie, context.getString(R.string.param_poster_size))).placeholder(R.drawable.bg_placeholder).into(viewHolder.imageView);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.image)
        ImageView imageView;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

package database;

import common.Constants;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public final class QueryVideos extends Query {

    private final ArrayList<Movie> movies;
    private final ArrayList<Show> shows;

    public QueryVideos() {
        movies = new ArrayList<>();
        shows = new ArrayList<>();
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<Show> getShows() {
        return shows;
    }

    /**
     * The method adds a movie or a show to movie or show list
     * @param action for given action
     * @param users for given users
     */

    public void addVideos(final ActionInputData action, final ArrayList<User> users) {
        for (User user : users) {
            if (action.getObjectType().equals(Constants.MOVIES)) {
                for (Movie film : user.getFilms()) {
                    movies.add(film);
                }
            } else if (action.getObjectType().equals(Constants.SHOWS)) {
                for (Show series : user.getSeries()) {
                    shows.add(series);
                }
            }
        }
    }

    /**
     * The method adds a movie with specified criteria to the movie list and then the list is sorted
     * @param action for given action
     * @return a message to be printed in output
     */

    public String ratingMovie(final ActionInputData action) {

        int size;
        boolean okYear = false, okGenre = false;
        String message;
        ArrayList<Movie> sorted = new ArrayList<>();

        for (Movie movie : this.movies) {
            for (String year : action.getFilters().get(Constants.YEAR_INDEX)) {
                if (movie.getYear() == Integer.parseInt(year)) {
                    okYear = true;
                    break;
                } else {
                    okYear = false;
                }
            }
            for (String gen : movie.getGenres()) {
                for (String genre : action.getFilters().get(Constants.GENRE_INDEX)) {
                    if (gen.equals(genre)) {
                        okGenre = true;
                        break;
                    } else {
                        okGenre = false;
                    }
                }
            }
            if (okGenre && okYear) {
                sorted.add(movie);
            }
        }

        if (sorted.size() > 0) {
            Collections.sort(sorted, new Comparator<Movie>() {
                @Override
                public int compare(final Movie a, final Movie b) {
                    return Double.compare(a.getRating(), b.getRating());
                }
            });
        }

        if (action.getNumber() < sorted.size()) {
            size = action.getNumber();
        } else {
            size = sorted.size();
        }

        message = generateMessage(size, null, sorted, null, action);
        return message;
    }

    /**
     * The method adds a show with specified criteria to the show list and then the list is sorted
     * @param action for given action
     * @return a message to be printed in output
     */

    public String ratingShow(final ActionInputData action) {

        int size;
        boolean okYear = false, okGenre = false;
        String message;
        ArrayList<Show> sorted = new ArrayList<>();

        for (Show show : this.shows) {
            for (String year : action.getFilters().get(Constants.YEAR_INDEX)) {
                if (year != null && show.getYear() == Integer.parseInt(year)) {
                    okYear = true;
                    break;
                } else {
                    okYear = false;
                }
            }
            for (String gen : show.getGenres()) {
                for (String genre : action.getFilters().get(Constants.GENRE_INDEX)) {
                    if (gen.equals(genre)) {
                        okGenre = true;
                        break;
                    } else {
                        okGenre = false;
                    }
                }
            }
            if (okGenre && okYear) {
                sorted.add(show);
            }
        }

        if (sorted.size() > 0) {
            Collections.sort(sorted, new Comparator<Show>() {
                @Override
                public int compare(final Show a, final Show b) {
                    return Double.compare(a.getShowRating(), b.getShowRating());
                }
            });
        }

        if (action.getNumber() < sorted.size()) {
            size = action.getNumber();
        } else {
            size = sorted.size();
        }

        message = generateMessage(size, null, null, sorted, action);

        return message;
    }
}

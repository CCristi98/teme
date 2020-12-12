package database;

import fileio.UserInputData;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;

import java.util.ArrayList;
import java.util.Map;

public final class User extends UserInputData {

    private ArrayList<Movie> films;
    private ArrayList<Show> series;

    public User(final String username, final String subscriptionType,
                    final Map<String, Integer> history,
                    final ArrayList<String> favoriteMovies) {
        super(username, subscriptionType, history, favoriteMovies);
        films = new ArrayList<>();
        series = new ArrayList<>();
    }

    public ArrayList<Movie> getFilms() {
        return films;
    }

    public ArrayList<Show> getSeries() {
        return series;
    }

    /**
     * The method is used for adding a favourite video to the favourites list
     * @param video is watched
     */
    public void favorite(final String video) {
        if (getHistory().getOrDefault(video, 0) == 0) {
            getFavoriteMovies().add(video);
        }
    }

    /**
     * The method is used for printing the output message of favorite command
     * @param video for print
     */
    public String favoriteMessage(final String video) {
        String message;
        if (getHistory().getOrDefault(video, 0) == 0) {
            message = "error -> " + video + " is not seen";
            return message;
        }

        for (String favouriteVideo : getFavoriteMovies()) {
            if (video.equals(favouriteVideo)) {
                message = "error -> " + video + " is already in favourite list";
                return message;
            }
        }
        message = "success -> " + video + " was added as favourite";
        return message;
    }

    /**
     * The method is used for adding a watched video to the viewed list
     * @param video to be watched
     */

    public void view(final String video) {
        getHistory().put(video, getHistory().getOrDefault(video, 0) + 1);

    }

    /**
     * method used for printing the output message of view command
     * @param video for print
     */

    public String viewMessage(final String video) {
        String message = "success -> " + video + " was viewed with total views of "
                + getHistory().get(video);
        return message;
    }

    /**
     * The method builds the arraylists of movies and shows for an user and sets ratings for them
     * @param command for given action
     * @param movies for given movie list
     * @param shows for given show list
     * @return a message to be printed in output
     */

    public String ratingMessage(final ActionInputData command,
                                final ArrayList<MovieInputData> movies,
                                final ArrayList<SerialInputData> shows) {
        String message = "error -> " + command.getTitle() + " is not seen";
        boolean ok = false;

        if (getHistory().getOrDefault(command.getTitle(), 0) != 0) {

            for (MovieInputData movie : movies) {
                if (movie.getTitle().equals(command.getTitle())) {

                    for (Movie film : this.films) {
                        if (film.getTitle().equals(command.getTitle())) {
                            ok = true;
                            break;
                        }
                    }
                    if (ok) {
                        message = "error -> " + command.getTitle()
                                + " has been already rated";
                    } else {
                        Movie m = new Movie(movie.getTitle(), movie.getCast(), movie.getGenres(),
                                movie.getYear(), movie.getDuration());
                        m.setRating(command.getGrade());
                        this.films.add(m);
                        message = "success -> " + command.getTitle() + " was rated with "
                                + command.getGrade() + " by " + getUsername();
                    }
                }
            }

            ok = false;
            Show ser = null;

            for (SerialInputData show : shows) {
                if (show.getTitle().equals(command.getTitle())) {

                    for (Show serial : this.series) {
                        if (serial.getTitle().equals(command.getTitle())) {
                            ser = serial;
                            for (UserSeason season : serial.getUserSeasons()) {
                                if (season.getCurrentSeason() == command.getSeasonNumber()) {
                                    ok = true;
                                    break;
                                }
                            }
                            if (ok) {
                                break;
                            }
                        }
                    }
                    if (ser == null) {
                        ser = new Show(show.getTitle(), show.getCast(), show.getGenres(),
                                show.getNumberSeason(), show.getSeasons(), show.getYear());
                        this.series.add(ser);
                    }
                    if (ok) {
                        message = "error -> " + command.getTitle()
                                + " has been already rated";
                    } else {
                        UserSeason season = new UserSeason(command.getSeasonNumber());
                        season.setRating(command.getGrade());
                        ser.getUserSeasons().add(season);
                        //System.out.println();
                        message = "success -> " + command.getTitle() + " was rated with "
                                + season.getRating() + " by " + getUsername();
                    }
                }
            }
        }
        return message;
    }
}

package database;

import entertainment.Season;
import fileio.SerialInputData;

import java.util.ArrayList;

public final class Show extends SerialInputData {

    private ArrayList<UserSeason> userSeasons;

    public Show(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, cast, genres, numberOfSeasons, seasons, year);
        this.userSeasons = new ArrayList<>();
    }


    public ArrayList<UserSeason> getUserSeasons() {
        return userSeasons;
    }

    /**
     * The method verifies if a season can be added to a show
     * @param  season to be added
     * @return true if the season can be added, otherwise false
     */
    public boolean addSeason(final UserSeason season) {
        boolean ok = false;
        for (UserSeason s : userSeasons) {
            if (season.getCurrentSeason() == s.getCurrentSeason()
                && season.getRating() == season.getRating()) {
                ok = true;
                break;
            }
        }
        if (ok) {
            return true;
        } else {
            userSeasons.add(season);
        }
        return false;
    }

    /**
     * The method computes the average rating of given ratings for a particular show
     * @return an average rating for a particular season
     */

    public Double getShowRating() {
        double rating;
        int sum = 0, nr = 0;
        for (UserSeason season : userSeasons) {
            sum += season.getRating();
            nr++;
        }
        rating = sum / nr;
        return rating;
    }

    @Override
    public String toString() {
        return "Show{"
                + "title='" + getTitle() + '\''
                + ", seasons=" + userSeasons
                + '}';
    }
}

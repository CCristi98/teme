package database;

import actor.ActorsAwards;
import fileio.ActorInputData;

import java.util.ArrayList;
import java.util.Map;

public final class Actor extends ActorInputData {

    public Actor(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        super(name, careerDescription, filmography, awards);
    }

    /**
     * The method computes the average rating of given ratings by users for videos
     * @param users
     * @return an average rating for an actor
     */
    public Double getActorRating(final ArrayList<User> users) {
        double rating;
        int sum = 0, nr = 0;

        for (String film : getFilmography()) {
            for (User user : users) {
                for (Movie playedfilm : user.getFilms()) {
                    if (playedfilm.getTitle().equals(film)) {
                        sum += playedfilm.getRating();
                        nr++;
                    }
                }
                for (Show playedshow : user.getSeries()) {
                    if (playedshow.getTitle().equals(film)) {
                        sum += playedshow.getShowRating();
                        nr++;
                    }
                }
            }
        }
        if (nr == 0) {
           return 0.0;
        }
        rating = (double) sum / nr;
        return rating;
    }

    /**
     * The method computes the number of awards received by an actor
     * @return number of awards
     */
    public int getTotalAwards() {
        int nrAwards = 0;
        for (Map.Entry<ActorsAwards, Integer> entry : getAwards().entrySet()) {
            nrAwards += entry.getValue();
        }
        return nrAwards;

    }

    @Override
    public String toString() {
        return "Name{" + super.getName() + "}";
    }
}

package database;

import common.Constants;
import fileio.ActionInputData;

import java.util.ArrayList;

public class Query {

    /**
     * The method generates the message of a query action of an actor
     * @param size for arraylist's size
     * @param actors given from sorted actors list
     * @param action given from input
     * @return converted message to be printed in output
     */

    public String generateMessage(final int size, final ArrayList<Actor> actors,
                                  final ArrayList<Movie> movies,
                                  final ArrayList<Show> shows,
                                  final ActionInputData action) {

        StringBuilder message = new StringBuilder();
        message.append("Query result: [");
        for (int i = 0; i < size; ++i) {
            if (action.getSortType().equals(Constants.ASC)) {
                if (action.getObjectType().equals(Constants.ACTORS)) {
                    message.append(actors.get(i).getName());
                } else if (action.getObjectType().equals(Constants.MOVIES)) {
                    message.append(movies.get(i).getTitle());
                } else if (action.getObjectType().equals(Constants.SHOWS)) {
                    message.append(shows.get(i).getTitle());
                }
            } else if (action.getSortType().equals(Constants.DESC)) {
                if (action.getObjectType().equals(Constants.ACTORS)) {
                    message.append(actors.get(size - i - 1).getName());
                } else if (action.getObjectType().equals(Constants.MOVIES)) {
                    message.append(movies.get(size - i - 1).getTitle());
                } else if (action.getObjectType().equals(Constants.SHOWS)) {
                    message.append(shows.get(i).getTitle());
                }
            }
            if (i < size - 1) {
                message.append(", ");
            }
        }
        message.append("]");

        return message.toString();

    }
}

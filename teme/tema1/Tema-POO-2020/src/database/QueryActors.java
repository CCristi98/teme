package database;

import actor.ActorsAwards;
import common.Constants;
import fileio.ActionInputData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public final class QueryActors extends Query {

    /**
     * The method sorts the list of actors by their ratings
     * @param action given from input
     * @param users to take the ratings from
     * @param actors given as input
     * @return message for average query action
     */
    public String average(final ActionInputData action, final ArrayList<User> users,
                          final ArrayList<Actor> actors) {

        int size;
        ArrayList<Actor> sorted = new ArrayList<>();
        String message;

        for (Actor a : actors) {
            if (a.getActorRating(users) != 0) {
                sorted.add(a);
            }
        }

        for (Actor a : sorted) {
            a.getActorRating(users);
        }

        if (sorted.size() > 0) {
            Collections.sort(sorted, new Comparator<Actor>() {
                @Override
                public int compare(final Actor a, final Actor b) {
                    return Double.compare(a.getActorRating(users), b.getActorRating(users));
                }
            });
        }

        /*for (Actor a : sorted) {
            System.out.println(a);
            System.out.println(a.getActorRating(users));
        }*/

        if (action.getNumber() < sorted.size()) {
            size = action.getNumber();
        } else {
            size = sorted.size();
        }

        message = generateMessage(size, sorted, null, null, action);
        return message;

    }

    /**
     * The method sorts the list of actors by their number of won awards
     * @param action given from input
     * @param actors given as input
     * @return message for awards query action
     */

    public String awards(final ActionInputData action, final ArrayList<Actor> actors) {
        String message;
        boolean ok;

        ArrayList<Actor> sorted = new ArrayList<>();
        for (Actor a : actors) {
            for (String award : action.getFilters().get(Constants.AWARDS_INDEX)) {
                ok = false;
                for (Map.Entry<ActorsAwards, Integer> entry : a.getAwards().entrySet()) {
                    if (award.equals(entry.getKey().toString())) {
                        ok = true;
                        break;
                    }
                }
                if (ok) {
                    sorted.add(a);
                } else {
                    break;
                }
            }
        }

        if (sorted.size() > 0) {
            Collections.sort(sorted, new Comparator<Actor>() {
                @Override
                public int compare(final Actor a, final Actor b) {
                    return Integer.compare(a.getTotalAwards(), b.getTotalAwards());
                }
            });
        }

        message = generateMessage(sorted.size(), sorted, null, null, action);
        return message;

    }

    /**
     * The method sorts the list of filtered actors by alphabet order
     * @param action given from input
     * @param actors given as input
     * @return message for filterDescription query action
     */

    public String filterDescription(final ActionInputData action, final ArrayList<Actor> actors) {
        String message, description;
        CharSequence seq;
        boolean ok = false;

        ArrayList<Actor> sorted = new ArrayList<>();
        for (Actor a : actors) {
            description = a.getCareerDescription();
            for (String word : action.getFilters().get(Constants.WORDS_INDEX)) {
                seq = word;
                    if (description.contains(seq)) {
                        ok = true;
                    } else {
                        ok = false;
                        break;
                    }
                }
            if (ok) {
                sorted.add(a);
            }
        }

        if (sorted.size() > 0) {
            Collections.sort(sorted, new Comparator<Actor>() {
                @Override
                public int compare(final Actor a, final Actor b) {
                    return a.getName().compareTo(b.getName());
                }
            });
        }

        message = generateMessage(sorted.size(), sorted, null, null, action);
        return message;

    }
}


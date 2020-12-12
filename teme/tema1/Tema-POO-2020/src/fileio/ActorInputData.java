package fileio;

import actor.ActorsAwards;

import java.util.ArrayList;
import java.util.Map;

/**
 * Information about an actor, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public class ActorInputData {
    /**
     * actor name
     */
    private String name;
    /**
     * description of the actor's career
     */
    private String careerDescription;
    /**
     * videos starring actor
     */
    private ArrayList<String> filmography;
    /**
     * awards won by the actor
     */
    private Map<ActorsAwards, Integer> awards;

    public ActorInputData(final String name, final String careerDescription,
                          final ArrayList<String> filmography,
                          final Map<ActorsAwards, Integer> awards) {
        this.name = name;
        this.careerDescription = careerDescription;
        this.filmography = filmography;
        this.awards = awards;
    }

    /**
     * getter for name
     */
    public String getName() {
        return name;
    }
    /**
     * setter for name
     */
    public void setName(final String name) {
        this.name = name;
    }
    /**
     * getter for filmography of an actor
     */
    public ArrayList<String> getFilmography() {
        return filmography;
    }
    /**
     * setter for filmography of an actor
     */
    public void setFilmography(final ArrayList<String> filmography) {
        this.filmography = filmography;
    }
    /**
     * getter for the awards won by an actor
     */
    public Map<ActorsAwards, Integer> getAwards() {
        return awards;
    }
    /**
     * getter for career drescription
     */
    public String getCareerDescription() {
        return careerDescription;
    }
    /**
     * setter for career description
     */
    public void setCareerDescription(final String careerDescription) {
        this.careerDescription = careerDescription;
    }
    /**
     * method used for formatting printed data of an input
     */
    @Override
    public String toString() {
        return "ActorInputData{"
                + "name='" + name + '\''
                + ", careerDescription='"
                + careerDescription + '\''
                + ", filmography=" + filmography + '}';
    }
}

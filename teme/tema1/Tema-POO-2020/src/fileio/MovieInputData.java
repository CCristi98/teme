package fileio;

import java.util.ArrayList;

/**
 * Information about a movie, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public class MovieInputData extends ShowInput {
    /**
     * Duration in minutes of a season
     */
    private final int duration;

    public MovieInputData(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, year, cast, genres);
        this.duration = duration;
    }

    /**
     * getter for episode duration of a season
     */
    public int getDuration() {
        return duration;
    }
    /**
     * method used for formatting printed data of an input
     */
    @Override
    public String toString() {
        return "MovieInputData{" + "title= "
                + super.getTitle() + "year= "
                + super.getYear() + "duration= "
                + duration + "cast {"
                + super.getCast() + " }\n"
                + "genres {" + super.getGenres() + " }\n ";
    }
}

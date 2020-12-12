package database;

import fileio.MovieInputData;

import java.util.ArrayList;

public final class Movie extends MovieInputData {
    private Double rating;

    public Movie(final String title, final ArrayList<String> cast,
                          final ArrayList<String> genres, final int year,
                          final int duration) {
        super(title, cast, genres, year, duration);
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(final Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Movie{"
                + "title='" + getTitle() + '\''
                + ", rating=" + rating
                + '}';
    }
}

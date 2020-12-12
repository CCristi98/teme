package database;

public final class UserSeason {
    private final int currentSeason;
    private int duration;
    private double rating;

    public UserSeason(final int currentSeason) {
        this.currentSeason = currentSeason;
    }

    public int getCurrentSeason() {
        return currentSeason;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(final int duration) {
        this.duration = duration;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(final double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "UserSeason{"
                + "currentSeason=" + currentSeason
                + ", duration=" + duration
                + ", rating=" + rating
                + '}';
    }
}

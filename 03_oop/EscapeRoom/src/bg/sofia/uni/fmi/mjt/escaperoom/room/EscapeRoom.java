package bg.sofia.uni.fmi.mjt.escaperoom.room;

import bg.sofia.uni.fmi.mjt.escaperoom.rating.Ratable;

import java.util.Objects;

public class EscapeRoom implements Ratable {
    private final String name;
    private Theme theme;
    private final Difficulty difficulty;
    private final int maxTimeToEscape;
    private double priceToPlay;
    private int maxReviewsCount;

    private double ratingsSum;
    private int ratingsCount;

    private int crrReviewsCount;
    private Review[] reviews;

    public EscapeRoom(String name, Theme theme, Difficulty difficulty, int maxTimeToEscape, double priceToPlay,
                      int maxReviewsCount) {
        this.name = name;
        this.theme = theme;
        this.difficulty = difficulty;
        this.maxTimeToEscape = maxTimeToEscape;
        this.priceToPlay = priceToPlay;
        this.maxReviewsCount = maxReviewsCount;
        this.reviews = new Review[maxReviewsCount];
    }

    @Override
    public double getRating() {
        if (ratingsCount != 0) {
            return ratingsSum / ratingsCount;
        }

        return 0.0;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getMaxTimeToEscape() {
        return maxTimeToEscape;
    }

    public Review[] getReviews() {
        Review[] crrReviews = new Review[crrReviewsCount];

        int crrReviewIndex = 0;

        for (int i = 0; i < maxReviewsCount; i++) {
            if (reviews[i] != null) {
                crrReviews[crrReviewIndex] = reviews[i];
                crrReviewIndex++;
            }
        }

        return crrReviews;
    }

    public void addReview(Review review) {
        reviews[crrReviewsCount] = review;
        crrReviewsCount++;

        if (crrReviewsCount == maxReviewsCount) {
            crrReviewsCount = 0;
        }

        ratingsSum += review.rating();
        ratingsCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EscapeRoom that = (EscapeRoom) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}

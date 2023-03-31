package bg.sofia.uni.fmi.mjt.netflix;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class NetflixRecommender {
    private final Collection<Content> dataset;

    public NetflixRecommender() throws FileNotFoundException {
        InputStream DATASET_SOURCE = this.getClass().getResourceAsStream("/dataset.txt");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(DATASET_SOURCE))) {
            this.dataset = reader.lines().skip(1).map(Content::of).toList();
        } catch(FileNotFoundException e) {
            throw new FileNotFoundException("Cannot find the file specified");
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while reading the file");
        }
    }

    /**
     * Returns all movies and shows from the dataset in undefined order as an unmodifiable List.
     * If the dataset is empty, returns an empty List.
     *
     * @return the list of all movies and shows.
     */
    public List<Content> getAllContent() {
        return dataset.stream().toList();
    }

    /**
     * Returns a list of all unique genres of movies and shows in the dataset in undefined order.
     * If the dataset is empty, returns an empty List.
     *
     * @return the list of all genres
     */
    public List<String> getAllGenres() {
        return dataset.stream()
                .flatMap(x -> x.genres().stream())
                .distinct()
                .toList();
    }

    /**
     * Returns the movie with the longest duration / run time. If there are two or more movies
     * with equal maximum run time, returns any of them. Shows in the dataset are not considered by this method.
     *
     * @return the movie with the longest run time
     * @throws NoSuchElementException in case there are no movies in the dataset.
     */
    public Content getTheLongestMovie() {
        return dataset.stream()
                .filter(x -> x.type() == ContentType.MOVIE)
                .max(Comparator.comparingInt(Content::runtime))
                .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Returns a breakdown of content by type (movie or show).
     *
     * @return a Map with key: a ContentType and value: the set of movies or shows on the dataset, in undefined order.
     */
    public Map<ContentType, Set<Content>> groupContentByType() {
        return dataset.stream()
                .collect(Collectors.groupingBy(Content::type, Collectors.toSet()));
    }

    /**
     * Returns the top N movies and shows sorted by weighed IMDB rating in descending order.
     * If there are fewer movies and shows than {@code n} in the dataset, return all of them.
     * If {@code n} is zero, returns an empty list.
     *
     * The weighed rating is calculated by the following formula:
     * Weighted Rating (WR) = (v ÷ (v + m)) × R + (m ÷ (v + m)) × C
     * where
     * R is the content's own average rating across all votes. If it has no votes, its R is 0.
     * C is the average rating of content across the dataset
     * v is the number of votes for a content
     * m is a tunable parameter: sensitivity threshold. In our algorithm, it's a constant equal to 10_000.
     *
     * Check https://stackoverflow.com/questions/1411199/what-is-a-better-way-to-sort-by-a-5-star-rating for details.
     *
     * @param n the number of the top-rated movies and shows to return
     * @return the list of the top-rated movies and shows
     * @throws IllegalArgumentException if {@code n} is negative.
     */
    public List<Content> getTopNRatedContent(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("N should be a positive integer!");
        }

        return dataset.stream()
                .sorted((c1, c2) -> Double.compare(
                        getWeightedRating(c1),
                        getWeightedRating(c2)
                ))
                .limit(n)
                .toList();
    }

    /**
     * Returns a list of content similar to the specified one sorted by similarity is descending order.
     * Two contents are considered similar, only if they are of the same type (movie or show).
     * The used measure of similarity is the number of genres two contents share.
     * If two contents have equal number of common genres with the specified one, their mutual oder
     * in the result is undefined.
     *
     * @param content the specified movie or show.
     * @return the sorted list of content similar to the specified one.
     */
    public List<Content> getSimilarContent(Content content) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    /**
     * Searches content by keywords in the description (case-insensitive).
     *
     * @param keywords the keywords to search for
     * @return an unmodifiable set of movies and shows whose description contains all specified keywords.
     */
    public Set<Content> getContentByKeywords(String... keywords) {
        throw new UnsupportedOperationException("Method not yet implemented");
    }

    private double getWeightedRating(Content c) {
        double averageImdbScore = dataset.stream().collect(Collectors.averagingDouble(Content::imdbScore));
        final int SENSITIVITY_THRESHOLD = 10_000;


        return (c.imdbVotes() / (c.imdbVotes() + SENSITIVITY_THRESHOLD)) * c.imdbScore() +
                (SENSITIVITY_THRESHOLD / (c.imdbVotes() + SENSITIVITY_THRESHOLD)) * averageImdbScore;
    }
}

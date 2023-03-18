package bg.sofia.uni.fmi.mjt.airbnb;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.Bookable;
import bg.sofia.uni.fmi.mjt.airbnb.filter.Criterion;

import java.util.Arrays;

public class Airbnb implements AirbnbAPI {
    private Bookable[] accommodations;

    public Airbnb(Bookable[] accommodations) {
        this.accommodations = accommodations;
    }

    public Bookable findAccommodationById(String id) {
        for (Bookable accommodation : accommodations) {
            if (accommodation.getId().equals(id)) {
                return accommodation;
            }
        }

        return null;
    }

    public double estimateTotalRevenue() {
        double totalRevenue = 0;

        for (Bookable accommodation : accommodations) {
            if (accommodation.isBooked()) {
                totalRevenue += accommodation.getTotalPriceOfStay();
            }
        }

        return totalRevenue;
    }

    public long countBookings() {
        long bookings = 0;

        for (Bookable accommodation : accommodations) {
            if (accommodation.isBooked()) {
                bookings++;
            }
        }

        return bookings;
    }

    public Bookable[] filterAccommodations(Criterion... criteria) {
        int countMatching = 0;
        for (Bookable accommodation : accommodations) {
            if (matchCriteria(accommodation, criteria)) {
                countMatching++;
            }
        }

        Bookable[] included = new Bookable[countMatching];
        int resultIndex = 0;
        for (Bookable accommodation : accommodations) {
            if (matchCriteria(accommodation, criteria)) {
                included[resultIndex++] = accommodation;
            }
        }

        return included;
    }

    private boolean matchCriteria(Bookable accommodation, Criterion[] criteria) {
        for (Criterion criterion : criteria) {
            if ((criterion != null) && !criterion.check(accommodation)) {
                return false;
            }
        }

        return true;
    }
}

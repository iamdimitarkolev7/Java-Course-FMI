package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Apartment extends BookableAccommodation {
    private static long numOfInstances;

    public Apartment(Location location, double pricePerNight) {
        super("APARTMENT", location, pricePerNight);
        numOfInstances++;
    }

    @Override
    protected long getIdSuffix() {
        return numOfInstances;
    }
}

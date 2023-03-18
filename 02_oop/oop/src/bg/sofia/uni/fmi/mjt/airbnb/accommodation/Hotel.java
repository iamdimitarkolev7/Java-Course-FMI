package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Hotel extends BookableAccommodation {
    private static long numOfInstances;

    public Hotel(Location location, double pricePerNight) {
        super("HOTEL", location, pricePerNight);
        this.numOfInstances++;
    }

    @Override
    protected long getIdSuffix() {
        return numOfInstances;
    }
}

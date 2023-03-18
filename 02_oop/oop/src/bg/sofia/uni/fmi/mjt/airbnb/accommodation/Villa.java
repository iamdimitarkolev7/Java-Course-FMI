package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

public class Villa extends BookableAccommodation {
    private static long numOfInstances;

    public Villa(Location location, double pricePerNight) {
        super("VILLA", location, pricePerNight);
        numOfInstances++;
    }

    @Override
    protected long getIdSuffix() {
        return numOfInstances;
    }
}

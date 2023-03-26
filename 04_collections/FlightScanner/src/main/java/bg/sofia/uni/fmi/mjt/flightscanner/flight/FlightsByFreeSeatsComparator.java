package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import java.util.Comparator;

public class FlightsByFreeSeatsComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight f1, Flight f2) {
        return Integer.compare(f1.getFreeSeatsCount(), f2.getFreeSeatsCount());
    }
}

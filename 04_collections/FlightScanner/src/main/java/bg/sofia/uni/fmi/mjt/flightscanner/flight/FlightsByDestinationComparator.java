package bg.sofia.uni.fmi.mjt.flightscanner.flight;

import java.util.Comparator;

public class FlightsByDestinationComparator implements Comparator<Flight> {

    @Override
    public int compare(Flight f1, Flight f2) {
        return f1.getTo().id().compareTo(f2.getTo().id());
    }
}

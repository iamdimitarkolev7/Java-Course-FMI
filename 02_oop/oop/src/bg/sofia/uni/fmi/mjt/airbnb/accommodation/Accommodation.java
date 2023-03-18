package bg.sofia.uni.fmi.mjt.airbnb.accommodation;

import bg.sofia.uni.fmi.mjt.airbnb.accommodation.location.Location;

import java.util.Objects;

public abstract class Accommodation {
    private final String id;
    private final String type;
    private final Location location;

    protected Accommodation(String type, Location location) {
        this.id = generateId();
        this.type = type;
        this.location = location;
    }

    private String generateId() {
        return this.type + "-" + getIdSuffix();
    }

    protected abstract long getIdSuffix();

    public String getId() {
        return this.id;
    }

    public String getType() {
        return this.type;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Accommodation that = (Accommodation) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", id, type, location);
    }
}

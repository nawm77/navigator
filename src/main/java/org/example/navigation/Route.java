package org.example.navigation;

import java.util.Arrays;
import java.util.Objects;

public class Route {
    private final Integer hashCode;
    private final double distance;
    private int popularity;
    private final boolean isFavorite;
    private String[] locationPoints;

    public Route(double distance, int popularity, boolean isFavorite, String[] locationPoints) {
        this.distance = distance;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
        this.locationPoints = locationPoints;
        this.hashCode = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return popularity == route.popularity && isFavorite == route.isFavorite && Objects.equals(distance, route.distance) && Objects.equals(locationPoints, route.locationPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, isFavorite, Arrays.hashCode(locationPoints));
    }

    public String getHashCode() {
        return String.valueOf(hashCode);
    }

    public double getDistance() {
        return distance;
    }

    public int getPopularity() {
        return popularity;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public String[] getLocationPoints() {
        return locationPoints;
    }

    public void incrementPopularity() {
        ++popularity;
    }

    public void setLocationPoints(String[] locationPoints) {
        this.locationPoints = locationPoints;
    }

    @Override
    public String toString() {
        return "Route{" +
                "hashCode='" + hashCode + '\'' +
                ", distance=" + distance +
                ", popularity=" + popularity +
                ", isFavorite=" + isFavorite +
                ", locationPoints=" + Arrays.toString(locationPoints) +
                '}';
    }
}

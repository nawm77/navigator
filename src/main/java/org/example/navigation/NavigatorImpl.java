package org.example.navigation;

import org.example.dataStructures.hashTable.ArrayList;
import org.example.dataStructures.hashTable.MonoHashTable;

import java.util.Arrays;
import java.util.Comparator;

public class NavigatorImpl implements Navigator {
    private final MonoHashTable<Route> routeMap;
    private final ArrayList<Route> favoriteRoutes;
    private final ArrayList<Route> topRoutes;

    public NavigatorImpl() {
        this.routeMap = new MonoHashTable<>();
        this.favoriteRoutes = new ArrayList<>();
        this.topRoutes = new ArrayList<>();
    }

    @Override
    public void addRoute(Route route) {
        if (isRouteExists(route)) {
            for (Route r : routeMap.values()) {
                if (r.getDistance() == route.getDistance()) {
                    if (r.getLocationPoints()[0].equals(route.getLocationPoints()[0]) &&
                            r.getLocationPoints()[r.getLocationPoints().length - 1].equals(route.getLocationPoints()[route.getLocationPoints().length - 1])
                    ) {
                        if(r.getLocationPoints().length < route.getLocationPoints().length) {
                            r.setLocationPoints(route.getLocationPoints());
                            return;
                        }
                    }
                }
            }
        }
        routeMap.add(route);
        if (route.isFavorite()) favoriteRoutes.add(route);
        topRoutes.add(route);
    }

    @Override
    public void removeRoute(String routeId) {
        Route removedRoute = routeMap.get(Integer.parseInt(routeId));
        routeMap.remove(Integer.parseInt(routeId));
        if (removedRoute != null) {
            favoriteRoutes.remove(removedRoute);
            topRoutes.remove(removedRoute);
        }
    }

    @Override
    public boolean contains(Route route) {
        return routeMap.containsValue(route);
    }

    @Override
    public int size() {
        return routeMap.size();
    }

    @Override
    public Route getRoute(String routeId) {
        return routeMap.get(Integer.parseInt(routeId));
    }

    @Override
    public void chooseRoute(String routeId) {
        Route chosenRoute = routeMap.get(Integer.parseInt(routeId));
        if (chosenRoute != null) {
            chosenRoute.incrementPopularity();
            topRoutes.remove(chosenRoute);
            topRoutes.add(chosenRoute);
        }
    }

    @Override
    public Iterable<Route> searchRoutes(String startPoint, String endPoint) {
        ArrayList<Route> result = new ArrayList<>();
        for (Route route : routeMap.values()) {
            ArrayList<String> locations = new ArrayList<>(route.getLocationPoints());
            int startIndex = locations.indexOf(startPoint);
            int endIndex = locations.indexOf(endPoint);
            if (startIndex >= 0 && endIndex > startIndex) {
                result.add(route);
            }
        }
        result.sort(Comparator.<Route, Integer>comparing(route -> {
                            ArrayList<String> locationPoints = new ArrayList<>(route.getLocationPoints());
                            int startIndex = locationPoints.indexOf(startPoint);
                            int endIndex = locationPoints.indexOf(endPoint);
                            return endIndex - startIndex;
                        })
                        .thenComparingInt(route -> route.getLocationPoints().length)
                        .thenComparingInt(Route::getPopularity).reversed()
                        .thenComparingDouble(Route::getDistance)
        );
        ArrayList<Route> list = new ArrayList<>(result.size());
        for (Route r : result) {
            if (r != null) list.add(r);
        }
        return list;
    }

    @Override
    public Iterable<Route> getFavoriteRoutes(String destinationPoint) {
        favoriteRoutes.sort(new FavoriteRouteComparator(routeMap));
        ArrayList<Route> result = new ArrayList<>();
        for (Route r : favoriteRoutes) {
            if (r != null && !r.getLocationPoints()[0].equals(destinationPoint)
                    && Arrays.asList(r.getLocationPoints()).contains(destinationPoint)) {
                result.add(r);
            }
        }
        return result;
    }

    @Override
    public Iterable<Route> getTop3Routes() {
        topRoutes.sort(new TopRouteComparator(routeMap));
        ArrayList<Route> result = new ArrayList<>();
        int index = 0;
        for (Route r : topRoutes) {
            if (r != null && index < 5) {
                result.add(r);
                index++;
            }
        }
        return result;
    }

    private record TopRouteComparator(MonoHashTable<Route> table) implements Comparator<Route> {
        @Override
        public int compare(Route route1, Route route2) {
            if (route1.getPopularity() != route2.getPopularity()) {
                return Integer.compare(route2.getPopularity(), route1.getPopularity());
            }
            if (route1.getDistance() != route2.getDistance()) {
                return Double.compare(route1.getDistance(), route2.getDistance());
            }
            if (route1.getLocationPoints().length != route2.getLocationPoints().length) {
                return Integer.compare(route1.getLocationPoints().length, route2.getLocationPoints().length);
            }
            return table.getOrder(route2.hashCode()) - table.getOrder(route1.hashCode());
        }
    }

    private record FavoriteRouteComparator(MonoHashTable<Route> table) implements Comparator<Route> {
        @Override
        public int compare(Route o1, Route o2) {
            if (o1.getDistance() != o2.getDistance()) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
            if (o2.getPopularity() != o1.getPopularity()) {
                return o2.getPopularity() - o1.getPopularity();
            }
            return table.getOrder(o2.hashCode()) - table.getOrder(o1.hashCode());
        }
    }

    private boolean isRouteExists(Route route) {
        for (Route r : routeMap.values()) {
            if (r.getDistance() == route.getDistance()) {
                if (r.getLocationPoints()[0].equals(route.getLocationPoints()[0]) &&
                        r.getLocationPoints()[r.getLocationPoints().length - 1].equals(route.getLocationPoints()[route.getLocationPoints().length - 1])
                ) {
                    return true;
                }
            }
        }
        return false;
    }
}
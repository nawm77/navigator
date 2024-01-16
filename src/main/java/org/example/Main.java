package org.example;

import org.example.dataStructures.hashTable.ArrayList;
import org.example.navigation.Navigator;
import org.example.navigation.NavigatorImpl;
import org.example.navigation.Route;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private final static Scanner sc = new Scanner(System.in);
    private static Boolean appIsRunning = Boolean.TRUE;

    public static void main(String[] args) {
        Navigator navigator = new NavigatorImpl();
        Iterable<Route> generatedRoutes = generateRandomRoutes();
        for (Route r : generatedRoutes) {
            System.out.println(r);
            navigator.addRoute(r);
        }
        System.out.println("Successfully generated 100 routes");
        while (appIsRunning) {
            int variant = menu();

            switch (variant) {
                case 1:
                    addNewRoute(navigator);
                    break;
                case 2:
                    removeRoute(navigator);
                    break;
                case 3:
                    checkIfContainsRoute(navigator);
                    break;
                case 4:
                    getNavigatorSize(navigator);
                    break;
                case 5:
                    getRouteFromNavigator(navigator);
                    break;
                case 6:
                    chooseRoute(navigator);
                    break;
                case 7:
                    searchRoute(navigator);
                    break;
                case 8:
                    getFavoriteRoutes(navigator);
                    break;
                case 9:
                    getTopRoutes(navigator);
                    break;
                case 10:
                    breakApp();
                    break;
                default:
                    System.out.println("Invalid option. Please choose a number between 1 and 10.");
                    break;
            }
        }
    }

    private static int menu() {
        System.out.println("Choose action:");
        System.out.println("1) Add new route");
        System.out.println("2) Remove existing route");
        System.out.println("3) Check if navigator contains route");
        System.out.println("4) Get navigator storage size");
        System.out.println("5) Get route from navigator");
        System.out.println("6) Choose route");
        System.out.println("7) Search route");
        System.out.println("8) Get favorite routes");
        System.out.println("9) Get top 5 routes");
        System.out.println("10) Exit");
        String choice = sc.nextLine();
        return Integer.parseInt(choice);
    }

    private static void breakApp() {
        System.out.println("BYE!");
        appIsRunning = Boolean.FALSE;
    }

    private static void addNewRoute(Navigator navigator) {
        System.out.print("Enter the distance of the route: ");
        double distance = Double.parseDouble(sc.nextLine());

        System.out.print("Enter the popularity of the route: ");
        int popularity = Integer.parseInt(sc.nextLine());

        System.out.print("Is it a favorite route? (true/false): ");
        boolean isFavorite = Boolean.parseBoolean(sc.nextLine());

        System.out.print("Enter the number of location points: ");
        int numLocationPoints = Integer.parseInt(sc.nextLine());

        String[] locationPoints = new String[numLocationPoints];
        for (int i = 0; i < numLocationPoints; i++) {
            int number = i + 1;
            System.out.print("Enter the name of city " + number + ": ");
            locationPoints[i] = sc.nextLine();
        }

        Route route = new Route(distance, popularity, isFavorite, locationPoints);
        navigator.addRoute(route);

        System.out.println("The following route has been added:");
        System.out.println(route);
    }

    private static void removeRoute(Navigator navigator) {
        System.out.print("Enter the hashCode of the route to be removed: ");
        String routeHashCode = sc.nextLine();
        navigator.removeRoute(routeHashCode);
        System.out.println("Route with hashCode " + routeHashCode + " has been removed from the navigator.");
    }

    private static void checkIfContainsRoute(Navigator navigator) {
        System.out.print("Enter the hashCode of the route to check: ");
        String routeHashCode = sc.nextLine();
        try {
            Route route = navigator.getRoute(routeHashCode);
            boolean contains = navigator.contains(route);
            System.out.println("Navigator contains route with hashCode " + routeHashCode + ": " + contains);
        } catch (Exception e) {
            System.out.println("Route with hashCode " + routeHashCode + " isn't exists in navigator");
        }
    }

    private static void getNavigatorSize(Navigator navigator) {
        int size = navigator.size();
        System.out.println("Navigator size: " + size);
    }

    private static void getRouteFromNavigator(Navigator navigator) {
        System.out.print("Enter the hashCode of the route to get: ");
        String routeHashCode = sc.nextLine();
        Route route = navigator.getRoute(routeHashCode);
        System.out.println("Route with hashCode " + routeHashCode + ": " + route);
    }

    private static void chooseRoute(Navigator navigator) {
        System.out.print("Enter the hashCode of the route to choose: ");
        String routeHashCode = sc.nextLine();
        navigator.chooseRoute(routeHashCode);
        System.out.println("Route with hashCode " + routeHashCode + " has been chosen.");
    }

    private static void searchRoute(Navigator navigator) {
        System.out.print("Enter the start point of the route: ");
        String startPoint = sc.nextLine();
        System.out.print("Enter the end point of the route: ");
        String endPoint = sc.nextLine();

        Iterable<Route> routes = navigator.searchRoutes(startPoint, endPoint);
        System.out.println("Found routes:");
        for (Route route : routes) {
            System.out.println(route);
        }
    }

    private static void getFavoriteRoutes(Navigator navigator) {
        System.out.print("Enter the destination point: ");
        String destination = sc.nextLine();

        Iterable<Route> favoriteRoutes = navigator.getFavoriteRoutes(destination);
        System.out.println("Favorite routes:");
        for (Route route : favoriteRoutes) {
            System.out.println(route);
        }
    }

    private static void getTopRoutes(Navigator navigator) {
        Iterable<Route> topRoutes = navigator.getTop3Routes();
        System.out.println("Top 5 routes:");
        for (Route route : topRoutes) {
            System.out.println(route);
        }
    }

    private static Iterable<Route> generateRandomRoutes() {
        ArrayList<Route> routes = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            double distance = random.nextDouble() * 100;
            int popularity = random.nextInt(100);
            boolean isFavorite = random.nextBoolean();
            int numLocationPoints = random.nextInt(10) + 1;
            String[] locationPoints = new String[numLocationPoints];
            for (int j = 0; j < numLocationPoints; j++) {
                locationPoints[j] = "City" + j;
            }
            Route route = new Route(distance, popularity, isFavorite, locationPoints);
            routes.add(route);
        }
        return routes;
    }
}
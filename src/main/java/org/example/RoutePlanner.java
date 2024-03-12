package org.example;
import com.google.maps.DirectionsApi;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.TravelMode;
import com.google.maps.model.Unit;

import java.io.IOException;

public class RoutePlanner implements IDUServiceRoutePlanner {

    private final String API_KEY;

    public RoutePlanner(String apiKey) {
        this.API_KEY = apiKey;
    }

    @Override
    public RouteDetails requestRouteAndEstimates(String startLocation, String targetLocation, String transportMeans) {

        // Validate transportMeans
        if (!isValidTransportMeans(transportMeans)) {
            throw new IllegalArgumentException("Invalid transport means.");
        }

        // Prepare the GeoApiContext with the API key
        GeoApiContext geoApiContext = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        try {
            // Request directions
            DirectionsResult directionsResult = DirectionsApi.newRequest(geoApiContext)
                    .origin(startLocation)
                    .destination(targetLocation)
                    .mode(TravelMode.valueOf(transportMeans.toUpperCase()))  // Convert to TravelMode enum
                    .units(Unit.METRIC)
                    .await();

            // Check if directions result is valid
            if (directionsResult.routes != null && directionsResult.routes.length > 0 &&
                    directionsResult.routes[0].legs != null && directionsResult.routes[0].legs.length > 0 &&
                    directionsResult.routes[0].legs[0].duration != null) {

                DirectionsRoute route = directionsResult.routes[0];
                DirectionsLeg leg = route.legs[0];


                // Extracting the additional route details
                String summary = route.summary;
                String durationText = leg.duration.humanReadable;
                String distanceInMeters = leg.distance.humanReadable;
                String estimatedTimeInSeconds = leg.duration.humanReadable;

                // Creating RouteDetail object with all data

                return new RouteDetails(estimatedTimeInSeconds, distanceInMeters, startLocation, targetLocation );
            } else {
                throw new IllegalStateException("Invalid directions result.");
            }
        } catch (ApiException | InterruptedException | IOException e) {
            // Log the error or throw a custom exception with a meaningful message
            e.printStackTrace(); // Log or replace with appropriate logging mechanism
            throw new RuntimeException("Failed to retrieve route details: " + e.getMessage(), e);
        }
    }

    // Helper method to validate transportMeans
    private boolean isValidTransportMeans(String transportMeans) {
        return transportMeans.equalsIgnoreCase("driving") ||
                transportMeans.equalsIgnoreCase("walking") ||
                transportMeans.equalsIgnoreCase("bicycling"); // ||
//                transportMeans.equalsIgnoreCase("transit");
    }
}

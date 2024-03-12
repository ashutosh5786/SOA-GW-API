package org.example;

public interface IDUServiceRoutePlanner {
    RouteDetails requestRouteAndEstimates(String startLocation, String targetLocation, String transportMeans);
}

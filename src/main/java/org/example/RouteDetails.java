package org.example;
public class RouteDetails {
    private String estimatedTimeInSeconds;
    private String distance;
    private String startAddress;
    private String endAddress;

    public RouteDetails(String estimatedTimeInSeconds, String distance, String startAddress, String endAddress) {
        this.estimatedTimeInSeconds = estimatedTimeInSeconds;
        this.distance = distance;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
    }

}

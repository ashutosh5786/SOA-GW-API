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

    public String getEstimatedTimeInSeconds() {
        return estimatedTimeInSeconds;
    }

    public void setEstimatedTimeInSeconds(String estimatedTimeInSeconds) {
        this.estimatedTimeInSeconds = estimatedTimeInSeconds;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

}

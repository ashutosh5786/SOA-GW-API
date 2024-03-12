package org.example;
public class RouteDetails {
    private long estimatedTimeInSeconds;

    public RouteDetails(long estimatedTimeInSeconds) {
        this.estimatedTimeInSeconds = estimatedTimeInSeconds;
    }

    public long getEstimatedTimeInSeconds() {
        return estimatedTimeInSeconds;
    }

    public void setEstimatedTimeInSeconds(long estimatedTimeInSeconds) {
        this.estimatedTimeInSeconds = estimatedTimeInSeconds;
    }
}

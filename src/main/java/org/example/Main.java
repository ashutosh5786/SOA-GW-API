package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        IDUAppDUService duService = new DUAppDUServiceImpl();
        IDUServiceRoutePlanner routePlanner = new RoutePlanner("AIzaSyC-IcYWYpPLYdDT3_hY96dHz3L-x-KDf-M"); // Change it after done developing

        port(8000); // Server Will Listen on Port 8000

        // Route for authenticating and requesting similar offers
        post("/authenticateAndRequestSimilarOffers", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            JsonArray jobOffers = new Gson().fromJson(req.body(), JsonArray.class);

            return duService.authenticateAndRequestSimilarOffers(username, password, jobOffers);
        });

        // Route for submitting selected job
        post("/submitSelectedJob", (req, res) -> {
            String offerId = req.queryParams("offerId");
            return duService.submitSelectedJob(offerId);
        });

        // Route for requesting route and estimates
        get("/requestRouteAndEstimates", (req, res) -> {

            String startLocation = req.queryParams("origin");
            String targetLocation = req.queryParams("destination");
            String transportMeans = req.queryParams("means");

            // Validate inputs
            if (startLocation == null || startLocation.isEmpty() ||
                    targetLocation == null || targetLocation.isEmpty() ||
                    transportMeans == null || transportMeans.isEmpty()) {
                res.status(400); // Set HTTP status code 400 (Bad Request)
                return "Invalid request parameters";
            }


            RouteDetails routeDetails = routePlanner.requestRouteAndEstimates(startLocation, targetLocation, transportMeans);
            if (routeDetails != null) {
                // Construct response JSON object with route details
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(routeDetails);
                res.status(200); // Set HTTP status code 200 (OK)
                res.type("application/json"); // Set response content type
                return jsonResponse; // Adjust the response based on RouteDetails fields
            } else {
                res.status(500); // Set HTTP status code 500 (Internal Server Error) if route details are not available
                return "Failed to retrieve route details.";
            }
//            return "Route and estimates request processed successfully!";

        });

        // Default route
        get("/", (req, res) -> "Hello There");
    }
}

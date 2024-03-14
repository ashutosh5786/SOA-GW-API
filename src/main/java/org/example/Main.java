package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import io.github.cdimascio.dotenv.Dotenv;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        IDUAppDUService duService = new DUAppDUServiceImpl();
        // Load API key from .env file
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");
        IDUServiceRoutePlanner routePlanner = new RoutePlanner(apiKey); // Change it after done developing

        port(8000); // Server Will Listen on Port 8000

        // Route for authenticating and requesting similar offers
        post("/authenticateAndRequestSimilarOffers", (req, res) -> {
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            JsonArray jobOffers = new Gson().fromJson(req.body(), JsonArray.class);

            String response = duService.authenticateAndRequestSimilarOffers(username, password, jobOffers);

            // Set status code based on response
            if (response.equals("Invalid Username OR Password")) {
                res.status(401); // Set HTTP status code 401 (Unauthorized)
            } else if (response.startsWith("Username parameter") || response.startsWith("Password parameter") || response.startsWith("Job details")) {
                res.status(400); // Set HTTP status code 400 (Bad Request)
            }

            else {
                res.status(200); // Set HTTP status code 200 (OK)
            }

            return response;        });

        // Route for submitting selected job
        post("/submitSelectedJob", (req, res) -> {
            String offerId = req.queryParams("offerId");
            String status =  duService.submitSelectedJob(offerId);

            if (status.startsWith("Selected")) {
                res.status(200);
            }

            else {
                res.status(404);
            }
            return status;
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

            try {
                RouteDetails routeDetails = routePlanner.requestRouteAndEstimates(startLocation, targetLocation, transportMeans);
                if (routeDetails != null) {
                    // Construct response JSON object with route details
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(routeDetails);
                    res.status(200); // Set HTTP status code 200 (OK)
                    res.type("application/json"); // Set response content type
                    return jsonResponse; // Adjust the response based on RouteDetails fields
                } else {
                    res.status(400); // Set HTTP status code 500 (Internal Server Error) if route details are not available
                    return "Failed to retrieve route details.";
                }

            } catch (Exception e) {

                res.status(400);
                return "Invalid Address or Unable to retrieve route details.";
            }

        });

        // Default route
        get("/", (req, res) -> "Hello There");
    }
}

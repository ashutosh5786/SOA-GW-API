package org.example;

import static spark.Spark.*;
import com.google.gson.Gson; // Import Gson library for JSON parsing
import java.util.ArrayList; // Import ArrayList

public class Main {

    private static final String CORRECT_USERNAME = "admin";
    private static final String CORRECT_PASSWORD = "password";
    public static void main(String[] args) {
        // Set port
        port(8000);

        // Route for authenticating and requesting similar offers
        post("/authenticateAndRequestSimilarOffers", (req, res) -> {

            // Check if Body contain data

            if( req.body() == null || req.body().isEmpty()){
                res.status(400); // Set the HTTP status code 400 (Bad Request)
                return "Body is missing";
            }

            // Check if username parameter is missing
            if (!req.queryParams().contains("username")) {
                res.status(400); // Set HTTP status code 400 (Bad Request)
                return "Username parameter is missing";
            }

            // Check if password parameter is missing
            if (!req.queryParams().contains("password")) {
                res.status(400); // Set HTTP status code 400 (Bad Request)
                return "Password parameter is missing";
            }

            // Check if joboffer parameter is missing
//            if (!req.queryParams().contains("joboffer")) {
//                res.status(400); // Set HTTP status code 400 (Bad Request)
//                return "Joboffer parameter is missing";
//            }

            // Proceed with authentication and request for similar offers logic
            String requestBody = req.body();
            String username = req.queryParams("username");
            String password = req.queryParams("password");
//            String[] jobOffer = new String[]{req.queryParams("joboffer")};
//            String jobOffer = req.queryParams("joboffer");
            if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD))
                res.status(200);  // Set HTTP request to 200 as cred are correct ALL OKK
            else {
                res.status(401);
                return "Invalid Username OR Password";  // Set HTTP request to 401 as Unauthorized
            }
            String[] jobOffer = new Gson().fromJson(req.body(), String[].class);

            return "Authentication and request for similar offers completed successfully!" + jobOffer;
        });

        // Route for submitting selected job
        post("/submitSelectedJob", (req, res) -> {
            // Handle submission of selected job
            // Implement submission of selected job logic here

            return "Selected job submitted successfully!";
        });

        // Route for requesting route and estimates
        get("/requestRouteAndEstimates", (req, res) -> {
            // Handle request for route and estimates
            // Implement request for route and estimates logic here

            return "Route and estimates request processed successfully!";
        });

        // Default route
        get("/", (req, res) -> "Hello, World!");
    }
}

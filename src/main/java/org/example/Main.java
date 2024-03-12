package org.example;

import static spark.Spark.*;
import com.google.gson.Gson; // Import Gson library for JSON parsing
import com.google.gson.JsonArray;
import com.google.gson.*;

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
                return "Job Details are missing: [{'title':'value', 'description':'value', 'pay':value, 'origin':'value', 'destination':'value'}]";
            }

            // Check if username parameter is missing
            if (!req.queryParams().contains("username")) {
                res.status(400); // Set HTTP status code 400 (Bad Request)
                return "Username parameter is missing: username";
            }

            // Check if password parameter is missing
            if (!req.queryParams().contains("password")) {
                res.status(400); // Set HTTP status code 400 (Bad Request)
                return "Password parameter is missing: password";
            }

            // Proceed with authentication and request for similar offers logic
            String username = req.queryParams("username");
            String password = req.queryParams("password");
            if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD))
                res.status(200);  // Set HTTP request to 200 as cred are correct ALL OKK
            else {
                res.status(401);
                return "Invalid Username OR Password";  // Set HTTP request to 401 as Unauthorized
            }
            JsonArray jobOffer = new Gson().fromJson(req.body(), JsonArray.class);

            for (JsonElement element : jobOffer) {
                if (!validateJobOfferObject(element.getAsJsonObject())) {
                    // If validation fails for any object, return an error response
                    res.status(400); // Set HTTP status code 400 (Bad Request)
                    return "Invalid job offer data";
                }
                else {
                    return getJsonElements();
                }
            }
                return "Something Went Wrong";

        });

        // Route for submitting selected job
        post("/submitSelectedJob", (req, res) -> {
            // Checking for OfferID
            if (!req.queryParams().contains("offerId")) {
                res.status(400); // Set HTTP status code 400 (Bad Request)
                return "OfferId parameter is missing: offerId";
            }
            String offerId = req.queryParams("offerId");
            JsonArray jobOffers = getJsonElements();

            // Iterate through job offers to find a match for the offerId
            boolean offerIdFound = false;
            for (JsonElement element : jobOffers) {
                JsonObject jobOffer = element.getAsJsonObject();
                if (jobOffer.get("offerId").getAsString().equals(offerId)) {
                    offerIdFound = true;
                    break;
                }
            }

            if (offerIdFound) {
                // Job offer with specified offerId found
                // Perform any additional logic here if needed
                return "Selected job submitted successfully!";
            } else {
                // No job offer found with the specified offerId
                res.status(404); // Set HTTP status code 404 (Not Found)
                return "Error: No job offer found with offerId " + offerId;
            }
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

    private static JsonArray getJsonElements() {
        JsonArray jobOfferArray = new JsonArray();

        // Create some job offer objects and add them to the JSON array
        JsonObject jobOffer1 = new JsonObject();
        jobOffer1.addProperty("offerId", 1001);
        jobOffer1.addProperty("title", "Restaurant 1");
        jobOffer1.addProperty("description", "Standard Delivery");
        jobOffer1.addProperty("origin", "London");
        jobOffer1.addProperty("Destination", "Frankfurt");
        jobOffer1.addProperty("pay", 5);
        jobOfferArray.add(jobOffer1);

        JsonObject jobOffer2 = new JsonObject();
        jobOffer2.addProperty("offerId", 1002);
        jobOffer2.addProperty("title", "Restaurant 2");
        jobOffer2.addProperty("description", "Fast Pace Delivery");
        jobOffer2.addProperty("origin", "London");
        jobOffer2.addProperty("Destination", "Paris");
        jobOffer2.addProperty("pay", 7);
        jobOfferArray.add(jobOffer2);

        JsonObject jobOffer3 = new JsonObject();
        jobOffer3.addProperty("offerId", 1003);
        jobOffer3.addProperty("title", "Restaurant 3");
        jobOffer3.addProperty("description", "Within 10 min Delivery");
        jobOffer3.addProperty("origin", "London");
        jobOffer3.addProperty("Destination", "Berlin");
        jobOffer3.addProperty("pay", 10);
        jobOfferArray.add(jobOffer3);
        return jobOfferArray;
    }

    // Method to validate a single job offer object
    private static boolean validateJobOfferObject(JsonObject jobOfferObject) {
        // Check if the object contains the required fields
        if (!jobOfferObject.has("title") || !jobOfferObject.has("description") || !jobOfferObject.has("pay") || !jobOfferObject.has("origin") || !jobOfferObject.has("destination")) {
            return false; // Missing required fields
        }

        // Check if the types of the fields are as expected
        return  jobOfferObject.get("title").isJsonPrimitive() &&
                jobOfferObject.get("description").isJsonPrimitive() &&
                jobOfferObject.get("pay").getAsJsonPrimitive().isNumber() &&
                jobOfferObject.get("origin").isJsonPrimitive() &&
                jobOfferObject.get("destination").isJsonPrimitive(); // Incorrect field types
    }
}



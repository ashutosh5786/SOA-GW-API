package org.example;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        IDUAppDUService duService = new DUAppDUServiceImpl();

        port(8000);

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
        get("/requestRouteAndEstimates", (req, res) -> "Route and estimates request processed successfully!");

        // Default route
        get("/", (req, res) -> "Hello There");
    }
}

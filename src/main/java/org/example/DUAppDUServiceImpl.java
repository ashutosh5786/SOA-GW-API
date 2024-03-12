package org.example;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DUAppDUServiceImpl implements IDUAppDUService {
    private static final String CORRECT_USERNAME = "admin";
    private static final String CORRECT_PASSWORD = "password";

    @Override
    public String authenticateAndRequestSimilarOffers(String username, String password, JsonArray jobOffers) {
        // Check if username is missing
        if (username == null || username.isEmpty()) {
            return "Username parameter is missing";
        }

        // Check if password is missing
        if (password == null || password.isEmpty()) {
            return "Password parameter is missing";
        }

        // Check if jobOffers is missing or empty
        if (jobOffers == null || jobOffers.isEmpty()) {
            return "Job details are missing";
        }

        // Authentication logic
        if (username.equals(CORRECT_USERNAME) && password.equals(CORRECT_PASSWORD)) {
            // Validate job offers
            for (JsonElement element : jobOffers) {
                if (!validateJobOfferObject(element.getAsJsonObject())) {
                    return "Invalid job offer data";
                }
            }
            // Proceed with request for similar offers logic
            return getJsonElements().toString(); // Return similar job offers
        } else {
            return "Invalid Username OR Password";
        }
    }

    @Override
    public String submitSelectedJob(String offerId) {
        // Check if OfferId is missing
        if (offerId == null || offerId.isEmpty()) {
            return "OfferId parameter is missing";
        }

        // Get job offers
        JsonArray jobOffers = getJsonElements();

        // Iterate through job offers to find a match for the offerId
        boolean offerIdFound = false;
        for (JsonElement element : jobOffers) {
            JsonObject jobOffer = element.getAsJsonObject();
            if (jobOffer.get("offerId").getAsString().equals(offerId)) {
                offerIdFound = true;
                // Optionally, mark the job offer as selected by the driver
                // jobOffer.addProperty("selected", true);
                break;
            }
        }

        if (offerIdFound) {
            // Job offer with specified offerId found
            // Perform any additional logic here if needed
            return "Selected job with offerId " + offerId + " submitted successfully!";
        } else {
            // No job offer found with the specified offerId
            return "Error: No job offer found with offerId " + offerId;
        }

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

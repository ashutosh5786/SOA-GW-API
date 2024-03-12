package org.example;
import com.google.gson.JsonArray;

public interface IDUAppDUService {
    String authenticateAndRequestSimilarOffers(String username, String password, JsonArray jobOffers);
    String submitSelectedJob(String offerId);
}

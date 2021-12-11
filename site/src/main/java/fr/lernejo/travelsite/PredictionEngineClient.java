package fr.lernejo.travelsite;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface PredictionEngineClient {
    @GET("api/temperatures")
    Call<List<Country>> getTemperaturePrediction(@Query("userCountry") String country);
}

package fr.lernejo.travelsite;

import fr.lernejo.prediction.TemperaturePrediction;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PredictionEngineClient {
    @GET("api/temperature")
    Call<TemperaturePrediction> getTemperaturePrediction(@Query("country") String country);
}

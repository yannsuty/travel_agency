package fr.lernejo.travelsite;

import fr.lernejo.prediction.TemperaturePrediction;
import fr.lernejo.travelsite.exception.ServerNotFoundException;
import retrofit2.Call;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PredictionEngineService {
    private final PredictionEngineClient predictionEngineClient;

    public PredictionEngineService(PredictionEngineClient predictionEngineClient) {
        this.predictionEngineClient = predictionEngineClient;
    }

    public TemperaturePrediction getTemperaturePrediction(String country) {
        Call<TemperaturePrediction> retrofitCall = predictionEngineClient.getTemperaturePrediction(country);
        try {
            return retrofitCall.execute().body();
        } catch (IOException e) {
            throw new ServerNotFoundException(e.getMessage());
        }
    }

}

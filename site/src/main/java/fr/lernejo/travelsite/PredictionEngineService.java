package fr.lernejo.travelsite;

import retrofit2.Call;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PredictionEngineService {
    private final PredictionEngineClient predictionEngineClient;

    public PredictionEngineService(PredictionEngineClient predictionEngineClient) {
        this.predictionEngineClient = predictionEngineClient;
    }

    public Call<List<Country>> getTemperaturePrediction(String country) {
        return predictionEngineClient.getTemperaturePrediction(country);
    }

}

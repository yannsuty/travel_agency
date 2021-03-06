package fr.lernejo.travelsite;

import fr.lernejo.travelsite.prediction.TemperaturePrediction;
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
            TemperaturePrediction temperaturePrediction = retrofitCall.execute().body();
            temperaturePrediction.temperatures();
            return temperaturePrediction;
        } catch (IOException | NullPointerException e) {
            throw new ServerNotFoundException(e.getMessage());
        }
    }

    public Travel getCountryPrediction(String country) {
        TemperaturePrediction prediction = getTemperaturePrediction(country);
        Double temperature = (prediction.temperatures().get(0).temperature()
            +prediction.temperatures().get(1).temperature())
            /2;
        return new Travel(country,temperature);
    }
}

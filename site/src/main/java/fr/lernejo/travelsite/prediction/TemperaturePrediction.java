package fr.lernejo.travelsite.prediction;

import java.util.List;

public record TemperaturePrediction(String country, List<DateTemperature> temperatures) {
}

package fr.lernejo.prediction;

import java.util.List;

public record TemperaturePrediction(String country, List<DateTemperature> temperatures) {
}

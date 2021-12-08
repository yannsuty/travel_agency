package fr.lernejo.prediction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureControllerTest {
    final TemperatureController temperatureController = new TemperatureController();

    @Test
    void getTemperaturePrediction() {
        TemperaturePrediction temperaturePrediction = temperatureController.getTemperaturePrediction("France");
        Assertions.assertEquals("France", temperaturePrediction.country());
        Assertions.assertThrows(UnknownCountryException.class, ()->{
            temperatureController.getTemperaturePrediction("Fr");
        });
    }
}

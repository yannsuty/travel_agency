package fr.lernejo.prediction;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class TemperatureController {
    private final TemperatureService temperatureService = new TemperatureService();

    @GetMapping("/api/temperature")
    public TemperaturePrediction getTemperaturePrediction(@RequestParam String country) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd");
        List<DateTemperature> liste = new ArrayList<>();
        liste.add(new DateTemperature(sdf.format(calendar.getTime()),temperatureService.getTemperature(country)));
        calendar.add(Calendar.DATE,-1);
        liste.add(new DateTemperature(sdf.format(calendar.getTime()),temperatureService.getTemperature(country)));
        return new TemperaturePrediction(country, liste);
    }

}

package fr.lernejo.travelsite;

import fr.lernejo.travelsite.prediction.TemperaturePrediction;
import fr.lernejo.travelsite.exception.CannotReadCountryFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@RestController
public class TravelController {
    private final PredictionEngineService predictionEngineService;
    private final UserRepository userRepository = UserRepository.getInstance();
    private final List<String> country_liste;

    public TravelController(PredictionEngineService predictionEngineService) {
        this.predictionEngineService=predictionEngineService;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("countries.txt");
        try {
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            Stream<String> lines = content.lines();
            country_liste = lines.toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CannotReadCountryFile(e.getMessage());
        }
    }

    @GetMapping(value="/api/travels")
    @ResponseBody
    public List<Travel> getTravels(@RequestParam String userName) {
        List<Travel> travels = new ArrayList<>();
        User user = userRepository.findUser(userName);
        Double userTemperature = predictionEngineService.getCountryPrediction(user.userCountry()).temperature();
        for(String country: country_liste) {
            Travel prediction = predictionEngineService.getCountryPrediction(country);
            if (user.weatherExpectation().equals(User.WeatherExpectation.WARMER) && prediction.temperature() > userTemperature+user.minimumTemperatureDistance())
                travels.add(prediction);
            else if (user.weatherExpectation().equals(User.WeatherExpectation.COLDER) && prediction.temperature() < userTemperature-user.minimumTemperatureDistance())
                travels.add(prediction);
        }
        return travels;
    }

    @GetMapping("/api/predictions")
    @ResponseBody
    public TemperaturePrediction getTemperatures(@RequestParam String country) {
        System.out.println("TravelController : getTemperatures");
        return predictionEngineService.getTemperaturePrediction(country);
    }

}

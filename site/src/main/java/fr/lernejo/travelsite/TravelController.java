package fr.lernejo.travelsite;

import org.springframework.http.MediaType;
import fr.lernejo.prediction.TemperaturePrediction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TravelController {
    private final PredictionEngineService predictionEngineService;
    private final UserRepository userRepository = UserRepository.getInstance();
    private final Map<String, Country> country_liste = new HashMap<>();

    public TravelController(PredictionEngineService predictionEngineService) {
        this.predictionEngineService=predictionEngineService;
        country_liste.put("France",new Country("France",10.2));
        country_liste.put("Espagne",new Country("Espagne",20.2));
    }

    @GetMapping(value="/api/travels", produces=MediaType.APPLICATION_JSON_VALUE)
    public List<Country> getTravels(@RequestParam String userName) {
        User user = userRepository.findUser(userName);
        return getTravelCountries(
            user.weatherExpectation(), //WARMER or COLDER
            country_liste.get(user.userCountry()).temperature(), //temperature in double
            user.minimumTemperatureDistance()
        );
    }

    @GetMapping("/api/predictions")
    @ResponseBody
    public TemperaturePrediction getTemperatures(@RequestParam String country) {
        System.out.println("TravelController : getTemperatures");
        return predictionEngineService.getTemperaturePrediction(country);
    }

    private List<Country> getTravelCountries(String weather, double temperature, int minimumTemperatureDistance) {
        List<Country> liste = new ArrayList<>();

        if (weather.equals("WARMER")) {
            country_liste.forEach(
                (name_country,country)->{
                    if (country.temperature()>temperature+minimumTemperatureDistance) {
                        liste.add(country);
                    }
                }
            );
        } else {
            country_liste.forEach(
                (name_country,country)->{
                    if (country.temperature()<temperature-minimumTemperatureDistance) {
                        liste.add(country);
                    }
                }
            );
        }

        return liste;
    }
}

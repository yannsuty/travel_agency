package fr.lernejo.travelsite;

import fr.lernejo.travelsite.exception.UserNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class InscriptionController {
    private final List<User> user_liste = new ArrayList<>();
    private final Map<String, Country> country_liste = new HashMap<>();

    public InscriptionController() {
        country_liste.put("France",new Country("France",10.2));
        country_liste.put("Espagne",new Country("Espagne",20.2));

    }

    @PostMapping("/api/inscription")
    public void inscription(@RequestBody User user) {
        user_liste.add(user);
    }

    @GetMapping("/api/travels")
    public List<Country> getTravels(@RequestParam String userName) {
        User user = findUser(userName);
        return getTravelCountries(
            user.weatherExpectation(), //WARMER or COLDER
            country_liste.get(user.userCountry()).temperature(), //temperature in double
            user.minimumTemperatureDistance()
        );
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

    private User findUser(String userName) {
        for (User user : user_liste)
            if (user.userName().equals(userName))
                return user;

        throw new UserNotFoundException();
    }
}

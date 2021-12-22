package fr.lernejo.travelsite;
public record User(String userEmail, String userName, String userCountry, WeatherExpectation weatherExpectation, int minimumTemperatureDistance) {
    enum WeatherExpectation {
        COLDER, WARMER;
    }
}

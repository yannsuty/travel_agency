package fr.lernejo.travelsite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.travelsite.prediction.DateTemperature;
import fr.lernejo.travelsite.prediction.TemperaturePrediction;
import org.junit.jupiter.api.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.LinkedList;
import java.util.List;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@MockServerSettings(ports = {7080})
public class SiteControllerMockTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void siteControllerColderTravel() throws Exception {
        createMockPredictionEngine();
        User user = new User("test@mail.com", "test", "France",
            User.WeatherExpectation.COLDER, 10);

        mvc
            .perform(MockMvcRequestBuilders.post("/api/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=test"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void siteControllerWarmerTravel() throws Exception {
        createMockPredictionEngine();
        User user = new User("test2@mail.com", "test2", "France",
            User.WeatherExpectation.WARMER, 10);

        mvc
            .perform(MockMvcRequestBuilders.post("/api/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
            .andExpect(MockMvcResultMatchers.status().isOk());

        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=test2"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void siteControllerPrediction() throws Exception {
        createMockPredictionEngine();
        mvc
            .perform(MockMvcRequestBuilders.get("/api/predictions?country=France"))
            .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void siteControllerUserNotFound() throws Exception {
        createMockPredictionEngine();
        mvc
            .perform(MockMvcRequestBuilders.get("/api/travels?userName=test3"))
            .andExpect(MockMvcResultMatchers.status().is(416));
    }

    private void createMockPredictionEngine() {
        List<DateTemperature> tempList = new LinkedList<>();
        tempList.add(new DateTemperature("2022-01-10", 18));
        tempList.add(new DateTemperature("2022-01-09", 9));
        TemperaturePrediction temp = new TemperaturePrediction("France", tempList);
        try {
            new MockServerClient("localhost", 7080)
                .when(
                    request()
                        .withMethod("GET")
                        .withPath("/api/temperature"))
                .respond(
                    response()
                        .withStatusCode(200)
                        .withHeaders(
                            new Header("Content-Type", "application/json"))
                        .withBody(new ObjectMapper().writeValueAsString(temp))
                );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

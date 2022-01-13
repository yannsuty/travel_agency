package fr.lernejo.travelsite;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.lernejo.travelsite.exception.ServerNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockserver.junit.jupiter.MockServerSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@MockServerSettings(ports = {7080})
public class SiteControllerTest {
    @Autowired
    MockMvc mvc;
    @Test
    void siteControllerServerNotFound() throws Exception {
        User user = new User("test@mail.com", "test", "France",
            User.WeatherExpectation.COLDER, 10);

        mvc
            .perform(MockMvcRequestBuilders.post("/api/inscription")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
            .andExpect(MockMvcResultMatchers.status().isOk());

//        mvc
//            .perform(MockMvcRequestBuilders.get("/api/travels?userName=test"))
//            .andExpect(result -> assertTrue(result.getResolvedException() instanceof ServerNotFoundException));
    }
}

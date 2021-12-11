package fr.lernejo.travelsite;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class InscriptionController {
    private final UserRepository userRepository = UserRepository.getInstance();

    @PostMapping("/api/inscription")
    public void inscription(@RequestBody User user) {
        userRepository.addUser(user);
    }
}

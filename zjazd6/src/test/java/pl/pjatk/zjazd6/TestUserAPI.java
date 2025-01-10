package pl.pjatk.zjazd6;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import pl.pjatk.zjazd6.model.User;
import pl.pjatk.zjazd6.service.UserService;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserAPI {

    @LocalServerPort
    private int port;

    @Autowired
    private UserService userService;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void shouldReturnAllUsers() {
        userService.createUser("Masny Bombel", "masny@bomebl.pl");
        userService.createUser("Sebastian Nowak", "sebastian@nowak.pl");

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/users", String.class);

        // Sprawdzenie statusu i zawartości
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody().contains("Masny Bombel"));
        assertTrue(response.getBody().contains("Sebastian Nowak"));
    }

    @Test
    public void shouldReturnUserById() {
        User user = userService.createUser("Masny Bombel", "masny@bombel.pl");
        restTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);
        Long userId = user.getId();

        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/users/" + userId,
                User.class);

        // Sprawdzenie statusu i zawartości
        assertEquals(200, response.getStatusCode().value());
        assertEquals("Masny Bombel", response.getBody().getName());

        ResponseEntity<String> errorResponse = restTemplate.getForEntity("http://localhost:" + port + "/users/999", String.class);

        // Sprawdzenie statusu błędu
        assertEquals(404, errorResponse.getStatusCode().value());
    }

    @Test
    public void shouldCreateUser() {
        User user = userService.createUser("Masny Bombel", "masny@bombel.pl");

        ResponseEntity<User> response = restTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);

        // Sprawdzenie statusu i danych użytkownika
        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getBody().getId());
        assertEquals("Masny Bombel", response.getBody().getName());
        assertEquals("masny@bombel.pl", response.getBody().getEmail());
    }

    @Test
    public void shouldReturnBadRequestWhenUserDataIsInvalid() {
        User invalidUser = userService.createUser("Masny Bombel", "");

        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/users", invalidUser, String.class);

        // Sprawdzanie statusu błędu
        assertEquals(400, response.getStatusCode().value());
    }

    @Test
    public void shouldUpdateUser() {
        User user = userService.createUser("Masny Bombel", "masny@bombel.pl");
        restTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);

        User updatedUser = userService.createUser("Masny Bombelek", "masnyfest@bombel.pl");

        restTemplate.put("http://localhost:" + port + "/users/1", updatedUser);

        // Sprawdzenie poprawności zmian
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:" + port + "/users/1", User.class);
        assertEquals("Masny Bombelek", response.getBody().getName());
        assertEquals("masnyfest@bombel.pl", response.getBody().getEmail());
    }

    @Test
    public void shouldReturnNotFoundWhenUserToUpdateDoesNotExist() {
        User updatedUser = userService.createUser("Masny Bombel", "masny@bombel.pl");

        restTemplate.put("http://localhost:" + port + "/users/999", updatedUser);

        // Sprawdzenie statusu błędu
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/users/999", String.class);
        assertEquals(404, response.getStatusCode().value());
    }

    @Test
    public void shouldDeleteUser() {
        User user = userService.createUser("Masny Bombel", "masny@bombel.pl");
        restTemplate.postForEntity("http://localhost:" + port + "/users", user, User.class);

        restTemplate.delete("http://localhost:" + port + "/users/1");

        // Sprawdzenie, czy użytkownik został usunięty
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/users/1", String.class);
        assertEquals(404, response.getStatusCode().value());
    }


}

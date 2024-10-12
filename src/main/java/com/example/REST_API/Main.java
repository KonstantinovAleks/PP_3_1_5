package com.example.REST_API;

import com.example.REST_API.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class Main {

    private final String URL = "http://94.198.50.185:7081/api/users";

    private final RestTemplate restTemplate = new RestTemplate();

    private final HttpHeaders httpHeaders = new HttpHeaders();

    static String result = "";

    public Main() {
        String sessionId = getAllUsers();
        httpHeaders.set("cookie", sessionId);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.createUser();
        main.updateUser();
        main.deleteUser(3L);
        if (result.length() != 18) {
            System.out.println("Ошибка");
        } else {
            System.out.println("Итоговый код - " + result);
        }
    }

    public String getAllUsers() {
        ResponseEntity<String> forEntyti = restTemplate.getForEntity(URL, String.class);
        return String.join(";", Objects.requireNonNull(forEntyti.getHeaders().get("set-cookie")));
    }

    public void createUser() {
        User user = new User("James", "Brown", (byte) 42);
        user.setId(3L);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        String request = restTemplate.postForEntity(URL, entity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }

    public void updateUser() {
        User user = new User("Thomas", "Shelby", (byte) 42);
        user.setId(3L);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        String response = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class).getBody();
        result = result + response;
        new ResponseEntity<>(response, HttpStatus.OK);
    }

    public void deleteUser(@PathVariable Long id) {
        HttpEntity<User> entity = new HttpEntity<>(httpHeaders);
        String request = restTemplate.exchange(URL + "/" + id, HttpMethod.DELETE, entity, String.class).getBody();
        result = result + request;
        new ResponseEntity<>(request, HttpStatus.OK);
    }
}

package com.epam.brest.course2015.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.Scanner;


/**
 * Created by alexander on 29.10.15.
 */
@Component
public class Main {

    Scanner sc = new Scanner(System.in);
    ClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    RestTemplate restTemplate = new RestTemplate(requestFactory);
    @Value("${user.protocol}://${user.host}:${user.port}/${user.prefix}/")
    private String url;
    @Value("${point.users}")
    private String urlUsers;
    @Value("${point.userByLogin}")
    private String urlUserByLogin;
    @Value("${point.userById}")
    private String urlUserById;
    @Value("${point.userAdd}")
    private String urlAddUser;
    @Value("${point.userDelete}")
    private String urlDeleteUser;
    @Value("${point.user}")
    private String urlUser;

    {
        restTemplate.setErrorHandler(new CustomResponseErrorHandler());
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        Main main = ctx.getBean(Main.class);
        main.menu();
    }

    private void printOptions() {
        System.out.println("=================================");
        System.out.println("| Options:                      |");
        System.out.println("|        1. Get all users       |");
        System.out.println("|        2. Get user by login   |");
        System.out.println("|        3. Get user by id      |");
        System.out.println("|        4. Add new user        |");
        System.out.println("|        5. Delete user         |");
        System.out.println("|        6. Update user         |");
        System.out.println("|        7. Exit                |");
        System.out.println("=================================");
    }
    private void menu() {
        int swValue = 0;
        System.out.println("=================================");
        System.out.println("|   MENU SELECTION DEMO         |");
        while (swValue != 7) {
            printOptions();
            System.out.print("Select option: ");
            if (sc.hasNextInt()) {
                swValue = sc.nextInt();
                checkValue(swValue);
            } else {
                System.out.println ("Bad Value: " + sc.next());
            }
        }
    }
    private void checkValue(int item) {
        switch (item) {
            case 1:
                getAllUsers();
                break;
            case 2:
                getUserByLogin();
                break;
            case 3:
                getUserById();
                break;
            case 4:
                addUser();
                break;
            case 5:
                deleteUser();
                break;
            case 6:
                updateUser();
                break;
            case 7:
                System.out.println("Exit.");
                break;
            default:
                System.out.println("Invalid Selection: " + item);
                break;
        }
    }
    private void getUserByLogin() {
        String userLogin = getString("     Enter user's login: ");
        ResponseEntity responseEntity;
        try {
            responseEntity = restTemplate.getForEntity(url + urlUserByLogin + "/" + userLogin, Object.class);
            System.out.println("     User: " + responseEntity.getBody());
        } catch (CustomException ex) {
            System.out.println("     ERROR: " + ex.getMessage());
            notExistingUser("login= " + userLogin);
        }
    }
    private void getAllUsers() {
        ResponseEntity responseEntity = restTemplate.getForEntity(url + urlUsers, Object.class);
        System.out.println("Users: " + responseEntity.getBody());
    }
    private void addUser() {
        String login = getString("     Enter new user's login: ");
        String password = getString("     Set new user's password: ");
        try {
            ResponseEntity responseEntity = restTemplate.postForEntity(url + urlAddUser + "/" + login + "/" + password, Object.class, Integer.class);
            System.out.println("New user added. Id is: " + responseEntity.getBody());
        } catch (CustomException ex) {
            System.out.println("     ERROR: " + ex.getMessage());
            System.out.println("User already exists");
        }
    }
    private void deleteUser() {
        Integer userId = getId("Enter Id of deleting user: ");
        try {
            ResponseEntity responseEntity = restTemplate.getForEntity(url + urlUserById + "/" + userId, Object.class);
            restTemplate.delete(url + urlDeleteUser + "/" + userId);
            System.out.println("User with Id= " + userId + " deleted.");
        } catch (CustomException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            notExistingUser("id= " + userId);
        }

    }
    private void updateUser() {
        Integer userId = getId("Enter Id of updating user: ");
        String password = getString("     Set new user's password: ");
        try {
            ResponseEntity responseEntity = restTemplate.getForEntity(url + urlUserById + "/" + userId, Object.class);
            restTemplate.put(url + urlUser + "/" + userId + "/" + password, Object.class);
            System.out.println("User's password with Id= " + userId + " is updated.");
        } catch (CustomException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            notExistingUser("id = " + userId);
        }
    }

    private void getUserById() {
        Integer userId = getId("Enter user's Id: ");
        try {
            ResponseEntity responseEntity = restTemplate.getForEntity(url + urlUserById + "/" + userId, Object.class);
            System.out.println("User with Id= " + userId + " is: " + responseEntity.getBody());
        } catch (CustomException ex) {
            System.out.println("ERROR: " + ex.getMessage());
            notExistingUser("id = " + userId);
        }
    }

    private Integer getId (String message) {
        Integer userId = 0;
        System.out.print(message);
        while (userId == 0) {
            if (sc.hasNextInt()) {
                userId = sc.nextInt();
            } else {
                System.out.println("Bad Value. Id must be Integer.");
                sc.next();
            }
        }
        return userId;
    }
    private String getString (String message) {
        String string = "";
        System.out.print(message);
        if (sc.hasNextLine()) {
            string = sc.next();
        }
        return string;
    }
    private void notExistingUser (String message) {
        System.out.println("Wrong user data. User with " + message + " does not exist");
    }
}

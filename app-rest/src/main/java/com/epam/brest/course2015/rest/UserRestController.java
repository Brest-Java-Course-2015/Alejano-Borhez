package com.epam.brest.course2015.rest;

import com.epam.brest.course2015.domain.User;
//import com.epam.brest.course2015.dto.UserDto;
import com.epam.brest.course2015.dto.UserDto;
import com.epam.brest.course2015.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by alexander on 18.10.15.
 */

@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @RequestMapping (value = "/users", method = RequestMethod.GET)
    public @ResponseBody List<User> getUsers() {
        return userService.getAllUsers();
    }
    @RequestMapping (value = "/user", method = RequestMethod.POST)
    public @ResponseBody Integer addUser (@RequestBody User user) {
        return userService.addUser(user);
    }


    @RequestMapping (value = "/user/{id}/{password}", method = RequestMethod.PUT)
    @ResponseStatus (value = HttpStatus.OK)
    public void updateUser (@PathVariable(value = "id") Integer id,
                            @PathVariable(value = "password") String password) {
        userService.updateUser(new User(id, password));
    }

    @RequestMapping (value = "/user/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus (value = HttpStatus.OK)
    public void deleteUser (@PathVariable(value = "id") Integer id) {
        userService.deleteUser(id);
    }

    @RequestMapping (value = "/user/bylogin/{login}", method = RequestMethod.GET)
    public @ResponseBody User getUserByLogin(@PathVariable(value = "login") String login) {
        return userService.getUserByLogin(login);
    }

    @RequestMapping (value = "/user/byid", method = RequestMethod.GET)
    public @ResponseBody User getUserById(@RequestParam(value = "id") Integer id) {
        return userService.getUserById(id);
    }

    @RequestMapping (value = "/useradd/{login}/{password}")
    public @ResponseBody Integer addUserByLogin (@PathVariable(value = "login") String login,
                                          @PathVariable(value = "password") String password) {
        return userService.addUser(new User(login, password));
    }

    @RequestMapping (value = "/userdto", method = RequestMethod.GET)
    public @ResponseBody UserDto getUserDto() {
        return userService.getUserDto();
    }


}

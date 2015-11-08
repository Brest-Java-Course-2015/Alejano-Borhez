package com.epam.brest.course2015.web;

import com.epam.brest.course2015.domain.User;
import com.epam.brest.course2015.dto.UserDto;
import com.epam.brest.course2015.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alexander on 30.10.15.
 */

@Controller
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String init() {
        return "redirect:/userList";
    }

    @RequestMapping("/userList")
    public ModelAndView getUserDto() {
        UserDto dto = userService.getUserDto();
        LOGGER.debug("users.size = " + dto.getUsers().size());
        return new ModelAndView("userList", "dto", dto);
    }

    @RequestMapping("/inputUser")
    public ModelAndView formUser() {
        return new ModelAndView("inputForm", "user", new User());
    }

    @RequestMapping("/submitUser")
    public String addUser(@RequestParam("login") String login, @RequestParam("password") String password) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        userService.addUser(user);
        return "redirect:/userList";
    }
}

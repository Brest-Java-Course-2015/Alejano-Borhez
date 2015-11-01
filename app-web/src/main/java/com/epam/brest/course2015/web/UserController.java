package com.epam.brest.course2015.web;

import com.epam.brest.course2015.dto.UserDto;
import com.epam.brest.course2015.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by alexander on 30.10.15.
 */

@Controller
public class UserController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserService userService;

    @RequestMapping("/userList")
    public ModelAndView getUserDto() {
        UserDto dto = userService.getUserDto();
        LOGGER.debug("users.size = " + dto.getUsers().size());
        return new ModelAndView("userlist", "dto", dto);
    }
}

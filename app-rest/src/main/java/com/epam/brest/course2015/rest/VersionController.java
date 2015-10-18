package com.epam.brest.course2015.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by alexander on 16.10.15.
 */
@RestController
public class VersionController {

    public static final String VERSION = "1.0";

    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String getVersion() {
        return VERSION;
    }

}
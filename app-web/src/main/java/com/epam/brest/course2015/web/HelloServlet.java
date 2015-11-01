package com.epam.brest.course2015.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by alexander on 30.10.15.
 */
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        resp.setContentType("text/plain");
        PrintWriter writer = resp.getWriter();
        try {
            writer.print("Hello, ");
            writer.print(name);
        } finally {
            writer.close();
        }
    }
}

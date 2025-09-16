package org.example.controller;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.User;
import org.example.service.UserService;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Получаем сервис из ServletContext (ручная "инъекция")
        Object svc = config.getServletContext().getAttribute("userService");
        if (!(svc instanceof UserService)) {
            throw new ServletException("UserService not initialized");
        }
        this.userService = (UserService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            List<User> users = userService.listUsers();
            req.setAttribute("users", users);

            // Передаём управление JSP
            req.getRequestDispatcher("/users.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private String escape(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        try {
            userService.addUser(name, email);
            resp.sendRedirect(req.getContextPath() + "/users");
        } catch (SQLException | IllegalArgumentException e) {
            resp.setContentType("text/html; charset=UTF-8");
            resp.getWriter().println("<p>Error: " + escape(e.getMessage()) + "</p>");
        }
    }
}
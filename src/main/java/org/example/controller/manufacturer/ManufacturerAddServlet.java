package org.example.controller.manufacturer;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Category;
import org.example.entity.Manufacturer;
import org.example.service.CategoryService;
import org.example.service.ManufacturerService;

import java.io.IOException;
import java.sql.SQLException;

public class ManufacturerAddServlet extends HttpServlet {

    private ManufacturerService manufacturerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // Получаем сервис из ServletContext (ручная "инъекция")
        Object svc = config.getServletContext().getAttribute("manufacturerService");
        if (!(svc instanceof ManufacturerService)) {
            throw new ServletException("manufacturerService not initialized");
        }
        this.manufacturerService = (ManufacturerService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/manufacturer/manufacturer_add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String country = req.getParameter("country");
        String email = req.getParameter("email");

        Manufacturer manufacturer = new Manufacturer(name.trim(), country.trim(), email.trim());

        try {
            manufacturerService.add(manufacturer);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/manufacturer/findAll");
    }
}

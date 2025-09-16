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

public class ManufacturerEditServlet extends HttpServlet {
    private ManufacturerService manufacturerService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Object svc = config.getServletContext().getAttribute("manufacturerService");
        if (!(svc instanceof ManufacturerService)) {
            throw new ServletException("manufacturerService not initialized");
        }
        this.manufacturerService = (ManufacturerService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    Manufacturer manufacturer = manufacturerService.findById(id);
                    req.setAttribute("manufacturer", manufacturer);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid manufacturer id", e);
                }
            }
        }

        req.getRequestDispatcher("/manufacturer/manufacturer_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    String name = req.getParameter("name");
                    String country = req.getParameter("country");
                    String email = req.getParameter("email");
                    Manufacturer manufacturer = new Manufacturer(id, name.trim(), country.trim(), email.trim());
                    manufacturerService.update(manufacturer);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/manufacturer/findAll");
    }
}

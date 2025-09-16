package org.example.controller.category;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.CategoryService;

import java.io.IOException;
import java.sql.SQLException;

public class CategoryDeleteServlet extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Object svc = config.getServletContext().getAttribute("categoryService");
        if (!(svc instanceof CategoryService)) {
            throw new ServletException("categoryService not initialized");
        }
        this.categoryService = (CategoryService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo(); // например: /5/delete
        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    categoryService.delete(id);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid category id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/category/findAll");
    }
}
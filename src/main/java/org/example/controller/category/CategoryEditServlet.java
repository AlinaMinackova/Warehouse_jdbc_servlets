package org.example.controller.category;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Category;
import org.example.service.CategoryService;

import java.io.IOException;
import java.sql.SQLException;


public class CategoryEditServlet extends HttpServlet {
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

        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]); // "5"
                    Category category = categoryService.findById(id);
                    req.setAttribute("category", category);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid category id", e);
                }
            }
        }

        req.getRequestDispatcher("/category/category_edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String path = req.getPathInfo(); // вернёт что-то вроде "/5/edit"

        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]); // "5"
                    String name = req.getParameter("name");
                    Category category = new Category(id, name.trim());
                    categoryService.update(category);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid category id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/category/findAll");
    }
}
package org.example.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.CategoryService;
import org.example.service.ManufacturerService;
import org.example.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;

public class ProductDeleteServlet extends HttpServlet {

    private ProductService productService;

    public void init() throws ServletException {
        Object ps = getServletContext().getAttribute("productService");

        if (!(ps instanceof ProductService)) {
            throw new ServletException("Services not initialized");
        }

        productService = (ProductService) ps;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo(); // например: /5/delete
        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    productService.delete(id);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/product/findAll");
    }
}

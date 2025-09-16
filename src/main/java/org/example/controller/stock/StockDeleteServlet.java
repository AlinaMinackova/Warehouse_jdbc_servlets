package org.example.controller.stock;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.StockService;

import java.io.IOException;
import java.sql.SQLException;

public class StockDeleteServlet extends HttpServlet {
    private StockService stockService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Object svc = config.getServletContext().getAttribute("stockService");
        if (!(svc instanceof StockService)) {
            throw new ServletException("stockService not initialized");
        }
        this.stockService = (StockService) svc;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getPathInfo(); // например: /5/delete
        if (path != null) {
            String[] parts = path.split("/");
            if (parts.length > 1) {
                try {
                    Long id = Long.parseLong(parts[1]);
                    stockService.delete(id);
                } catch (NumberFormatException | SQLException e) {
                    throw new ServletException("Invalid id", e);
                }
            }
        }
        resp.sendRedirect(req.getContextPath() + "/stock/findAll");
    }
}

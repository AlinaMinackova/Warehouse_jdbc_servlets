package org.example.controller.stock;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Product;
import org.example.entity.Stock;
import org.example.service.ProductService;
import org.example.service.StockService;
import org.example.service.WarehouseService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class StockFindAllServlet extends HttpServlet {
    private StockService stockService;
    private WarehouseService warehouseService;
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Object svc = config.getServletContext().getAttribute("stockService");
        Object ws = config.getServletContext().getAttribute("warehouseService");
        Object ps = config.getServletContext().getAttribute("productService");
        if (!(svc instanceof StockService) || !(ws instanceof WarehouseService) || !(ps instanceof ProductService)) {
            throw new ServletException("stockService not initialized");
        }
        this.stockService = (StockService) svc;
        this.warehouseService = (WarehouseService) ws;
        this.productService = (ProductService) ps;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Получаем параметры страницы и размера
            int page = 0;
            int size = 12;

            String pageParam = req.getParameter("page");
            String sizeParam = req.getParameter("size");

            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
            if (sizeParam != null) {
                size = Integer.parseInt(sizeParam);
            }

            String warehouseIdStr = req.getParameter("warehouseId");
            String productIdStr = req.getParameter("productId");
            String sort = req.getParameter("sort");

            Long warehouseId = (warehouseIdStr != null && !warehouseIdStr.isEmpty())
                    ? Long.parseLong(warehouseIdStr) : null;
            Long productId = (productIdStr != null && !productIdStr.isEmpty())
                    ? Long.parseLong(productIdStr) : null;

            List<Stock> stocks = stockService.findFiltered(warehouseId, productId, sort);

            // Получаем список болезней (можно сделать постранично вручную)
//            List<Stock> stocks = stockService.findAll();

            // Если хочешь постранично:
            int fromIndex = Math.min(page * size, stocks.size());
            int toIndex = Math.min(fromIndex + size, stocks.size());
            List<Stock> stockPage = stocks.subList(fromIndex, toIndex);

            // Ставим атрибуты для JSP
            req.setAttribute("stocks", stockPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", size);
            req.setAttribute("totalPages", (int) Math.ceil((double) stocks.size() / size));

            req.setAttribute("selectedWarehouse", warehouseId);
            req.setAttribute("selectedProduct", productId);
            req.setAttribute("selectedSort", sort);

            // нужно подгрузить списки для фильтров
            req.setAttribute("warehouses", warehouseService.findAll());
            req.setAttribute("products", productService.findAll());

            // Передаём управление JSP
            req.getRequestDispatcher("/stock/stock_list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

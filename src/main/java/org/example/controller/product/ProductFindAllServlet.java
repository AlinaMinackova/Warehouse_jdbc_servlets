package org.example.controller.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Product;
import org.example.entity.Stock;
import org.example.service.CategoryService;
import org.example.service.ManufacturerService;
import org.example.service.ProductService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProductFindAllServlet extends HttpServlet {

    private ProductService productService;
    private ManufacturerService manufacturerService;
    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        Object ps = getServletContext().getAttribute("productService");
        Object ms = getServletContext().getAttribute("manufacturerService");
        Object cs = getServletContext().getAttribute("categoryService");

        if (!(ps instanceof ProductService) || !(ms instanceof ManufacturerService) || !(cs instanceof CategoryService)) {
            throw new ServletException("Services not initialized");
        }

        productService = (ProductService) ps;
        manufacturerService = (ManufacturerService) ms;
        categoryService = (CategoryService) cs;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // Параметры постраничного вывода
            int page = 0;
            int size = 12;

            String pageParam = req.getParameter("page");
            String sizeParam = req.getParameter("size");

            if (pageParam != null) page = Integer.parseInt(pageParam);
            if (sizeParam != null) size = Integer.parseInt(sizeParam);


            String manufacturerIdStr = req.getParameter("manufacturerId");
            String categoryIdStr = req.getParameter("categoryId");

            Long manufacturerId = (manufacturerIdStr != null && !manufacturerIdStr.isEmpty())
                    ? Long.parseLong(manufacturerIdStr) : null;
            Long categoryId = (categoryIdStr != null && !categoryIdStr.isEmpty())
                    ? Long.parseLong(categoryIdStr) : null;

            List<Product> allProducts = productService.findFiltered(manufacturerId, categoryId);
//            List<Product> allProducts = productService.findAll();

            // Постранично
            int fromIndex = Math.min(page * size, allProducts.size());
            int toIndex = Math.min(fromIndex + size, allProducts.size());
            List<Product> productPage = allProducts.subList(fromIndex, toIndex);

            // Атрибуты для JSP
            req.setAttribute("products", productPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", size);
            req.setAttribute("totalPages", (int) Math.ceil((double) allProducts.size() / size));

            // Списки для выпадающих фильтров
            req.setAttribute("manufacturers", manufacturerService.findAll());
            req.setAttribute("categories", categoryService.findAll());

            // Сохраняем выбранные фильтры
            req.setAttribute("selectedManufacturer", manufacturerId);
            req.setAttribute("selectedCategory", categoryId);

            req.getRequestDispatcher("/product/product_list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

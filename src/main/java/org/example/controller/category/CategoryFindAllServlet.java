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
import java.util.List;

public class CategoryFindAllServlet extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            // Получаем параметры страницы и размера
            int page = 0;
            int size = 5;

            String pageParam = req.getParameter("page");
            String sizeParam = req.getParameter("size");

            if (pageParam != null) {
                page = Integer.parseInt(pageParam);
            }
            if (sizeParam != null) {
                size = Integer.parseInt(sizeParam);
            }

            // Получаем параметр поиска
            String keyword = req.getParameter("keyword");

            // Получаем список категорий
            List<Category> categories;
            if (keyword != null && !keyword.trim().isEmpty()) {
                categories = categoryService.search(keyword.trim());
                req.setAttribute("keyword", keyword);
            } else {
                categories = categoryService.findAll();
            }

            // Если хочешь постранично:
            int fromIndex = Math.min(page * size, categories.size());
            int toIndex = Math.min(fromIndex + size, categories.size());
            List<Category> categoryPage = categories.subList(fromIndex, toIndex);

            // Ставим атрибуты для JSP
            req.setAttribute("categories", categoryPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", size);
            req.setAttribute("totalPages", (int) Math.ceil((double) categories.size() / size));

            // Передаём управление JSP
            req.getRequestDispatcher("/category/category_list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

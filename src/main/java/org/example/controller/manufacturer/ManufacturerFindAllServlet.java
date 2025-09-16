package org.example.controller.manufacturer;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.entity.Category;
import org.example.entity.Manufacturer;
import org.example.service.ManufacturerService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class ManufacturerFindAllServlet extends HttpServlet {
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

            // Параметр поиска
            String keyword = req.getParameter("keyword");

            List<Manufacturer> manufacturers;
            if (keyword != null && !keyword.trim().isEmpty()) {
                manufacturers = manufacturerService.search(keyword.trim());
                req.setAttribute("keyword", keyword); // чтобы отобразить в input
            } else {
                manufacturers = manufacturerService.findAll();
            }


            // Если хочешь постранично:
            int fromIndex = Math.min(page * size, manufacturers.size());
            int toIndex = Math.min(fromIndex + size, manufacturers.size());
            List<Manufacturer> manufacturerPage = manufacturers.subList(fromIndex, toIndex);

            // Ставим атрибуты для JSP
            req.setAttribute("manufacturers", manufacturerPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("pageSize", size);
            req.setAttribute("totalPages", (int) Math.ceil((double) manufacturers.size() / size));

            // Передаём управление JSP
            req.getRequestDispatcher("/manufacturer/manufacturer_list.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}

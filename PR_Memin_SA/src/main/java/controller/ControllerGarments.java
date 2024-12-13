package controller;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;
import model.Garments;

import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import service.ServiceGarments;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/garments")
public class ControllerGarments extends HttpServlet {

    private ServiceGarments service;

    @Override
    public void init() throws ServletException {
        service = new ServiceGarments();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String code = request.getParameter("code");
        String name = request.getParameter("name");
        String brand = request.getParameter("brand");

        if ("inactive".equals(action)) {
            List<Garments> garments = service.getInactiveGarments();
            request.setAttribute("garments", garments);
            request.setAttribute("inactive", true);
            request.getRequestDispatcher("view/Garments.jsp").forward(request, response);
        } else if ("pdf".equals(action)) {
            downloadPdfGarments(response);
        } else if ("xls".equals(action)) {
            downloadXlsGarments(response);
        } else if ("csv".equals(action)) {
            downloadCsvGarments(response);
        } else if ("sortByName".equals(action)) {
            List<Garments> garments = service.getAllGarmentsSortedByName();
            request.setAttribute("garments", garments);
            request.setAttribute("inactive", false);
            request.getRequestDispatcher("view/Garments.jsp").forward(request, response);
        } else if (code != null || name != null || brand != null) {
            List<Garments> garments = service.searchGarments(code, name, brand);
            request.setAttribute("garments", garments);
            request.setAttribute("inactive", false);
            request.getRequestDispatcher("view/Garments.jsp").forward(request, response);
        } else {
            List<Garments> garments = service.getAllGarments();
            request.setAttribute("garments", garments);
            request.setAttribute("inactive", false);
            request.getRequestDispatcher("view/Garments.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("restore".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.restoreGarment(id);
            response.sendRedirect("garments?action=inactive");
        } else if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            service.DeleteGarment(id);
            response.sendRedirect("garments");
        } else if ("create".equals(action)) {
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            String brand = request.getParameter("brand");
            String category = request.getParameter("category");
            BigDecimal price = new BigDecimal(request.getParameter("price").replace(",", "."));

            Garments garment = new Garments();
            garment.setCode(code);
            garment.setName(name);
            garment.setBrand(brand);
            garment.setCategory(category);
            garment.setPrice(price);

            service.createGarment(garment);
            response.sendRedirect("garments");
        } else if ("update".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            String code = request.getParameter("code");
            String name = request.getParameter("name");
            String brand = request.getParameter("brand");
            String category = request.getParameter("category");
            BigDecimal price = new BigDecimal(request.getParameter("price").replace(",", "."));

            Garments garment = new Garments();
            garment.setId(id);
            garment.setCode(code);
            garment.setName(name);
            garment.setBrand(brand);
            garment.setCategory(category);
            garment.setPrice(price);

            service.updateGarment(garment);
            response.sendRedirect("garments");
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida");
        }
    }

    public void downloadPdfGarments(HttpServletResponse response) throws IOException {
        List<Garments> garments = service.getAllGarmentsMaster();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=garments.pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
            PageSize pageSize = PageSize.A4;
            pdfDoc.setDefaultPageSize(pageSize);

            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);
            document.setMargins(20, 20, 20, 20);

            Paragraph title = new Paragraph("Lista de Prendas");
            title.setFontSize(18);
            title.setBold();
            title.setMarginBottom(10);
            document.add(title);

            com.itextpdf.layout.element.Table table = new com.itextpdf.layout.element.Table(UnitValue.createPercentArray(7));
            table.setWidth(UnitValue.createPercentValue(100));

            Cell cell = new Cell().add(new Paragraph("ID"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Código"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Nombre"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Marca"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Categoría"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Precio"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Estado"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            for (Garments garment : garments) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(garment.getId()))));
                table.addCell(new Cell().add(new Paragraph(garment.getCode())));
                table.addCell(new Cell().add(new Paragraph(garment.getName())));
                table.addCell(new Cell().add(new Paragraph(garment.getBrand())));
                table.addCell(new Cell().add(new Paragraph(garment.getCategory())));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", garment.getPrice()))));
                table.addCell(new Cell().add(new Paragraph(garment.getState().equals("A") ? "Activo" : "Inactivo")));
            }

            document.add(table);
            document.close();
        }
    }

    // Método para descargar datos en formato XLS
    public void downloadXlsGarments(HttpServletResponse response) throws IOException {
        List<Garments> garments = service.getAllGarmentsMaster();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=garments.xls");

        try (OutputStream outputStream = response.getOutputStream()) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Garments");

            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("ID");
            row.createCell(1).setCellValue("Código");
            row.createCell(2).setCellValue("Nombre");
            row.createCell(3).setCellValue("Marca");
            row.createCell(4).setCellValue("Categoría");
            row.createCell(5).setCellValue("Precio");
            row.createCell(6).setCellValue("Estado");

            int rowIndex = 1;
            for (Garments garment : garments) {
                row = sheet.createRow(rowIndex);
                row.createCell(0).setCellValue(garment.getId());
                row.createCell(1).setCellValue(garment.getCode());
                row.createCell(2).setCellValue(garment.getName());
                row.createCell(3).setCellValue(garment.getBrand());
                row.createCell(4).setCellValue(garment.getCategory());
                row.createCell(5).setCellValue(String.format("%.2f", garment.getPrice()));
                row.createCell(6).setCellValue(garment.getState().equals("A") ? "Activo" : "Inactivo");
                rowIndex++;
            }

            workbook.write(outputStream);
            workbook.close();
        }
    }

    // Método para descargar datos en formato CSV
    public void downloadCsvGarments(HttpServletResponse response) throws IOException {
        List<Garments> garments = service.getAllGarmentsMaster();
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=garments.csv");

        try (OutputStream outputStream = response.getOutputStream()) {
            PrintWriter writer = new PrintWriter(outputStream);
            writer.println("ID,Código,Nombre,Marca,Categoría,Precio,Estado");

            for (Garments garment : garments) {
                writer.println(garment.getId() + "," + garment.getCode() + "," + garment.getName() + "," + garment.getBrand() + "," + garment.getCategory() + "," + String.format("%.2f", garment.getPrice()) + "," + (garment.getState().equals("A") ? "Activo" : "Inactivo"));
            }

            writer.close();
        }
    }
}
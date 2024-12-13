package controller;

import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.UnitValue;
import model.Garments;
import model.Kardex;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.w3c.dom.Document;
import service.ServiceGarments;
import service.ServiceKardex;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@WebServlet("/kardex")
public class ControllerKardex extends HttpServlet {

    private ServiceKardex serviceKardex;
    private ServiceGarments serviceGarment; // Nuevo servicio para productos

    @Override
    public void init() throws ServletException {
        serviceKardex = new ServiceKardex();
        serviceGarment = new ServiceGarments(); // Inicializa el servicio de productos
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("searchByOptionalParams".equals(action)) {
            Date registrationDate = null;
            String registrationDateParam = request.getParameter("registrationDate");
            if (registrationDateParam != null && !registrationDateParam.isEmpty()) {
                registrationDate = Date.valueOf(registrationDateParam);
            }

            String brand = request.getParameter("brand");
            String motion = request.getParameter("motion");

            List<Kardex> kardexList = serviceKardex.getKardexByOptionalParams(registrationDate, brand, motion);
            request.setAttribute("kardexList", kardexList);
        } else if ("sortByProductName".equals(action)) {
            List<Kardex> kardexList = serviceKardex.getAllKardexSortedByProductName();
            request.setAttribute("kardexList", kardexList);
        } else if ("inactive".equals(action)) {
            List<Kardex> kardexList = serviceKardex.getDeletedKardex();
            request.setAttribute("kardexList", kardexList);
            request.setAttribute("inactive", true);
        } else {
            List<Kardex> kardexList = serviceKardex.getAllKardex();
            request.setAttribute("kardexList", kardexList);
            request.setAttribute("inactive", false);
        }

        List<Garments> activeGarments = serviceGarment.getGarmentsOption();
        request.setAttribute("activeGarments", activeGarments);

        request.getRequestDispatcher("view/Kardex.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("remove".equals(action)) { // Cambiado de "delete" a "remove"
            // Eliminar un registro (marcarlo como inactivo)
            int id = Integer.parseInt(request.getParameter("id"));
            serviceKardex.deleteKardex(id);
            response.sendRedirect("kardex?action=list");
        } else if ("recover".equals(action)) { // Cambiado de "restore" a "recover"
            // Restaurar un registro (marcarlo como activo)
            int id = Integer.parseInt(request.getParameter("id"));
            serviceKardex.restoreKardex(id);
            response.sendRedirect("kardex?action=inactive");
        } else if ("add".equals(action)) { // Cambiado de "create" a "add"
            // Crear un nuevo registro de Kardex
            Kardex newKardex = new Kardex();
            newKardex.setProducts_id(Integer.parseInt(request.getParameter("products_id")));
            newKardex.setRegistration_date(Date.valueOf(request.getParameter("registrationDate")));
            newKardex.setMonth(request.getParameter("month"));
            newKardex.setMotion(request.getParameter("motion"));
            newKardex.setAmount(Integer.parseInt(request.getParameter("amount")));
            newKardex.setUnit_cost(new BigDecimal(request.getParameter("unitCost")));
            newKardex.setTotal_cost(new BigDecimal(request.getParameter("totalCost")));
            newKardex.setState("A"); // Asumiendo que el nuevo Kardex es activo

            serviceKardex.createKardex(newKardex); // Método para crear un nuevo Kardex
            response.sendRedirect("kardex?action=list");
        } else if ("edit".equals(action)) { // Agregado para actualizar un registro de Kardex
            // Actualizar un registro de Kardex
            int id = Integer.parseInt(request.getParameter("id"));
            Kardex updatedKardex = new Kardex();
            updatedKardex.setId(id);
            updatedKardex.setProducts_id(Integer.parseInt(request.getParameter("products_id")));
            updatedKardex.setRegistration_date(Date.valueOf(request.getParameter("registrationDate")));
            updatedKardex.setMonth(request.getParameter("month"));
            updatedKardex.setMotion(request.getParameter("motion"));
            updatedKardex.setAmount(Integer.parseInt(request.getParameter("amount")));
            updatedKardex.setUnit_cost(new BigDecimal(request.getParameter("unitCost")));
            updatedKardex.setTotal_cost(new BigDecimal(request.getParameter("totalCost")));
            updatedKardex.setState("A"); // Asumiendo que el Kardex sigue activo

            serviceKardex.updateKardex(updatedKardex); // Método para actualizar el Kardex
            response.sendRedirect("kardex?action=list");
        }
    }

    private void downloadKardexData(HttpServletResponse response, String format) throws IOException {
        List<Kardex> kardexList = serviceKardex.getAllKardexMaster();

        switch (format) {
            case "pdf":
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=kardex.pdf");
                downloadPdfKardex(response);
                break;
            case "xls":
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition", "attachment; filename=kardex.xls");
                generateXLS(kardexList, response.getOutputStream());
                break;
            case "csv":
                response.setContentType("text/csv");
                response.setHeader("Content-Disposition", "attachment; filename=kardex.csv");
                generateCSV(kardexList, response.getOutputStream());
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Formato inválido");
                break;
        }
    }

    private void downloadPdfKardex(HttpServletResponse response) throws IOException {
        List<Kardex> kardexList = serviceKardex.getAllKardexMaster();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=kardex.pdf");

        try (OutputStream outputStream = response.getOutputStream()) {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(outputStream));
            PageSize pageSize = PageSize.A4;
            pdfDoc.setDefaultPageSize(pageSize);

            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdfDoc);
            document.setMargins(20, 20, 20, 20);

            Paragraph title = new Paragraph("Lista de Kardex");
            title.setFontSize(18);
            title.setBold();
            title.setMarginBottom(10);
            document.add(title);

            Table table = new Table(UnitValue.createPercentArray(9));
            table.setWidth(UnitValue.createPercentValue(100));

            // Crear celdas de encabezado
            Cell cell = new Cell().add(new Paragraph("ID"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Producto"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Fecha de Registro"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Mes"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Movimiento"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Cantidad"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Costo Unitario"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Costo Total"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            cell = new Cell().add(new Paragraph("Estado"));
            cell.setBackgroundColor(com.itextpdf.kernel.colors.ColorConstants.LIGHT_GRAY);
            table.addCell(cell);

            // Agregar filas de datos
            for (Kardex kardex : kardexList) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(kardex.getId()))));
                table.addCell(new Cell().add(new Paragraph(kardex.getProductName())));
                table.addCell(new Cell().add(new Paragraph(kardex.getRegistration_date().toString())));
                table.addCell(new Cell().add(new Paragraph(kardex.getMonth())));
                table.addCell(new Cell().add(new Paragraph(kardex.getMotion())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(kardex.getAmount()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", kardex.getUnit_cost()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", kardex.getTotal_cost()))));
                table.addCell(new Cell().add(new Paragraph(kardex.getState().equals("A") ? "Activo" : "Inactivo")));
            }

            document.add(table);
            document.close();
        }
    }

    private void generateXLS(List<Kardex> kardexList, OutputStream out) {
        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Kardex");

            // Crear fila de encabezado
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nombre del Producto");
            headerRow.createCell(2).setCellValue("Fecha de Registro");
            headerRow.createCell(3).setCellValue("Mes");
            headerRow.createCell(4).setCellValue("Movimiento");
            headerRow.createCell(5).setCellValue("Cantidad");
            headerRow.createCell(6).setCellValue("Costo Unitario");
            headerRow.createCell(7).setCellValue("Costo Total");
            headerRow.createCell(8).setCellValue("Estado");

            // Agregar los datos
            int rowNum = 1;
            for (Kardex kardex : kardexList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(kardex.getId());
                row.createCell(1).setCellValue(kardex.getProductName());
                row.createCell(2).setCellValue(kardex.getRegistration_date().toString());
                row.createCell(3).setCellValue(kardex.getMonth());
                row.createCell(4).setCellValue(kardex.getMotion());
                row.createCell(5).setCellValue(kardex.getAmount());
                row.createCell(6).setCellValue(kardex.getUnit_cost().doubleValue());
                row.createCell(7).setCellValue(kardex.getTotal_cost().doubleValue());
                row.createCell(8).setCellValue(kardex.getState().equals("A") ? "Activo" : "Inactivo");
            }

            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateCSV(List<Kardex> kardexList, OutputStream out) throws IOException {
        try (PrintWriter writer = new PrintWriter(out)) {
            writer.println("ID,Producto,Fecha de Registro,Mes,Movimiento,Cantidad,Costo Unitario,Costo Total,Estado");

            for (Kardex kardex : kardexList) {
                writer.printf("%d,%s,%s,%s,%s,%d,%.2f,%.2f,%s%n",
                        kardex.getId(),
                        kardex.getProductName(),
                        kardex.getRegistration_date(),
                        kardex.getMonth(),
                        kardex.getMotion(),
                        kardex.getAmount(),
                        kardex.getUnit_cost(),
                        kardex.getTotal_cost(),
                        kardex.getState().equals("A") ? "Activo" : "Inactivo");
            }
        }
    }
}
package umg.progra2.Reportes;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import umg.progra2.DataBase.Model.Producto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.stream.Stream;


public class PdfReport {
    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLDITALIC);
    private static final Font HEADER_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);
    private DecimalFormat df = new DecimalFormat("#.00");

    public void generateProductReport(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER);
        PdfWriter.getInstance(document, new FileOutputStream(outputPath));
        document.open();
        addTitle(document);
        addProductTable(document, productos);
        document.close();
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("REPORTE DE PRODUCTOS", TITLE_FONT);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(Chunk.NEWLINE);
    }

    private void addProductTable(Document document, List<Producto> productos) throws DocumentException {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        addTableHeader(table);
        addRows(table, productos);
        document.add(table);
        addSummary(document, productos);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("ID", "Descripción", "Origen", "Precio", "Cantidad", "Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.RED);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, HEADER_FONT));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, List<Producto> productos) {
        double grandTotal = 0.0;
        for (Producto producto : productos) {
            double total = producto.getPrecio() * producto.getCantidad();
            table.addCell(new Phrase(String.valueOf(producto.getIdProducto()), NORMAL_FONT));
            table.addCell(new Phrase(producto.getDescripcion(), NORMAL_FONT));
            table.addCell(new Phrase(producto.getOrigen(), NORMAL_FONT));
            table.addCell(new Phrase("Q. " + df.format(producto.getPrecio()), NORMAL_FONT));
            table.addCell(new Phrase(String.valueOf(producto.getCantidad()), NORMAL_FONT));
            table.addCell(new Phrase("Q. " + df.format(total), NORMAL_FONT));
            grandTotal += total;
        }

        PdfPCell totalCell = new PdfPCell(new Phrase("Gran Total", HEADER_FONT));
        totalCell.setColspan(5);
        totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(totalCell);
        table.addCell(new Phrase("Q. " + df.format(grandTotal), HEADER_FONT));
    }

    private void addSummary(Document document, List<Producto> productos) throws DocumentException {
        double totalPrecio = productos.stream().mapToDouble(producto -> producto.getPrecio() * producto.getCantidad()).sum();
        int totalCantidad = productos.stream().mapToInt(Producto::getCantidad).sum();
        Paragraph summary = new Paragraph("Resumen:", TITLE_FONT);
        summary.setSpacingBefore(20);
        summary.setSpacingAfter(10);
        document.add(summary);
        document.add(new Paragraph("Precio Total: Q. " + df.format(totalPrecio), NORMAL_FONT));
        document.add(new Paragraph("Cantidad total: " + totalCantidad, NORMAL_FONT));
    }

    // Métodos para generar reportes específicos
    public void generateReportEliminarPrecioCero(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        generateProductReport(productos, outputPath);
    }

    public void generateReportPrecioMenorA(List<Producto> productos, double precioMaximo, String outputPath) throws DocumentException, IOException {
        List<Producto> filteredProductos = productos.stream()
                .filter(producto -> producto.getPrecio() < precioMaximo)
                .toList();
        generateProductReport(filteredProductos, outputPath);
    }

    public void generateReportExistenciaMenorA(List<Producto> productos, int cantidadMaxima, String outputPath) throws DocumentException, IOException {
        List<Producto> filteredProductos = productos.stream()
                .filter(producto -> producto.getCantidad() < cantidadMaxima)
                .toList();
        generateProductReport(filteredProductos, outputPath);
    }

    public void generateReportPrecioEntre(List<Producto> productos, double minPrecio, double maxPrecio, String outputPath) throws DocumentException, IOException {
        List<Producto> filteredProductos = productos.stream()
                .filter(producto -> producto.getPrecio() >= minPrecio && producto.getPrecio() <= maxPrecio)
                .toList();
        generateProductReport(filteredProductos, outputPath);
    }

    public void generateReportOrdenadosPorPrecio(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        List<Producto> sortedProductos = productos.stream()
                .sorted((p1, p2) -> Double.compare(p2.getPrecio(), p1.getPrecio()))
                .toList();
        generateProductReport(sortedProductos, outputPath);
    }

    public void generateReportOrdenadosPorExistencia(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        List<Producto> sortedProductos = productos.stream()
                .sorted((p1, p2) -> Integer.compare(p1.getCantidad(), p2.getCantidad()))
                .toList();
        generateProductReport(sortedProductos, outputPath);
    }

    public void generateReportExistenciaMenor20(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        List<Producto> filteredProductos = productos.stream()
                .filter(producto -> producto.getCantidad() < 20)
                .toList();
        generateProductReport(filteredProductos, outputPath);
    }

    public void generateReportPorPais(List<Producto> productos, String origen, String outputPath) throws DocumentException, IOException {
        List<Producto> filteredProductos = productos.stream()
                .filter(producto -> producto.getOrigen().equalsIgnoreCase(origen))
                .toList();
        generateProductReport(filteredProductos, outputPath);
    }

    public void generateReportConPrecioMayorA(List<Producto> productos, double precioMinimo, String outputPath) throws DocumentException, IOException {
        List<Producto> filteredProductos = productos.stream()
                .filter(producto -> producto.getPrecio() > precioMinimo)
                .toList();
        generateProductReport(filteredProductos, outputPath);
    }

    public void generateReportAgrupadosPorPais(List<Producto> productos, String outputPath) throws DocumentException, IOException {
        List<Producto> groupedProductos = productos.stream()
                .sorted((p1, p2) -> p1.getOrigen().compareToIgnoreCase(p2.getOrigen()))
                .toList();
        generateProductReport(groupedProductos, outputPath);
    }
}

        //Stream.of("ID", "Descripción", "Origen", "Precio"):
        //Crea un flujo (Stream) de datos con los elementos "ID", "Descripción", "Origen", y "Precio".


//        Este código utiliza la clase Stream de Java para crear un flujo de datos con los títulos de las columnas de una tabla PDF. Luego, para cada título de columna, se crea una celda de encabezado en la tabla con ciertas propiedades (color de fondo, ancho del borde, y texto).
//                Explicación del Código
//        Stream.of("ID", "Descripción", "Origen", "Precio"):
//        Crea un flujo (Stream) de datos con los elementos "ID", "Descripción", "Origen", y "Precio".
//.forEach(columnTitle -> { ... }):
//        Para cada elemento en el flujo (en este caso, cada título de columna), ejecuta el bloque de código dentro de las llaves { ... }.
//        Dentro del bloque forEach:
//        PdfPCell header = new PdfPCell();: Crea una nueva celda para la tabla PDF.
//        header.setBackgroundColor(BaseColor.LIGHT_GRAY);: Establece el color de fondo de la celda a gris claro.
//                header.setBorderWidth(2);: Establece el ancho del borde de la celda a 2 puntos.
//                header.setPhrase(new Phrase(columnTitle, HEADER_FONT));: Establece el texto de la celda con el título de la columna y la fuente de encabezado.
//                table.addCell(header);: Añade la celda a la tabla.
//¿Qué es Stream.of?
//                Stream.of es un método estático de la clase Stream en Java que se utiliza para crear un flujo (Stream) a partir de una secuencia de elementos. En este caso, se está utilizando para crear un flujo de cadenas de texto ("ID", "Descripción", "Origen", "Precio").
//        Código

     //Fin de addTableHeader


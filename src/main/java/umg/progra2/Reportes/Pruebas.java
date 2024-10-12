package umg.progra2.Reportes;


import com.itextpdf.text.DocumentException;
import umg.progra2.DB.DatabaseConnection;
import umg.progra2.DataBase.Dao.ProductoDAO;
import umg.progra2.DataBase.Model.Producto;
import umg.progra2.DataBase.Services.ProductoService;

import javax.swing.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import java.sql.Connection;


public class Pruebas {

    private static ProductoService productoService;

    static {
        try {
            Connection connection = DatabaseConnection.getConnection();
            productoService = new ProductoService(new ProductoDAO(connection));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al establecer la conexión: " + e.getMessage());
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        try {
            mostrarMenuReportes();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private static void mostrarMenuReportes() {
        String[] opciones = {
                "1. Eliminar productos con precio 0",
                "2. Reporte de productos con precio menor a 100",
                "3. Reporte de productos con existencia menor a 30",
                "4. Reporte de productos con precio entre 200 y 400",
                "5. Reporte de productos ordenados por precio (Mayor a Menor)",
                "6. Reporte de productos ordenados por existencia (Menor a Mayor)",
                "7. Reporte de productos con existencia menor a 20",
                "8. Reporte de productos de un país específico",
                "9. Reporte con precio mayor a 2000",
                "10. Reporte de productos agrupados por país y ordenados (Mayor a Menor)"
        };

        String seleccion = (String) JOptionPane.showInputDialog(
                null,
                "Seleccione un reporte:",
                "Menú de Reportes",
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0]
        );

        if (seleccion != null) {
            switch (seleccion) {
                case "1. Eliminar productos con precio 0":
                    generarReporteEliminarPrecioCero();
                    break;
                case "2. Reporte de productos con precio menor a 100":
                    generarReportePorPrecio(100);
                    break;
                case "3. Reporte de productos con existencia menor a 30":
                    generarReportePorExistencia(30);
                    break;
                case "4. Reporte de productos con precio entre 200 y 400":
                    generarReportePorPrecioEntre(200, 400);
                    break;
                case "5. Reporte de productos ordenados por precio (Mayor a Menor)":
                    generarReporteOrdenadosPorPrecio();
                    break;
                case "6. Reporte de productos ordenados por existencia (Menor a Mayor)":
                    generarReporteOrdenadosPorExistencia();
                    break;
                case "7. Reporte de productos con existencia menor a 20":
                    generarReportePorExistencia(20);
                    break;
                case "8. Reporte de productos de un país específico":
                    generarReportePorPais();
                    break;
                case "9. Reporte con precio mayor a 2000":
                    generarReportePorPrecio(2000);
                    break;
                case "10. Reporte de productos agrupados por país y ordenados (Mayor a Menor)":
                    generarReporteAgrupadosPorPais();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        }
    }

    private static void generarReporteEliminarPrecioCero() {
        String outputPath = "c:\\tmp\\reporte_productos_sin_precio_cero.pdf";
        try {
            productoService.eliminarProductosConPrecioCero();
            JOptionPane.showMessageDialog(null, "Productos con precio 0 eliminados exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar productos: " + e.getMessage());
        }
    }

    private static void generarReportePorPrecio(double precioMaximo) {
        String outputPath = "c:\\tmp\\reporte_productos_con_precio_menor_a_" + precioMaximo + ".pdf";
        try {
            List<Producto> productos = productoService.generarReportePrecioMenorA(precioMaximo);
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private static void generarReportePorExistencia(int cantidad) {
        String outputPath = "c:\\tmp\\reporte_productos_con_existencia_menor_a_" + cantidad + ".pdf";
        try {
            List<Producto> productos = productoService.generarReporteExistenciaMenorA(cantidad);
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private static void generarReportePorPrecioEntre(double minPrecio, double maxPrecio) {
        String outputPath = "c:\\tmp\\reporte_productos_con_precio_entre_" + minPrecio + "_y_" + maxPrecio + ".pdf";
        try {
            List<Producto> productos = productoService.generarReportePrecioEntre(minPrecio, maxPrecio);
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private static void generarReporteOrdenadosPorPrecio() {
        String outputPath = "c:\\tmp\\reporte_productos_ordenados_por_precio.pdf";
        try {
            List<Producto> productos = productoService.generarReporteOrdenadosPorPrecio();
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private static void generarReporteOrdenadosPorExistencia() {
        String outputPath = "c:\\tmp\\reporte_productos_ordenados_por_existencia.pdf";
        try {
            List<Producto> productos = productoService.generarReporteOrdenadosPorExistencia();
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private static void generarReportePorPais() {
        String pais = JOptionPane.showInputDialog("Ingrese el país:");
        if (pais != null && !pais.trim().isEmpty()) {
            String outputPath = "c:\\tmp\\reporte_productos_de_pais_" + pais + ".pdf";
            try {
                List<Producto> productos = productoService.generarReportePorPais(pais);
                new PdfReport().generateProductReport(productos, outputPath);
                JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
            } catch (DocumentException | IOException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un país válido.");
        }
    }

    private static void generarReporteAgrupadosPorPais() {
        String outputPath = "c:\\tmp\\reporte_productos_agrupados_por_pais.pdf";
        try {
            List<Producto> productos = productoService.generarReporteAgrupadosPorPais();
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }
}

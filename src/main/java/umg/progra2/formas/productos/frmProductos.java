package umg.progra2.formas.productos;

import com.itextpdf.text.DocumentException;
import umg.progra2.DB.DatabaseConnection;
import umg.progra2.DataBase.Dao.ProductoDAO;
import umg.progra2.DataBase.Model.Producto;
import umg.progra2.DataBase.Services.ProductoService;
import umg.progra2.Reportes.PdfReport;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class frmProductos {
    private JPanel jPanelPrincipal;
    private JLabel lblTitulo;
    private JLabel lblId;
    private JLabel lblNombre;
    private JLabel lblOrigen;
    private JTextField textFieldidProducto;
    private JTextField textFieldNombreProducto;
    private JComboBox<String> comboBoxOrigen;
    private JButton buttonGrabar;
    private JButton buttonBuscar;
    private JButton buttonActualizar;
    private JLabel lblPrecio;
    private JTextField textFieldPrecio;
    private JLabel lblCantidad;
    private JTextField textFieldCantidad;
    private JComboBox<String> comboBoxReportes;

    private ProductoService productoService;

    public static void main(String[] args) {
        JFrame frame = new JFrame("frmProductos");
        frame.setContentPane(new frmProductos().jPanelPrincipal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public frmProductos() {
        try {
            productoService = new ProductoService(new ProductoDAO(DatabaseConnection.getConnection()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage());
            System.exit(1);
        }

        comboBoxOrigen.addItem("China");
        comboBoxOrigen.addItem("México");
        comboBoxOrigen.addItem("Argentina");
        comboBoxOrigen.addItem("EEUU");
        comboBoxOrigen.addItem("España");
        comboBoxOrigen.addItem("Japón");
        comboBoxOrigen.addItem("Chile");
        comboBoxOrigen.addItem("Italia");
        comboBoxOrigen.addItem("Canadá");
        comboBoxOrigen.addItem("Brasil");
        comboBoxOrigen.addItem("Francia");
        comboBoxOrigen.addItem("India");
        comboBoxOrigen.addItem("Australia");
        comboBoxOrigen.addItem("Sri Lanka");
        comboBoxOrigen.addItem("Colombia");
        comboBoxOrigen.addItem("Uruguay");
        comboBoxOrigen.addItem("Dinamarca");
        comboBoxOrigen.addItem("Suiza");

        buttonGrabar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Producto producto = new Producto();
                producto.setDescripcion(textFieldNombreProducto.getText());
                producto.setOrigen(comboBoxOrigen.getSelectedItem().toString());

                try {
                    productoService.addProducto(producto);
                    JOptionPane.showMessageDialog(null, "Producto agregado exitosamente");
                    clearFields();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al agregar producto: " + ex.getMessage());
                }
            }
        });

        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = textFieldidProducto.getText();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un ID de producto.");
                    return;
                }

                int idProducto = Integer.parseInt(idText);
                try {
                    Producto productoEncontrado = productoService.getProducto(idProducto);
                    if (productoEncontrado != null) {
                        textFieldNombreProducto.setText(productoEncontrado.getDescripcion());
                        comboBoxOrigen.setSelectedItem(productoEncontrado.getOrigen());
                    } else {
                        JOptionPane.showMessageDialog(null, "Código de Producto no existe");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error al buscar producto: " + ex.getMessage());
                }
            }
        });

        buttonActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idText = textFieldidProducto.getText();
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Debe ingresar un ID de producto válido para actualizar.");
                    return;
                }

                int idProducto = Integer.parseInt(idText);
                try {
                    Producto productoExistente = productoService.getProducto(idProducto);
                    if (productoExistente != null) {
                        Producto productoActualizado = new Producto();
                        productoActualizado.setIdProducto(idProducto);
                        productoActualizado.setDescripcion(textFieldNombreProducto.getText());
                        productoActualizado.setOrigen(comboBoxOrigen.getSelectedItem().toString());

                        productoService.updateProducto(productoActualizado);
                        JOptionPane.showMessageDialog(null, "Producto actualizado exitosamente.");
                    } else {
                        JOptionPane.showMessageDialog(null, "El código de producto no existe.");
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                }
            }
        });

        comboBoxReportes.addItem("1. Eliminar productos con precio 0");
        comboBoxReportes.addItem("2. Reporte de productos con precio menor a 100");
        comboBoxReportes.addItem("3. Reporte de productos con existencia menor a 30");
        comboBoxReportes.addItem("4. Reporte de productos con precio entre 200 y 400");
        comboBoxReportes.addItem("5. Reporte de productos ordenados por precio (Mayor a Menor)");
        comboBoxReportes.addItem("6. Reporte de productos ordenados por existencia (Menor a Mayor)");
        comboBoxReportes.addItem("7. Reporte de productos con existencia menor a 20");
        comboBoxReportes.addItem("8. Reporte de productos de un país específico");
        comboBoxReportes.addItem("9. Reporte con precio mayor a 2000");
        comboBoxReportes.addItem("10. Reporte de productos agrupados por país y ordenados (Mayor a Menor)");

        comboBoxReportes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String seleccion = (String) comboBoxReportes.getSelectedItem();
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
        });
    }

    private void generarReporteEliminarPrecioCero() {
        try {
            productoService.eliminarProductosConPrecioCero();
            JOptionPane.showMessageDialog(null, "Productos con precio 0 eliminados exitosamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar productos: " + e.getMessage());
        }
    }

    private void generarReportePorPrecio(double precioMaximo) {
        String outputPath = "c:\\tmp\\reporte_productos_con_precio_menor_a_" + precioMaximo + ".pdf";
        try {
            List<Producto> productos = productoService.generarReportePrecioMenorA(precioMaximo);
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void generarReportePorExistencia(int cantidad) {
        String outputPath = "c:\\tmp\\reporte_productos_con_existencia_menor_a_" + cantidad + ".pdf";
        try {
            List<Producto> productos = productoService.generarReporteExistenciaMenorA(cantidad);
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void generarReportePorPrecioEntre(double minPrecio, double maxPrecio) {
        String outputPath = "c:\\tmp\\reporte_productos_con_precio_entre_" + minPrecio + "_y_" + maxPrecio + ".pdf";
        try {
            List<Producto> productos = productoService.generarReportePrecioEntre(minPrecio, maxPrecio);
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void generarReporteOrdenadosPorPrecio() {
        String outputPath = "c:\\tmp\\reporte_productos_ordenados_por_precio.pdf";
        try {
            List<Producto> productos = productoService.generarReporteOrdenadosPorPrecio();
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void generarReporteOrdenadosPorExistencia() {
        String outputPath = "c:\\tmp\\reporte_productos_ordenados_por_existencia.pdf";
        try {
            List<Producto> productos = productoService.generarReporteOrdenadosPorExistencia();
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void generarReportePorPais() {
        String origen = JOptionPane.showInputDialog("Ingrese el país:");
        if (origen != null && !origen.isEmpty()) {
            String outputPath = "c:\\tmp\\reporte_productos_de_" + origen + ".pdf";
            try {
                List<Producto> productos = productoService.generarReportePorPais(origen);
                new PdfReport().generateProductReport(productos, outputPath);
                JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
            } catch (DocumentException | IOException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe ingresar un país válido.");
        }
    }

    private void generarReporteAgrupadosPorPais() {
        String outputPath = "c:\\tmp\\reporte_productos_agrupados_por_pais.pdf";
        try {
            List<Producto> productos = productoService.generarReporteAgrupadosPorPais();
            new PdfReport().generateProductReport(productos, outputPath);
            JOptionPane.showMessageDialog(null, "Reporte generado exitosamente en: " + outputPath);
        } catch (DocumentException | IOException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar el reporte: " + e.getMessage());
        }
    }

    private void clearFields() {
        textFieldidProducto.setText("");
        textFieldNombreProducto.setText("");
        comboBoxOrigen.setSelectedIndex(0);
    }
}
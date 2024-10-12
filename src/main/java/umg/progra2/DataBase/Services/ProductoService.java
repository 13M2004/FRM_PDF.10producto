package umg.progra2.DataBase.Services;

import umg.progra2.DataBase.Dao.ProductoDAO;
import umg.progra2.DataBase.Model.Producto;

import java.sql.SQLException;
import java.util.List;

public class ProductoService {
        private ProductoDAO productoDAO;

        public ProductoService(ProductoDAO productoDAO) {
                this.productoDAO = productoDAO;
        }

        public void addProducto(Producto producto) throws SQLException {
                productoDAO.addProducto(producto);
        }

        public Producto getProducto(int idProducto) throws SQLException {
                return productoDAO.getProducto(idProducto);
        }

        public void updateProducto(Producto producto) throws SQLException {
                productoDAO.updateProducto(producto);
        }

        public void eliminarProductosConPrecioCero() throws SQLException {
                productoDAO.eliminarProductosConPrecioCero();
        }

        public List<Producto> generarReportePrecioMenorA(double precioMaximo) throws SQLException {
                return productoDAO.getProductosPrecioMenorA(precioMaximo);
        }

        public List<Producto> generarReporteExistenciaMenorA(int cantidadMaxima) throws SQLException {
                return productoDAO.getProductosExistenciaMenorA(cantidadMaxima);
        }

        public List<Producto> generarReportePrecioEntre(double minPrecio, double maxPrecio) throws SQLException {
                return productoDAO.getProductosPrecioEntre(minPrecio, maxPrecio);
        }

        public List<Producto> generarReporteOrdenadosPorPrecio() throws SQLException {
                return productoDAO.getProductosOrdenadosPorPrecio();
        }

        public List<Producto> generarReporteOrdenadosPorExistencia() throws SQLException {
                return productoDAO.getProductosOrdenadosPorExistencia();
        }

        public List<Producto> generarReporteExistenciaMenor20() throws SQLException {
                return productoDAO.getProductosExistenciaMenor20();
        }

        public List<Producto> generarReportePorPais(String origen) throws SQLException {
                return productoDAO.getProductosPorPais(origen);
        }

        public List<Producto> generarReporteConPrecioMayorA(double precioMinimo) throws SQLException {
                return productoDAO.getProductosConPrecioMayorA(precioMinimo);
        }

        public List<Producto> generarReporteAgrupadosPorPais() throws SQLException {
                return productoDAO.getProductosAgrupadosPorPais();
        }
}
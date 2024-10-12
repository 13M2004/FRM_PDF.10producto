package umg.progra2.DataBase.Dao;

import umg.progra2.DataBase.Model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProductoDAO {
    private Connection connection;

    public ProductoDAO(Connection connection) {
        this.connection = connection;
    }

    public void addProducto(Producto producto) throws SQLException {
        String query = "INSERT INTO tb_producto (descripcion, origen, precio, cantidad) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, producto.getDescripcion());
            stmt.setString(2, producto.getOrigen());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.executeUpdate();
        }
    }

    public Producto getProducto(int idProducto) throws SQLException {
        String query = "SELECT * FROM tb_producto WHERE id_producto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto")); // Cambiado a id_producto
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setOrigen(rs.getString("origen"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
                return producto;
            }
            return null;
        }
    }

    public void updateProducto(Producto producto) throws SQLException {
        String query = "UPDATE tb_producto SET descripcion = ?, origen = ?, precio = ?, cantidad = ? WHERE id_producto = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, producto.getDescripcion());
            stmt.setString(2, producto.getOrigen());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getCantidad());
            stmt.setInt(5, producto.getIdProducto()); // Cambiado a id_producto
            stmt.executeUpdate();
        }
    }

    public void eliminarProductosConPrecioCero() throws SQLException {
        String query = "DELETE FROM tb_producto WHERE precio = 0";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.executeUpdate();
        }
    }

    public List<Producto> getProductosPrecioMenorA(double precioMaximo) throws SQLException {
        String query = "SELECT * FROM tb_producto WHERE precio < ?";
        return getProductos(query, precioMaximo);
    }

    public List<Producto> getProductosExistenciaMenorA(int cantidadMaxima) throws SQLException {
        String query = "SELECT * FROM tb_producto WHERE cantidad < ?";
        return getProductos(query, cantidadMaxima);
    }

    public List<Producto> getProductosPrecioEntre(double minPrecio, double maxPrecio) throws SQLException {
        String query = "SELECT * FROM tb_producto WHERE precio BETWEEN ? AND ?";
        return getProductos(query, minPrecio, maxPrecio);
    }

    public List<Producto> getProductosOrdenadosPorPrecio() throws SQLException {
        String query = "SELECT * FROM tb_producto ORDER BY precio DESC";
        return getProductos(query);
    }

    public List<Producto> getProductosOrdenadosPorExistencia() throws SQLException {
        String query = "SELECT * FROM tb_producto ORDER BY cantidad ASC";
        return getProductos(query);
    }

    public List<Producto> getProductosExistenciaMenor20() throws SQLException {
        String query = "SELECT * FROM tb_producto WHERE cantidad < 20";
        return getProductos(query);
    }

    public List<Producto> getProductosPorPais(String origen) throws SQLException {
        String query = "SELECT * FROM tb_producto WHERE origen = ?";
        return getProductos(query, origen);
    }

    public List<Producto> getProductosConPrecioMayorA(double precioMinimo) throws SQLException {
        String query = "SELECT * FROM tb_producto WHERE precio > ?";
        return getProductos(query, precioMinimo);
    }

    public List<Producto> getProductosAgrupadosPorPais() throws SQLException {
        String query = "SELECT * FROM tb_producto ORDER BY origen, precio DESC";
        return getProductos(query);
    }

    private List<Producto> getProductos(String query, Object... params) throws SQLException {
        List<Producto> productos = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto")); // Cambiado a id_producto
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setOrigen(rs.getString("origen"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setCantidad(rs.getInt("cantidad"));
                productos.add(producto);
            }
        }
        return productos;
    }
}
package umg.progra2.DataBase.Model;


public class Producto {

    private int id_producto;
    private String descripcion;
    private String origen;
    private double precio;
    private int cantidad;

    public Producto() {}

    public Producto(int idProducto, String descripcion, String origen, double precio, int cantidad) {
        this.id_producto = idProducto;
        this.descripcion = descripcion;
        this.origen = origen;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public int getIdProducto() {
        return id_producto;
    }

    public void setIdProducto(int idProducto) {
        this.id_producto = idProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "idProducto=" + id_producto +
                ", descripcion='" + descripcion + '\'' +
                ", origen='" + origen + '\'' +
                ", precio=" + precio +
                ", cantidad=" + cantidad +
                '}';
    }
}

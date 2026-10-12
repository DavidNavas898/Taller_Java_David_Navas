public class Producto {
    private String nombre;
    private double precio;
    private int stock;
    private String categoria;

    public Producto(String nombre, double precio, int stock, String categoria) {
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
        this.categoria = categoria;
    }

    public boolean hayStock(int cantidad) {
        return cantidad <= stock;
    }

    public void disminuirStock(int cantidad) {
        if (hayStock(cantidad)) {
            stock = stock - cantidad;
        }
    }

    public String getDetalle() {
        return "";
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getCategoria() {
        return categoria;
    }
}

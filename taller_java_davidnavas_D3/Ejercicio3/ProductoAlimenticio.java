public class ProductoAlimenticio extends Producto {
    private String fechaCaducidad;

    public ProductoAlimenticio(String nombre, double precio, int stock, String fechaCaducidad) {
        super(nombre, precio, stock, "Alimentos");
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getFechaCaducidad() {
        return fechaCaducidad;
    }

    @Override
    public String getDetalle() {
        return "Vence: " + fechaCaducidad;
    }
}

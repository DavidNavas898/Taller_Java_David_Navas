public class ProductoElectronico extends Producto {
    private int mesesGarantia;

    public ProductoElectronico(String nombre, double precio, int stock, int mesesGarantia) {
        super(nombre, precio, stock, "Electronica");
        this.mesesGarantia = mesesGarantia;
    }

    public int getMesesGarantia() {
        return mesesGarantia;
    }

    @Override
    public String getDetalle() {
        return "Garantia: " + mesesGarantia + " meses";
    }
}

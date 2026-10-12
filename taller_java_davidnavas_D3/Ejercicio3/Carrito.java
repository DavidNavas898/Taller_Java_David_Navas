import java.util.ArrayList;

public class Carrito {
    private static final double MONTO_MINIMO_DESCUENTO = 1000;
    private static final double PORCENTAJE_DESCUENTO = 0.10;

    private Cliente cliente;
    private Inventario inventario;
    private ArrayList<Producto> productos;
    private ArrayList<Integer> cantidades;

    public Carrito(Cliente cliente, Inventario inventario) {
        this.cliente = cliente;
        this.inventario = inventario;
        productos = new ArrayList<>();
        cantidades = new ArrayList<>();
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        if (cantidad <= 0 || !inventario.hayStock(producto, cantidad)) {
            return false;
        }

        inventario.disminuirStock(producto, cantidad);

        int posicion = productos.indexOf(producto);
        if (posicion >= 0) {
            cantidades.set(posicion, cantidades.get(posicion) + cantidad);
        } else {
            productos.add(producto);
            cantidades.add(cantidad);
        }
        return true;
    }

    public double calcularSubtotal() {
        double subtotal = 0;
        for (int i = 0; i < productos.size(); i++) {
            subtotal = subtotal + productos.get(i).getPrecio() * cantidades.get(i);
        }
        return subtotal;
    }

    public double calcularDescuento() {
        double subtotal = calcularSubtotal();
        if (subtotal > MONTO_MINIMO_DESCUENTO) {
            return subtotal * PORCENTAJE_DESCUENTO;
        }
        return 0;
    }

    public double calcularTotal() {
        return calcularSubtotal() - calcularDescuento();
    }

    public boolean estaVacio() {
        return productos.isEmpty();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void mostrarDetalle() {
        if (estaVacio()) {
            System.out.println("El carrito esta vacio.");
            return;
        }

        System.out.printf("%-15s %8s %10s %12s%n", "Producto", "Cant.", "Precio", "Subtotal");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            System.out.printf("%-15s %8d %10.2f %12.2f%n", producto.getNombre(), cantidad, producto.getPrecio(), producto.getPrecio() * cantidad);
        }
        System.out.printf("Subtotal actual: $%.2f%n", calcularSubtotal());
    }

    public void mostrarFactura() {
        System.out.println("=============== FACTURA ===============");
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Email: " + cliente.getEmail());
        System.out.println("---------------------------------------");
        System.out.printf("%-15s %8s %10s %12s%n", "Producto", "Cant.", "Precio", "Subtotal");
        for (int i = 0; i < productos.size(); i++) {
            Producto producto = productos.get(i);
            int cantidad = cantidades.get(i);
            System.out.printf("%-15s %8d %10.2f %12.2f%n", producto.getNombre(), cantidad, producto.getPrecio(), producto.getPrecio() * cantidad);
        }
        System.out.println("---------------------------------------");
        System.out.printf("SUBTOTAL:  $%.2f%n", calcularSubtotal());
        if (calcularDescuento() > 0) {
            System.out.printf("DESCUENTO (%.0f%%): -$%.2f%n", PORCENTAJE_DESCUENTO * 100, calcularDescuento());
        } else {
            System.out.printf("DESCUENTO: $0.00 (aplica en compras mayores a $%.2f)%n", MONTO_MINIMO_DESCUENTO);
        }
        System.out.printf("TOTAL A PAGAR: $%.2f%n", calcularTotal());
        System.out.println("=======================================");
    }
}

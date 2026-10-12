import java.util.ArrayList;

public class Inventario {
    private ArrayList<Producto> productos;

    public Inventario() {
        productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public ArrayList<String> getCategorias() {
        ArrayList<String> categorias = new ArrayList<>();
        for (Producto producto : productos) {
            if (!categorias.contains(producto.getCategoria())) {
                categorias.add(producto.getCategoria());
            }
        }
        return categorias;
    }

    public ArrayList<Producto> buscarPorCategoria(String categoria) {
        ArrayList<Producto> resultado = new ArrayList<>();
        for (Producto producto : productos) {
            if (producto.getCategoria().equalsIgnoreCase(categoria)) {
                resultado.add(producto);
            }
        }
        return resultado;
    }

    public boolean hayStock(Producto producto, int cantidad) {
        return productos.contains(producto) && producto.hayStock(cantidad);
    }

    public boolean disminuirStock(Producto producto, int cantidad) {
        if (!hayStock(producto, cantidad)) {
            return false;
        }
        producto.disminuirStock(cantidad);
        return true;
    }

    public void mostrarLista(ArrayList<Producto> lista) {
        if (lista.isEmpty()) {
            System.out.println("No hay productos para mostrar.");
            return;
        }

        for (int i = 0; i < lista.size(); i++) {
            Producto producto = lista.get(i);
            System.out.printf("%d. %-12s %-12s $%9.2f  Stock: %-3d %s%n", (i + 1), producto.getNombre(), producto.getCategoria(), producto.getPrecio(), producto.getStock(), producto.getDetalle());
        }
    }
}

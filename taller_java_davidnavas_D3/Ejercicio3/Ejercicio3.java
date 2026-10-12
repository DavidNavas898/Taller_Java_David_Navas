import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Inventario inventario = new Inventario();
        inventario.agregarProducto(new ProductoElectronico("Mouse", 25, 10, 12));
        inventario.agregarProducto(new ProductoElectronico("Teclado", 45, 8, 12));
        inventario.agregarProducto(new ProductoElectronico("Monitor", 320, 5, 24));
        inventario.agregarProducto(new ProductoElectronico("Audifonos", 60, 6, 6));
        inventario.agregarProducto(new ProductoAlimenticio("Cafe 500g", 12, 20, "15/03/2027"));
        inventario.agregarProducto(new ProductoAlimenticio("Chocolate", 4.5, 30, "20/12/2026"));
        inventario.agregarProducto(new ProductoAlimenticio("Galletas", 3, 25, "30/11/2026"));
        inventario.agregarProducto(new Producto("Lampara", 35, 7, "Hogar"));
        inventario.agregarProducto(new Producto("Cojin", 18, 12, "Hogar"));

        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(new Cliente("Maria Lopez", "maria@correo.com"));
        clientes.add(new Cliente("Juan Rodriguez", "juan@correo.com"));

        System.out.println("===== TIENDA ONLINE =====");
        System.out.print("Ingrese su email para iniciar sesion: ");
        String email = sc.nextLine().trim();
        while (!emailValido(email)) {
            System.out.print("Email no valido, intente de nuevo: ");
            email = sc.nextLine().trim();
        }

        Cliente cliente = buscarCliente(clientes, email);
        if (cliente == null) {
            System.out.println("No encontramos una cuenta con ese email. Vamos a registrarlo.");
            System.out.print("Ingrese su nombre: ");
            String nombre = sc.nextLine().trim();
            while (nombre.isEmpty()) {
                System.out.print("El nombre no puede estar vacio: ");
                nombre = sc.nextLine().trim();
            }
            cliente = new Cliente(nombre, email);
            clientes.add(cliente);
        }

        System.out.println("Bienvenido, " + cliente.getNombre());

        Carrito carrito = new Carrito(cliente, inventario);
        int opcion = 0;

        while (opcion != 4) {
            System.out.println();
            System.out.println("===== MENU =====");
            System.out.println("1. Ver catalogo completo");
            System.out.println("2. Agregar producto al carrito");
            System.out.println("3. Ver carrito");
            System.out.println("4. Finalizar compra");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1:
                    System.out.println();
                    System.out.println("----------- CATALOGO -----------");
                    inventario.mostrarLista(inventario.getProductos());
                    break;
                case 2:
                    ArrayList<String> categorias = inventario.getCategorias();
                    System.out.println();
                    System.out.println("Categorias disponibles:");
                    for (int i = 0; i < categorias.size(); i++) {
                        System.out.println((i + 1) + ". " + categorias.get(i));
                    }
                    System.out.print("Seleccione una categoria: ");
                    int numeroCategoria = leerEntero(sc);

                    if (numeroCategoria < 1 || numeroCategoria > categorias.size()) {
                        System.out.println("Categoria no valida.");
                        break;
                    }

                    ArrayList<Producto> filtrados = inventario.buscarPorCategoria(categorias.get(numeroCategoria - 1));
                    System.out.println();
                    System.out.println("----- " + categorias.get(numeroCategoria - 1).toUpperCase() + " -----");
                    inventario.mostrarLista(filtrados);

                    System.out.print("Numero del producto: ");
                    int numeroProducto = leerEntero(sc);
                    if (numeroProducto < 1 || numeroProducto > filtrados.size()) {
                        System.out.println("Producto no valido.");
                        break;
                    }

                    Producto elegido = filtrados.get(numeroProducto - 1);
                    System.out.print("Cantidad: ");
                    int cantidad = leerEntero(sc);

                    if (cantidad <= 0) {
                        System.out.println("La cantidad debe ser mayor a 0.");
                    } else if (!inventario.hayStock(elegido, cantidad)) {
                        System.out.println("No hay suficiente stock. Disponible: " + elegido.getStock());
                    } else if (carrito.agregarProducto(elegido, cantidad)) {
                        System.out.println("Producto agregado al carrito.");
                    } else {
                        System.out.println("No se pudo agregar el producto.");
                    }
                    break;
                case 3:
                    System.out.println();
                    carrito.mostrarDetalle();
                    break;
                case 4:
                    System.out.println();
                    if (carrito.estaVacio()) {
                        System.out.println("No compro ningun producto. Hasta luego.");
                    } else {
                        carrito.mostrarFactura();
                        System.out.println("Gracias por su compra, " + cliente.getNombre() + ".");
                    }
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }

        sc.close();
    }

    public static Cliente buscarCliente(ArrayList<Cliente> clientes, String email) {
        for (Cliente cliente : clientes) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return cliente;
            }
        }
        return null;
    }

    public static boolean emailValido(String email) {
        int arroba = email.indexOf("@");
        int punto = email.lastIndexOf(".");
        return arroba > 0 && punto > arroba + 1 && punto < email.length() - 1;
    }

    public static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Valor no valido, intente de nuevo: ");
            sc.next();
        }
        int numero = sc.nextInt();
        sc.nextLine();
        return numero;
    }
}

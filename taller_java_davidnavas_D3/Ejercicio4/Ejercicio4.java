import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio4 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Agenda agenda = new Agenda("agenda.txt");

        int cargados = agenda.cargarDesdeArchivo();
        System.out.println("Se cargaron " + cargados + " contactos desde el archivo.");

        int opcion = 0;

        while (opcion != 7) {
            System.out.println();
            System.out.println("===== AGENDA DE CONTACTOS =====");
            System.out.println("1. Registrar contacto");
            System.out.println("2. Buscar contacto");
            System.out.println("3. Listar todos los contactos");
            System.out.println("4. Listar contactos por grupo");
            System.out.println("5. Eliminar contacto");
            System.out.println("6. Guardar agenda");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1:
                    String nombre = leerTexto(sc, "Nombre: ");
                    String telefono = leerTexto(sc, "Telefono: ");
                    String email = leerTexto(sc, "Email: ");
                    String grupo = elegirGrupo(sc);
                    agenda.agregarContacto(new Contacto(nombre, telefono, email, grupo));
                    break;
                case 2:
                    String buscar = leerTexto(sc, "Texto a buscar en el nombre: ");
                    ArrayList<Contacto> resultados = agenda.buscarContacto(buscar);
                    if (resultados.isEmpty()) {
                        System.out.println("No se encontraron contactos.");
                    } else {
                        System.out.println("Se encontraron " + resultados.size() + " contactos:");
                        for (Contacto contacto : resultados) {
                            contacto.mostrarInfo();
                        }
                    }
                    break;
                case 3:
                    agenda.mostrarTodos();
                    break;
                case 4:
                    String grupoBuscado = elegirGrupo(sc);
                    agenda.mostrarContactosPorGrupo(grupoBuscado);
                    break;
                case 5:
                    String eliminar = leerTexto(sc, "Nombre completo a eliminar: ");
                    if (agenda.eliminarContacto(eliminar)) {
                        System.out.println("Contacto eliminado.");
                    } else {
                        System.out.println("Contacto no encontrado.");
                    }
                    break;
                case 6:
                    if (agenda.guardarEnArchivo()) {
                        System.out.println("Agenda guardada (" + agenda.getCantidad() + " contactos).");
                    } else {
                        System.out.println("No se pudo guardar la agenda.");
                    }
                    break;
                case 7:
                    if (agenda.guardarEnArchivo()) {
                        System.out.println("Agenda guardada. Hasta luego.");
                    } else {
                        System.out.println("No se pudo guardar la agenda. Hasta luego.");
                    }
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }

        sc.close();
    }

    public static String elegirGrupo(Scanner sc) {
        String[] grupos = {"Familia", "Trabajo", "Amigos", "Otros"};

        System.out.println("Grupos:");
        for (int i = 0; i < grupos.length; i++) {
            System.out.println((i + 1) + ". " + grupos[i]);
        }
        System.out.print("Seleccione un grupo: ");
        int numero = leerEntero(sc);

        while (numero < 1 || numero > grupos.length) {
            System.out.print("Grupo no valido, intente de nuevo: ");
            numero = leerEntero(sc);
        }
        return grupos[numero - 1];
    }

    public static String leerTexto(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        String texto = sc.nextLine().trim();
        while (texto.isEmpty()) {
            System.out.print("No puede estar vacio. " + mensaje);
            texto = sc.nextLine().trim();
        }
        return texto;
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

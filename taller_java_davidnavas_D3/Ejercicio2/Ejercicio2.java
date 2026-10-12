import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Curso curso = new Curso();

        System.out.print("Cuantos estudiantes desea registrar? ");
        int n = leerEntero(sc);
        while (n <= 0) {
            System.out.print("Debe ser mayor a 0. Intente de nuevo: ");
            n = leerEntero(sc);
        }

        System.out.print("Cuantas asignaturas tiene el curso? ");
        int m = leerEntero(sc);
        while (m <= 0) {
            System.out.print("Debe ser mayor a 0. Intente de nuevo: ");
            m = leerEntero(sc);
        }

        ArrayList<String> nombresAsignaturas = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String nombreAsignatura = leerTexto(sc, "Nombre de la asignatura " + (i + 1) + ": ");
            while (contieneIgnorandoMayusculas(nombresAsignaturas, nombreAsignatura)) {
                nombreAsignatura = leerTexto(sc, "Esa asignatura ya existe. Ingrese otro nombre: ");
            }
            nombresAsignaturas.add(nombreAsignatura);
        }

        for (int i = 0; i < n; i++) {
            System.out.println();
            System.out.println("Estudiante " + (i + 1));
            String nombre = leerTexto(sc, "Nombre: ");
            while (curso.buscarEstudiante(nombre) != null) {
                nombre = leerTexto(sc, "Ya existe un estudiante con ese nombre. Ingrese otro: ");
            }

            System.out.print("Numero de faltas: ");
            int faltas = leerEntero(sc);
            while (faltas < 0) {
                System.out.print("Las faltas no pueden ser negativas: ");
                faltas = leerEntero(sc);
            }

            Estudiante estudiante = new Estudiante(nombre, faltas);
            for (String nombreAsignatura : nombresAsignaturas) {
                double nota = leerNota(sc, "Nota de " + nombreAsignatura + " (0 a 10): ");
                estudiante.agregarAsignatura(new Asignatura(nombreAsignatura, nota));
            }

            curso.agregarEstudiante(estudiante);
        }

        System.out.println();
        curso.mostrarReporte();

        int opcion = 0;
        while (opcion != 5) {
            System.out.println();
            System.out.println("===== REGISTRO ACADEMICO =====");
            System.out.println("1. Ver reporte del curso");
            System.out.println("2. Buscar estudiante");
            System.out.println("3. Modificar nota");
            System.out.println("4. Guardar reporte en archivo");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1:
                    curso.mostrarReporte();
                    break;
                case 2:
                    String nombreBuscar = leerTexto(sc, "Nombre del estudiante: ");
                    Estudiante encontrado = curso.buscarEstudiante(nombreBuscar);
                    if (encontrado != null) {
                        encontrado.mostrarInfo();
                    } else {
                        System.out.println("Estudiante no encontrado.");
                    }
                    break;
                case 3:
                    String nombreEstudiante = leerTexto(sc, "Nombre del estudiante: ");
                    Estudiante estudiante = curso.buscarEstudiante(nombreEstudiante);
                    if (estudiante == null) {
                        System.out.println("Estudiante no encontrado.");
                        break;
                    }

                    String nombreAsignatura = leerTexto(sc, "Nombre de la asignatura: ");
                    if (estudiante.buscarAsignatura(nombreAsignatura) == null) {
                        System.out.println("Asignatura no encontrada.");
                        break;
                    }

                    double nuevaNota = leerNota(sc, "Nueva nota (0 a 10): ");
                    if (curso.modificarNota(nombreEstudiante, nombreAsignatura, nuevaNota)) {
                        System.out.println("Nota modificada correctamente.");
                        estudiante.mostrarInfo();
                    } else {
                        System.out.println("No se pudo modificar la nota.");
                    }
                    break;
                case 4:
                    System.out.print("Nombre del archivo (ej: reporte.txt): ");
                    String nombreArchivo = sc.nextLine().trim();
                    if (nombreArchivo.isEmpty()) {
                        nombreArchivo = "reporte.txt";
                    }
                    if (!nombreArchivo.endsWith(".txt")) {
                        nombreArchivo = nombreArchivo + ".txt";
                    }

                    if (curso.guardarReporte(nombreArchivo)) {
                        System.out.println("Reporte guardado en " + nombreArchivo);
                    } else {
                        System.out.println("No se pudo guardar el reporte.");
                    }
                    break;
                case 5:
                    System.out.println("Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }

        sc.close();
    }

    public static boolean contieneIgnorandoMayusculas(ArrayList<String> lista, String texto) {
        for (String elemento : lista) {
            if (elemento.equalsIgnoreCase(texto)) {
                return true;
            }
        }
        return false;
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

    public static double leerNota(Scanner sc, String mensaje) {
        System.out.print(mensaje);
        double nota = leerDouble(sc);
        while (nota < 0 || nota > 10) {
            System.out.print("La nota debe estar entre 0 y 10: ");
            nota = leerDouble(sc);
        }
        return nota;
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

    public static double leerDouble(Scanner sc) {
        while (!sc.hasNextDouble()) {
            System.out.print("Valor no valido, intente de nuevo: ");
            sc.next();
        }
        double numero = sc.nextDouble();
        sc.nextLine();
        return numero;
    }
}

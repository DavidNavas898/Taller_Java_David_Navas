import java.util.InputMismatchException;
import java.util.Scanner;

public class Ejercicio5 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = leerEntero(sc, "Cuantos alumnos desea registrar? ");
        while (n <= 0) {
            n = leerEntero(sc, "Debe ser mayor a 0. Intente de nuevo: ");
        }

        Alumno[] alumnos = new Alumno[n];
        double sumaOriginal = 0;

        for (int i = 0; i < n; i++) {
            System.out.println();
            System.out.println("Alumno " + (i + 1));
            System.out.print("Nombre: ");
            String nombre = sc.nextLine().trim();
            while (nombre.isEmpty()) {
                System.out.print("El nombre no puede estar vacio: ");
                nombre = sc.nextLine().trim();
            }

            double parcial1 = leerDouble(sc, "Parcial 1 - 30% (0 a 10): ", 0, 10);
            double parcial2 = leerDouble(sc, "Parcial 2 - 30% (0 a 10): ", 0, 10);
            double examenFinal = leerDouble(sc, "Examen final - 40% (0 a 10): ", 0, 10);

            alumnos[i] = new Alumno(nombre, parcial1, parcial2, examenFinal);
            sumaOriginal = sumaOriginal + alumnos[i].getNotaOriginal();
        }

        double promedioOriginal = sumaOriginal / n;
        System.out.println();
        System.out.printf("Promedio del curso: %.2f%n", promedioOriginal);

        boolean seAplicoCurva = false;

        if (promedioOriginal < 6) {
            System.out.println("El promedio es menor a 6. Seleccione el tipo de curva:");
            System.out.println("1. Sumar puntos fijos");
            System.out.println("2. Aumentar un porcentaje");
            System.out.println("3. Curva basada en el mejor alumno");
            int tipoCurva = leerEntero(sc, "Opcion: ");
            while (tipoCurva < 1 || tipoCurva > 3) {
                tipoCurva = leerEntero(sc, "Opcion no valida, intente de nuevo: ");
            }

            if (tipoCurva == 1) {
                double puntos = leerDouble(sc, "Puntos a sumar (0 a 10): ", 0, 10);
                for (int i = 0; i < n; i++) {
                    alumnos[i].aplicarCurva(puntos);
                }
                System.out.printf("Se aplico una curva de +%.2f puntos.%n", puntos);
            } else if (tipoCurva == 2) {
                double porcentaje = leerDouble(sc, "Porcentaje a aumentar (0 a 100): ", 0, 100);
                for (int i = 0; i < n; i++) {
                    alumnos[i].aplicarCurvaPorcentual(porcentaje);
                }
                System.out.printf("Se aplico una curva del %.2f%%.%n", porcentaje);
            } else {
                double notaMasAlta = alumnos[0].getNotaOriginal();
                for (int i = 1; i < n; i++) {
                    if (alumnos[i].getNotaOriginal() > notaMasAlta) {
                        notaMasAlta = alumnos[i].getNotaOriginal();
                    }
                }
                double puntos = 10 - notaMasAlta;
                for (int i = 0; i < n; i++) {
                    alumnos[i].aplicarCurva(puntos);
                }
                System.out.printf("La nota mas alta fue %.2f, se aplico una curva de +%.2f puntos.%n", notaMasAlta, puntos);
            }

            seAplicoCurva = true;
        } else {
            System.out.println("El promedio es 6 o mas, no se aplica curva.");
        }

        System.out.println();
        System.out.printf("%-15s %6s %6s %6s %9s %7s %9s%n", "Nombre", "P1", "P2", "EF", "Original", "Final", "Estado");
        for (int i = 0; i < n; i++) {
            String estado;
            if (alumnos[i].estaAprobado()) {
                estado = "Aprobo";
            } else {
                estado = "Reprobo";
            }
            System.out.printf("%-15s %6.2f %6.2f %6.2f %9.2f %7.2f %9s%n", alumnos[i].getNombre(), alumnos[i].getNotaParcial1(), alumnos[i].getNotaParcial2(), alumnos[i].getExamenFinal(), alumnos[i].getNotaOriginal(), alumnos[i].getNotaFinal(), estado);
        }

        double sumaFinal = 0;
        int rescatados = 0;
        for (int i = 0; i < n; i++) {
            sumaFinal = sumaFinal + alumnos[i].getNotaFinal();
            if (!alumnos[i].aproboConNotaOriginal() && alumnos[i].estaAprobado()) {
                rescatados++;
            }
        }
        double mediaFinal = sumaFinal / n;

        double sumaCuadrados = 0;
        for (int i = 0; i < n; i++) {
            double diferencia = alumnos[i].getNotaFinal() - mediaFinal;
            sumaCuadrados = sumaCuadrados + diferencia * diferencia;
        }
        double desviacion = Math.sqrt(sumaCuadrados / n);

        System.out.println();
        System.out.println("===== ESTADISTICAS =====");
        if (seAplicoCurva) {
            System.out.printf("Media antes de la curva: %.2f%n", promedioOriginal);
            System.out.printf("Nueva media del curso: %.2f%n", mediaFinal);
        } else {
            System.out.printf("Media del curso: %.2f%n", mediaFinal);
        }
        System.out.printf("Desviacion estandar de las notas finales: %.2f%n", desviacion);
        System.out.println("Alumnos que aprobaron gracias a la curva: " + rescatados);

        sc.close();
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje);
            try {
                int numero = sc.nextInt();
                sc.nextLine();
                return numero;
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero entero.");
                sc.nextLine();
            }
        }
    }

    public static double leerDouble(Scanner sc, String mensaje, double minimo, double maximo) {
        while (true) {
            System.out.print(mensaje);
            try {
                double numero = sc.nextDouble();
                sc.nextLine();
                if (numero >= minimo && numero <= maximo) {
                    return numero;
                }
                System.out.printf("El valor debe estar entre %.0f y %.0f.%n", minimo, maximo);
            } catch (InputMismatchException e) {
                System.out.println("Debe ingresar un numero.");
                sc.nextLine();
            }
        }
    }
}

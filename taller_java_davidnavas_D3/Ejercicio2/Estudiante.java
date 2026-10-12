import java.util.ArrayList;

public class Estudiante {
    private String nombre;
    private ArrayList<Asignatura> asignaturas;
    private double promedio;
    private int faltas;

    public Estudiante(String nombre, int faltas) {
        this.nombre = nombre;
        this.faltas = faltas;
        asignaturas = new ArrayList<>();
        promedio = 0;
    }

    public void agregarAsignatura(Asignatura asignatura) {
        asignaturas.add(asignatura);
        calcularPromedio();
    }

    public void calcularPromedio() {
        if (asignaturas.isEmpty()) {
            promedio = 0;
            return;
        }

        double suma = 0;
        for (Asignatura asignatura : asignaturas) {
            suma = suma + asignatura.getNotaFinal();
        }
        promedio = suma / asignaturas.size();
    }

    public boolean aprobo() {
        return promedio >= 6 && faltas < 10;
    }

    public String getEstado() {
        if (aprobo()) {
            return "Aprobo";
        }
        return "Reprobo";
    }

    public Asignatura buscarAsignatura(String nombreAsignatura) {
        for (Asignatura asignatura : asignaturas) {
            if (asignatura.getNombre().equalsIgnoreCase(nombreAsignatura)) {
                return asignatura;
            }
        }
        return null;
    }

    public void mostrarInfo() {
        System.out.println("Estudiante: " + nombre);
        for (Asignatura asignatura : asignaturas) {
            System.out.printf("  %-15s %.2f%n", asignatura.getNombre(), asignatura.getNotaFinal());
        }
        System.out.printf("  Promedio: %.2f%n", promedio);
        System.out.println("  Faltas: " + faltas);
        System.out.println("  Estado: " + getEstado());
    }

    public String getNombre() {
        return nombre;
    }

    public double getPromedio() {
        return promedio;
    }

    public int getFaltas() {
        return faltas;
    }
}

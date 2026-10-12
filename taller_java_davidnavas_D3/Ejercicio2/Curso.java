import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Curso {
    private ArrayList<Estudiante> estudiantes;

    public Curso() {
        estudiantes = new ArrayList<>();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        estudiantes.add(estudiante);
    }

    public double calcularPromedioGeneral() {
        if (estudiantes.isEmpty()) {
            return 0;
        }

        double suma = 0;
        for (Estudiante estudiante : estudiantes) {
            suma = suma + estudiante.getPromedio();
        }
        return suma / estudiantes.size();
    }

    public int contarAprobados() {
        int aprobados = 0;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.aprobo()) {
                aprobados++;
            }
        }
        return aprobados;
    }

    public int contarReprobados() {
        return estudiantes.size() - contarAprobados();
    }

    public Estudiante getMejorEstudiante() {
        if (estudiantes.isEmpty()) {
            return null;
        }

        Estudiante mejor = estudiantes.get(0);
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getPromedio() > mejor.getPromedio()) {
                mejor = estudiante;
            }
        }
        return mejor;
    }

    public Estudiante getPeorEstudiante() {
        if (estudiantes.isEmpty()) {
            return null;
        }

        Estudiante peor = estudiantes.get(0);
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getPromedio() < peor.getPromedio()) {
                peor = estudiante;
            }
        }
        return peor;
    }

    public Estudiante buscarEstudiante(String nombre) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombre)) {
                return estudiante;
            }
        }
        return null;
    }

    public boolean modificarNota(String nombreEstudiante, String nombreAsignatura, double nuevaNota) {
        if (nuevaNota < 0 || nuevaNota > 10) {
            return false;
        }

        Estudiante estudiante = buscarEstudiante(nombreEstudiante);
        if (estudiante == null) {
            return false;
        }

        Asignatura asignatura = estudiante.buscarAsignatura(nombreAsignatura);
        if (asignatura == null) {
            return false;
        }

        asignatura.setNotaFinal(nuevaNota);
        estudiante.calcularPromedio();
        return true;
    }

    public String generarReporte() {
        String reporte = "===== REPORTE DEL CURSO =====\n";
        reporte = reporte + String.format("%-15s %10s %8s %10s%n", "Nombre", "Promedio", "Faltas", "Estado");

        for (Estudiante estudiante : estudiantes) {
            reporte = reporte + String.format("%-15s %10.2f %8d %10s%n", estudiante.getNombre(), estudiante.getPromedio(), estudiante.getFaltas(), estudiante.getEstado());
        }

        reporte = reporte + "\n";
        reporte = reporte + String.format("Promedio general del grupo: %.2f%n", calcularPromedioGeneral());
        reporte = reporte + "Aprobados: " + contarAprobados() + "\n";
        reporte = reporte + "Reprobados: " + contarReprobados() + "\n";

        Estudiante mejor = getMejorEstudiante();
        Estudiante peor = getPeorEstudiante();
        if (mejor != null && peor != null) {
            reporte = reporte + String.format("Mayor promedio: %s (%.2f)%n", mejor.getNombre(), mejor.getPromedio());
            reporte = reporte + String.format("Menor promedio: %s (%.2f)%n", peor.getNombre(), peor.getPromedio());
        }

        return reporte;
    }

    public void mostrarReporte() {
        System.out.print(generarReporte());
    }

    public boolean guardarReporte(String nombreArchivo) {
        try {
            FileWriter escritor = new FileWriter(nombreArchivo);
            escritor.write(generarReporte());
            escritor.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

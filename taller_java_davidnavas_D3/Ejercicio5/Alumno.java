public class Alumno {
    private String nombre;
    private double notaParcial1;
    private double notaParcial2;
    private double examenFinal;
    private double notaOriginal;
    private double notaFinal;

    public Alumno(String nombre, double notaParcial1, double notaParcial2, double examenFinal) {
        this.nombre = nombre;
        this.notaParcial1 = notaParcial1;
        this.notaParcial2 = notaParcial2;
        this.examenFinal = examenFinal;
        calcularNotaOriginal();
    }

    public void calcularNotaOriginal() {
        notaOriginal = notaParcial1 * 0.3 + notaParcial2 * 0.3 + examenFinal * 0.4;
        notaFinal = notaOriginal;
    }

    public void aplicarCurva(double puntos) {
        notaFinal = notaOriginal + puntos;
        if (notaFinal > 10) {
            notaFinal = 10;
        }
    }

    public void aplicarCurvaPorcentual(double porcentaje) {
        notaFinal = notaOriginal + notaOriginal * porcentaje / 100;
        if (notaFinal > 10) {
            notaFinal = 10;
        }
    }

    public boolean estaAprobado() {
        return notaFinal >= 6;
    }

    public boolean aproboConNotaOriginal() {
        return notaOriginal >= 6;
    }

    public String getNombre() {
        return nombre;
    }

    public double getNotaParcial1() {
        return notaParcial1;
    }

    public double getNotaParcial2() {
        return notaParcial2;
    }

    public double getExamenFinal() {
        return examenFinal;
    }

    public double getNotaOriginal() {
        return notaOriginal;
    }

    public double getNotaFinal() {
        return notaFinal;
    }
}

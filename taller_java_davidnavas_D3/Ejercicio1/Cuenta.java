public abstract class Cuenta {
    protected String titular;
    protected double saldo;
    protected String pin;

    public Cuenta(String titular, double saldo, String pin) {
        this.titular = titular;
        this.saldo = saldo;
        this.pin = pin;
    }

    public String getTitular() {
        return titular;
    }

    public double consultarSaldo() {
        return saldo;
    }

    public boolean validarPin(String pinIngresado) {
        return pin.equals(pinIngresado);
    }

    public abstract boolean depositar(double monto);

    public abstract boolean retirar(double monto);

    public abstract String getTipo();
}

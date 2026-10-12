public class CuentaAhorro extends CuentaBancaria {
    private double tasaInteres;

    public CuentaAhorro(String titular, double saldo, String pin, double tasaInteres) {
        super(titular, saldo, pin);
        this.tasaInteres = tasaInteres;
    }

    @Override
    public boolean depositar(double monto) {
        if (monto <= 0) {
            return false;
        }
        double interes = monto * tasaInteres;
        saldo = saldo + monto + interes;
        registrarMovimiento(String.format("Deposito: +$%.2f (interes: +$%.2f)", monto, interes));
        return true;
    }

    public double getTasaInteres() {
        return tasaInteres;
    }

    @Override
    public String getTipo() {
        return "Cuenta de Ahorro";
    }
}

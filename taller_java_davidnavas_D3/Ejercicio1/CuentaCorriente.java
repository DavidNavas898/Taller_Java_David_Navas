public class CuentaCorriente extends CuentaBancaria {
    private double limiteSobregiro;

    public CuentaCorriente(String titular, double saldo, String pin, double limiteSobregiro) {
        super(titular, saldo, pin);
        this.limiteSobregiro = limiteSobregiro;
    }

    @Override
    public boolean puedeRetirar(double monto) {
        return monto <= saldo + limiteSobregiro;
    }

    public double getLimiteSobregiro() {
        return limiteSobregiro;
    }

    @Override
    public String getTipo() {
        return "Cuenta Corriente";
    }
}

import java.util.ArrayList;

public class CuentaBancaria extends Cuenta {
    private ArrayList<String> movimientos;

    public CuentaBancaria(String titular, double saldo, String pin) {
        super(titular, saldo, pin);
        movimientos = new ArrayList<>();
    }

    @Override
    public boolean depositar(double monto) {
        if (monto <= 0) {
            return false;
        }
        saldo = saldo + monto;
        registrarMovimiento(String.format("Deposito: +$%.2f", monto));
        return true;
    }

    @Override
    public boolean retirar(double monto) {
        if (monto <= 0 || !puedeRetirar(monto)) {
            return false;
        }
        saldo = saldo - monto;
        registrarMovimiento(String.format("Retiro: -$%.2f", monto));
        return true;
    }

    public boolean transferir(CuentaBancaria cuentaDestino, double monto) {
        if (cuentaDestino == null || cuentaDestino == this) {
            return false;
        }
        if (monto <= 0 || !puedeRetirar(monto)) {
            return false;
        }
        saldo = saldo - monto;
        cuentaDestino.recibirTransferencia(titular, monto);
        registrarMovimiento(String.format("Transferencia a %s: -$%.2f", cuentaDestino.getTitular(), monto));
        return true;
    }

    private void recibirTransferencia(String titularOrigen, double monto) {
        saldo = saldo + monto;
        registrarMovimiento(String.format("Transferencia de %s: +$%.2f", titularOrigen, monto));
    }

    public boolean puedeRetirar(double monto) {
        return monto <= saldo;
    }

    protected void registrarMovimiento(String movimiento) {
        movimientos.add(movimiento);
        if (movimientos.size() > 5) {
            movimientos.remove(0);
        }
    }

    public ArrayList<String> getMovimientos() {
        return movimientos;
    }

    @Override
    public String getTipo() {
        return "Cuenta Bancaria";
    }
}

import java.util.ArrayList;

public class Banco {
    private ArrayList<CuentaBancaria> cuentas;

    public Banco() {
        cuentas = new ArrayList<>();
    }

    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
    }

    public CuentaBancaria buscarCuenta(String titular) {
        for (CuentaBancaria cuenta : cuentas) {
            if (cuenta.getTitular().equalsIgnoreCase(titular)) {
                return cuenta;
            }
        }
        return null;
    }

    public boolean validarPin(String titular, String pin) {
        CuentaBancaria cuenta = buscarCuenta(titular);
        if (cuenta == null) {
            return false;
        }
        return cuenta.validarPin(pin);
    }

    public void mostrarTitulares() {
        for (CuentaBancaria cuenta : cuentas) {
            System.out.println("- " + cuenta.getTitular());
        }
    }
}

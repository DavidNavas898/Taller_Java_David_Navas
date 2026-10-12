import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Banco banco = new Banco();
        banco.agregarCuenta(new CuentaAhorro("Carlos Gomez", 500000, "1234", 0.01));
        banco.agregarCuenta(new CuentaCorriente("Ana Torres", 300000, "4321", 200000));
        banco.agregarCuenta(new CuentaBancaria("Luis Perez", 150000, "1111"));

        System.out.println("===== BIENVENIDO AL CAJERO =====");
        System.out.print("Ingrese el nombre del titular: ");
        String titular = sc.nextLine().trim();
        CuentaBancaria cuenta = banco.buscarCuenta(titular);

        while (cuenta == null) {
            System.out.print("No existe una cuenta con ese titular. Intente de nuevo: ");
            titular = sc.nextLine().trim();
            cuenta = banco.buscarCuenta(titular);
        }

        int intentos = 0;
        boolean acceso = false;

        while (intentos < 3 && !acceso) {
            System.out.print("Ingrese su PIN: ");
            String pin = sc.nextLine().trim();

            if (banco.validarPin(cuenta.getTitular(), pin)) {
                acceso = true;
            } else {
                intentos++;
                System.out.println("PIN incorrecto. Intentos restantes: " + (3 - intentos));
            }
        }

        if (!acceso) {
            System.out.println("Cuenta bloqueada.");
            sc.close();
            return;
        }

        System.out.println("Bienvenido, " + cuenta.getTitular() + " (" + cuenta.getTipo() + ")");

        int opcion = 0;
        while (opcion != 6) {
            System.out.println();
            System.out.println("===== CAJERO AUTOMATICO =====");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Depositar dinero");
            System.out.println("4. Transferir dinero");
            System.out.println("5. Consultar movimientos");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = leerEntero(sc);

            switch (opcion) {
                case 1:
                    System.out.printf("Tipo de cuenta: %s%n", cuenta.getTipo());
                    System.out.printf("Su saldo es: $%.2f%n", cuenta.consultarSaldo());
                    if (cuenta instanceof CuentaCorriente) {
                        CuentaCorriente corriente = (CuentaCorriente) cuenta;
                        System.out.printf("Limite de sobregiro: $%.2f%n", corriente.getLimiteSobregiro());
                    }
                    if (cuenta instanceof CuentaAhorro) {
                        CuentaAhorro ahorro = (CuentaAhorro) cuenta;
                        System.out.printf("Interes por deposito: %.1f%%%n", ahorro.getTasaInteres() * 100);
                    }
                    break;
                case 2:
                    System.out.print("Ingrese el monto a retirar: ");
                    double retiro = leerDouble(sc);
                    if (retiro <= 0) {
                        System.out.println("El monto debe ser mayor a 0.");
                    } else if (cuenta.retirar(retiro)) {
                        System.out.printf("Retiro exitoso. Nuevo saldo: $%.2f%n", cuenta.consultarSaldo());
                    } else {
                        System.out.println("Fondos insuficientes.");
                    }
                    break;
                case 3:
                    System.out.print("Ingrese el monto a depositar: ");
                    double deposito = leerDouble(sc);
                    if (cuenta.depositar(deposito)) {
                        System.out.printf("Deposito exitoso. Nuevo saldo: $%.2f%n", cuenta.consultarSaldo());
                    } else {
                        System.out.println("El monto debe ser mayor a 0.");
                    }
                    break;
                case 4:
                    System.out.println("Cuentas registradas:");
                    banco.mostrarTitulares();
                    System.out.print("Titular de la cuenta destino: ");
                    String nombreDestino = sc.nextLine().trim();
                    CuentaBancaria destino = banco.buscarCuenta(nombreDestino);

                    if (destino == null) {
                        System.out.println("No existe una cuenta con ese titular.");
                    } else if (destino == cuenta) {
                        System.out.println("No puede transferir a su propia cuenta.");
                    } else {
                        System.out.print("Ingrese el monto a transferir: ");
                        double montoTransferencia = leerDouble(sc);
                        if (montoTransferencia <= 0) {
                            System.out.println("El monto debe ser mayor a 0.");
                        } else if (cuenta.transferir(destino, montoTransferencia)) {
                            System.out.printf("Transferencia exitosa a %s. Nuevo saldo: $%.2f%n", destino.getTitular(), cuenta.consultarSaldo());
                        } else {
                            System.out.println("Fondos insuficientes.");
                        }
                    }
                    break;
                case 5:
                    ArrayList<String> movimientos = cuenta.getMovimientos();
                    if (movimientos.isEmpty()) {
                        System.out.println("No hay movimientos registrados.");
                    } else {
                        System.out.println("Ultimos movimientos:");
                        for (int i = 0; i < movimientos.size(); i++) {
                            System.out.println((i + 1) + ". " + movimientos.get(i));
                        }
                    }
                    break;
                case 6:
                    System.out.println("Gracias por usar el cajero. Hasta luego.");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        }

        sc.close();
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

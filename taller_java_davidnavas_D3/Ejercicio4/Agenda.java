import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Agenda {
    private static final int DIGITOS_TELEFONO = 10;

    private ArrayList<Contacto> contactos;
    private String nombreArchivo;

    public Agenda(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
        contactos = new ArrayList<>();
    }

    public boolean agregarContacto(Contacto c) {
        if (c.getNombre().contains(";") || c.getEmail().contains(";")) {
            System.out.println("Los datos no pueden contener el caracter ;");
            return false;
        }

        if (!telefonoValido(c.getTelefono())) {
            System.out.println("El telefono debe tener " + DIGITOS_TELEFONO + " digitos y solo numeros.");
            return false;
        }

        if (!emailValido(c.getEmail())) {
            System.out.println("El email debe contener @ y un punto (ej: nombre@correo.com).");
            return false;
        }

        for (Contacto contacto : contactos) {
            if (contacto.getNombre().equalsIgnoreCase(c.getNombre())) {
                System.out.println("Ya existe un contacto con ese nombre.");
                return false;
            }
            if (contacto.getTelefono().equals(c.getTelefono())) {
                System.out.println("Ya existe un contacto con ese telefono.");
                return false;
            }
        }

        contactos.add(c);
        System.out.println("Contacto agregado correctamente.");
        return true;
    }

    private boolean telefonoValido(String telefono) {
        if (telefono.length() != DIGITOS_TELEFONO) {
            return false;
        }

        for (int i = 0; i < telefono.length(); i++) {
            char caracter = telefono.charAt(i);
            if (caracter < '0' || caracter > '9') {
                return false;
            }
        }
        return true;
    }

    private boolean emailValido(String email) {
        int arroba = email.indexOf("@");
        int punto = email.lastIndexOf(".");
        return arroba > 0 && punto > arroba + 1 && punto < email.length() - 1;
    }

    public ArrayList<Contacto> buscarContacto(String nombre) {
        ArrayList<Contacto> resultado = new ArrayList<>();
        String textoBuscado = nombre.toLowerCase();

        for (Contacto contacto : contactos) {
            if (contacto.getNombre().toLowerCase().contains(textoBuscado)) {
                resultado.add(contacto);
            }
        }
        return resultado;
    }

    public void mostrarTodos() {
        if (contactos.isEmpty()) {
            System.out.println("La agenda esta vacia.");
            return;
        }

        for (int i = 0; i < contactos.size(); i++) {
            System.out.print((i + 1) + ". ");
            contactos.get(i).mostrarInfo();
        }
    }

    public void mostrarContactosPorGrupo(String grupo) {
        int encontrados = 0;

        for (Contacto contacto : contactos) {
            if (contacto.getGrupo().equalsIgnoreCase(grupo)) {
                encontrados++;
                System.out.print(encontrados + ". ");
                contacto.mostrarInfo();
            }
        }

        if (encontrados == 0) {
            System.out.println("No hay contactos en el grupo " + grupo + ".");
        }
    }

    public boolean eliminarContacto(String nombre) {
        for (int i = 0; i < contactos.size(); i++) {
            if (contactos.get(i).getNombre().equalsIgnoreCase(nombre)) {
                contactos.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean guardarEnArchivo() {
        try {
            FileWriter escritor = new FileWriter(nombreArchivo);
            for (Contacto contacto : contactos) {
                escritor.write(contacto.getNombre() + ";" + contacto.getTelefono() + ";" + contacto.getEmail() + ";" + contacto.getGrupo() + "\n");
            }
            escritor.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public int cargarDesdeArchivo() {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            return 0;
        }

        contactos.clear();
        try {
            Scanner lector = new Scanner(archivo);
            while (lector.hasNextLine()) {
                String linea = lector.nextLine();
                String[] partes = linea.split(";");
                if (partes.length == 4) {
                    contactos.add(new Contacto(partes[0], partes[1], partes[2], partes[3]));
                }
            }
            lector.close();
        } catch (FileNotFoundException e) {
            return 0;
        }
        return contactos.size();
    }

    public int getCantidad() {
        return contactos.size();
    }
}

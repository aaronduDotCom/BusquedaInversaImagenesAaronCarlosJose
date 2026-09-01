package Model.Repositorios;
// se encarga de la logica de serializar y deserializar las imagenes de la lista doble

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import Model.Estructuras.Vector;
import Model.Estructuras.Nodo;

public class Serializador {

    public void guardar(Nodo<Vector<Object>> cabeza, String ruta) throws IOException {
        // Primero se cuenta cuantos nodos hay, recorriendo con getSiguiente()
        int total = 0;
        Nodo<Vector<Object>> actual = cabeza;
        while (actual != null) {
            total++;
            actual = actual.getSiguiente();
        }

        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(ruta)))) {

            out.writeInt(total);
            actual = cabeza;
            while (actual != null) {
                Vector<Object> datos = actual.getValor();

                String rutaImagen = (String) datos.getPos(0);
                out.writeUTF(rutaImagen);

                int cantidadBins = datos.tamanno() - 1; // sin contar la ruta en pos 0
                out.writeInt(cantidadBins);
                for (int i = 1; i <= cantidadBins; i++) {
                    out.writeDouble((Double) datos.getPos(i));
                }

                actual = actual.getSiguiente();
            }
        }
    }

    public Nodo<Vector<Object>> cargar(String ruta) throws IOException {
        Nodo<Vector<Object>> cabeza = null;
        Nodo<Vector<Object>> cola = null;

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(ruta)))) {

            int total = in.readInt();
            for (int i = 0; i < total; i++) {
                String rutaImagen = in.readUTF();
                int cantidadBins = in.readInt();

                Vector<Object> datos = new Vector<>(cantidadBins + 1);
                datos.insertar(rutaImagen);
                for (int j = 0; j < cantidadBins; j++) {
                    datos.insertar((Object) in.readDouble());
                }

                Nodo<Vector<Object>> nuevo = new Nodo<>(datos);
                if (cabeza == null) {
                    cabeza = nuevo;
                    cola = nuevo;
                } else {
                    cola.setSiguiente(nuevo);
                    nuevo.setAnterior(cola);
                    cola = nuevo;
                }
            }
        }
        return cabeza;
    }
}
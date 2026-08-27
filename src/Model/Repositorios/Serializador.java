package Model.Repositorios;
// se encarga de la logica de serializar y deserializar las imagenes de la lista doble
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Serializador{

    public void guardar(ListaDoble<VectorCaracteristico> lista, String ruta) throws IOException {
        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(ruta)))) {

            out.writeInt(lista.tamano());
            Iterador<VectorCaracteristico> it = lista.getIterador();
            while (it.haySiguiente()) {
                VectorCaracteristico v = it.siguiente();
                out.writeUTF(v.getRutaImagen());
                double[] datos = v.getDatos();
                out.writeInt(datos.length);
                for (double d : datos) {
                    out.writeDouble(d);
                }
            }
        }
    }

    public ListaDoble<VectorCaracteristico> cargar(String ruta) throws IOException {
        ListaDoble<VectorCaracteristico> lista = new ListaDoble<>();
        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(new FileInputStream(ruta)))) {

            int total = in.readInt();
            for (int i = 0; i < total; i++) {
                String rutaImagen = in.readUTF();
                int n = in.readInt();
                double[] datos = new double[n];
                for (int j = 0; j < n; j++) {
                    datos[j] = in.readDouble();
                }
                lista.insertarFinal(new VectorCaracteristico(rutaImagen, datos));
            }
        }
        return lista;
    }
}
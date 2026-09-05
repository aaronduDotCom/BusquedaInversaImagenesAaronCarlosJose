
package Model.Repositorios;
import Model.Estructuras.Iterator;
import Model.Estructuras.Nodo;
import Model.Estructuras.Vector;
import Model.Estructuras.ColeccionImagen;
import Model.Imagen.Imagen;
import Model.Services.Busqueda.HistogramaColor;

import java.io.*;

public class Serializador {

    public void guardar(ColeccionImagen coleccion, String ruta)
            throws IOException {

        try (DataOutputStream out = new DataOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(ruta)))) {

            // Primer iterador: contar las imágenes
            Iterator<Imagen> iterador = coleccion.getIterador();

            int cantidad = 0;

            while (iterador.hasNext()) {
                iterador.next();
                cantidad++;
            }

            // Guardar cantidad de imágenes
            out.writeInt(cantidad);

            // Segundo iterador: guardar los datos
            iterador = coleccion.getIterador();

            while (iterador.hasNext()) {

                Imagen imagen = iterador.next();

                // Guardar nombre
                out.writeUTF(imagen.getId().toString());


            }
        }
    }
    public ColeccionImagen cargar(String ruta)
            throws IOException {

        ColeccionImagen coleccion =
                new ColeccionImagen();

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(ruta)))) {

            int cantidad = in.readInt();

            for (int i = 0; i < cantidad; i++) {

                String nombre = in.readUTF();

                int tamanioVector = in.readInt();

                Vector<Integer> vector =
                        new Vector<>(tamanioVector);

                for (int j = 0; j < tamanioVector; j++) {
                    int valor = in.readInt();
                    vector.insertar(valor);
                }

                Imagen imagenData =
                        new Imagen(nombre, vector);

                coleccion.insertarFinal(imagenData);
            }
        }

        return coleccion;
    }

    public void serializarCarpeta(String rutaCarpeta,
                                  String rutaBin)
            throws Exception {

        File carpeta = new File(rutaCarpeta);

        if (!carpeta.exists()) {
            throw new FileNotFoundException(
                    "La carpeta no existe: " + rutaCarpeta
            );
        }

        if (!carpeta.isDirectory()) {
            throw new IOException(
                    "La ruta indicada no es una carpeta: "
                            + rutaCarpeta
            );
        }

        File[] archivos = carpeta.listFiles();

        if (archivos == null) {
            throw new IOException(
                    "No se pudieron leer los archivos de la carpeta."
            );
        }

        ColeccionImagen coleccion =
                new ColeccionImagen();

        HistogramaColor histograma =
                new HistogramaColor();

        for (File archivo : archivos) {

            if (!archivo.getName()
                    .toLowerCase()
                    .endsWith(".png")) {
                continue;
            }

            System.out.println(
                    "Procesando: " + archivo.getName()
            );

            Imagen imagen =
                    new Imagen(archivo);

            Vector<Integer> vector =
                    histograma.calculaVector(imagen);

            imagen = new Imagen(
                    archivo.getName(),
                    vector
            );

            coleccion.insertarFinal(imagen);
        }

        guardar(coleccion, rutaBin);

        System.out.println(
                "Serialización terminada."
        );

        System.out.println(
                "Archivo creado: " + rutaBin
        );
    }

    public boolean existeBin(String ruta) {

        File archivo = new File(ruta);

        return archivo.exists()
                && archivo.isFile();
    }

    public int cantidadImagenes(String ruta)
            throws IOException {

        try (DataInputStream in = new DataInputStream(
                new BufferedInputStream(
                        new FileInputStream(ruta)))) {

            return in.readInt();
        }
    }
}


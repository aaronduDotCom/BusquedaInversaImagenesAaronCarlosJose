
package Model.Serializador;
import Model.Estructuras.Iterator;
import Model.Estructuras.Vector;
import Model.Estructuras.ColeccionImagenData;
import Model.Imagen.Imagen;
import Model.Imagen.HistogramaColor;
import Model.Imagen.ImagenData;
import java.io.*;
import java.util.UUID;

public class Serializador {
    private static final int TAMANNO_HISTOGRAMA = 64;

    public void guardar(ColeccionImagenData coleccion, String ruta) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(ruta)))) {

            out.writeInt(coleccion.tamano());
            // Primer iterador: contar las imágenes
            Iterator<ImagenData> iterador = coleccion.getIterador();

            int cantidad = 0;

            while (iterador.hasNext()) {
                ImagenData imagenData = iterador.next();
                // Guardar UUID
                out.writeUTF(imagenData.getId().toString());
                // Guardar ruta de la imagen
                out.writeUTF(imagenData.getRuta());
                // Guardar tamaño del vector
                out.writeInt(imagenData.getVector().tamanno());
                Vector<Double> vector = imagenData.getVector();
                out.writeInt(vector.tamanno());
                Iterator<Double> iteradorVector = vector.getIterador();
                while (iteradorVector.hasNext()) {
                    out.writeDouble(iteradorVector.next());
                }
            }

            }
        }

    public ColeccionImagenData cargar(String ruta) throws IOException {

        ColeccionImagenData coleccion = new ColeccionImagenData();
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(ruta)))) {

            int cantidadImagenes = in.readInt();

            for (int i = 0; i < cantidadImagenes; i++) {

                UUID id = UUID.fromString(in.readUTF());

                String rutaImagen = in.readUTF();
                int tamannoVector = in.readInt();
                if (tamannoVector != TAMANNO_HISTOGRAMA) {
                    throw new IOException("El histograma almacenado debe tener " + TAMANNO_HISTOGRAMA + " posiciones");
                }
                Vector<Double> vector = new Vector<>(tamannoVector);
                for (int j = 0; j < tamannoVector; j++) {

                    vector.insertar(in.readDouble());
                }
                ImagenData imagenData =

                new ImagenData(vector, id, rutaImagen);
                coleccion.insertarFinal(imagenData);
            }
        }

        return coleccion;
    }

    public ColeccionImagenData procesarCarpeta(String rutaCarpeta) throws Exception {

        if (rutaCarpeta == null || rutaCarpeta.isBlank()) {
            throw new IllegalArgumentException("La ruta de la carpeta no puede estar vacía");
        }

        File carpeta = new File(rutaCarpeta);

        if (!carpeta.exists()) {
            throw new FileNotFoundException("La carpeta no existe: " + rutaCarpeta);
        }

        if (!carpeta.isDirectory()) {
            throw new IOException("La ruta seleccionada no corresponde a una carpeta");
        }

        File[] archivos = carpeta.listFiles();
        if (archivos == null) {
            throw new IOException("No se pudieron leer los archivos de la carpeta");
        }

        ColeccionImagenData coleccion = new ColeccionImagenData();
        HistogramaColor histogramaColor = new HistogramaColor();

        for (File archivo : archivos) {

            if (!archivo.isFile()) {
                continue;
            }

            if (!esArchivoImagen(archivo)) {
                continue;
            }

            try {
                Imagen imagen = new Imagen(archivo);
                Vector<Double> histograma = histogramaColor.calculaVector(imagen);
                ImagenData imagenData = new ImagenData(histograma, imagen.getId(), imagen.getRuta());
                coleccion.insertarFinal(imagenData);
            } catch (IOException e) {
            }
        }

        return coleccion;
    }

    public ColeccionImagenData serializarCarpeta(String rutaCarpeta, String rutaArchivoBinario
    ) throws Exception {

        ColeccionImagenData coleccion = procesarCarpeta(rutaCarpeta);
        if (coleccion.tamano() == 0) {
            throw new IOException("La carpeta no contiene imágenes válidas");
        }

        guardar(coleccion, rutaArchivoBinario);

        return coleccion;
    }

    private boolean esArchivoImagen(File archivo) {
        String nombre = archivo.getName().toLowerCase();

        return nombre.endsWith(".png")
                || nombre.endsWith(".jpg")
                || nombre.endsWith(".jpeg")
                || nombre.endsWith(".bmp");
    }


    public boolean existeBin(String rutaArchivo) {

        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            return false;
        }
        File archivo = new File(rutaArchivo);
        return archivo.exists() && archivo.isFile();
    }

    public int cantidadImagenes(
            String rutaArchivo
    ) throws IOException {
        if (rutaArchivo == null || rutaArchivo.isBlank()) {
            throw new IllegalArgumentException("La ruta del archivo no puede estar vacía");
        }

        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(rutaArchivo)))) {
            int cantidad = in.readInt();
            if (cantidad < 0) {
                throw new IOException("Cantidad de imágenes inválida");
            }

            return cantidad;
        }
    }
}




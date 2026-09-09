import Model.Estructuras.ColeccionImagenData;
import Model.Repositorios.RepoImagenes;
import Model.Serializador.Serializador;

import javax.swing.*;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        try {

            JFileChooser selector = new JFileChooser();
            selector.setDialogTitle("Seleccione la carpeta del banco de imágenes");
            selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int opcion = selector.showOpenDialog(null);

            if (opcion != JFileChooser.APPROVE_OPTION) {
                System.exit(0);
            }

            File carpetaSeleccionada = selector.getSelectedFile();
            Serializador serializador = new Serializador();
            String rutaBin = new File(carpetaSeleccionada, "banco_indice.bin").getAbsolutePath();

            ColeccionImagenData coleccion;
            if (serializador.existeBin(rutaBin)) {
                System.out.println("CACHE HIT: cargando desde " + rutaBin);
                coleccion = serializador.cargar(rutaBin);
            } else {
                System.out.println("CACHE MISS: reprocesando carpeta completa");
                coleccion = serializador.serializarCarpeta(carpetaSeleccionada.getAbsolutePath(), rutaBin);
            }
            RepoImagenes.getInstance().reemplazar(coleccion);
            // ColeccionImagenData coleccion = serializador.procesarCarpeta(carpetaSeleccionada.getAbsolutePath());
            // RepoImagenes.getInstance().reemplazar(coleccion);
            Presentation.Busqueda.Model model = new Presentation.Busqueda.Model();
            Presentation.Busqueda.View view = new Presentation.Busqueda.View();
            Presentation.Busqueda.Controller controller = new Presentation.Busqueda.Controller(view, model);

            view.setVisible(true);
        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }
}
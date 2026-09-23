package Busqueda;

import Busqueda.Model.Estructuras.ColeccionImagenData;
import Busqueda.Model.Repositorios.RepoImagenes;
import Busqueda.Model.Serializador.Serializador;
import Busqueda.Presentation.Busqueda.Controller;

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
            Busqueda.Presentation.Busqueda.Model model = new Busqueda.Presentation.Busqueda.Model();
            Busqueda.Presentation.Busqueda.View view = new Busqueda.Presentation.Busqueda.View();
            Busqueda.Presentation.Busqueda.Controller controller = new Busqueda.Presentation.Busqueda.Controller(view, model);
            ColeccionImagenData coleccion;
            int binsBanco = controller.getCantidadBins();

            if (serializador.existeBin(rutaBin)) {
                System.out.println(
                        "CACHE HIT: cargando desde " + rutaBin
                );

                coleccion = serializador.cargar(
                        rutaBin,
                        binsBanco
                );
            } else {
                System.out.println(
                        "CACHE MISS: reprocesando carpeta completa"
                );

                coleccion = serializador.serializarCarpeta(
                        carpetaSeleccionada.getAbsolutePath(),
                        rutaBin,
                        binsBanco
                );
            }

            controller.setBinsBanco(binsBanco);

            RepoImagenes.getInstance().reemplazar(coleccion);


            view.setVisible(true);
        } catch (Exception ex) {

            JOptionPane.showMessageDialog(
                    null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }
}
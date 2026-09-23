package Busqueda;

import Busqueda.Model.Estructuras.ColeccionImagenData;
import Busqueda.Model.Repositorios.RepoImagenes;
import Busqueda.Model.Serializador.Serializador;
import Busqueda.Presentation.Busqueda.Controller;
import Busqueda.Presentation.Busqueda.Model;
import Busqueda.Presentation.Busqueda.View;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
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
            String rutaCarpeta = carpetaSeleccionada.getAbsolutePath();
            String rutaBin = new File(carpetaSeleccionada, "banco_indice.bin").getAbsolutePath();

            Model model = new Model();
            View view = new View();

            Controller controller = new Controller(view, model);
            Serializador serializador = new Serializador();
            int binsIniciales = controller.getCantidadBins();
            ColeccionImagenData coleccion;
            if (serializador.existeBin(rutaBin)) {
                System.out.println("CACHE HIT: cargando desde " + rutaBin);
                try {
                    coleccion = serializador.cargar(rutaBin, binsIniciales);
                } catch (Exception ex) {
                    System.out.println("El archivo binario no es compatible " + "con la configuración actual.");
                    System.out.println("El banco será reconstruido con " + binsIniciales + " bins.");
                    coleccion = serializador.serializarCarpeta(rutaCarpeta, rutaBin, binsIniciales);
                }

            } else {
                System.out.println("CACHE MISS: reprocesando carpeta completa");
                coleccion = serializador.serializarCarpeta(rutaCarpeta, rutaBin, binsIniciales);
            }
            RepoImagenes repositorio = RepoImagenes.getInstance();
            repositorio.configurarRutas(rutaCarpeta, rutaBin);
            repositorio.reemplazar(coleccion, binsIniciales);
            view.setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            String mensaje = ex.getMessage();
            if (mensaje == null || mensaje.isBlank()) {
                mensaje = "Ocurrió un error al iniciar la aplicación";
            }
            JOptionPane.showMessageDialog(null, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
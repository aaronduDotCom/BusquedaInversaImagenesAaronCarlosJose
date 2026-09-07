package Presentation.Busqueda;

import Model.Imagen.Imagen;
import java.io.File;

public class Controller {

    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        cargarMetodosBusqueda();
    }

    private void cargarMetodosBusqueda() {
        view.getSearchMethodcomboBox().removeAllItems();

        view.getSearchMethodcomboBox().addItem("Similitud Coseno");
        view.getSearchMethodcomboBox().addItem("Similitud Euclidiana");
        view.getSearchMethodcomboBox().addItem("Intersección de Histogramas");
    }

    public void selectImage(String path) throws Exception {
        File imageFile = new File(path);
        Imagen selectedImage = new Imagen(imageFile);

        if (selectedImage.getImagenBI() == null) {
            throw new Exception("El archivo seleccionado no contiene una imagen válida");
        }

        model.setCurrent(selectedImage);
    }

    public void search() throws Exception {
        if (model.getCurrent() == null) {
            throw new Exception("Debe seleccionar una imagen antes de buscar");
        }

        String selectedMethod = (String) view.getSearchMethodcomboBox().getSelectedItem();
        if (selectedMethod == null || selectedMethod.isBlank()) {
            throw new Exception("Debe seleccionar un método de búsqueda");
        }

        model.setMethod(selectedMethod);

        System.out.println("Imagen seleccionada: " + model.getCurrent().getRuta());
        System.out.println("Método seleccionado: " + model.getMethod());

        // Pendiente:
        // 1. Llamar al servicio correspondiente.
        // 2. Obtener las imágenes similares.
        // 3. Ordenar los resultados.
        // 4. Abrir la pantalla de resultados.
    }
}
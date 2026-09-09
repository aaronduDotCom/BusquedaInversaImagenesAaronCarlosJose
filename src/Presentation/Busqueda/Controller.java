package Presentation.Busqueda;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.ResultadoBusqueda;
import Model.Excepciones.CantidadResultadosInvalida;
import Model.Excepciones.MetodoBusquedaInvalido;
import Model.Excepciones.ValidacionImagen;
import Model.Imagen.Imagen;
import Model.Services.Busqueda.BuscadorInverso;
import Model.Services.Fabricas.FabricaBusqueda;
import Model.Services.Fabricas.FabricaBusquedaBubble;
import Model.Services.Fabricas.FabricaBusquedaMerge;
import Model.Services.Ordenamiento.BubbleSort;
import Model.Services.Ordenamiento.MergeSort;
import Model.Services.Ordenamiento.MetodoOrdenamiento;
import Model.Services.Similitud.MetodoSimilitud;

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
        cargarMetodosOrdenamiento();
        cargarBins();
    }

    private void cargarMetodosBusqueda() {
        view.getSearchMethodcomboBox().removeAllItems();

        view.getSearchMethodcomboBox().addItem("Similitud Coseno");
        view.getSearchMethodcomboBox().addItem("Similitud Euclidiana");
        view.getSearchMethodcomboBox().addItem("Intersección de Histogramas");
    }

    private void cargarMetodosOrdenamiento(){
        view.getComboBoxOrdenamiento().removeAllItems();
        view.getComboBoxOrdenamiento().addItem("Bubble Sort");
        view.getComboBoxOrdenamiento().addItem("Merge Sort");
    }

    public void cargarBins(){
        view.getComboBoxBins().removeAllItems();
        view.getComboBoxBins().addItem("2");
        view.getComboBoxBins().addItem("4");
        view.getComboBoxBins().addItem("8");
        view.getComboBoxBins().addItem("16");
        view.getComboBoxBins().addItem("32");
        view.getComboBoxBins().addItem("64");
        view.getComboBoxBins().addItem("128");
        view.getComboBoxBins().addItem("256");

    }

    public void selectImage(String path) throws Exception {
        File imageFile = new File(path);
        Imagen selectedImage = new Imagen(imageFile);

        if (selectedImage.getImagenBI() == null) {
            throw new ValidacionImagen("El archivo seleccionado no contiene una imagen válida");
        }

        model.setCurrent(selectedImage);
    }

    public void search() throws Exception {

        if (model.getCurrent() == null) {
            throw new ValidacionImagen("Debe seleccionar una imagen antes de buscar");
        }
        String metodoBusquedaSeleccionado = (String) view.getSearchMethodcomboBox().getSelectedItem();
        if (metodoBusquedaSeleccionado == null || metodoBusquedaSeleccionado.isBlank()) {
            throw new MetodoBusquedaInvalido("Debe seleccionar un método de búsqueda");
        }

        String textoCantidad = view.getCantidadResultados().getText().trim();
        if (textoCantidad.isEmpty()) {
            throw new CantidadResultadosInvalida("Debe indicar la cantidad de resultados");
        }
        int cantidadResultados;
        try {
            cantidadResultados = Integer.parseInt(textoCantidad);
        } catch (NumberFormatException ex) {
            throw new CantidadResultadosInvalida("La cantidad de resultados debe ser un número entero");
        }

        if (cantidadResultados <= 0) {
            throw new CantidadResultadosInvalida("La cantidad de resultados debe ser mayor que cero");
        }
        model.setMethod(metodoBusquedaSeleccionado);
        /*
         * ABSTRACT FACTORY
         */
        FabricaBusqueda fabrica;
        if ("Similitud Coseno".equals(metodoBusquedaSeleccionado)) {
            fabrica = new FabricaBusquedaCoseno();
        } else if ("Similitud Euclidiana".equals(metodoBusquedaSeleccionado)) {
            fabrica = new FabricaBusquedaEuclidiana();
        } else {
            fabrica = new FabricaBusquedaInterseccion();
        }
        MetodoBusqueda metodoBusqueda = fabrica.crearMetodoBusqueda();
        /*
         * STRATEGY DE ORDENAMIENTO
         */
        MetodoOrdenamiento metodoOrdenamiento;
        String ordenamientoSeleccionado = (String) view.getComboBoxOrdenamiento().getSelectedItem();
        if ("Merge Sort".equals(ordenamientoSeleccionado)) {
            metodoOrdenamiento = new MergeSort();
        } else {
            metodoOrdenamiento = new BubbleSort();
        }
        /*
         * BUSQUEDA
         */
        BuscadorInverso buscador = new BuscadorInverso();
        ResultadoBusqueda resultados = buscador.buscar(model.getCurrent(), metodoBusqueda, metodoOrdenamiento, cantidadResultados);

        /*
         * MVC Pantalla Resultado
         */
        Presentation.Busqueda.PantallaResultado.Model resultadoModel = new Presentation.Busqueda.PantallaResultado.Model();
        Presentation.Busqueda.PantallaResultado.View resultadoView = new Presentation.Busqueda.PantallaResultado.View();
        Presentation.Busqueda.PantallaResultado.Controller resultadoController = new Presentation.Busqueda.PantallaResultado.Controller(resultadoView, resultadoModel);
        resultadoModel.setCurrent(model.getCurrent());
        resultadoModel.setMethod(model.getMethod());
        resultadoModel.setResults(resultados);
        resultadoView.setVisible(true);

        view.dispose();
    }
}
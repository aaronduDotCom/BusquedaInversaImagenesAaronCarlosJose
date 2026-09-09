package Presentation.Busqueda;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.ResultadoBusqueda;
import Model.Estructuras.Vector;
import Model.Excepciones.CantidadResultadosInvalida;
import Model.Excepciones.MetodoBusquedaInvalido;
import Model.Excepciones.ValidacionImagen;
import Model.Imagen.HistogramaColor;
import Model.Imagen.Imagen;
import Model.Imagen.ImagenData;
import Model.Repositorios.RepoImagenes;
import Model.Services.Busqueda.BuscadorInverso;
import Model.Services.Fabricas.*;
import Model.Services.Ordenamiento.BubbleSort;
import Model.Services.Ordenamiento.MergeSort;
import Model.Services.Ordenamiento.MetodoOrdenamiento;
import Model.Services.Similitud.MetodoSimilitud;

import java.io.File;
import java.util.UUID;

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

        String metodoSeleccionado = (String) view.getSearchMethodcomboBox().getSelectedItem();

        if (metodoSeleccionado == null || metodoSeleccionado.isBlank()) {

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

        model.setMethod(metodoSeleccionado);
        /*
         * ABSTRACT FACTORY
         */
        FabricaSimilitud fabrica;
        if ("Coseno".equals(metodoSeleccionado)) {
            fabrica = new FabricaSimilitudCoseno();
        } else if ("Euclidiana".equals(metodoSeleccionado)) {
            fabrica = new FabricaSimilitudEuclidiana();
        } else {
            fabrica = new FabricaSimilitudInterseccion();
        }
        MetodoSimilitud metodoSimilitud = fabrica.crearMetodoSimilitud();
        /*
         * STRATEGY ORDENAMIENTO
         */
        MetodoOrdenamiento metodoOrdenamiento;
        String ordenamientoSeleccionado = (String) view.getComboBoxOrdenamiento().getSelectedItem();
        if ("Merge Sort".equals(ordenamientoSeleccionado)) {
            metodoOrdenamiento = new MergeSort();

        } else {
            metodoOrdenamiento = new BubbleSort();
        }

        /*
         * CREAR IMAGENDATA DE CONSULTA
         */
        HistogramaColor histograma = new HistogramaColor();
        Vector<Double> vectorCaracteristico = histograma.calculaVector(model.getCurrent());
        ImagenData imagenConsulta = new ImagenData(vectorCaracteristico, UUID.randomUUID(), model.getCurrent().getRuta());
        /*
         * BUSQUEDA
         */
        BuscadorInverso buscador = new BuscadorInverso(metodoSimilitud, metodoOrdenamiento);
        ResultadoBusqueda resultados = buscador.buscar(imagenConsulta, RepoImagenes.getInstance().obtenerImagenes(), cantidadResultados);
        /*
         * PANTALLA RESULTADOS
         */
        Presentation.Busqueda.PantallaResultado.Model resultadoModel = new Presentation.Busqueda.PantallaResultado.Model(model.getCurrent(), resultados, model.getMethod());
        Presentation.Busqueda.PantallaResultado.View resultadoView = new Presentation.Busqueda.PantallaResultado.View();
        Presentation.Busqueda.PantallaResultado.Controller resultadoController = new Presentation.Busqueda.PantallaResultado.Controller(resultadoView, resultadoModel);
        resultadoModel.setCurrent(model.getCurrent());
        resultadoModel.setMethod(model.getMethod());
        resultadoModel.setResults(resultados);

        resultadoView.setVisible(true);

        view.dispose();
    }
}
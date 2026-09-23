package Busqueda.Presentation.Busqueda;

import Busqueda.Model.Estructuras.ResultadoBusqueda;
import Busqueda.Model.Estructuras.Vector;
import Busqueda.Model.Excepciones.CantidadResultadosInvalida;
import Busqueda.Model.Excepciones.MetodoBusquedaInvalido;
import Busqueda.Model.Excepciones.ValidacionImagen;
import Busqueda.Model.Imagen.HistogramaColor;
import Busqueda.Model.Imagen.Imagen;
import Busqueda.Model.Imagen.ImagenData;
import Busqueda.Model.Repositorios.RepoImagenes;
import Busqueda.Model.Services.Busqueda.BuscadorInverso;
import Busqueda.Model.Services.Fabricas.*;
import Busqueda.Model.Services.Ordenamiento.BubbleSort;
import Busqueda.Model.Services.Ordenamiento.MergeSort;
import Busqueda.Model.Services.Ordenamiento.MetodoOrdenamiento;
import Busqueda.Model.Services.Similitud.MetodoSimilitud;

import java.io.File;
import java.util.UUID;

public class Controller {

    private final View view;
    private final Model model;
    private int binsBanco;

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

    public int getBinsBanco() {
        return binsBanco;
    }

    public void setBinsBanco(int binsBanco) {
        this.binsBanco = binsBanco;
    }

    public void selectImage(String path) throws Exception {
        File imageFile = new File(path);
        Imagen selectedImage = new Imagen(imageFile);

        if (selectedImage.getImagenBI() == null) {
            throw new ValidacionImagen("El archivo seleccionado no contiene una imagen válida");
        }

        model.setCurrent(selectedImage);
    }

    public int getCantidadBins() {
        String binsSeleccionados = (String) view.getComboBoxBins().getSelectedItem();
        return Integer.parseInt(binsSeleccionados);
    }

    public void search() throws Exception {

        // aqui se tiene que poner que la cantidad de bins sean las seleccionadas por el usuario
        String binsSeleccionados = (String) view.getComboBoxBins().getSelectedItem();
        int cantidadBins = Integer.parseInt(binsSeleccionados);
        if (cantidadBins != binsBanco) {
            throw new ValidacionImagen(
                    "El banco de imágenes fue procesado con "
                            + binsBanco
                            + " bins. Para buscar debe seleccionar "
                            + binsBanco
                            + " bins o reconstruir el banco."
            );
        }

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

        FabricaSimilitud fabrica;

        if ("Similitud Coseno".equals(metodoSeleccionado)) {
            fabrica = new FabricaSimilitudCoseno();

        } else if ("Similitud Euclidiana".equals(metodoSeleccionado)) {
            fabrica = new FabricaSimilitudEuclidiana();

        } else if (
                "Intersección de Histogramas"
                        .equals(metodoSeleccionado)
        ) {
            fabrica = new FabricaSimilitudInterseccion();

        } else {
            throw new MetodoBusquedaInvalido(
                    "Método de búsqueda no reconocido: "
                            + metodoSeleccionado
            );
        }
        MetodoSimilitud metodoSimilitud = fabrica.crearMetodoSimilitud();

        MetodoOrdenamiento metodoOrdenamiento;
        String ordenamientoSeleccionado = (String) view.getComboBoxOrdenamiento().getSelectedItem();
        if ("Merge Sort".equals(ordenamientoSeleccionado)) {
            metodoOrdenamiento = new MergeSort();

        } else {
            metodoOrdenamiento = new BubbleSort();
        }

        HistogramaColor histograma = new HistogramaColor();
        Vector<Double> vectorCaracteristico = histograma.calculaVector(model.getCurrent(), cantidadBins);
        ImagenData imagenConsulta = new ImagenData(vectorCaracteristico, UUID.randomUUID(), model.getCurrent().getRuta());
        BuscadorInverso buscador = new BuscadorInverso(metodoSimilitud, metodoOrdenamiento, cantidadBins);
        ResultadoBusqueda resultados = buscador.buscar(imagenConsulta, RepoImagenes.getInstance().obtenerImagenes(), cantidadResultados);
        Busqueda.Presentation.Busqueda.PantallaResultado.Model resultadoModel = new Busqueda.Presentation.Busqueda.PantallaResultado.Model(model.getCurrent(), resultados, model.getMethod());
        Busqueda.Presentation.Busqueda.PantallaResultado.View resultadoView = new Busqueda.Presentation.Busqueda.PantallaResultado.View();
        Busqueda.Presentation.Busqueda.PantallaResultado.Controller resultadoController = new Busqueda.Presentation.Busqueda.PantallaResultado.Controller(resultadoView, resultadoModel);
        resultadoModel.setCurrent(model.getCurrent());
        resultadoModel.setMethod(model.getMethod());
        resultadoModel.setResults(resultados);
        resultadoView.setVisible(true);

        view.dispose();
    }
}
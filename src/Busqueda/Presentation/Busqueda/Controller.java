package Busqueda.Presentation.Busqueda;

import Busqueda.Model.Estructuras.ColeccionImagenData;
import Busqueda.Model.Estructuras.ResultadoBusqueda;
import Busqueda.Model.Estructuras.Vector;
import Busqueda.Model.Excepciones.CantidadResultadosInvalida;
import Busqueda.Model.Excepciones.MetodoBusquedaInvalido;
import Busqueda.Model.Excepciones.ValidacionImagen;
import Busqueda.Model.Imagen.HistogramaColor;
import Busqueda.Model.Imagen.Imagen;
import Busqueda.Model.Imagen.ImagenData;
import Busqueda.Model.Repositorios.RepoImagenes;
import Busqueda.Model.Serializador.Serializador;
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


    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        cargarMetodosBusqueda();
        cargarMetodosOrdenamiento();
        cargarBins();
        int binsBanco = RepoImagenes.getInstance().getCantidadBins();
        if (binsBanco > 0){
            view.getComboBoxBins().setSelectedItem(String.valueOf(binsBanco));
        }
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

    public int getCantidadBins() {
        String binsSeleccionados = (String) view.getComboBoxBins().getSelectedItem();
        return Integer.parseInt(binsSeleccionados);
    }

    public void search() throws Exception {

        // Validar que haya una imagen seleccionada
        if (model.getCurrent() == null) {
            throw new ValidacionImagen("Debe seleccionar una imagen antes de buscar");
        }

        int cantidadBins = getCantidadBins();

        RepoImagenes repositorio = RepoImagenes.getInstance();

        if (cantidadBins != repositorio.getCantidadBins()) {

            String rutaCarpeta = repositorio.getRutaCarpeta();
            String rutaArchivoBinario = repositorio.getRutaArchivoBinario();

            if (rutaCarpeta == null || rutaCarpeta.isBlank()) {
                throw new ValidacionImagen("No se conoce la carpeta del banco de imágenes");
            }

            if (rutaArchivoBinario == null || rutaArchivoBinario.isBlank()) {
                throw new ValidacionImagen("No se conoce la ruta del archivo binario");
            }
            Serializador serializador = new Serializador();
            ColeccionImagenData nuevaColeccion = serializador.serializarCarpeta(rutaCarpeta, rutaArchivoBinario, cantidadBins);

            if (nuevaColeccion.tamano() == 0) {
                throw new ValidacionImagen("No se encontraron imágenes válidas " + "al reconstruir el banco");
            }

            repositorio.reemplazar(nuevaColeccion, cantidadBins);
            System.out.println("Banco reconstruido con " + cantidadBins + " bins");
            System.out.println("Tamaño de cada vector: " + (cantidadBins * cantidadBins * cantidadBins));
            System.out.println("Cantidad de imágenes procesadas: " + nuevaColeccion.tamano());
        }

        String metodoSeleccionado = (String) view.getSearchMethodcomboBox().getSelectedItem();

        if (metodoSeleccionado == null || metodoSeleccionado.isBlank()) {
            throw new MetodoBusquedaInvalido("Debe seleccionar un método de búsqueda");
        }


        String ordenamientoSeleccionado = (String) view.getComboBoxOrdenamiento().getSelectedItem();

        if (ordenamientoSeleccionado == null || ordenamientoSeleccionado.isBlank()) {
            throw new IllegalArgumentException("Debe seleccionar un método de ordenamiento");
        }

        String textoCantidad = view.getCantidadResultados().getText().trim();

        if (textoCantidad.isEmpty()) {
            throw new CantidadResultadosInvalida("Debe indicar la cantidad de resultados");
        }

        int cantidadResultados;

        try {
            cantidadResultados = Integer.parseInt(textoCantidad);

        } catch (NumberFormatException ex) {
            throw new CantidadResultadosInvalida("La cantidad de resultados debe ser " + "un número entero");
        }

        if (cantidadResultados <= 0) {
            throw new CantidadResultadosInvalida("La cantidad de resultados debe ser mayor que cero");
        }

        int cantidadImagenesBanco = repositorio.obtenerImagenes().tamano();

        if (cantidadImagenesBanco == 0) {
            throw new ValidacionImagen("El banco de imágenes está vacío");
        }
        if (cantidadResultados > cantidadImagenesBanco) {
            throw new CantidadResultadosInvalida("La cantidad de resultados no puede superar " + "la cantidad de imágenes del banco: " + cantidadImagenesBanco);
        }

        FabricaSimilitud fabrica;
        if ("Similitud Coseno".equals(metodoSeleccionado)) {
            fabrica = new FabricaSimilitudCoseno();
        } else if ("Similitud Euclidiana".equals(metodoSeleccionado)) {
            fabrica = new FabricaSimilitudEuclidiana();
        } else if ("Intersección de Histogramas".equals(metodoSeleccionado)) {
            fabrica = new FabricaSimilitudInterseccion();
        } else {
            throw new MetodoBusquedaInvalido("Método de búsqueda no reconocido: " + metodoSeleccionado);
        }

        MetodoSimilitud metodoSimilitud = fabrica.crearMetodoSimilitud();
        MetodoOrdenamiento metodoOrdenamiento;
        if ("Merge Sort".equals(ordenamientoSeleccionado)) {
            metodoOrdenamiento = new MergeSort();
        } else if ("Bubble Sort".equals(ordenamientoSeleccionado)) {
            metodoOrdenamiento = new BubbleSort();
        } else {
            throw new IllegalArgumentException("Método de ordenamiento no reconocido: " + ordenamientoSeleccionado);
        }

        HistogramaColor histograma = new HistogramaColor();
        Vector<Double> vectorCaracteristico = histograma.calculaVector(model.getCurrent(), cantidadBins);
        ImagenData imagenConsulta = new ImagenData(vectorCaracteristico, UUID.randomUUID(), model.getCurrent().getRuta());
        BuscadorInverso buscador = new BuscadorInverso(metodoSimilitud, metodoOrdenamiento, cantidadBins);
        ResultadoBusqueda resultados = buscador.buscar(imagenConsulta, repositorio.obtenerImagenes(), cantidadResultados);
        if (resultados == null) {
            throw new ValidacionImagen("La búsqueda no produjo resultados");
        }
        model.setMethod(metodoSeleccionado);
        Busqueda.Presentation.Busqueda.PantallaResultado.Model resultadoModel = new Busqueda.Presentation.Busqueda.PantallaResultado.Model(model.getCurrent(), resultados, metodoSeleccionado);
        Busqueda.Presentation.Busqueda.PantallaResultado.View resultadoView = new Busqueda.Presentation.Busqueda.PantallaResultado.View();
        Busqueda.Presentation.Busqueda.PantallaResultado.Controller resultadoController = new Busqueda.Presentation.Busqueda.PantallaResultado.Controller(resultadoView, resultadoModel);
        resultadoController.mostrarResultados(model.getCurrent(), metodoSeleccionado, resultados);
        view.dispose();
    }
}
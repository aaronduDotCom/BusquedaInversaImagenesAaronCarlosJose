package Presentation.Busqueda;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.ResultadoBusqueda;
import Model.Excepciones.MetodoBusquedaInvalido;
import Model.Excepciones.ValidacionImagen;
import Model.Imagen.HistogramaColor;
import Model.Imagen.Imagen;
import Model.Imagen.ImagenData;
import Model.Serializador.Serializador;
import Model.Services.Busqueda.BuscadorInverso;
import Model.Services.Fabricas.FabricaSimilitud;
import Model.Services.Fabricas.FabricaSimilitudCoseno;
import Model.Services.Fabricas.FabricaSimilitudEuclidiana;
import Model.Services.Fabricas.FabricaSimilitudInterseccion;
import Model.Services.Ordenamiento.BubbleSort;
import Model.Services.Ordenamiento.MergeSort;
import Model.Services.Ordenamiento.MetodoOrdenamiento;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.io.File;

public class Controller {

    private final View view;
    private final Model model;

    // Pantalla de resultados (se crea una sola vez, se reutiliza)
    private Presentation.Busqueda.Resultado.Controller controladorResultados;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;

        view.setController(this);
        view.setModel(model);

        cargarMetodosBusqueda();
        cargarMetodosOrdenamiento();
        cargarTamannosBin();
    }

    private void cargarMetodosBusqueda() {
        view.getSearchMethodcomboBox().removeAllItems();
        view.getSearchMethodcomboBox().addItem("Similitud Coseno");
        view.getSearchMethodcomboBox().addItem("Similitud Euclidiana");
        view.getSearchMethodcomboBox().addItem("Intersección de Histogramas");
    }

    private void cargarMetodosOrdenamiento() {
        view.getOrdenamientoComboBox().removeAllItems();
        view.getOrdenamientoComboBox().addItem("Bubble Sort");
        view.getOrdenamientoComboBox().addItem("Merge Sort");
    }

    // Llena el combo desde la lista que define HistogramaColor,
    // asi no hay que repetir los valores validos en dos lugares.
    private void cargarTamannosBin() {
        view.getBinsComboBox().removeAllItems();
        for (int tamanno : HistogramaColor.TAMANNOS_BIN_VALIDOS) {
            int posiciones = HistogramaColor.calcularTamannoVector(tamanno);
            view.getBinsComboBox().addItem(tamanno + "  (" + posiciones + " valores por imagen)");
        }
        view.getBinsComboBox().setSelectedIndex(
                indiceDe(HistogramaColor.TAMANNO_BIN_POR_DEFECTO));
    }

    private int indiceDe(int tamannoBin) {
        for (int i = 0; i < HistogramaColor.TAMANNOS_BIN_VALIDOS.length; i++) {
            if (HistogramaColor.TAMANNOS_BIN_VALIDOS[i] == tamannoBin) {
                return i;
            }
        }
        return 0;
    }

    public void selectImage(String path) throws Exception {
        File imageFile = new File(path);
        Imagen selectedImage = new Imagen(imageFile);

        if (selectedImage.getImagenBI() == null) {
            throw new ValidacionImagen("El archivo seleccionado no contiene una imagen válida");
        }

        model.setCurrent(selectedImage);
    }

    // Deja que el usuario escoja la carpeta de imagenes a indexar.
    // Se pide una sola vez; despues queda guardada en el modelo.
    private boolean pedirCarpetaSiHaceFalta() {
        if (model.getCarpeta() != null && !model.getCarpeta().isBlank()) {
            return true;
        }

        JFileChooser selector = new JFileChooser();
        selector.setDialogTitle("Seleccione la carpeta con las imágenes a indexar");
        selector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selector.setAcceptAllFileFilterUsed(false);

        if (selector.showOpenDialog(view.getContentPanel()) != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        model.setCarpeta(selector.getSelectedFile().getAbsolutePath());
        return true;
    }

    public void search() throws Exception {
        if (model.getCurrent() == null) {
            throw new ValidacionImagen("Debe seleccionar una imagen antes de buscar");
        }

        String metodoSeleccionado = (String) view.getSearchMethodcomboBox().getSelectedItem();
        if (metodoSeleccionado == null || metodoSeleccionado.isBlank()) {
            throw new MetodoBusquedaInvalido("Debe seleccionar un método de búsqueda");
        }

        String ordenSeleccionado = (String) view.getOrdenamientoComboBox().getSelectedItem();
        if (ordenSeleccionado == null || ordenSeleccionado.isBlank()) {
            throw new MetodoBusquedaInvalido("Debe seleccionar un método de ordenamiento");
        }

        int tamannoBin = HistogramaColor.TAMANNOS_BIN_VALIDOS[
                Math.max(0, view.getBinsComboBox().getSelectedIndex())];

        int cantidad = leerCantidadResultados();

        if (!pedirCarpetaSiHaceFalta()) {
            return; // el usuario cancelo
        }

        model.setMethod(metodoSeleccionado);
        model.setOrdenamiento(ordenSeleccionado);
        model.setTamannoBin(tamannoBin);
        model.setCantidadResultados(cantidad);

        ejecutarBusqueda(metodoSeleccionado, ordenSeleccionado, tamannoBin, cantidad);
    }

    private int leerCantidadResultados() throws MetodoBusquedaInvalido {
        String texto = view.getCantidadResultadosTextField().getText();
        if (texto == null || texto.isBlank()) {
            return 10; // valor por defecto si lo dejan vacio
        }
        try {
            int cantidad = Integer.parseInt(texto.trim());
            if (cantidad <= 0) {
                throw new MetodoBusquedaInvalido("La cantidad de resultados debe ser mayor a 0");
            }
            return cantidad;
        } catch (NumberFormatException e) {
            throw new MetodoBusquedaInvalido("La cantidad de resultados debe ser un número entero");
        }
    }

    // El indexado de miles de imagenes tarda minutos. Si se hiciera en el hilo
    // de Swing, la ventana se congelaria por completo. Por eso va en un
    // SwingWorker: el trabajo pesado corre aparte y la interfaz sigue viva.
    private void ejecutarBusqueda(String metodo, String orden, int tamannoBin, int cantidad) {
        view.getBuscarButton().setEnabled(false);
        model.setEstado("Preparando el índice...");

        SwingWorker<ResultadoBusqueda, String> trabajo = new SwingWorker<>() {

            @Override
            protected ResultadoBusqueda doInBackground() throws Exception {
                Serializador serializador = new Serializador(tamannoBin);

                // Cada tamanno de bin tiene su propio .bin, asi no se pisan
                // entre si y no hay que reindexar al cambiar de opcion.
                String rutaBin = "indice_" + tamannoBin + ".bin";

                ColeccionImagenData indice;
                if (serializador.existeBin(rutaBin)) {
                    publish("Cargando índice existente...");
                    indice = serializador.cargar(rutaBin);
                } else {
                    publish("Indexando la carpeta (esto puede tardar varios minutos)...");
                    indice = serializador.serializarCarpeta(model.getCarpeta(), rutaBin);
                }

                publish("Calculando el histograma de la imagen consultada...");
                HistogramaColor histograma = new HistogramaColor(tamannoBin);
                Imagen consulta = model.getCurrent();
                ImagenData datosConsulta = new ImagenData(
                        histograma.calculaVector(consulta), consulta.getId(), consulta.getRuta());

                publish("Comparando contra " + indice.tamano() + " imágenes...");
                BuscadorInverso buscador = new BuscadorInverso(
                        crearFabrica(metodo).crearMetodoSimilitud(), crearOrdenamiento(orden));

                return buscador.buscar(datosConsulta, indice, cantidad);
            }

            @Override
            protected void process(java.util.List<String> mensajes) {
                model.setEstado(mensajes.get(mensajes.size() - 1));
            }

            @Override
            protected void done() {
                view.getBuscarButton().setEnabled(true);
                try {
                    ResultadoBusqueda resultados = get();
                    model.setEstado("Listo: " + resultados.tamano() + " resultados");
                    abrirPantallaResultados(model.getCurrent(), metodo, resultados);
                } catch (Exception e) {
                    model.setEstado("");
                    Throwable causa = e.getCause() != null ? e.getCause() : e;
                    JOptionPane.showMessageDialog(view.getContentPanel(),
                            causa.getMessage(), "Error durante la búsqueda",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        trabajo.execute();
    }

    private FabricaSimilitud crearFabrica(String metodo) throws MetodoBusquedaInvalido {
        if (metodo.contains("Coseno")) {
            return new FabricaSimilitudCoseno();
        }
        if (metodo.contains("Euclidiana")) {
            return new FabricaSimilitudEuclidiana();
        }
        if (metodo.contains("Intersec")) {
            return new FabricaSimilitudInterseccion();
        }
        throw new MetodoBusquedaInvalido("Método de búsqueda desconocido: " + metodo);
    }

    private MetodoOrdenamiento crearOrdenamiento(String orden) throws MetodoBusquedaInvalido {
        if (orden.contains("Bubble")) {
            return new BubbleSort();
        }
        if (orden.contains("Merge")) {
            return new MergeSort();
        }
        throw new MetodoBusquedaInvalido("Método de ordenamiento desconocido: " + orden);
    }

    private void abrirPantallaResultados(Imagen consulta, String metodo, ResultadoBusqueda resultados) {
        if (controladorResultados == null) {
            Presentation.Busqueda.Resultado.View vistaResultados =
                    new Presentation.Busqueda.Resultado.View();
            Presentation.Busqueda.Resultado.Model modeloResultados =
                    new Presentation.Busqueda.Resultado.Model();
            controladorResultados = new Presentation.Busqueda.Resultado.Controller(
                    vistaResultados, modeloResultados);
        }
        controladorResultados.mostrarResultados(consulta, metodo, resultados);
    }
}
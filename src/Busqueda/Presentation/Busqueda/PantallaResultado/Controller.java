package Busqueda.Presentation.Busqueda.PantallaResultado;
import Busqueda.Model.Imagen.Imagen;
import Busqueda.Model.Estructuras.ResultadoBusqueda;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void mostrarResultados(Imagen imangenConsulta, String metodo, ResultadoBusqueda resultados){ // esto esta en rojo por que en Busqueda.Model aun no hay una lista de resultados
        model.setCurrent(imangenConsulta);
        model.setMethod(metodo);
        model.setResults(resultados);
        view.setVisible(true);
    }

    public void nuevaBusqueda(){
        Busqueda.Presentation.Busqueda.Model nuevoModel = new Busqueda.Presentation.Busqueda.Model();
        Busqueda.Presentation.Busqueda.View nuevaView = new Busqueda.Presentation.Busqueda.View();
        Busqueda.Presentation.Busqueda.Controller nuevoController = new Busqueda.Presentation.Busqueda.Controller(nuevaView, nuevoModel);

        nuevaView.setVisible(true);
        view.dispose();
    }
}

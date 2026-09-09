package Presentation.Busqueda.PantallaResultado;
import Model.Imagen.Imagen;
import Model.Estructuras.ResultadoBusqueda;

public class Controller {
    private final View view;
    private final Model model;

    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        view.setController(this);
        view.setModel(model);
    }

    public void mostrarResultados(Imagen imangenConsulta, String metodo, ResultadoBusqueda resultados){ // esto esta en rojo por que en Model aun no hay una lista de resultados
        model.setCurrent(imangenConsulta);
        model.setMethod(metodo);
        model.setResults(resultados);
        view.setVisible(true);
    }

    public void nuevaBusqueda(){
        model.clear();
        view.setVisible(false);

    }
}

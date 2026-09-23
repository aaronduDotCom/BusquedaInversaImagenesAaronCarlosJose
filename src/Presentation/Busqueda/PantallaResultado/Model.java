package Presentation.Busqueda.PantallaResultado;
import Model.Estructuras.ResultadoBusqueda;
import Model.Imagen.Imagen;
import Presentation.AbstractModel;

import java.util.List;

public class Model extends AbstractModel{
    public static final String CURRENT = "current";
    public static final String METHOD = "method";
    public static final String RESULTS = "results";

    private Imagen current;
    private String method;
    private ResultadoBusqueda results; // aun no hay clase ResultadoBusqueda

    public Model(Imagen current, ResultadoBusqueda results, String method) {
        this.current = current;
        if (results == null) {
            this.results = new ResultadoBusqueda();
        } else {
            this.results = results;
        }

        this.method = method;
    }

    public Imagen getCurrent() {
        return current;
    }

    public void setCurrent(Imagen current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public ResultadoBusqueda getResults() {
        return results;
    }

    public void setResults(ResultadoBusqueda results) {
        if (results == null){
            this.results = new ResultadoBusqueda();
        } else {
            this.results = results;
        }
        firePropertyChange(RESULTS);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
        firePropertyChange(METHOD);
    }
    public void clear(){
        this.current = null;
        this.method = "";
        this.results = new ResultadoBusqueda();
        firePropertyChange(CURRENT);
        firePropertyChange(METHOD);
        firePropertyChange(RESULTS);

    }
}

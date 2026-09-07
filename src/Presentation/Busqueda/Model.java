package Presentation.Busqueda;

import Model.Imagen.Imagen;
import Presentation.AbstractModel;

public class Model extends AbstractModel {

    public static final String CURRENT = "current";
    public static final String METHOD = "method";

    private Imagen current;
    private String method;

    public Model() {
        this.current = null;
        this.method = "";
    }

    public Imagen getCurrent() {
        return current;
    }

    public void setCurrent(Imagen current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
        firePropertyChange(METHOD);
    }
}
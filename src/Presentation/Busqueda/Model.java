package Presentation.Busqueda;

import Model.Imagen.HistogramaColor;
import Model.Imagen.Imagen;
import Presentation.AbstractModel;

public class Model extends AbstractModel {

    public static final String CURRENT = "current";
    public static final String METHOD = "method";
    public static final String ORDENAMIENTO = "ordenamiento";
    public static final String TAMANNO_BIN = "tamannoBin";
    public static final String CANTIDAD_RESULTADOS = "cantidadResultados";
    public static final String CARPETA = "carpeta";
    public static final String ESTADO = "estado";

    private Imagen current;
    private String method;
    private String ordenamiento;
    private int tamannoBin;
    private int cantidadResultados;
    private String carpeta;
    private String estado;

    public Model() {
        this.current = null;
        this.method = "";
        this.ordenamiento = "";
        this.tamannoBin = HistogramaColor.TAMANNO_BIN_POR_DEFECTO;
        this.cantidadResultados = 10;
        this.carpeta = null;
        this.estado = "";
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

    public String getOrdenamiento() {
        return ordenamiento;
    }

    public void setOrdenamiento(String ordenamiento) {
        this.ordenamiento = ordenamiento;
        firePropertyChange(ORDENAMIENTO);
    }

    public int getTamannoBin() {
        return tamannoBin;
    }

    public void setTamannoBin(int tamannoBin) {
        this.tamannoBin = tamannoBin;
        firePropertyChange(TAMANNO_BIN);
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
        firePropertyChange(CANTIDAD_RESULTADOS);
    }

    public String getCarpeta() {
        return carpeta;
    }

    public void setCarpeta(String carpeta) {
        this.carpeta = carpeta;
        firePropertyChange(CARPETA);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        firePropertyChange(ESTADO);
    }
}
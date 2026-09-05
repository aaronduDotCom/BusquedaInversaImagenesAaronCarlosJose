package Presentation.Busqueda;
import Model.Imagen.Imagen;
import Presentation.AbstractModel;

public class Model extends AbstractModel {

    /*
    * se necesita la imagen que suben para hacer la comparacion
    * sacarle los datos a esa imagen
    * preguntar por qué méto-do quiere buscar la similitud
    * hacerlo
    * mostrar los resultados
    * se necesita un modelo para la imagen que suben y otro para la coleccion de imagenes que se van a mostrar
    * como el caso de login y password el proyecto
    * el resultdo es una ventana que popea con la imagen que suben y las imagenes que son similares a esa
    * despues de en la pantalla principal se pida la imagen y el metodo de busqueda
    * */


    public static final String CURRENT = "current";

    private Imagen current;

    public Model(Imagen current) {
        this.current = current;
    }

    public void setCurrent(Imagen current) {
        Imagen oldCurrent = this.current;
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public Imagen getCurrent() {
        return current;
    }
}

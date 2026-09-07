package Model.Estructuras;
import Model.Services.Busqueda.Resultado;

public class ResultadoBusqueda extends ColeccionAbstracta<Resultado>{
    public ResultadoBusqueda(){
        super();
    }
    public void agregar(Resultado resultado){
        insertarFinal(resultado);
    }
}

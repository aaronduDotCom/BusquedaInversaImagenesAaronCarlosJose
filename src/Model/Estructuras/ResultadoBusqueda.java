package Model.Estructuras;
import Model.Services.Busqueda.Resultado;

public class ResultadoBusqueda extends ColeccionAbstracta<Resultado>{
    public ResultadoBusqueda(){
        super();
    }
    public void agregar(Resultado resultado){
        insertarFinal(resultado);
    }
    // ascendente
    // true : menor a mayor, para Euclidiana
    // false : mayor a menor, para las otras dos
}

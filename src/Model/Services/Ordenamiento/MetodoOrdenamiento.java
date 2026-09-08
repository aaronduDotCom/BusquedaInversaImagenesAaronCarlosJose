package Model.Services.Ordenamiento;

import Model.Estructuras.ResultadoBusqueda;

public interface MetodoOrdenamiento {
    ResultadoBusqueda ordenar(ResultadoBusqueda resultados, boolean ascendente);
    // ascendente
    // true : menor a mayor, para Euclidiana
    // false : mayor a menor, para las otras dos
}
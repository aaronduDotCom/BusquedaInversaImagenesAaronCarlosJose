package Busqueda.Model.Services.Ordenamiento;

import Busqueda.Model.Estructuras.ResultadoBusqueda;

public interface MetodoOrdenamiento {
    ResultadoBusqueda ordenar(ResultadoBusqueda resultados, boolean ascendente);
    // ascendente
    // true : menor a mayor, para Euclidiana
    // false : mayor a menor, para las otras dos
}
package Model.Services.Busqueda;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.Iterator;
import Model.Estructuras.ResultadoBusqueda;
import Model.Imagen.ImagenData;
import Model.Services.Ordenamiento.MetodoOrdenamiento;
import Model.Services.Similitud.MetodoSimilitud;

public class BuscadorInverso {

    private final MetodoSimilitud metodoSimilitud;
    private final MetodoOrdenamiento metodoOrdenamiento;

    public BuscadorInverso(
            MetodoSimilitud metodoSimilitud,
            MetodoOrdenamiento metodoOrdenamiento) {

        this.metodoSimilitud = metodoSimilitud;
        this.metodoOrdenamiento = metodoOrdenamiento;
    }

    public ResultadoBusqueda buscar(
            ImagenData imagenConsulta,
            ColeccionImagenData coleccion,
            int cantidadResultados) {

        if (cantidadResultados > coleccion.tamano()) {
            cantidadResultados = coleccion.tamano();
        }

        ResultadoBusqueda resultados = new ResultadoBusqueda();

        Iterator<ImagenData> iterador = coleccion.getIterador();

        while (iterador.hasNext()) {
            ImagenData actual = iterador.next();
            if (actual.getRuta().equals(imagenConsulta.getRuta())) {
                continue;
            }
            double valor = metodoSimilitud.calcular(imagenConsulta, actual);
            resultados.agregar(new Resultado(actual, valor));
        }
        ResultadoBusqueda resultadosOrdenados = metodoOrdenamiento.ordenar(resultados, metodoSimilitud.esAscendente());

        ResultadoBusqueda resultadosFinales = new ResultadoBusqueda();
        Iterator<Resultado> iteradorResultados = resultadosOrdenados.getIterador();
        int contador = 0;
        while (iteradorResultados.hasNext() && contador < cantidadResultados) {
            resultadosFinales.agregar(iteradorResultados.next());
            contador++;
        }

        return resultadosFinales;
    }
}
package Busqueda.Model.Services.Busqueda;

import Busqueda.Model.Estructuras.ColeccionImagenData;
import Busqueda.Model.Estructuras.Iterator;
import Busqueda.Model.Estructuras.ResultadoBusqueda;
import Busqueda.Model.Imagen.ImagenData;
import Busqueda.Model.Services.Ordenamiento.MetodoOrdenamiento;
import Busqueda.Model.Services.Similitud.MetodoSimilitud;

public class BuscadorInverso {

    private final MetodoSimilitud metodoSimilitud;
    private final MetodoOrdenamiento metodoOrdenamiento;
    private int bins;

    public BuscadorInverso(MetodoSimilitud metodoSimilitud, MetodoOrdenamiento metodoOrdenamiento, int bins) {
        this.metodoSimilitud = metodoSimilitud;
        this.metodoOrdenamiento = metodoOrdenamiento;
        this.bins = bins;
    }

    public void setBins(int bins){this.bins = bins;}

    public ResultadoBusqueda buscar(ImagenData imagenConsulta, ColeccionImagenData coleccion, int cantidadResultados) {

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
            double valor = metodoSimilitud.calcular(imagenConsulta, actual, bins);
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
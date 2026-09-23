package Model.Services.Busqueda;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.Iterator;
import Model.Estructuras.ResultadoBusqueda;
import Model.Imagen.ImagenData;
import Model.Services.Similitud.MetodoSimilitud;
import Model.Services.Ordenamiento.MetodoOrdenamiento;

// recibe camina por la coleccion de imagenes data y va buscando
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

    public ResultadoBusqueda buscar(ImagenData imagenConsulta, ColeccionImagenData coleccion) {
        ResultadoBusqueda resultados = new ResultadoBusqueda();

        Iterator<ImagenData> iterador = coleccion.getIterador();
        while (iterador.hasNext()) {
            ImagenData actual = iterador.next();

            if (actual.getId().equals(imagenConsulta.getId())) {
                continue; // no comparar la imagen contra si misma
            }

            double valor = metodoSimilitud.calcular(imagenConsulta, actual, bins);
            resultados.agregar(new Resultado(actual, valor));
        }

        return metodoOrdenamiento.ordenar(resultados, metodoSimilitud.esAscendente());
    }
}
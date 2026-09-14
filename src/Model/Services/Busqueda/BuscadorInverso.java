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

    public BuscadorInverso(MetodoSimilitud metodoSimilitud, MetodoOrdenamiento metodoOrdenamiento) {
        this.metodoSimilitud = metodoSimilitud;
        this.metodoOrdenamiento = metodoOrdenamiento;
    }

    public ResultadoBusqueda buscar(ImagenData imagenConsulta, ColeccionImagenData coleccion) {
        ResultadoBusqueda resultados = new ResultadoBusqueda();

        Iterator<ImagenData> iterador = coleccion.getIterador();
        while (iterador.hasNext()) {
            ImagenData actual = iterador.next();

            if (actual.getId().equals(imagenConsulta.getId())) {
                continue; // no comparar la imagen contra si misma
            }

            double valor = metodoSimilitud.calcular(imagenConsulta, actual);
            resultados.agregar(new Resultado(actual, valor));
        }

        return metodoOrdenamiento.ordenar(resultados, metodoSimilitud.esAscendente());
    }

    // Igual que el anterior, pero devolviendo solo los primeros N resultados
    public ResultadoBusqueda buscar(ImagenData imagenConsulta, ColeccionImagenData coleccion, int cantidad) {
        ResultadoBusqueda ordenados = buscar(imagenConsulta, coleccion);

        if (cantidad <= 0 || cantidad >= ordenados.tamano()) {
            return ordenados;
        }

        ResultadoBusqueda recortados = new ResultadoBusqueda();
        Iterator<Resultado> iterador = ordenados.getIterador();
        int contados = 0;
        while (iterador.hasNext() && contados < cantidad) {
            recortados.agregar(iterador.next());
            contados++;
        }
        return recortados;
    }
}
package Model.Services.Busqueda;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.Iterator;
import Model.Estructuras.ResultadoBusqueda;
import Model.Imagen.ImagenData;
import Model.Services.Fabricas.FabricaBusqueda;
import Model.Services.Ordenamiento.MetodoOrdenamiento;
import Model.Services.Similitud.MetodoSimilitud;

// Recibe la imagen de consulta y la coleccion indexada, calcula que tan
// parecidas son con el MetodoSimilitud dado, ordena los resultados con
// el MetodoOrdenamiento que produzca la FabricaBusqueda dada, y devuelve
// los N resultados mas parecidos.
public class BuscadorInverso {

    public ResultadoBusqueda buscar(ImagenData consulta,
                                    ColeccionImagenData coleccion,
                                    MetodoSimilitud metodoSimilitud,
                                    FabricaBusqueda fabricaOrdenamiento,
                                    int cantidadResultados) {

        if (consulta == null) {
            throw new IllegalArgumentException("La imagen de consulta no puede ser null");
        }
        if (coleccion == null) {
            throw new IllegalArgumentException("La coleccion indexada no puede ser null");
        }
        if (metodoSimilitud == null) {
            throw new IllegalArgumentException("Debe indicar un metodo de similitud");
        }
        if (fabricaOrdenamiento == null) {
            throw new IllegalArgumentException("Debe indicar como ordenar los resultados");
        }
        if (cantidadResultados <= 0) {
            throw new IllegalArgumentException("La cantidad de resultados debe ser mayor a 0");
        }

        ResultadoBusqueda resultados = new ResultadoBusqueda();

        Iterator<ImagenData> it = coleccion.getIterador();
        while (it.hasNext()) {
            ImagenData candidata = it.next();
            double valor = metodoSimilitud.calcular(consulta, candidata);
            resultados.agregar(new Resultado(candidata, valor));
        }

        MetodoOrdenamiento metodoOrdenamiento = fabricaOrdenamiento.crearMetodoOrdenamiento();
        ResultadoBusqueda ordenados = metodoOrdenamiento.ordenar(resultados, metodoSimilitud.esAscendente());

        return recortar(ordenados, cantidadResultados);
    }

    private ResultadoBusqueda recortar(ResultadoBusqueda ordenados, int cantidad) {
        ResultadoBusqueda top = new ResultadoBusqueda();
        Iterator<Resultado> it = ordenados.getIterador();

        int contados = 0;
        while (it.hasNext() && contados < cantidad) {
            top.agregar(it.next());
            contados++;
        }
        return top;
    }
}
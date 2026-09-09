package Model.Services.Fabricas;

import Model.Services.Ordenamiento.MergeSort;
import Model.Services.Ordenamiento.MetodoOrdenamiento;

public class FabricaBusquedaMerge extends FabricaBusqueda {
    @Override
    public MetodoOrdenamiento crearMetodoOrdenamiento() {
        return new MergeSort();
    }
}
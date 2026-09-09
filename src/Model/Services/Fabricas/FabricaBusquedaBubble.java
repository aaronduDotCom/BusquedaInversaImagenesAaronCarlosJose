package Model.Services.Fabricas;

import Model.Services.Ordenamiento.BubbleSort;
import Model.Services.Ordenamiento.MetodoOrdenamiento;

public class FabricaBusquedaBubble extends FabricaBusqueda {
    @Override
    public MetodoOrdenamiento crearMetodoOrdenamiento() {
        return new BubbleSort();
    }
}
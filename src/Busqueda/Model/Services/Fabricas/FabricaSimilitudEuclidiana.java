package Busqueda.Model.Services.Fabricas;

import Busqueda.Model.Services.Similitud.DistanciaEuclidiana;
import Busqueda.Model.Services.Similitud.MetodoSimilitud;

public class FabricaSimilitudEuclidiana extends FabricaSimilitud {

    @Override
    public MetodoSimilitud crearMetodoSimilitud() {
        return new DistanciaEuclidiana();
    }
}
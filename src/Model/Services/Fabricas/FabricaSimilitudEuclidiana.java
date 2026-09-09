package Model.Services.Fabricas;

import Model.Services.Similitud.DistanciaEuclidiana;
import Model.Services.Similitud.MetodoSimilitud;

public class FabricaSimilitudEuclidiana extends FabricaSimilitud {

    @Override
    public MetodoSimilitud crearMetodoSimilitud() {
        return new DistanciaEuclidiana();
    }
}
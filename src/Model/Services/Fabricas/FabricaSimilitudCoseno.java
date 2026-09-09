package Model.Services.Fabricas;

import Model.Services.Similitud.MetodoSimilitud;
import Model.Services.Similitud.SimilitudCoseno;

public class FabricaSimilitudCoseno extends FabricaSimilitud {

    @Override
    public MetodoSimilitud crearMetodoSimilitud() {
        return new SimilitudCoseno();
    }
}
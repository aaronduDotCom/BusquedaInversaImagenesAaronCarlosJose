package Busqueda.Model.Services.Fabricas;

import Busqueda.Model.Services.Similitud.MetodoSimilitud;
import Busqueda.Model.Services.Similitud.SimilitudCoseno;

public class FabricaSimilitudCoseno extends FabricaSimilitud {

    @Override
    public MetodoSimilitud crearMetodoSimilitud() {
        return new SimilitudCoseno();
    }
}
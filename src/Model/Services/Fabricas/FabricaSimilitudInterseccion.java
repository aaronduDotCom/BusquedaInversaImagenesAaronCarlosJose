package Model.Services.Fabricas;

import Model.Services.Similitud.InterseccionHistogramas;
import Model.Services.Similitud.MetodoSimilitud;

public class FabricaSimilitudInterseccion extends FabricaSimilitud {

    @Override
    public MetodoSimilitud crearMetodoSimilitud() {
        return new InterseccionHistogramas();
    }
}
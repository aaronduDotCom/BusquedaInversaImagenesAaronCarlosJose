package Busqueda.Model.Services.Fabricas;

import Busqueda.Model.Services.Similitud.InterseccionHistogramas;
import Busqueda.Model.Services.Similitud.MetodoSimilitud;

public class FabricaSimilitudInterseccion extends FabricaSimilitud {

    @Override
    public MetodoSimilitud crearMetodoSimilitud() {
        return new InterseccionHistogramas();
    }
}
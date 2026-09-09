package Model.Services.Fabricas;

import Model.Services.Ordenamiento.MetodoOrdenamiento;

// Fabrica abstracta: cada subclase sabe crear el MetodoOrdenamiento
// concreto que le corresponde, sin que BuscadorInverso tenga que
// conocer las clases BubbleSort/MergeSort directamente.
public abstract class FabricaBusqueda {
    public abstract MetodoOrdenamiento crearMetodoOrdenamiento();
}
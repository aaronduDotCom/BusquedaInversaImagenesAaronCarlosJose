package Model.Estructuras;

import Model.Excepciones.FinalDeVector;
import Model.Excepciones.VectorNulo;

//Itera Vectores
public class VectorIterator<T> implements Iterator<T> {

    private Vector<T> vector;
    private int posicion;

    public VectorIterator(Vector<T> vector) {
        if (vector == null){
            throw new VectorNulo("El vector no puede ser nulo");
        }
        this.vector = vector;
        posicion = 0;
    }

    @Override
    public boolean hasNext() {
        return posicion < vector.tamanno();
    }

    @Override
    public T next() {
        if (!hasNext()){
            throw new FinalDeVector("No hay más elementos");
        }
        T valor = vector.getPos(posicion);
        posicion++;
        return valor;
    }

    @Override
    public T actual () {
        return null;
    }
}


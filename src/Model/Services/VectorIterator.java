package Model.Services;

import Model.Vector;

//Itera Vectores
public class VectorIterator<T> implements Iterator<T> {

    private Vector<T> vector;
    private int posicion;

    public VectorIterator(Vector<T> vector) {
        this.vector = vector;
        posicion = 0;
    }

    @Override
    public boolean hasNext() {
        return posicion < vector.tamanno();
    }

    @Override
    public T next() {
        return vector.getPos(posicion++);
    }
}

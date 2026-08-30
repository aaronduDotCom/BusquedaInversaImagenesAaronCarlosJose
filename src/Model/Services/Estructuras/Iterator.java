package Model.Services.Estructuras;

//clase iterador padre para VectorIterador y ListIterator
public interface Iterator<T> {

    boolean hasNext();
    T next();
}

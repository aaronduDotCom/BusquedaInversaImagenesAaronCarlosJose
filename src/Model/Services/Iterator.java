package Model.Services;

//clase iterador padre para VectorIterador y ListIterator
public interface Iterator<T> {

    boolean hasNext();
    T next();
}

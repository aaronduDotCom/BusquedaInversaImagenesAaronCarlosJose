package Model.Estructuras;

//clase iterador padre para VectorIterador y ListIterator
public interface Iterator<T> {
    boolean hasNext();
    T next(); //devuelve el valor actual y camina una posición
    T actual();
}

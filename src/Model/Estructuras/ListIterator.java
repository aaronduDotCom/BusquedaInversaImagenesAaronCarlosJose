package Model.Estructuras;

public class ListIterator<T> implements Iterator<T>{
    private Nodo<T> actual;

    public ListIterator(Nodo<T> actual) {
        this.actual = actual;
    }

    @Override
    public boolean hasNext(){
        return actual != null;
    }

    @Override
    public T next(){
        if (!hasNext()){
            throw new IllegalStateException("No hay más elementos");
        }
        T valor = actual.getValor();
        actual = actual.getSiguiente();
        return valor;
    }

    @Override
    public T actual () {
        return (T) actual;
    }
}

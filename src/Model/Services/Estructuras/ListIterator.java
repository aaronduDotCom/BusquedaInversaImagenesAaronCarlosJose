package Model.Services.Estructuras;

public class ListIterator<T> implements Iterator<T>{
    private Nodo<T> actual;

    public ListIterator(Nodo<T> actual) {
        this.actual = actual;
    }

    public boolean hasNext(){
        return actual != null;
    }

    public T next(){
        if (!hasNext()){
            throw new IllegalStateException("No hay más elementos");
        }
        T valor = actual.getValor();
        actual = actual.getSiguiente();
        return valor;
    }
}

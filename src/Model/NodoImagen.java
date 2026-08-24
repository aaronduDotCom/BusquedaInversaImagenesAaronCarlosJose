package Model;
// nodo de la lista doble
// doble puntero

public class NodoImagen<T> { // la T es como vimos en clases para que pueda ser generico el tipo de dato
    private T valor;
    private NodoImagen<T> anterior;
    private NodoImagen<T> siguiente;

    public NodoImagen(T valor){ this.valor = valor; }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public NodoImagen<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoImagen<T> anterior) {
        this.anterior = anterior;
    }

    public NodoImagen<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoImagen<T> siguiente) {
        this.siguiente = siguiente;
    }
}
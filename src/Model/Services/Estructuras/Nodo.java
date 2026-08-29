package Model.Services.Estructuras;
// nodo de la lista doble
// doble puntero

public class Nodo<T> { // la T es como vimos en clases para que pueda ser generico el tipo de dato
    private T valor;
    private Nodo<T> anterior;
    private Nodo<T> siguiente;

    public Nodo(T valor){ this.valor = valor; }

    public void setValor(T valor) {
        this.valor = valor;
    }

    public T getValor() {
        return valor;
    }

    public Nodo<T> getAnterior() {
        return anterior;
    }

    public void setAnterior(Nodo<T> anterior) {
        this.anterior = anterior;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}
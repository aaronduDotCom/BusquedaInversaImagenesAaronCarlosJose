package Model.Services.Estructuras;

public abstract class ColeccionAbstracta<T> {

    protected Nodo<T> cabeza;
    protected Nodo<T> cola;

    public void insertarInicio(T dato){
        Nodo<T> Nodo = new Nodo<>(dato);
        if (cabeza == null){
            cabeza = Nodo;
            cola = Nodo;
            return;
        }
        Nodo.setSiguiente(cabeza);
        cabeza.setAnterior(Nodo);
        cabeza = Nodo;
    }

    public void insertarFinal(T dato){
        Nodo<T> Nodo = new Nodo<>(dato);
        if (cabeza == null){
            cabeza = Nodo;
            cola = Nodo;
            return;
        }
        cola.setSiguiente(Nodo);
        Nodo.setAnterior(cola);
        cola = Nodo;
    }

    public void eliminarPosterior(Nodo<T> nodo){
        if (nodo == null || nodo.getSiguiente() == null){
            return;
        }
        Nodo<T> temp = nodo.getSiguiente();
        Nodo<T> tempNext = temp.getSiguiente();

        nodo.setSiguiente(tempNext);
        if (tempNext != null){
            tempNext.setAnterior(nodo);
        }else{
            cola = nodo;
        }
    }

    public boolean eliminarCualquiera(T valor){
        Nodo<T> actual = cabeza;

        while (actual != null){
            if (actual.getValor().equals(valor)) {
                Nodo<T> anterior = actual.getAnterior();
                Nodo<T> siguiente = actual.getSiguiente();
                if (anterior != null){
                    anterior.setSiguiente(siguiente);
                }else{
                    cabeza = siguiente;
                }
                if (siguiente != null){
                    siguiente.setAnterior(anterior);
                }else{
                    cola = anterior;
                }
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public int contador(){
        int contador = 0;
        Nodo<T> actual = cabeza;
        while(actual != null){
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }

    public Iterator<T> getIterador(){
        return new ListIterator<>(cabeza);
    }

}

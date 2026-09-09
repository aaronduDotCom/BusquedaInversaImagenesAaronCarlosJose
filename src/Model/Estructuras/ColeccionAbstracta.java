package Model.Estructuras;

public abstract class ColeccionAbstracta<T> {

    protected Nodo<T> cabeza;
    protected Nodo<T> cola;
    protected int tamano;

    public ColeccionAbstracta() {
        this.cola = null;
        this.tamano = 0;
        this.cabeza = null;
    }

    public void insertarInicio(T dato){
        Nodo<T> Nodo = new Nodo<>(dato);
        if (cabeza == null){
            cabeza = Nodo;
            cola = Nodo;
        }else{
            Nodo.setSiguiente(cabeza);
            cabeza.setAnterior(Nodo);
            cabeza = Nodo;
        }
        tamano++;
    }

    public void insertarFinal(T dato){
        Nodo<T> Nodo = new Nodo<>(dato);
        if (cabeza == null){
            cabeza = Nodo;
            cola = Nodo;
        }else{
            cola.setSiguiente(Nodo);
            Nodo.setAnterior(cola);
            cola = Nodo;
        }
        tamano++;
    }

    public boolean eliminarPosicion(int posicion){
        if (posicion < 0 || posicion >= tamano){
            return false;
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < posicion; i++){
            actual = actual.getSiguiente();
        }
        desconectar(actual);
        return true;
    }

    public boolean eliminarCualquiera(T valor){
        Nodo<T> actual = cabeza;

        while (actual != null){
            boolean iguales = actual.getValor() == null ? valor == null : actual.getValor().equals(valor);
            if (iguales){
                desconectar(actual);
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    private void desconectar(Nodo<T> nodo){
        Nodo<T> anterior = nodo.getAnterior();
        Nodo<T> siguiente = nodo.getSiguiente();

        // caso 1: nodo es la cabeza
        if (anterior == null){
            cabeza = siguiente;
        }else{
            anterior.setSiguiente(siguiente);
        }
        // caso 2: nodo es la cola
        if (siguiente == null){
            cola = anterior;
        }else{
            siguiente.setAnterior(anterior);
        }
        tamano--;
    }

    public int tamano(){
        return tamano;
    }

    public boolean estaVacia(){
        return cabeza == null;
    }

    public Iterator<T> getIterador(){
        return new ListIterator<>(cabeza);
    }

}

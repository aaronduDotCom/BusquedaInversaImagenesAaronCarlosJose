package Model.Services.Estructuras;


// cambiar a coleccion generica

// aqui va la lista doble
// metodos para ordenamiento
// uno tiene que ser malo, y otro tiene que ser bueno
// burbuja para n¨2 merge sort n log n
// metodos necesarios:
// insertar al inicio
// insertar al final
// eliminar posterior
// eliminar cualquiera
// tamaño de la lista
// Get iterador
// otro metodo necesario
public class ColeccionImagen<T> {
    private NodoImagen<T> cabeza;
    private NodoImagen<T> cola; // anterior

    // metodos necesarios
    public void insertarInicio(T valor){
        NodoImagen<T> Nodo = new NodoImagen<>(valor);

        // primer caso si la lista esta vacia
        // los dos punteros apuntar al mismo nodo
        if (cabeza == null){
            cabeza = Nodo;
            cola = Nodo;
            return;
        }

        // segundo caso si la lista no esta vacia
        Nodo.setSiguiente(cabeza);
        cabeza.setAnterior(Nodo);
        cabeza = Nodo;
    }

    public void insertarFinal(T valor){
        NodoImagen<T> Nodo = new NodoImagen<>(valor);

        // primer caso si la lista esta vacia
        if (cabeza == null){
            cabeza = Nodo;
            cola = cabeza;
            return;
        }

        // segundo caso si la lista no esta vacia
        cola.setSiguiente(Nodo);
        Nodo.setAnterior(cola);
        cola = Nodo;
    }

    public void eliminarPosterior(NodoImagen<T> nodo){ // elimina el nodo que esta despues del nodo que le pasamos
        // primer caso si el nodo que queremos eliminar no tiene posterior
        if (nodo == null || nodo.getSiguiente() == null){
            return;
        }

        // creamos temporales para no perder la lista
        NodoImagen<T> temp = nodo.getSiguiente(); // temporal que vamos a eliminar
        NodoImagen<T> siguienteTemp = temp.getSiguiente(); // temporal que vamos a conectar para no perder la lista

        // aqui lo eliminamos, lo perdemos de la lista
        nodo.setSiguiente(siguienteTemp);
        // aqui conectamos el siguiente del nodo que acabamos de eliminar con el nodo que le pasamos
        if (siguienteTemp != null){
            siguienteTemp.setAnterior(nodo);
        }else{
            // si el que eliminamos era el ultimo, tenemos que actualizar la cola
            cola = nodo;
        }
    }

    public boolean eliminarCualquiera(T valor){
        // como el metodo recibe un valor, ese es el valor que vamos a eliminar en la lista donde sea que se encuentre
        NodoImagen<T> actual = cabeza;

        // para evitar hacer monton de casos, podemos recorrer la lista hasta que lleguemos al final
        while (actual != null){
            // si encontramos el valor
            if (actual.getValor().equals(valor)){
                // posicionamos los punteros
                NodoImagen<T> anterior = actual.getAnterior();
                NodoImagen<T> siguiente = actual.getSiguiente();

                // primero verificamos que el anterior no sea nulo, si es nulo significa que estamos en la cabeza
                if (anterior != null){
                    anterior.setSiguiente(siguiente);
                } else {
                    // si es nulo, significa que estamos en la cabeza
                    cabeza = siguiente;
                }

                // ahora caso contrario, verificamos que el siguiente no sea nulo
                if (siguiente != null){
                    siguiente.setAnterior(anterior);
                } else {
                    cola = anterior;
                }
                 return true;
            }
             actual = actual.getSiguiente();
        }
        return false;
    }

    public int tamaño(){
        int contador = 0;
        NodoImagen<T> actual = cabeza;

        while (actual != null){
            contador++;
            actual = actual.getSiguiente();
        }
        return contador;
    }
}

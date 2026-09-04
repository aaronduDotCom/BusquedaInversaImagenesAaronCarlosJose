package Model.Estructuras;

// cambiar a coleccion generica
import Model.Imagen.ImagenData;

public class ColeccionImagenData extends ColeccionAbstracta<ImagenData>{

    public ColeccionImagenData(){
        super();
    }

    // aquí deben de ir los metodo para ordenamiento
    // solo que no hemos discutido que vamos a usar para hacer la comparacion de las imagenes

    // metodos imcompletos, solo para que se vea la estructura
    public void BubbleSort(){
        if (cabeza == null) return;

        boolean exito;
        do {
            exito = false;
            Nodo<ImagenData> actual = cabeza;
            // esto para no romper DEMETER
            Nodo<ImagenData> siguiente = actual.getSiguiente();
            ImagenData valorA = actual.getValor();
            ImagenData valorB = siguiente.getValor();

            while (actual.getSiguiente() != null){
                // aqui va la comparacion
                if(/*lo que sea que va a intercambiar*/ false){
                    actual.setValor(valorB);
                    siguiente.setValor(valorA);
                    exito = true;
                }
                actual = actual.getSiguiente();
            }
        } while (exito);
    }

    public void ordenarMergeSort(){
        cabeza = mergeSort(cabeza);
        reconstruirEnlaces();
    }
    private Nodo<ImagenData> mergeSort(Nodo<ImagenData> inicio){
        if (inicio == null || inicio.getSiguiente() == null){
            return inicio;
        }
        Nodo<ImagenData> mitad = obtenerMitad(inicio);
        Nodo<ImagenData> siguienteAMitad = mitad.getSiguiente();
        /* aqui hacemos la separacion de la lista en dos, como es doblemente enlazada
        * esto se puede hacer sin perder ningun puntero, simplemente hacemos que el puntero
        * de la mitad apunte a null y el mitad+1 apunte para atras a null*/
        mitad.setSiguiente(null);
        siguienteAMitad.setAnterior(null);

        Nodo<ImagenData> izquierda = mergeSort(inicio);
        Nodo<ImagenData> derecha = mergeSort(siguienteAMitad);

        return mezclar(izquierda, derecha);

    }

    private Nodo<ImagenData> obtenerMitad(Nodo<ImagenData> inicio){
        // puntero lento/ rapido para encontrar la mitad de la lista, el rapido avanza a por 2 del lento
        if (inicio == null || inicio.getSiguiente() == null){
            return inicio;
        }
        Nodo<ImagenData> lento = inicio;
        Nodo<ImagenData> rapido = inicio.getSiguiente();
        Nodo<ImagenData> aux;

        while (rapido.getSiguiente() != null){
            lento = lento.getSiguiente();
            aux = rapido.getSiguiente();
            rapido = aux.getSiguiente();
            if (rapido == null){
                break;
            }
        }
        return lento;
    }

    /*private Nodo<ImagenData> mezclar(Nodo<ImagenData> izquierda, Nodo<ImagenData> derecha){
        Nodo<ImagenData> aux = new Nodo<>(null);
        Nodo<ImagenData> cola = aux;

        while (izquierda != null && derecha != null){
            if (comparacion los valores que decidamos){
                cola.setSiguiente(izquierda);
                izquierda = izquierda.getSiguiente();
            }else {
                cola.setSiguiente(derecha);
                derecha = derecha.getSiguiente();
            }
            cola = cola.getSiguiente();
        }

        if (izquierda == null){
            cola.setSiguiente(derecha);
        }else{
            cola.setSiguiente(izquierda);
        }
        return aux.getSiguiente();
    }
*/
    private void reconstruirEnlaces(){
        Nodo<ImagenData> actual = cabeza;
        Nodo<ImagenData> anterior = null;

        while (actual != null){
            actual.setAnterior(anterior);
            anterior = actual;
            actual = actual.getSiguiente();
        }
        this.cola = anterior;
    }

}




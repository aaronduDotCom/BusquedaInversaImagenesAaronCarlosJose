package Model.Estructuras;

import Model.Imagen.ResultadoImagenData;

public class ColeccionResultadoImagenData extends ColeccionAbstracta<ResultadoImagenData>{
    public ColeccionResultadoImagenData(){super();}


    // aquí deben de ir los metodo para ordenamiento
    // solo que no hemos discutido que vamos a usar para hacer la comparacion de las imagenes

    // metodos imcompletos, solo para que se vea la estructura
    public void BubbleSort(){
        if (cabeza == null) return;

        boolean exito;
        do {
            exito = false;
            Nodo<ResultadoImagenData> actual = cabeza;
            // esto para no romper DEMETER
            Nodo<ResultadoImagenData> siguiente = actual.getSiguiente();
            ResultadoImagenData valorA = actual.getValor();
            ResultadoImagenData valorB = siguiente.getValor();

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
    private Nodo<ResultadoImagenData> mergeSort(Nodo<ResultadoImagenData> inicio){
        if (inicio == null || inicio.getSiguiente() == null){
            return inicio;
        }
        Nodo<ResultadoImagenData> mitad = obtenerMitad(inicio);
        Nodo<ResultadoImagenData> siguienteAMitad = mitad.getSiguiente();
        /* aqui hacemos la separacion de la lista en dos, como es doblemente enlazada
         * esto se puede hacer sin perder ningun puntero, simplemente hacemos que el puntero
         * de la mitad apunte a null y el mitad+1 apunte para atras a null*/
        mitad.setSiguiente(null);
        siguienteAMitad.setAnterior(null);

        Nodo<ResultadoImagenData> izquierda = mergeSort(inicio);
        Nodo<ResultadoImagenData> derecha = mergeSort(siguienteAMitad);

        return null; //mezclar(izquierda, derecha);

    }

    private Nodo<ResultadoImagenData> obtenerMitad(Nodo<ResultadoImagenData> inicio){
        // puntero lento/ rapido para encontrar la mitad de la lista, el rapido avanza a por 2 del lento
        if (inicio == null || inicio.getSiguiente() == null){
            return inicio;
        }
        Nodo<ResultadoImagenData> lento = inicio;
        Nodo<ResultadoImagenData> rapido = inicio.getSiguiente();
        Nodo<ResultadoImagenData> aux;

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

    private Nodo<ResultadoImagenData> mezclar(Nodo<ResultadoImagenData> izquierda, Nodo<ResultadoImagenData> derecha){
        Nodo<ResultadoImagenData> aux = new Nodo<>(null);
        Nodo<ResultadoImagenData> cola = aux;

        while (izquierda != null && derecha != null){
            if (izquierda.getValor().getVector().){
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

    private void reconstruirEnlaces(){
        Nodo<ResultadoImagenData> actual = cabeza;
        Nodo<ResultadoImagenData> anterior = null;

        while (actual != null){
            actual.setAnterior(anterior);
            anterior = actual;
            actual = actual.getSiguiente();
        }
        this.cola = anterior;
    }
}

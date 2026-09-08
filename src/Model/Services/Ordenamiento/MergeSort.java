package Model.Services.Ordenamiento;

import Model.Estructuras.Iterator;
import Model.Estructuras.ResultadoBusqueda;
import Model.Estructuras.Vector;
import Model.Services.Busqueda.Resultado;

public class MergeSort implements MetodoOrdenamiento {

    @Override
    public ResultadoBusqueda ordenar(ResultadoBusqueda resultados, boolean ascendente) {
        if (resultados == null) {
            throw new IllegalArgumentException("La colección de resultados no puede ser null");
        }
        Vector<Resultado> vector = new Vector<>(resultados.tamano());
        Iterator<Resultado> iterador = resultados.getIterador();
        while (iterador.hasNext()) {
            vector.insertar(iterador.next());
        }

        if (vector.tamanno() > 1) {
            mergeSort(vector, 0, vector.tamanno() - 1, ascendente);
        }

        ResultadoBusqueda resultadosOrdenados = new ResultadoBusqueda();
        Iterator<Resultado> iteradorOrdenado = vector.getIterador();
        while (iteradorOrdenado.hasNext()) {resultadosOrdenados.agregar(iteradorOrdenado.next());
        }

        return resultadosOrdenados;
    }

    private void mergeSort(Vector<Resultado> vector, int inicio, int fin, boolean ascendente) {

        if (inicio >= fin) {
            return;
        }

        int mitad = inicio + (fin - inicio) / 2;
        mergeSort(vector, inicio, mitad, ascendente);
        mergeSort(vector, mitad + 1, fin, ascendente);

        mezclar(vector, inicio, mitad, fin, ascendente);
    }
    private void mezclar(Vector<Resultado> vector, int inicio, int mitad, int fin, boolean ascendente) {
        int cantidadIzquierda = mitad - inicio + 1;
        int cantidadDerecha = fin - mitad;
        Vector<Resultado> izquierda = new Vector<>(cantidadIzquierda);
        Vector<Resultado> derecha = new Vector<>(cantidadDerecha);

        for (int i = 0; i < cantidadIzquierda; i++) {
            izquierda.insertar(vector.getPos(inicio + i));
        }

        for (int i = 0; i < cantidadDerecha; i++) {
            derecha.insertar(vector.getPos(mitad + 1 + i));
        }

        int posicionIzquierda = 0;
        int posicionDerecha = 0;
        int posicionOriginal = inicio;

        while (posicionIzquierda < izquierda.tamanno() && posicionDerecha < derecha.tamanno()) {
            Resultado resultadoIzquierdo = izquierda.getPos(posicionIzquierda);
            Resultado resultadoDerecho = derecha.getPos(posicionDerecha);
            boolean tomarIzquierda = debeTomarIzquierda(resultadoIzquierdo, resultadoDerecho, ascendente);
            if (tomarIzquierda) {
                vector.setPos(posicionOriginal, resultadoIzquierdo);
                posicionIzquierda++;
            } else {
                vector.setPos(posicionOriginal, resultadoDerecho);

                posicionDerecha++;
            }

            posicionOriginal++;
        }

        while (posicionIzquierda < izquierda.tamanno()) {
            vector.setPos(posicionOriginal, izquierda.getPos(posicionIzquierda));

            posicionIzquierda++;
            posicionOriginal++;
        }

        while (posicionDerecha < derecha.tamanno()) {
            vector.setPos(posicionOriginal, derecha.getPos(posicionDerecha));
            posicionDerecha++;
            posicionOriginal++;
        }
    }

    private boolean debeTomarIzquierda(Resultado izquierda, Resultado derecha, boolean ascendente) {
        if (ascendente) {
            return izquierda.getValor() <= derecha.getValor();
        }

        return izquierda.getValor() >= derecha.getValor();
    }
}
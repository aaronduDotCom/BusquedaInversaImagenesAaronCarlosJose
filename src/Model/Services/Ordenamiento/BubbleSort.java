package Model.Services.Ordenamiento;

import Model.Estructuras.Iterator;
import Model.Estructuras.ResultadoBusqueda;
import Model.Estructuras.Vector;
import Model.Excepciones.ResultadoNulo;
import Model.Services.Busqueda.Resultado;

public class BubbleSort implements MetodoOrdenamiento {

    @Override
    public ResultadoBusqueda ordenar(ResultadoBusqueda resultados, boolean ascendente) {
        if (resultados == null) {
            throw new ResultadoNulo("La colección de resultados no puede ser null");
        }

        Vector<Resultado> vector = new Vector<>(resultados.tamano());
        Iterator<Resultado> iterador = resultados.getIterador();
        while (iterador.hasNext()) {
            vector.insertar(iterador.next());
        }
        boolean huboIntercambio;
        int limite = vector.tamanno() - 1;
        do {
            huboIntercambio = false;
            for (int i = 0; i < limite; i++) {
                Resultado actual = vector.getPos(i);
                Resultado siguiente = vector.getPos(i + 1);

                boolean intercambiar;

                if (ascendente) {
                    intercambiar = actual.getValor() > siguiente.getValor();
                } else {
                    intercambiar = actual.getValor() < siguiente.getValor();
                }

                if (intercambiar) {
                    vector.setPos(i, siguiente);
                    vector.setPos(i + 1, actual);
                    huboIntercambio = true;
                }
            }
            limite--;

        } while (huboIntercambio && limite > 0);

        ResultadoBusqueda ordenados = new ResultadoBusqueda();
        Iterator<Resultado> iteradorVector = vector.getIterador();
        while (iteradorVector.hasNext()) {
            ordenados.agregar(iteradorVector.next());
        }
        return ordenados;
    }
}

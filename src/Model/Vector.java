package Model;

//Vector generico para uso en el proyecto de BusquedaInversa
public class Vector<T> {
    // Guia de como implementar
    //Vector<Imagen> vectorImagen = new VectorImagen<>(3);
    //vectorImagen.agregar(ImagenProfeGuapo);
    //vectorImagen.agregar(Foto);

    private Object[] objetos;
    private int contador;
    private int cant;

    public Vector(int c){
        cant = c;
        objetos = new Object[cant];
        contador = 0;
    }

    public void insertar(T nuevo){
        if (contador == 0){
            objetos[0] = nuevo;
            contador++;
            return;
        } else if (contador == cant) {
            return;
        }
        objetos[contador] = nuevo;
        contador++;
    }

    @SuppressWarnings("unchecked")
    public T getPos(int indice) {
        return (T) objetos[indice];
    }

    public int tamanno(){
        return contador;
    }

    public Object getIterador(){ //consultar al profe por esto
        return null;
    }


}

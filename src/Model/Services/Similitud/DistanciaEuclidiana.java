package Model.Services.Similitud;

import Model.Excepciones.ValidacionImagen;
import Model.Imagen.ImagenData;

public class DistanciaEuclidiana implements MetodoSimilitud {

    @Override
    public double calcular(ImagenData a, ImagenData b) {
        //    _____________________
        //   / n
        //  /  E      (A_i - B_i)²
        // v   i = 1

        validarImagenes(a, b);

        // Sumatoria
        double resultado = 0;

        for (int i = 0; i < 64; i++) {
            double diferencia =
                    a.getVector().getPos(i)
                            - b.getVector().getPos(i);

            // Potencia
            resultado += diferencia * diferencia;
        }

        // La raíz se aplica después de completar
        // toda la sumatoria
        return Math.sqrt(resultado);
    }

    @Override
    public boolean esAscendente() {
        // Entre menor sea la distancia,
        // mayor es la similitud entre las imágenes
        return true;
    }

    @Override
    public String getNombre() {
        return "Distancia Euclidiana";
    }

    // esto hay que correrlo a una clase excepcion propia, pero por ahora lo dejo asi
    private void validarImagenes(ImagenData a, ImagenData b) {
        if (a == null || b == null) {
            throw new ValidacionImagen("Las imágenes no pueden ser null");
        }

        if (a.getVector() == null || b.getVector() == null) {
            throw new ValidacionImagen("Los vectores no pueden ser null");
        }

        if (a.getVector().tamanno() != b.getVector().tamanno()) {
            throw new ValidacionImagen("Los vectores deben tener el mismo tamaño");
        }

        if (a.getVector().tamanno() != 64) {
            throw new ValidacionImagen("Los histogramas deben tener 64 posiciones");
        }
    }
}
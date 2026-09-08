package Model.Services.Busqueda;

import Model.Imagen.ImagenData;

public class Resultado {

    private ImagenData imagenData;
    private double valor;

    public Resultado(ImagenData imagenData, double valor) {
        this.imagenData = imagenData;
        this.valor = valor;
    }

    public ImagenData getImagenData() {
        return imagenData;
    }

    public double getValor() {
        return valor;
    }
}
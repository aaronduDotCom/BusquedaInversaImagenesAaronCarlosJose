package Model.Imagen;

import java.awt.Color;

import Model.Estructuras.Vector;
import Model.Excepciones.CodigoColorInvalido;

//saca la info de una imagen y devuelve los resultados para crear una imagenData

public class HistogramaColor {
    public int evaluarPosBin(int color, int bins){
        if(color >= 0 && color <= 256) {
            return color/bins;
        }else
            throw new CodigoColorInvalido("codigo color invalido");
    }

    //Este metodo se usa una vez por imagen
    public Vector<Double> calculaVector(Imagen imagen, int bins){
        Vector<Integer> vc = new Vector<>(bins); // vs = vector conteos
        //Inicializamos en 0s
        for (int i = 0; i < bins; i++) {
            vc.insertar(0);
        }

        //Tomamos ancho y altura del objeto imagen
        int width = imagen.getImagenBI().getWidth(); //
        int height = imagen.getImagenBI().getHeight();

        int r;
        int g;
        int b;

        //recorremos todos los pixeles de la imagen
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                // Get pixel color (Equivalent to: bitmap.GetPixel(x, y))
                int rgb = imagen.getImagenBI().getRGB(i, j); // posible inversion de coordenadas
                Color color = new Color(rgb, true); // true handles alpha channel

                // Extraemos canal de color
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                //evaluamos posiciones de los pixeles en sus bins
                int binR = evaluarPosBin(red,bins);
                int binG = evaluarPosBin(green,bins);
                int binB = evaluarPosBin(blue,bins);

                //localizamos el color en el vector caracteristico y lo contamos en el vector
                int posVector = binR*4*4 + binG*4 + binB*1;
                vc.setPos(posVector, vc.getPos(posVector) + 1);
            }
        }

        //Creamos un nuevo vector pero es la version normalizada del anterior
        Vector<Double> vn = new Vector<>(bins);
        double totalPixeles = (double)width * height;

        for (int i = 0; i < bins; i++) {
            double valor = (double)vc.getPos(i);
            vn.insertar(valor/totalPixeles + 0.0);
        }
        // vector normalizado, tiene que ser double
        return vn;
    }
}
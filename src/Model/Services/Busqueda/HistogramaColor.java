package Model.Services.Busqueda;

import java.awt.Color;

import Model.Imagen.Imagen;
import Model.Estructuras.Vector;

//saca la info de una imagen y devuelve los resultados para crear una imagenData

public class HistogramaColor {
    public int evaluarPosBin(int color){
        if(color >= 0 && color <= 63){
            return 0;
        }else if(color >= 64 && color <= 127){
            return 1;
        }else if(color >= 128 && color <= 191){
            return 2;
        }else if(color >= 192 && color <= 255){
            return 3;
        } else {
            throw new RuntimeException("codigo color invalido");
        }
    }

    //Este metodo se usa una vez por imagen
    public Vector<Integer> calculaVector(Imagen imagen){
        Vector<Integer> vc = new Vector<>(64);
        //Inicializamos en 0s
        for (int i = 0; i < 64; i++) {
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
                int rgb = imagen.getImagenBI().getRGB(i, j);
                Color color = new Color(rgb, true); // true handles alpha channel

                // Extraemos canal de color
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();

                //evaluamos posiciones de los pixeles en sus bins
                int binR = evaluarPosBin(red);
                int binG = evaluarPosBin(green);
                int binB = evaluarPosBin(blue);

                //localizamos el color en el vector caracteristico y lo contamos en el vector
                int posVector = binR * 4*4 + binG * 4 + binB * 1;
                vc.setPos(posVector, vc.getPos(posVector) + 1);
            }
        }

        //Creamos un nuevo vector pero es la version normalizada del anterior
        Vector<Double> vn = new Vector<>(64);
        int totalPixeles = width * height;

        for (int i = 0; i < 64; i++) {
            int valor = vc.getPos(i);
            vn.insertar(valor/totalPixeles + 0.0);
        }

        return vc;
    }
}
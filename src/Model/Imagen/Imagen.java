package Model.Imagen;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// recibe una imagen y la convierte para poder manipularla
// falta sacarle los pixeles y guardarlos en un arreglo
public class Imagen{
    //private String id;
    private BufferedImage imagenBI;

    public Imagen(File imagenPNG) throws Exception {
        imagenBI = ImageIO.read(imagenPNG);
    }

    public BufferedImage getImagenBI() {
        return imagenBI;
    }

}

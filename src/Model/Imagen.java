package Model;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;


public class Imagen{
    private BufferedImage imagenBI;

    public Imagen(File imagenPNG) throws Exception {
        imagenBI = ImageIO.read(imagenPNG);
    }

    public BufferedImage getImagenBI() {
        return imagenBI;
    }

}

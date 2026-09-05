package Model.Imagen;

import Model.Estructuras.Vector;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.UUID;
import javax.imageio.ImageIO;

// recibe una imagen y la convierte para poder manipularla
// falta sacarle los pixeles y guardarlos en un arreglo
public class Imagen{
    //private String id;
    private UUID id;
    private BufferedImage imagenBI;

    public Imagen(File imagenPNG) throws Exception {
        imagenBI = ImageIO.read(imagenPNG);
        id = UUID.randomUUID();
    }

    public Imagen(String name, Vector<Integer> vector) {
    }

    public BufferedImage getImagenBI() {
        return imagenBI;
    }

    public UUID getId() {
        return id;
    }
}


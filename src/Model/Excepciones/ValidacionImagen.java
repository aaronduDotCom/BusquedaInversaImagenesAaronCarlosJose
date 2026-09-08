package Model.Excepciones;

public class ValidacionImagen extends RuntimeException {
    public ValidacionImagen(String message) {
        super(message);
    }
}

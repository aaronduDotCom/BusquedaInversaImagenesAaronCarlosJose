package Model.Excepciones;

public class MetodoBusquedaInvalido extends RuntimeException {
    public MetodoBusquedaInvalido(String message) {
        super(message);
    }
}

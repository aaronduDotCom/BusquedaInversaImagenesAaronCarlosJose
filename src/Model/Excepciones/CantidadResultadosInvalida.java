package Model.Excepciones;

public class CantidadResultadosInvalida extends RuntimeException {
    public CantidadResultadosInvalida(String message) {
        super(message);
    }
}

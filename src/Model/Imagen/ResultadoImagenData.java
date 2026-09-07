package Model.Imagen;

public class ResultadoImagenData {
    private ImagenData iD;
    private double result;

    public ResultadoImagenData(ImagenData i, double r){
        iD = i;
        result = r;
    }

    public ImagenData getiD() {
        return iD;
    }

    public double getResult() {
        return result;
    }
}

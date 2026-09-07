package Model.Services.Similitud;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.ColeccionResultadoImagenData;
import Model.Imagen.ImagenData;
import Model.Imagen.ResultadoImagenData;

public class DistanciaEucriliana {
    public double simiEuc(ImagenData a, ImagenData b) {
        //    _____________________
        //   / n
        //  /  E      (A_i - B_i)²
        // v   i = 1

        //Sumatoria
        double result = 0;
        for (int i = 0; i < 64; i++){
            result += Math.sqrt(a.getVector().getPos(i) - b.getVector().getPos(i));

            //potencia
            result *= result;
        }

        result = Math.sqrt(result);

        return result;
    }

    public ColeccionResultadoImagenData resultadoSE(ImagenData iD, ColeccionImagenData cID){
        ColeccionResultadoImagenData cRID = new ColeccionResultadoImagenData();
        while(cID.getIterador().hasNext()){
            cRID.insertarInicio(new ResultadoImagenData(cID.getIterador().actual(),simiEuc(iD,cID.getIterador().actual())));

            cID.getIterador().next();
        }
        cRID.ordenarMergeSort();
        return cRID;
    }
}

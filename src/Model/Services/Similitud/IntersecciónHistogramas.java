package Model.Services.Similitud;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.ColeccionResultadoImagenData;
import Model.Imagen.ImagenData;
import Model.Imagen.ResultadoImagenData;

public class IntersecciónHistogramas {
    public double simiIntHis(ImagenData a, ImagenData b) {
        //     n
        //     E      min(A_i - B_i)
        //     i = 1

        //Sumatoria
        double result = 0;
        for (int i = 0; i < 64; i++){
            result += Math.min(a.getVector().getPos(i), b.getVector().getPos(i)); //sacamos el minimo de ambos

            //potencia
            result *= result;
        }

        result = Math.sqrt(result);

        return result;
    }

    public ColeccionResultadoImagenData resultadoSE(ImagenData iD, ColeccionImagenData cID){
        ColeccionResultadoImagenData cRID = new ColeccionResultadoImagenData();
        while(cID.getIterador().hasNext()){
            cRID.insertarInicio(new ResultadoImagenData(cID.getIterador().actual(),simiIntHis(iD,cID.getIterador().actual())));

            cID.getIterador().next();
        }
        cRID.ordenarMergeSort();
        return cRID;
    }
}

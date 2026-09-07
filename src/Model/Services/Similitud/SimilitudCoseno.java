package Model.Services.Similitud;

import Model.Estructuras.ColeccionImagenData;
import Model.Estructuras.ColeccionResultadoImagenData;
import Model.Imagen.ImagenData;
import Model.Imagen.ResultadoImagenData;

public class SimilitudCoseno { //Entre mas cerca de 1 mejor, -1 < x < 1
    public double simiCos(ImagenData a, ImagenData b){
        //     A * B
        //   ---------
        // ||A|| * ||B||

        // A * B
        double aTimesb = 0;
        for(int i = 0; i < 64; i++){
            aTimesb  += a.getVector().getIterador().next() * a.getVector().getIterador().next();
        }

        // ||A|| * ||B||
        int sumA = 0;
        for(int i = 0; i < 64; i++){
            sumA += a.getVector().getIterador().next();
        }
        double normalA = Math.sqrt(sumA);

        int sumB = 0;
        for(int i = 0; i < 64; i++){
            sumB += b.getVector().getIterador().next();
        }
        double normalB = Math.sqrt(sumB);

        double normaATimesNormalB = normalA * normalB;

        //   ---------

        if(normaATimesNormalB != 0) {
            return aTimesb / normaATimesNormalB;
        } else {
            throw new RuntimeException("Division by 0 in SimilitudCoseno");
        }
    }

    public ColeccionResultadoImagenData resultadoSC(ImagenData iD, ColeccionImagenData cID){
        ColeccionResultadoImagenData cRID = new ColeccionResultadoImagenData();
        while(cID.getIterador().hasNext()){
            cRID.insertarInicio(new ResultadoImagenData(cID.getIterador().actual(),simiCos(iD,cID.getIterador().actual())));

            cID.getIterador().next();
        }
        cRID.ordenarMergeSort();
        return cRID;
    }
}

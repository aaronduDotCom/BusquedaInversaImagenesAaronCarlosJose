package Busqueda.Model.Repositorios;

import Busqueda.Model.Estructuras.ColeccionImagenData;

public class RepoImagenes {

    private static RepoImagenes instancia;

    private ColeccionImagenData imagenes;
    private int cantidadBins;
    private String rutaCarpeta;
    private String rutaArchivoBinario;

    private RepoImagenes() {
        imagenes = new ColeccionImagenData();
        cantidadBins = 0;
    }

    public static RepoImagenes getInstance() {
        if (instancia == null) {
            instancia = new RepoImagenes();
        }

        return instancia;
    }

    public ColeccionImagenData obtenerImagenes() {
        return imagenes;
    }

    public void reemplazar(
            ColeccionImagenData imagenes,
            int cantidadBins
    ) {
        if (imagenes == null) {
            throw new IllegalArgumentException(
                    "La colección de imágenes no puede ser null"
            );
        }

        if (cantidadBins <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad de bins debe ser mayor que cero"
            );
        }

        this.imagenes = imagenes;
        this.cantidadBins = cantidadBins;
    }

    public int getCantidadBins() {
        return cantidadBins;
    }

    public String getRutaCarpeta() {
        return rutaCarpeta;
    }

    public String getRutaArchivoBinario() {
        return rutaArchivoBinario;
    }

    public void configurarRutas(
            String rutaCarpeta,
            String rutaArchivoBinario
    ) {
        this.rutaCarpeta = rutaCarpeta;
        this.rutaArchivoBinario = rutaArchivoBinario;
    }
}
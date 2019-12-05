package com.example.bookreview;

public class PojoKategori {

    private String NamaKategori;

    public PojoKategori(String namaKategori) {
        NamaKategori = namaKategori;
    }

    public void setNamaKategori(String NamaKategori){
        this.NamaKategori = NamaKategori;
    }

    public String getNamaKategori (){
        return NamaKategori;
    }

}

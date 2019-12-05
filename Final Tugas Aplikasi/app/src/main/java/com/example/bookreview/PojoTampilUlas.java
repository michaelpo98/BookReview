package com.example.bookreview;

public class PojoTampilUlas {
    private String nama, rating, komentar;


    public PojoTampilUlas(String nama, String rating, String komentar) {
        this.nama = nama;
        this.rating = rating;
        this.komentar = komentar;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public String getNama() {
        return nama;
    }

    public String getRating() {
        return rating;
    }

    public String getKomentar() {
        return komentar;
    }
}

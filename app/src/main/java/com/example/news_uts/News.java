package com.example.news_uts;

public class News {
    String judul;
    String desc;
    String umur;
    String kategori;
    int image;

    public News(String judul, String description, int image, String kategori, String umur) {
        this.judul = judul;
        this.desc = description;
        this.umur = umur;
        this.kategori = kategori;
        this.image = image;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDescription() {
        return desc;
    }

    public void setDescription(String description) {
        this.desc = description;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}

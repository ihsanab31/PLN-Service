package com.sundevs.ihsan.plnservice.model;

/**
 * Created by iihsa on 8/14/2017.
 */

public class Data {
    private  String id_ber, judul, value, images, date;
    private  String id_kel, id_pel, alamat, waktu, tanggal, keluhan, t_kel, gambar;
    public Data (){

    }
    public Data(String id_ber, String judul, String value, String images, String date, String id_kel, String id_pel, String alamat, String waktu, String tanggal, String keluhan, String t_kel, String gambar){
        this.id_ber = id_ber;
        this.judul = judul;
        this.value= value;
        this.images = images;
        this.date = date;
        this.id_ber=id_ber;
        this.id_kel=id_kel;
        this.alamat=alamat;
        this.waktu=waktu;
        this.tanggal=tanggal;
        this.keluhan=keluhan;
        this.t_kel=t_kel;
        this.gambar=gambar;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setId_ber(String id_ber) {
        this.id_ber = id_ber;
    }

    public String getId_ber() {
        return id_ber;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getImages() {
        return images;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getJudul() {
        return judul;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setId_kel(String id_kel) {
        this.id_kel = id_kel;
    }

    public String getId_kel() {
        return id_kel;
    }

    public void setId_pel(String id_pel) {
        this.id_pel = id_pel;
    }

    public String getId_pel() {
        return id_pel;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setT_kel(String t_kel) {
        this.t_kel = t_kel;
    }

    public String getT_kel() {
        return t_kel;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getGambar() {
        return gambar;
    }
}

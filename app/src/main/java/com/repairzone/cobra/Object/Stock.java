package com.repairzone.cobra.Object;

public class Stock {
    private String nama, satuan;
    private int jumlah;

    public Stock(String nama, String satuan, int jumlah) {
        this.nama = nama;
        this.satuan = satuan;
        this.jumlah = jumlah;
    }

    public Stock(String nama, String satuan) {
        this.nama = nama;
        this.satuan = satuan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }
}

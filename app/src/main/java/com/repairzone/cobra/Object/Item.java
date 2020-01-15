package com.repairzone.cobra.Object;

public class Item {
    private String nama, satuan;

    public Item(String nama, String satuan) {
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
}

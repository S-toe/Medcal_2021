package com.berwyn.medcal;

import com.google.gson.annotations.SerializedName;

abstract class Obat {

    @SerializedName("obat")
    public String Obat;

    public Obat(String Obat){
        this.Obat = Obat;
    }

    public String getObat() {
        return Obat;
    }

    public void setObat(String obat) {
        Obat = obat;
    }
}

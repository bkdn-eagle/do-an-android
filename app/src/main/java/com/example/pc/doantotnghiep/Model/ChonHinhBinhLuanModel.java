package com.example.pc.doantotnghiep.Model;

public class ChonHinhBinhLuanModel {
    String duongDan;
    boolean checkBox;
    public String getDuongDan() {
        return duongDan;
    }

    public void setDuongDan(String duongDan) {
        this.duongDan = duongDan;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public ChonHinhBinhLuanModel(String duongDan, boolean checkBox) {
        this.duongDan = duongDan;
        this.checkBox = checkBox;

    }
}

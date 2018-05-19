package com.example.pc.doantotnghiep.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by PC on 07/04/2018.
 */

public class ChiNhanhQuanAnModel implements Parcelable {
    String diachi;
    Double latitude;
    Double longitude;
    Double khoangcach;

    public ChiNhanhQuanAnModel(){

    }

    protected ChiNhanhQuanAnModel(Parcel in) {
        diachi = in.readString();
        if (in.readByte() == 0) {
            latitude = null;
        } else {
            latitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            longitude = null;
        } else {
            longitude = in.readDouble();
        }
        if (in.readByte() == 0) {
            khoangcach = null;
        } else {
            khoangcach = in.readDouble();
        }
    }

    public static final Creator<ChiNhanhQuanAnModel> CREATOR = new Creator<ChiNhanhQuanAnModel>() {
        @Override
        public ChiNhanhQuanAnModel createFromParcel(Parcel in) {
            return new ChiNhanhQuanAnModel(in);
        }

        @Override
        public ChiNhanhQuanAnModel[] newArray(int size) {
            return new ChiNhanhQuanAnModel[size];
        }
    };

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getKhoangcach() {
        return khoangcach;
    }

    public void setKhoangcach(Double khoangcach) {
        this.khoangcach = khoangcach;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(diachi);
        if (latitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(latitude);
        }
        if (longitude == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(longitude);
        }
        if (khoangcach == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(khoangcach);
        }
    }
}

package com.example.pc.doantotnghiep.Controller;

import android.util.Log;

import com.example.pc.doantotnghiep.Model.DownloadPolyLine;
import com.example.pc.doantotnghiep.Model.ParserPolyline;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DanDuongToiQuanAnController {
    ParserPolyline parserPolyline;
    DownloadPolyLine downloadPolyLine;

    public DanDuongToiQuanAnController() {

    }

    public void HienThiDanDuongToiQuanAn(GoogleMap googleMap, String duongdan) {
        parserPolyline = new ParserPolyline();
        downloadPolyLine = new DownloadPolyLine();
        downloadPolyLine.execute(duongdan);

        try {
            String dataJson = downloadPolyLine.get();
            Log.d("kiemtradataJson", dataJson);
            List<LatLng> latLngList = parserPolyline.LayDanhSachToaDo(dataJson);
            PolylineOptions polylineOptions = new PolylineOptions();
            for (LatLng valuelatLng : latLngList) {
                polylineOptions.add(valuelatLng);
            }
            Polyline polyline = googleMap.addPolyline(polylineOptions);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

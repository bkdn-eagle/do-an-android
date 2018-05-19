package com.example.pc.doantotnghiep.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.doantotnghiep.Model.DatMon;
import com.example.pc.doantotnghiep.Model.MonAnModel;
import com.example.pc.doantotnghiep.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterMonAn extends RecyclerView.Adapter<AdapterMonAn.HolderMonAn> {
    Context context;
    List<MonAnModel> monAnModels;
    public static List<DatMon> datMonList = new ArrayList<>();


    public AdapterMonAn(Context context, List<MonAnModel> monAnModels) {
        this.context = context;
        this.monAnModels = monAnModels;

    }


    @Override
    public AdapterMonAn.HolderMonAn onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_monan, parent, false);
        return new AdapterMonAn.HolderMonAn(view);
    }

    @Override
    public void onBindViewHolder(final AdapterMonAn.HolderMonAn holder, int position) {
        final MonAnModel monAnModel = monAnModels.get(position);
        holder.txtTenMonAn.setText(monAnModel.getTenmon());
        holder.txtSoLuong.setTag(0);
        holder.imgTangSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                dem++;
                holder.txtSoLuong.setText(dem + "");
                holder.txtSoLuong.setTag(dem);

                DatMon datMonTag = (DatMon) holder.imgGiamSoLuong.getTag();
                if (datMonTag != null) {
                    AdapterMonAn.datMonList.remove(datMonTag);
                }

                DatMon datMon = new DatMon();
                datMon.setSoLuong(dem);
                datMon.setTenMonAn(monAnModel.getTenmon());
                datMon.setGiaTien(monAnModel.getGiatien());
                holder.imgGiamSoLuong.setTag(datMon);
                AdapterMonAn.datMonList.add(datMon);

            }
        });


        holder.imgGiamSoLuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int dem = Integer.parseInt(holder.txtSoLuong.getTag().toString());
                if (dem != 0) {
                    dem--;
                    if (dem == 0) {
                        DatMon datMon = (DatMon) view.getTag();
                        AdapterMonAn.datMonList.remove(datMon);

                    }
                } else {

                }
                holder.txtSoLuong.setText(dem + "");
                holder.txtSoLuong.setTag(dem);

            }
        });

    }



    @Override
    public int getItemCount() {
        return monAnModels.size();
    }

    public class HolderMonAn extends RecyclerView.ViewHolder {
        TextView txtTenMonAn, txtSoLuong;
        ImageView imgGiamSoLuong, imgTangSoLuong;

        public HolderMonAn(View itemView) {
            super(itemView);
            txtTenMonAn = itemView.findViewById(R.id.txtTenMonAn);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            imgGiamSoLuong = itemView.findViewById(R.id.imgGiamSoLuong);
            imgTangSoLuong = itemView.findViewById(R.id.imgTangSoLuong);
        }
    }
}

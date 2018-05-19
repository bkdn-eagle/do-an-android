package com.example.pc.doantotnghiep.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.doantotnghiep.Model.BinhLuanModel;
import com.example.pc.doantotnghiep.R;
import com.example.pc.doantotnghiep.View.HienThiChiTietBinhLuan;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class AdapterRecyclerHinhBinhLuan extends RecyclerView.Adapter<AdapterRecyclerHinhBinhLuan.ViewHolder> {
    Context context;
    int layout_hinh;
    List<Bitmap> listHinhBinhLuan;
    BinhLuanModel binhLuanModel;
    Boolean checkChitiet;

    public AdapterRecyclerHinhBinhLuan(Context context, int layout_hinh, List<Bitmap> listHinhBinhLuan, BinhLuanModel binhLuanModel, Boolean checkChitiet) {
        this.context = context;
        this.layout_hinh = layout_hinh;
        this.listHinhBinhLuan = listHinhBinhLuan;
        this.binhLuanModel = binhLuanModel;
        this.checkChitiet = checkChitiet;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageBinhLuan;
        TextView txtSoHinhBinhLuan;
        FrameLayout khungSoHinhBinhLuan;

        public ViewHolder(View itemView) {
            super(itemView);
            imageBinhLuan = itemView.findViewById(R.id.imageBinhLuan);
            txtSoHinhBinhLuan = itemView.findViewById(R.id.txtSoHinhBinhLuan);
            khungSoHinhBinhLuan = itemView.findViewById(R.id.khungSoHinhBinhLuan);
        }
    }

    @Override
    public AdapterRecyclerHinhBinhLuan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_hinh, parent, false);
        AdapterRecyclerHinhBinhLuan.ViewHolder viewHolder = new AdapterRecyclerHinhBinhLuan.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerHinhBinhLuan.ViewHolder holder, int position) {
        holder.imageBinhLuan.setImageBitmap(listHinhBinhLuan.get(position));
        if (!checkChitiet) {
            if (position == 3) {
                holder.khungSoHinhBinhLuan.setVisibility(View.VISIBLE);
                int soHinhConLai = listHinhBinhLuan.size() - 4;
                holder.txtSoHinhBinhLuan.setText("+" + soHinhConLai);

                holder.imageBinhLuan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent iHienThiCHiTietBinhLuan = new Intent(context, HienThiChiTietBinhLuan.class);
                        iHienThiCHiTietBinhLuan.putExtra("binhluanmodel", binhLuanModel);
                        context.startActivity(iHienThiCHiTietBinhLuan);
                    }
                });
            }
        }

    }

    @Override
    public int getItemCount() {
        if (!checkChitiet) {
            if (listHinhBinhLuan.size() < 4) {
                return listHinhBinhLuan.size();
            } else {
                return 4;
            }

        } else return listHinhBinhLuan.size();
    }
}

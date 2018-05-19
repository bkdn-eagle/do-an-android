package com.example.pc.doantotnghiep.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.pc.doantotnghiep.Model.BinhLuanModel;
import com.example.pc.doantotnghiep.Model.QuanAnModel;
import com.example.pc.doantotnghiep.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterBinhLuan extends RecyclerView.Adapter<AdapterBinhLuan.ViewHolder> {
    Context context;
    int layout;
    List<BinhLuanModel> binhLuanModelList;
    long luotThich = 0;
    QuanAnModel quanAnModel;

    public AdapterBinhLuan(Context context, int layout, List<BinhLuanModel> binhLuanModelList, QuanAnModel quanAnModel) {
        this.context = context;
        this.layout = layout;
        this.binhLuanModelList = binhLuanModelList;
        this.quanAnModel = quanAnModel;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView txtTieudebinhluan, txtNodungbinhluan, txtChamDiemBinhLuan;
        RecyclerView recyclerHinhBinhLuan;
        Button btnnlove, btnlove;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTieudebinhluan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtNodungbinhluan = itemView.findViewById(R.id.txtNodungbinhluan);
            txtChamDiemBinhLuan = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            circleImageView = itemView.findViewById(R.id.cicleImageUser);
            recyclerHinhBinhLuan = itemView.findViewById(R.id.recyclerHinhBinhLuan);
            btnlove = itemView.findViewById(R.id.btnlove);
            btnnlove = itemView.findViewById(R.id.btnnlove);
        }
    }


    @Override
    public AdapterBinhLuan.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        AdapterBinhLuan.ViewHolder viewHolder = new AdapterBinhLuan.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterBinhLuan.ViewHolder holder, int position) {
        final BinhLuanModel binhLuanModel = binhLuanModelList.get(position);
        holder.txtTieudebinhluan.setText(binhLuanModel.getTieude());
        holder.txtNodungbinhluan.setText(binhLuanModel.getNoidung());
        holder.txtChamDiemBinhLuan.setText(binhLuanModel.getChamdiem() + "");


        holder.btnnlove.setText(binhLuanModel.getLuotthich() + "");

        try {
            setHinhAnhUser(holder.circleImageView, binhLuanModel.getThanhVienModel().getHinhanhapp());
        } catch (Exception e) {

        }
        final List<Bitmap> bitmapList = new ArrayList<>();
        for (String listHinh : binhLuanModel.getHinhanhBinhLuanList()) {
            StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference("hinhanh").child(listHinh);
            long ONE_MEGA = 1024 * 1024;
            storageHinhUser.getBytes(ONE_MEGA).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    bitmapList.add(bitmap);

                    if (bitmapList.size() == binhLuanModel.getHinhanhBinhLuanList().size()) {
                        AdapterRecyclerHinhBinhLuan adapterRecyclerHinhBinhLuan = new AdapterRecyclerHinhBinhLuan(context, R.layout.custom_layout_hinhbinhluan, bitmapList, binhLuanModel, false);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
                        holder.recyclerHinhBinhLuan.setLayoutManager(layoutManager);
                        holder.recyclerHinhBinhLuan.setAdapter(adapterRecyclerHinhBinhLuan);
                    }
                }
            });
        }
        holder.btnnlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luotThich = binhLuanModel.getLuotthich();
                holder.btnnlove.setVisibility(View.GONE);
                holder.btnlove.setVisibility(View.VISIBLE);
                luotThich += 1;
                binhLuanModel.setLuotthich(luotThich);
                holder.btnlove.setText(luotThich + "");
                FirebaseDatabase.getInstance().getReference().child("binhluans").child(quanAnModel.getMaquanan()).child(binhLuanModel.getMabinhluan()).child("luotthich").setValue(binhLuanModel.getLuotthich()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });
        holder.btnlove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                luotThich = binhLuanModel.getLuotthich();
                holder.btnnlove.setVisibility(View.VISIBLE);
                holder.btnlove.setVisibility(View.GONE);
                luotThich -= 1;
                holder.btnnlove.setText(luotThich + "");
                binhLuanModel.setLuotthich(luotThich);
                FirebaseDatabase.getInstance().getReference().child("binhluans").child(quanAnModel.getMaquanan()).child(binhLuanModel.getMabinhluan()).child("luotthich").setValue(binhLuanModel.getLuotthich()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        int soBinhLuan = binhLuanModelList.size();

        return soBinhLuan;
    }

    private void setHinhAnhUser(final CircleImageView circleImageView, String link) {
        StorageReference storageHinhUser = FirebaseStorage.getInstance().getReference("thanhvien").child(link);
        long ONE_MEGA = 1024 * 1024;
        storageHinhUser.getBytes(ONE_MEGA).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                circleImageView.setImageBitmap(bitmap);
            }
        });
    }
}

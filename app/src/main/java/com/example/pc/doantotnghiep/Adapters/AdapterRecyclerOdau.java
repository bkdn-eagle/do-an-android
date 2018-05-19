package com.example.pc.doantotnghiep.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pc.doantotnghiep.Model.BinhLuanModel;
import com.example.pc.doantotnghiep.Model.ChiNhanhQuanAnModel;
import com.example.pc.doantotnghiep.Model.QuanAnModel;
import com.example.pc.doantotnghiep.R;
import com.example.pc.doantotnghiep.View.ChiTietQuanAnActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.util.List;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by PC on 21/03/2018.
 */

public class AdapterRecyclerOdau extends RecyclerView.Adapter<AdapterRecyclerOdau.ViewHolder> {
    List<QuanAnModel> quanAnModelList;
    int resource;
    Context context;

    public AdapterRecyclerOdau(Context context, List<QuanAnModel> quanAnModelList, int resource) {
        this.quanAnModelList = quanAnModelList;
        this.resource = resource;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenQuanAnOdau, txtTieudebinhluan, txtTieudebinhluan2, txtNodungbinhluan, txtNodungbinhluan2, txtKhoangCachQuanAnODau;
        TextView txtTongBinhLuan, txtTongHinhBinhLuan, txtChamDiemBinhLuan, txtChamDiemBinhLuan2, txtDiemTrungBinhQuanAn, txtDiaChiQuanAnODau;
        Button btnDatMonOdau;
        ImageView imageHinhQuanAnOdau;
        CircleImageView cicleImageUser, cicleImageUser2;
        LinearLayout containerBinhLuan2, containerBinhLuan;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTenQuanAnOdau = itemView.findViewById(R.id.txtTenQuanQuanOdau);
            btnDatMonOdau = itemView.findViewById(R.id.btnDatMonOdau);
            imageHinhQuanAnOdau = itemView.findViewById(R.id.imageHinhQuanAnOdau);

            txtTieudebinhluan = itemView.findViewById(R.id.txtTieudebinhluan);
            txtNodungbinhluan = itemView.findViewById(R.id.txtNodungbinhluan);

            txtNodungbinhluan2 = itemView.findViewById(R.id.txtNodungbinhluan2);
            txtTieudebinhluan2 = itemView.findViewById(R.id.txtTieudebinhluan2);

            cicleImageUser = itemView.findViewById(R.id.cicleImageUser);
            cicleImageUser2 = itemView.findViewById(R.id.cicleImageUser2);

            containerBinhLuan = itemView.findViewById(R.id.containerBinhLuan);
            containerBinhLuan2 = itemView.findViewById(R.id.containerBinhLuan2);

            txtChamDiemBinhLuan = itemView.findViewById(R.id.txtChamDiemBinhLuan);
            txtChamDiemBinhLuan2 = itemView.findViewById(R.id.txtChamDiemBinhLuan2);

            txtTongHinhBinhLuan = itemView.findViewById(R.id.txtTongHinhBinhLuan);
            txtTongBinhLuan = itemView.findViewById(R.id.txtTongBinhLuan);
            txtDiemTrungBinhQuanAn = itemView.findViewById(R.id.txtDiemTrungBinhQuanAn);
            txtDiaChiQuanAnODau = itemView.findViewById(R.id.txtDiaChiQuanAnODau);
            txtKhoangCachQuanAnODau = itemView.findViewById(R.id.txtKhoangCachQuanAnODau);
            cardView = itemView.findViewById(R.id.cardViewOdau);
        }
    }

    @Override
    public AdapterRecyclerOdau.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final AdapterRecyclerOdau.ViewHolder holder, int position) {
        final QuanAnModel quanAnModel = quanAnModelList.get(position);
        holder.txtTenQuanAnOdau.setText(quanAnModel.getTenquanan());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan", quanAnModel);
                context.startActivity(iChiTietQuanAn);
            }
        });
        holder.txtTenQuanAnOdau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iChiTietQuanAn = new Intent(context, ChiTietQuanAnActivity.class);
                iChiTietQuanAn.putExtra("quanan", quanAnModel);
                context.startActivity(iChiTietQuanAn);
            }
        });

        if (quanAnModel.getGiaohang()) {
            holder.btnDatMonOdau.setVisibility(View.VISIBLE);
        }

        if (quanAnModel.getHinhanhquanan().size() > 0) {
            StorageReference storageHinhAnh = FirebaseStorage.getInstance().getReference().child("hinhanh").child(quanAnModel.getHinhanhquanan().get(0));
            long ONE_MEGABYTE = 1024 * 1024;
            storageHinhAnh.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    holder.imageHinhQuanAnOdau.setImageBitmap(bitmap);
                }
            });
        }

        if (quanAnModel.getBinhLuanModelList().size() > 0) {
            BinhLuanModel binhLuanModel1 = quanAnModel.getBinhLuanModelList().get(0);
            holder.txtTieudebinhluan.setText(binhLuanModel1.getTieude());
            holder.txtNodungbinhluan.setText(binhLuanModel1.getNoidung());
            holder.txtChamDiemBinhLuan.setText("" + binhLuanModel1.getChamdiem());
            long ONE_MEGABYTE = 1024 * 1024;

            try{
                StorageReference storageHinhUser1 = FirebaseStorage.getInstance().getReference("thanhvien").child(binhLuanModel1.getThanhVienModel().getHinhanhapp());
                storageHinhUser1.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        holder.cicleImageUser.setImageBitmap(bitmap);
                    }
                });
            }catch (Exception e)
            {

            }

            if (quanAnModel.getBinhLuanModelList().size() > 1) {
                BinhLuanModel binhLuanModel2 = quanAnModel.getBinhLuanModelList().get(1);
                holder.txtTieudebinhluan2.setText(binhLuanModel2.getTieude());
                holder.txtNodungbinhluan2.setText(binhLuanModel2.getNoidung());
                holder.txtChamDiemBinhLuan2.setText("" + binhLuanModel2.getChamdiem());
                try{
                    StorageReference storageHinhUser2 = FirebaseStorage.getInstance().getReference("thanhvien").child(binhLuanModel2.getThanhVienModel().getHinhanhapp());
                    storageHinhUser2.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            holder.cicleImageUser2.setImageBitmap(bitmap);
                        }
                    });
                }catch (Exception e){

                }

            }

            holder.txtTongBinhLuan.setText(quanAnModel.getBinhLuanModelList().size() + "");
            int tongHinhBinhLuan = 0;
            double tongDiemBinhLuan = 0;
            for (BinhLuanModel binhLuanModel : quanAnModel.getBinhLuanModelList()) {
                tongHinhBinhLuan += binhLuanModel.getHinhanhBinhLuanList().size();
                tongDiemBinhLuan += binhLuanModel.getChamdiem();


            }
            DecimalFormat df = new DecimalFormat("#.0");
            holder.txtDiemTrungBinhQuanAn.setText(df.format(tongDiemBinhLuan / quanAnModel.getBinhLuanModelList().size()) + "");
            holder.txtTongHinhBinhLuan.setText(tongHinhBinhLuan + "");
        } else {
            holder.containerBinhLuan.setVisibility(View.GONE);
            holder.containerBinhLuan2.setVisibility(View.GONE);
        }
        // Lay chi nhanh quan an va dia chi gan nhat
        if (quanAnModel.getChiNhanhQuanAnModelList().size() > 0) {
            ChiNhanhQuanAnModel chiNhanhQuanAnModelTam = quanAnModel.getChiNhanhQuanAnModelList().get(0);
            for (ChiNhanhQuanAnModel chiNhanhQuanAnModel : quanAnModel.getChiNhanhQuanAnModelList()) {
                if (chiNhanhQuanAnModelTam.getKhoangcach() >= chiNhanhQuanAnModel.getKhoangcach()) {
                    chiNhanhQuanAnModelTam = chiNhanhQuanAnModel;
                }
            }
            DecimalFormat df = new DecimalFormat("#.00");

            holder.txtDiaChiQuanAnODau.setText(chiNhanhQuanAnModelTam.getDiachi());

            holder.txtKhoangCachQuanAnODau.setText(df.format(chiNhanhQuanAnModelTam.getKhoangcach()) + " km");
        }
    }
    @Override
    public int getItemCount() {
        return quanAnModelList.size();
    }
}

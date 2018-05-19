package com.example.pc.doantotnghiep.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.pc.doantotnghiep.R;

import java.util.List;


public class AdapterHienThiHinhAnQuanAn extends RecyclerView.Adapter<AdapterHienThiHinhAnQuanAn.ViewHolderHinhBinhLuan> {

    Context context;
    int resource;
    List<Bitmap> list;

    public AdapterHienThiHinhAnQuanAn(Context context, int resource, List<Bitmap> list){
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public ViewHolderHinhBinhLuan onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);
        ViewHolderHinhBinhLuan viewHolderHinhBinhLuan = new ViewHolderHinhBinhLuan(view);
        return viewHolderHinhBinhLuan;
    }

    @Override
    public void onBindViewHolder(ViewHolderHinhBinhLuan holder, int position) {

        Bitmap uri = list.get(position);
        holder.imageView.setImageBitmap(uri);
        holder.imgXoa.setTag(position);
        holder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int vitri = (int) v.getTag();
                list.remove(vitri);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolderHinhBinhLuan extends RecyclerView.ViewHolder {
        ImageView imageView,imgXoa;

        public ViewHolderHinhBinhLuan(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imgChonHinhBinhLuan);
            imgXoa = (ImageView) itemView.findViewById(R.id.imgeDelete);
        }
    }
}

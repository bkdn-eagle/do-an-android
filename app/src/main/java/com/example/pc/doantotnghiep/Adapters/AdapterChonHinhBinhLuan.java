package com.example.pc.doantotnghiep.Adapters;

import android.content.Context;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.example.pc.doantotnghiep.Model.ChonHinhBinhLuanModel;
import com.example.pc.doantotnghiep.R;

import java.util.List;

public class AdapterChonHinhBinhLuan extends RecyclerView.Adapter<AdapterChonHinhBinhLuan.ViewHodler> {
    Context context;
    List<ChonHinhBinhLuanModel> stringList;
    int resource;

    public AdapterChonHinhBinhLuan(Context context, int resource, List<ChonHinhBinhLuanModel> stringList) {
        this.context = context;
        this.stringList = stringList;
        this.resource = resource;
    }

    @Override
    public AdapterChonHinhBinhLuan.ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        AdapterChonHinhBinhLuan.ViewHodler viewHolder = new AdapterChonHinhBinhLuan.ViewHodler(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(AdapterChonHinhBinhLuan.ViewHodler holder, final int position) {
        final ChonHinhBinhLuanModel chonHinhBinhLuanModel = stringList.get(position);
        Uri uri = Uri.parse(chonHinhBinhLuanModel.getDuongDan());
        holder.imageView.setImageURI(uri);
        holder.checkBox.setChecked(chonHinhBinhLuanModel.isCheckBox());
       holder.checkBox.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               CheckBox checkBox = (CheckBox) view;
               chonHinhBinhLuanModel.setCheckBox(checkBox.isChecked());
               stringList.get(position).setCheckBox(checkBox.isChecked());
           }
       });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        ImageView imageView;
        CheckBox checkBox;

        public ViewHodler(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgChonHinhBinhLuan);
            checkBox = itemView.findViewById(R.id.checkBoxChonHinhBinhLuan);
            this.setIsRecyclable(false);
        }
    }
}

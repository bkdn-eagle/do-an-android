package com.example.pc.doantotnghiep.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.pc.doantotnghiep.Model.ThucDonModel;
import com.example.pc.doantotnghiep.R;

import java.util.List;

public class AdapterThucDon extends RecyclerView.Adapter<AdapterThucDon.HolderThucDon> {
    Context context;
    List<ThucDonModel> thucDonModels;


    public AdapterThucDon(Context context, List<ThucDonModel> thucDonModels) {
        this.context = context;
        this.thucDonModels = thucDonModels;

    }

    @Override
    public AdapterThucDon.HolderThucDon onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_thucdon, parent, false);
        return new HolderThucDon(view);
    }

    @Override
    public void onBindViewHolder(AdapterThucDon.HolderThucDon holder, int position) {
        ThucDonModel thucDonModel = thucDonModels.get(position);
        holder.txtTenThucDon.setText(thucDonModel.getTenthucdon());
        holder.recyclerMonAn.setLayoutManager(new LinearLayoutManager(context));
        AdapterMonAn adapterMonAn= new AdapterMonAn(context,thucDonModel.getMonAnModelList());
        holder.recyclerMonAn.setAdapter(adapterMonAn);
        adapterMonAn.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return thucDonModels.size();
    }

    public class HolderThucDon extends RecyclerView.ViewHolder {
        TextView txtTenThucDon;
        RecyclerView recyclerMonAn;

        public HolderThucDon(View itemView) {
            super(itemView);
            recyclerMonAn = itemView.findViewById(R.id.recyclerMonAn);
            txtTenThucDon = itemView.findViewById(R.id.txtTenThucDon);
        }
    }
}

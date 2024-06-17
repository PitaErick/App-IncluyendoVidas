package com.example.controller;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.models.RecursoLugares;
import com.example.incluyendovidas.R;

import java.util.List;

public class AdapterRecursoLugar extends RecyclerView.Adapter<AdapterRecursoLugar.MyViewHolder> {

    private List<RecursoLugares> dataList;

    public AdapterRecursoLugar(List<RecursoLugares> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<RecursoLugares> lugaresConFiltro) {
        this.dataList.clear();
        this.dataList.addAll(lugaresConFiltro);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemlugares, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecursoLugares data = dataList.get(position);
        holder.titLugar.setText(data.getNombre());
        holder.descripcion.setText(data.getDescripcion());
        holder.direccion.setText(data.getUbicacion());
        holder.contacto.setText(data.getContacto());
        holder.rating.setRating(data.getResenas());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titLugar;
        public TextView descripcion;
        public TextView direccion;
        public TextView horario;
        public TextView contacto;
        public RatingBar rating;
        public MyViewHolder(View view) {
            super(view);
            titLugar = view.findViewById(R.id.txtTituloLugar);
            descripcion = view.findViewById(R.id.txtDescripcionLugar);
            direccion = view.findViewById(R.id.txtDireccionLugar);
            horario = view.findViewById(R.id.txtHorarioLugar);
            contacto = view.findViewById(R.id.txtContactoLugar);
            rating = view.findViewById(R.id.rtgResenaLugar);
        }
    }
}

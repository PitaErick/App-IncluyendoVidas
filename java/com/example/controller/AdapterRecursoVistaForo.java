package com.example.controller;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.incluyendovidas.R;
import com.example.incluyendovidas.VistaTemaForoActivity;
import com.example.models.RecursoRespuestaForo;
import com.example.models.RecursoRespuestaForo;

import java.util.List;

public class AdapterRecursoVistaForo extends RecyclerView.Adapter<AdapterRecursoVistaForo.MyViewHolder> {

    private List<RecursoRespuestaForo> dataList;

    public AdapterRecursoVistaForo(List<RecursoRespuestaForo> dataList) {
        this.dataList = dataList;
    }

    public void updateData(List<RecursoRespuestaForo> lugaresConFiltro) {
        this.dataList.clear();
        this.dataList.addAll(lugaresConFiltro);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemrespuesta, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(dataList!=null){
            RecursoRespuestaForo data = dataList.get(position);
            holder.autorRespuesta.setText(String.valueOf(data.getAutorRespuesta()));
            holder.respuestaForo.setText(data.getDescripcionRespuesta());
        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView autorRespuesta;
        public TextView respuestaForo;

        public MyViewHolder(View view) {
            super(view);
            autorRespuesta = view.findViewById(R.id.txtAutorRespuestaForo);
            respuestaForo = view.findViewById(R.id.txtRespuestaForo);
        }
    }
}

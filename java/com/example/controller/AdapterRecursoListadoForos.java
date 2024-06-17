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
import com.example.models.RecursoTemaForo;

import java.util.List;

public class AdapterRecursoListadoForos extends RecyclerView.Adapter<AdapterRecursoListadoForos.MyViewHolder> {

    private List<RecursoTemaForo> dataList;
    private Context context;

    public AdapterRecursoListadoForos(Context context, List<RecursoTemaForo> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public void updateData(List<RecursoTemaForo> lugaresConFiltro) {
        this.dataList.clear();
        this.dataList.addAll(lugaresConFiltro);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_itemlistadoforos, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RecursoTemaForo data = dataList.get(position);
        holder.IDTemaForo.setText(String.valueOf(data.getIdTemaForo()));
        holder.temaForo.setText(data.getTema());
        holder.tipoDiscapacidadTemaForo.setText(data.getTipoDiscapacidad());
        holder.autorTemaForo.setText(data.getAutor());
        System.out.println(String.valueOf(data.getIdTemaForo()));
        holder.btnForo.setOnClickListener(v -> {
            Intent intent = new Intent(context, VistaTemaForoActivity.class);
            intent.putExtra("tema", data.getIdTemaForo());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView IDTemaForo;
        public TextView temaForo;
        public TextView tipoDiscapacidadTemaForo;
        public TextView autorTemaForo;
        public Button btnForo;

        public MyViewHolder(View view) {
            super(view);
            IDTemaForo = view.findViewById(R.id.txtIDTemaForo);
            temaForo = view.findViewById(R.id.txtListTemaForo);
            tipoDiscapacidadTemaForo = view.findViewById(R.id.txtTipoDiscapacidadTemaForo);
            autorTemaForo = view.findViewById(R.id.txtListadoAutorTemaForo);
            btnForo = view.findViewById(R.id.btnListaTemaForo);
        }
    }
}

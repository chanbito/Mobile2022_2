package com.example.mobile2022_2.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.R;
import com.example.mobile2022_2.Repository.ListaRepository;
import com.example.mobile2022_2.view.ListFragment;

import java.util.Calendar;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "ListAdapter";
    private final Fragment fragment;
    private List<Lista> listaList;
    private ListaRepository repo;


    public ListAdapter(
            List<Lista> listaList, Fragment fragment) {
        this.listaList = listaList;
        this.fragment = fragment;
        repo = ListaRepository.getInstance(fragment.getContext());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutVH = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_lista_vh,
                viewGroup,false);
        return new ListViewHolder(layoutVH);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Lista obj = listaList.get(i);
        if(obj != null){
            viewHolder.itemView.setOnClickListener(new ClickItemListener(obj,fragment));
            Log.e(TAG,obj.getDesc());
            TextView tv1 = viewHolder.itemView.findViewById(R.id.tituloTextView);
            TextView tv2 = viewHolder.itemView.findViewById(R.id.checkedTextView);
            TextView tv3 = viewHolder.itemView.findViewById(R.id.totalTextView);
            tv1.setText(obj.getDesc());
            tv2.setText(""+  repo.GetItensUnchecked(obj.getId()));
            tv3.setText(""+repo.GetItensSize(obj.getId()));
        }else{
            ((TextView)viewHolder.itemView.findViewById(R.id.tituloTextView)).setText("");
            ((TextView)viewHolder.itemView.findViewById(R.id.checkedTextView)).setText("");
            ((TextView)viewHolder.itemView.findViewById(R.id.totalTextView)).setText("");
            ((TextView)viewHolder.itemView.findViewById(R.id.barraTextView)).setText("");
        }

    }

    @Override
    public int getItemCount() {
        return listaList.size();
    }
}
class ListViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public ListViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
}
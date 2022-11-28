package com.example.mobile2022_2.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.R;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = "ItemsAdapter";
    private List<Item> ItemList;

    public ItemsAdapter(List<Item> ItemList) {
        this.ItemList = ItemList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutVH = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_vh,
                        viewGroup,false);
        return new ListViewHolder(layoutVH);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Item obj = ItemList.get(i);
        Log.e(TAG,obj.getMedida());
        TextView tv1 = viewHolder.itemView.findViewById(R.id.QTDtextView);
        TextView tv2 = viewHolder.itemView.findViewById(R.id.UnidadeTextView);
        TextView tv3 = viewHolder.itemView.findViewById(R.id.produtotextView);
        tv1.setText(obj.getquantidade());
        tv2.setText(obj.getMedida());
        tv3.setText(obj.getProduto().getDesc());
        ((CheckBox) viewHolder.itemView.findViewById(R.id.checkBox)).setChecked(obj.getAtivo());
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
}

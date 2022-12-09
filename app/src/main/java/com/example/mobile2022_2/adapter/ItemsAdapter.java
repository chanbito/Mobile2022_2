package com.example.mobile2022_2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.R;
import com.example.mobile2022_2.Repository.ItemRepository;
import com.example.mobile2022_2.view.ItemFragment;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final String TAG = "ItemsAdapter";
    private List<Item> ItemListFilter;
    private final List<Item> ItemList;
    private ItemRepository repo;

    public ItemsAdapter(List<Item> ItemList, Context context) {
        this.ItemListFilter = new ArrayList<>();
        this.ItemListFilter.addAll(ItemList);
        this.ItemList = ItemList;
        repo = new ItemRepository(context);

    }

    public void ItemListUnchecked(List<Item> _ItemList){
        Log.e(TAG,"ItemListUnchecked add uncheck " + _ItemList.size());
        ItemListFilter = _ItemList;
    }

    public void filter(boolean onlyUnchecked){
        Log.e(TAG,"onlyUnchecked " + onlyUnchecked);
        this.ItemListFilter = new ArrayList<>();
        for (Item item:
                ItemList) {
            if(onlyUnchecked){
                if(item != null)
                if(!item.getAtivo()) {
                    Log.e(TAG,"ItemListchecked add uncheck ");
                    Log.e(TAG,"Item " + item.getProduto().getDesc() + " ativo " + item.getAtivo());
                    ItemListFilter.add(item);
                }
            }
            else{
                this.ItemListFilter.clear();
                this.ItemListFilter.addAll(ItemList);
            }
        }
        //atualiza tela
        this.notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutVH = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_item_vh,
                        viewGroup,false);
        return new ItemViewHolder(layoutVH);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        Item obj = ItemListFilter.get(i);
        if(obj == null){
            return;
        }
        TextView tv1 = viewHolder.itemView.findViewById(R.id.QTDtextView);
        TextView tv2 = viewHolder.itemView.findViewById(R.id.UnidadeTextView);
        TextView tv3 = viewHolder.itemView.findViewById(R.id.produtotextView);
        tv1.setText(""+obj.getquantidade());
        tv2.setText(obj.getMedida());
        tv3.setText(""+obj.getProduto().getDesc());
        ((CheckBox) viewHolder.itemView.findViewById(R.id.checkBox_ativo)).setChecked(obj.getAtivo());
        ((CheckBox) viewHolder.itemView.findViewById(R.id.checkBox_ativo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean b = ((CheckBox)view).isChecked();
                obj.setAtivo(b);
                repo.updateItem(obj);
                Log.e(TAG,"setando: " + obj.getProduto().getDesc());
                Log.e(TAG,"b: " + b);
                Log.e(TAG,"setando: " + obj.getAtivo());
                if(ItemFragment.OcultarAtivo && b){
                    ItemListFilter.remove(obj);
                    Log.e(TAG,"ItemList.remove(obj) " + obj.getId_lista());
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return ItemListFilter.size();
    }


}

class ItemViewHolder extends RecyclerView.ViewHolder {
    public View view;
    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }
}

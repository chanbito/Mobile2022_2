package com.example.mobile2022_2.Repository;

import android.content.Context;

import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ItemRepository {
    private final String TAG = "ItemRepository";
    private List<Item> Itens;
    private static ItemRepository instance;
    private Context context;

    public ItemRepository(Context context) {
        super();
        this.context = context;
        Itens = new ArrayList<>();
        ProdutoRepository produtoRepository = ProdutoRepository.getInstance(this.context);
        Itens.add(new Item(1, true,"UN",produtoRepository.getProdutobyid(1),1, 1));
        Itens.add(new Item(2, true,"UN",produtoRepository.getProdutobyid(3),1,1));
        Itens.add(new Item(3, true,"L",produtoRepository.getProdutobyid(1),1, 3));
        Itens.add(new Item(4, true,"MG",produtoRepository.getProdutobyid(2),1, 500));
        Itens.add(new Item(5, false,"KG",produtoRepository.getProdutobyid(5),1, 5));
    }

    public static ItemRepository getInstance(Context contexto) {
        if (instance == null) {
            instance = new ItemRepository(contexto);
        }
        return instance;
    }

    public List<Item> getItens() {
        return Itens;
    }

    public Item getItembyid(int id) {

        for (Item item :
                this.getItens()) {
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }
    public List<Item> getItembyList(int id) {
        List<Item> ret = new ArrayList<>();
        for (Item item :
                this.getItens()) {
            if(item.getId_lista() == id){
                ret.add(item);
            }
        }
        return ret;
    }
}

package com.example.mobile2022_2.Repository;

import android.content.Context;


import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaRepository {
    private final String TAG = "ListaRepository";
    private List<Lista> Listas;
    private static ListaRepository instance;
    private Context context;

    public ListaRepository(Context context) {
        super();
        this.context = context;
        Listas = new ArrayList<>();
        ItemRepository itemRepository = new ItemRepository(context);
        Listas.add(new Lista(2,"zaffari2", Calendar.getInstance().getTime(),true,new ArrayList<Item>()));
        Listas.add(new Lista(1,"zaffari", Calendar.getInstance().getTime(),true,itemRepository.getItembyList(1)));
    }

    public static ListaRepository getInstance(Context cont) {
        if(instance == null)
            instance = new ListaRepository(cont);
        return instance;
    }

    public List<Lista> getListas() {
        //return users;
        return Listas;
    }

    public Lista getlistbyid(int id) {

        for (Lista item :
                this.getListas()) {
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }
}

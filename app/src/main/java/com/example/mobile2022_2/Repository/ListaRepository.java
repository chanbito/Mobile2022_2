package com.example.mobile2022_2.Repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListaRepository {
    private final String TAG = "ListaRepository";
    private ArrayList<Lista> Listas;
    private static ListaRepository instance;
    private Context context;

    public ListaRepository(Context context) {
        super();
        this.context = context;
        if(Listas == null){
            Listas = new ArrayList<>();
            ItemRepository itemRepository = new ItemRepository(context);
            Listas.add(new Lista(2,"zaffari2", Calendar.getInstance().getTime(),true,new ArrayList<Item>()));
            Listas.add(new Lista(1,"zaffari", Calendar.getInstance().getTime(),true,itemRepository.getItembyList(1)));
        }
    }

    public static ListaRepository getInstance(Context cont) {
        if(instance == null)
            instance = new ListaRepository(cont);
        return instance;
    }

    public ArrayList<Lista> getListas() {
        return Listas;
    }

    public List<Lista> getListasAtivas() {
        ArrayList<Lista> l = new ArrayList<>();
        for (Lista item :
                this.getListas()) {
            if(item.getAtivo()){
                l.add(item);
            }
        }
        return l;
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

    public Lista addLista(Lista l) {
        l.setAtivo(true);
        l.setId(Listas.size()+1);
        Listas.add(l);
        return l;
    }

    public void updateLista(Lista l) {
        if(l.getId() < 0){
            this.addLista(l);
        }else{
            ArrayList<Lista> list = this.getListas();
            for (int i = 0; i < list.size()-1; i++) {
                if(list.get(i).getId() == l.getId()){
                    list.set(i,l);
                }
            }
        }
    }
}

package com.example.mobile2022_2.Repository;

import android.content.Context;

import com.example.mobile2022_2.Models.Categoria;
import com.example.mobile2022_2.Models.Lista;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CategoriaRepository {
    private final String TAG = "CategoriaRepository";
    private List<Categoria> categorias;
    private static CategoriaRepository instance;
    private Context context;

    public CategoriaRepository(Context context) {
        super();
        this.context = context;
        categorias = new ArrayList<>();
        categorias.add(new Categoria(1,"Limpeza",true));
        categorias.add(new Categoria(2,"Higiene",true));
        categorias.add(new Categoria(3,"Padaria",true));
        categorias.add(new Categoria(4,"Bebida",true));
        categorias.add(new Categoria(5,"Hortifruti",true));
    }

    public List<Categoria> getCats() {
        //return users;


        return categorias;
    }

    public Categoria getCatsbyid(int id) {

        for (Categoria item :
                this.getCats()) {
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }

    public static CategoriaRepository getInstance(Context contexto) {
        if (instance == null) {
            instance = new CategoriaRepository(contexto);
        }
        return instance;
    }
}

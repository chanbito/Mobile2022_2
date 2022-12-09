package com.example.mobile2022_2.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;


import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.Models.Produto;

import java.lang.reflect.Array;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ListaRepository {
    private final String TAG = "ListaRepository";
    private static ListaRepository instance;
    private SQLiteDatabase database;
    private Context context;
    private final String select = "SELECT id, descricao, criacao, ativo FROM listas ";

    public ListaRepository(Context context) {
        super();
        this.context = context;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
    }

    public static ListaRepository getInstance(Context cont) {
        if(instance == null)
            instance = new ListaRepository(cont);
        return instance;
    }

    public List<Lista> getListasAtivas() {
        ArrayList<Lista> ret = new ArrayList<>();
        String sql = select + " where ativo=?;";
        String[] args = {1+""};
        Cursor cursor = database.rawQuery(sql, args);
        cursor.moveToFirst();
        do {
            ret.add(ListaFromCursor(cursor));
        } while (cursor.moveToNext());

        return ret;
    }

    private Lista ListaFromCursor(Cursor cursor) {
        //a data ainda n ta usada,então azar
        try{
            return new Lista(
                    cursor.getInt(0),
                    cursor.getString(1),
                    Calendar.getInstance().getTime(),
                    cursor.getInt(3) == 1
            );
        }catch(Exception e) {
            return null;
        }
    }

    public Lista getlistbyid(int id) {
        String sql = select + " where id=id;";
        String[] args = {id+""};
        Cursor cursor = database.rawQuery(sql, args);
        cursor.moveToFirst();

        return ListaFromCursor(cursor);
    }

    public Lista addLista(Lista l) {
        String sql = "INSERT INTO listas (descricao, criacao, ativo) VALUES(?, ?, 1);";
        //para usar execSQL os args são um array de Object, não de Strings
        Object[] args = {l.getDesc(), l.getCriacao().toString()};
        database.execSQL(sql, args);
        Cursor cursorid = database.rawQuery("SELECT max(id) from listas",null);
        cursorid.moveToFirst();
        l.setId(cursorid.getInt(0));
        return l;
    }

    public int GetItensUnchecked(int id_lista){
        int ret = 0;
        ItemRepository rep = new ItemRepository(context);
        List<Item> itens = rep.getItembyList(id_lista);
        for (Item it :
                itens) {
            if(it != null)
            if(it.getAtivo())
                ret++;
        }
        return ret;
    }

    public int GetItensSize(int id_lista){
        ItemRepository rep = new ItemRepository(context);
        List<Item> itens = rep.getItembyList(id_lista);
        if(itens.get(0) == null)
            return 0;
        return itens.size();
    }

    public void updateLista(Lista l) {
        if(l.getId() < 0){
            this.addLista(l);
        }else{
            String sql = "UPDATE listas SET descricao=?, ativo=? WHERE id=?;";
            //para usar execSQL os args são um array de Object, não de Strings
            Object[] args = {l.getDesc(), l.getAtivo() ? 1 : 0, l.getId()};
            database.execSQL(sql,args);
        }
    }
}

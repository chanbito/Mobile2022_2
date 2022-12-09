package com.example.mobile2022_2.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mobile2022_2.Models.Item;
import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.Models.Produto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ItemRepository {
    private final String TAG = "ItemRepository";
    private static ItemRepository instance;
    private Context context;
    private SQLiteDatabase database;
    private final String select = "SELECT id, quantidade, ativo, medida, id_produto, id_lista FROM Items ";

    private Item ItemFromCursor(Cursor cursor) {
        ProdutoRepository rep = new ProdutoRepository(context);
        try {
            return new Item(
                    cursor.getInt(0),
                    cursor.getInt(2) == 1,
                    cursor.getString(3),
                    rep.getProdutobyid(cursor.getInt(4)),
                    cursor.getInt(5),
                    cursor.getInt(1));
        } catch (Exception e) {
            return null;
        }
    }

    public void updateItem(Item l) {
            String sql = "UPDATE Items SET quantidade=?, id_lista=?, ativo=?, medida=?, id_produto=? WHERE id=?;";
            //para usar execSQL os args são um array de Object, não de Strings
            Object[] args = {
                    l.getquantidade(),
                    l.getId_lista(),
                    l.getAtivo()? 1 : 0,
                    l.getMedida(),
                    l.getProduto().getId(),
                    l.getId()
            };
            database.execSQL(sql,args);
    }

    public ItemRepository(Context context) {
        super();
        this.context = context;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
    }

    public static ItemRepository getInstance(Context contexto) {
        if (instance == null) {
            instance = new ItemRepository(contexto);
        }
        return instance;
    }

    public Item addItem(String medida, int id_produto, int id_lista, int qtd){
        ProdutoRepository produtoRepository = ProdutoRepository.getInstance(this.context);

        String sql = "INSERT INTO Items (quantidade, id_lista, ativo, medida, id_produto) VALUES(?, ?, ?, ?, ?);";
        //para usar execSQL os args são um array de Object, não de Strings
        String[] args = {qtd+"", id_lista+"",0+"",medida,id_produto+""};
        database.execSQL(sql, args);
        Cursor cursorid = database.rawQuery("SELECT max(id) from Items",null);
        cursorid.moveToFirst();
        Item i = new Item(cursorid.getInt(0), false, medida,
                produtoRepository.getProdutobyid(id_produto),id_lista,qtd);

        Log.e(TAG,"add Liastas: " + i.getId());
        Log.e(TAG,"add Liastas lista: " + i.getId_lista());

        return i;
    }

    public Item getItembyid(int id) {
        String sql = select + " where id=?;";
        String[] args = {id+""};
        Cursor cursor = database.rawQuery(sql, args);
        cursor.moveToFirst();
        return ItemFromCursor(cursor);
    }

    public List<Item> getItembyList(int id) {
        ArrayList<Item> ret = new ArrayList<>();
        String sql = select + " where id_lista=?;";
        String[] args = {id+""};
        Cursor cursor = database.rawQuery(sql, args);
        cursor.moveToFirst();
        do {
            ret.add(ItemFromCursor(cursor));
        } while (cursor.moveToNext());
        return ret;
    }
}

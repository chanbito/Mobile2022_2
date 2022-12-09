package com.example.mobile2022_2.Repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.mobile2022_2.Models.Produto;

import java.util.ArrayList;
import java.util.List;

public class ProdutoRepository {
    private final String TAG = "ProdutoRepository";
    private static ProdutoRepository instance;
    private Context context;
    private SQLiteDatabase database;
    private final String select = "SELECT id, descricao, ativo, ult_valor FROM produtos ";

    public ProdutoRepository(Context context) {
        super();
        this.context = context;
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
    }

    public static ProdutoRepository getInstance(Context cont) {
        if(instance == null)
            instance = new ProdutoRepository(cont);
        return instance;
    }

    public List<Produto> getProdutos() {
        ArrayList<Produto> ret = new ArrayList<>();
        String sql = select + " where ativo=?;";
        String[] args = {1+""};
        Cursor cursor = database.rawQuery(sql, args);
        cursor.moveToFirst();
        do {
            ret.add(ProdutoFromCursor(cursor));
        } while (cursor.moveToNext());
        return ret;
    }

    public Produto getProdutobyid(int id) {
        for (Produto item :
                this.getProdutos()) {
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }

    private Produto ProdutoFromCursor(Cursor cursor) {
        try {
            return new Produto(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getDouble(2),
                    cursor.getInt(3) == 1);
        } catch (Exception e) {
            return null;
        }
    }

    public Produto CreateProduto(String s){
        Log.e(TAG,"salvei produto s: " + s);
        String sql = "INSERT INTO produtos (descricao) VALUES(?);";
        //para usar execSQL os args são um array de Object, não de Strings
        String[] args = {s};
        database.execSQL(sql, args);
        Cursor cursorid = database.rawQuery("SELECT max(id) from produtos",null);
        cursorid.moveToFirst();
        Log.e(TAG,"salvei produto id: " + cursorid.getInt(0));
        return new Produto(cursorid.getInt(0), s, 0,true);
    }
}

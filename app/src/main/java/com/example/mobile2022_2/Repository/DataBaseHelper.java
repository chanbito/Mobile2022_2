package com.example.mobile2022_2.Repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mmqmBD";
    private static final Integer DB_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String list = "CREATE TABLE listas (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " descricao TEXT,criacao TEXT,ativo TEXT DEFAULT (1));";
        sqLiteDatabase.execSQL(list);

        String prod = "CREATE TABLE produtos (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descricao TEXT,ativo INTEGER DEFAULT (1),ult_valor REAL DEFAULT (0.0));\n";
        sqLiteDatabase.execSQL(prod);

        String item = "CREATE TABLE Items (\n" +
                "\t id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t quantidade INTEGER,\n" +
                "\t id_lista INTEGER,\n" +
                "\t ativo INTEGER DEFAULT (1),\n" +
                "\t medida TEXT,\n" +
                "\t id_produto INTEGER,\n" +
                "\t CONSTRAINT Items_FK FOREIGN KEY (id_lista) REFERENCES listas(id) ON DELETE CASCADE,\n" +
                "\t CONSTRAINT Items_FK_1 FOREIGN KEY (id_produto) REFERENCES produtos(id) ON DELETE CASCADE\n" +
                ");";
        sqLiteDatabase.execSQL(item);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        for (int i = 0; i < oldVersion-newVersion; i++) {
            switch (i){
                default:
                    break;
            }

        }


    }
}

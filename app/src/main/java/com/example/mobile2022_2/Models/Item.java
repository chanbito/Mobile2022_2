package com.example.mobile2022_2.Models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Item implements Parcelable {
    private int quantidade;
    private int id;
    private int id_lista;
    private boolean ativo;
    private String medida;
    private Produto produto;

    public Item(int id, boolean ativo, String medida, Produto produto,int id_lista, int qtd) {
        this.id = id;
        this.id_lista = id_lista;
        this.ativo = ativo;
        this.medida = medida;
        this.produto = produto;
        this.quantidade = qtd;
    }

    protected Item(Parcel in) {
        id = in.readInt();
        id_lista = in.readInt();
        ativo = in.readByte() != 0;
        medida = in.readString();
        quantidade = in.readInt();
        produto = in.readParcelable(Produto.class.getClassLoader());
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getMedida() {
        return medida;
    }

    public void setMedida(String medida) {
        this.medida = medida;
    }

    public void setAtivo(boolean ativo) {
        Log.e("item","setAtivo " + ativo);
        this.ativo = ativo;
    }

    public boolean getAtivo() {return this.ativo;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getquantidade() {
        return quantidade;
    }

    public void setquantidade(int qtd) {
        this.quantidade = qtd;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(id_lista);
        parcel.writeByte((byte) (ativo ? 1 : 0));
        parcel.writeString(medida);
        parcel.writeInt(quantidade);
        parcel.writeParcelable(produto, i);
    }

    public int getId_lista() {
        return id_lista;
    }

    public void setId_lista(int id_lista) {
        this.id_lista = id_lista;
    }
}

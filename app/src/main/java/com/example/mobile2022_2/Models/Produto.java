package com.example.mobile2022_2.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Produto implements Parcelable {
    private int id;
    private String desc;
    private double valor_ult;
    private boolean ativo;
    private Categoria categoria;


    public Produto(int id, String desc, double valor_ult, boolean ativo, Categoria categoria) {
        this.id = id;
        this.desc = desc;
        this.valor_ult = valor_ult;
        this.ativo = ativo;
        this.categoria = categoria;
    }

    protected Produto(Parcel in) {
        id = in.readInt();
        desc = in.readString();
        valor_ult = in.readDouble();
        ativo = in.readByte() != 0;
        categoria = in.readParcelable(Categoria.class.getClassLoader());
    }

    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    public double getValor_ult() {
        return valor_ult;
    }

    public void setValor_ult(float valor_ult) {
        this.valor_ult = valor_ult;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(desc);
        parcel.writeDouble(valor_ult);
        parcel.writeByte((byte) (ativo ? 1 : 0));
        parcel.writeParcelable(categoria, i);
    }
}

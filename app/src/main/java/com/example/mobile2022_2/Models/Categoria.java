package com.example.mobile2022_2.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Categoria implements Parcelable {
    private int id;
    private String descricao;
    private boolean ativo;

    public Categoria(int id, String descricao, boolean ativo) {
        this.id = id;
        this.descricao = descricao;
        this.ativo = ativo;
    }

    protected Categoria(Parcel in) {
        id = in.readInt();
        descricao = in.readString();
        ativo = in.readByte() != 0;
    }

    public static final Creator<Categoria> CREATOR = new Creator<Categoria>() {
        @Override
        public Categoria createFromParcel(Parcel in) {
            return new Categoria(in);
        }

        @Override
        public Categoria[] newArray(int size) {
            return new Categoria[size];
        }
    };

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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
        parcel.writeString(descricao);
        parcel.writeByte((byte) (ativo ? 1 : 0));
    }
}

package com.example.mobile2022_2.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

public class Lista implements Parcelable {
    private int id;
    private String desc;
    private Date criacao;
    private boolean ativo;
    private List<Item> items;

    public Lista(int id, String desc, Date criacao, boolean ativo, List<Item> items) {
        this.id = id;
        this.desc = desc;
        this.criacao = criacao;
        this.ativo = ativo;
        this.items = items;
    }

    protected Lista(Parcel in) {
        id = in.readInt();
        desc = in.readString();
        ativo = in.readByte() != 0;
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<Lista> CREATOR = new Creator<Lista>() {
        @Override
        public Lista createFromParcel(Parcel in) {
            return new Lista(in);
        }

        @Override
        public Lista[] newArray(int size) {
            return new Lista[size];
        }
    };

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public Date getCriacao() {
        return criacao;
    }

    public void setCriacao(Date criacao) {
        this.criacao = criacao;
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
        parcel.writeByte((byte) (ativo ? 1 : 0));
        parcel.writeTypedList(items);
    }
}

package com.example.mobile2022_2.Repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.example.mobile2022_2.Models.Lista;
import com.example.mobile2022_2.Models.Produto;
import com.example.mobile2022_2.Models.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UserRepository {
    private final String TAG = "UserRepository";
    private List<User> users;
    private static UserRepository instance;
    private Context context;

    public UserRepository(Context context) {
        super();
        this.context = context;
        users = new ArrayList<User>();
        users.add(new User(1,"gab","gab@test","1"));
        users.add(new User(0,"gab2","gab2@test","2"));
    }

    public static UserRepository getInstance(Context cont) {
        if(instance == null)
            instance = new UserRepository(cont);
        return instance;
    }

    public List<User> getUsers() {
        //return users;

        return users;
    }

    public User getUserbyid(int id) {

        for (User item :
                this.getUsers()) {
            if(item.getId() == id){
                return item;
            }
        }
        return null;
    }
}

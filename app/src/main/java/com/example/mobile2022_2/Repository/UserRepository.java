package com.example.mobile2022_2.Repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    public List<User> getUsers() {
        //return users;
        List<User> users = new ArrayList<User>();
        users.add(new User(1,"gab","gab@test","1"));
        users.add(new User(0,"gab2","gab2@test","2"));
        return users;
    }
}
